package com.library.pages.auteur;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.DBConnection;

public class DeleteAuthor {

    public static void delete(Auteur author) {
        // Show confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Delete Author");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to delete this author?");
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the author from the database
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(
                             "DELETE FROM auteur WHERE aut_num = ?")) {
                    statement.setInt(1, author.getAutNum());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Author Deleted", "The author has been successfully deleted.");
                        // Refresh the table
                        AuteurPage.fetchData();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to Delete Author", "Failed to delete the author. Please try again.");
                    }
                } catch (SQLException e) {
                    System.err.println("Database error:");
                    System.err.println("Error Message: " + e.getMessage());
                    System.err.println("SQL State: " + e.getSQLState());
                    System.err.println("Error Code: " + e.getErrorCode());
                    e.printStackTrace(); // Print the stack trace
                }
            }
        });
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
