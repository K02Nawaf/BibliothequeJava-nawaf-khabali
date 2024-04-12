package com.library.pages.livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.DBConnection;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeleteLivre {
    
    public static void delete(Livre livre) {
        // Retrieve the ISBN of the livre to be deleted
        int isbn = livre.getIsbn();
        
        // Show confirmation dialog before deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to delete this livre?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Execute SQL DELETE statement to remove the livre from the database
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement("DELETE FROM livre WHERE ISBN = ?")) {
                    statement.setInt(1, isbn);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        // Show success message
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Livre Deleted", "The livre has been successfully deleted.");
                        // Refresh data in the TableView after successful deletion
                        LivrePage.fetchData();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to Delete Livre", "Failed to delete the livre. Please try again.");
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
