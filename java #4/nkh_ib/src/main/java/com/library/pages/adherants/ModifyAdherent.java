package com.library.pages.adherants;

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

public class ModifyAdherent {

    public static void display(Adherent adherent) {
        // Create a new stage for the modify adherent dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Modify Adherent");

        // Create UI components for the modify adherent form
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(adherent.getNom());

        Label surnameLabel = new Label("Surname:");
        TextField surnameField = new TextField(adherent.getPrenom());

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(adherent.getEmail());

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField(adherent.getAdresse());

        // Create the "Modify" button
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> {
            // Extract modified adherent details from the text fields
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();

            // Validate modified adherent details
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || address.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            // Update adherent in the database
            try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "UPDATE adherent SET nom = ?, prenom = ?, email = ?, adresse = ? WHERE Adh_num = ?")) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, email);
                statement.setString(4, address);
                statement.setInt(5, adherent.getAdhNum());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Adherent Modified", "The adherent has been successfully modified.");
                    dialogStage.close();
                    // Refresh data in the TableView after successful modification
                    AdherentPage.fetchData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Modify Adherent", "Failed to modify the adherent. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error:");
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace(); // Print the stack trace
            }
        });

        // Create layout for the modify adherent dialog
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(addressLabel, 0, 3);
        gridPane.add(addressField, 1, 3);
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
