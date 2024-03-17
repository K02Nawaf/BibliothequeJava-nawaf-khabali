package com.library.pages.auteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.DBConnection;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyAuthor {

    public static void display(Auteur author) {
        // Create a new stage for the modify author dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Modify Author");

        // Create UI components for the modify author form
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(author.getNom());

        Label surnameLabel = new Label("Surname:");
        TextField surnameField = new TextField(author.getPrenom());

        Label birthDateLabel = new Label("Date of Birth:");
        TextField birthDateField = new TextField(author.getDateNaissance());

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField(author.getDescription());

        // Create the "Modify" button
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> {
            // Extract modified author details from the text fields
            String name = nameField.getText();
            String surname = surnameField.getText();
            String birthDate = birthDateField.getText();
            String description = descriptionField.getText();

            // Validate modified author details
            if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            // Update author in the database
            try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "UPDATE auteur SET nom = ?, prenom = ?, date_naissance = ?, description = ? WHERE aut_num = ?")) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, birthDate);
                statement.setString(4, description);
                statement.setInt(5, author.getAutNum());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Author Modified", "The author has been successfully modified.");
                    dialogStage.close();
                    // Refresh data in the TableView after successful addition
                    AuteurPage.fetchData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Modify Author", "Failed to modify the author. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error:");
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace(); // Print the stack trace
            }
        });

        // Create layout for the modify author dialog
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);
        gridPane.add(birthDateLabel, 0, 2);
        gridPane.add(birthDateField, 1, 2);
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionField, 1, 3);
        gridPane.add(modifyButton, 0, 4, 2, 1);

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        dialogStage.setScene(scene);

        // Display the dialog
        dialogStage.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
