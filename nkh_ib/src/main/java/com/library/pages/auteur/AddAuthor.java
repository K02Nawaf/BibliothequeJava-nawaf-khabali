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

public class AddAuthor {

    public static void display(AuteurPage auteurPage) {
        // Create a new stage for the add author dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Add Author");

        // Create UI components for the add author form
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label surnameLabel = new Label("Surname:");
        TextField surnameField = new TextField();

        Label birthDateLabel = new Label("Date of Birth:");
        TextField birthDateField = new TextField();

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        // Create the "Add" button
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            // Extract author details from the text fields
            String name = nameField.getText();
            String surname = surnameField.getText();
            String birthDate = birthDateField.getText();
            String description = descriptionField.getText();

            // Validate author details
            if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            // Add author to the database
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO auteur (nom, prenom, date_naissance, description) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, birthDate);
                statement.setString(4, description);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Author Added", "The author has been successfully added.");
                    dialogStage.close();
                    // Refresh data in the TableView after successful addition
                    AuteurPage.fetchData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Add Author", "Failed to add the author. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error:");
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace(); // Print the stack trace
            }
        });

        // Create layout for the add author dialog
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
        gridPane.add(addButton, 0, 4, 2, 1);

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
