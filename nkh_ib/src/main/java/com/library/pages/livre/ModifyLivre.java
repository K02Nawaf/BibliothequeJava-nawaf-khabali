package com.library.pages.livre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.DBConnection;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyLivre {

    public static void display(Livre livre) {
        // Create a new stage for the modify livre dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Modify Livre");

        // Create UI components for the modify livre form
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField(livre.getTitre());

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField(String.valueOf(livre.getPrix()));

        Label genreLabel = new Label("Genre:");
        TextField genreField = new TextField(livre.getGenre());

        Label authorLabel = new Label("Author:");
        ComboBox<String> authorComboBox = new ComboBox<>();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField(String.valueOf(livre.getNmbLivre()));

        // Fetch authors from the database and populate the ComboBox
        fetchAuthors(authorComboBox);

        // Set the selected author in the ComboBox
        authorComboBox.setValue(livre.getAuteur());

        // Create the "Modify" button
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> {
            // Extract modified livre details from the text fields
            String title = titleField.getText();
            int price = Integer.parseInt(priceField.getText());
            String genre = genreField.getText();
            String selectedAuthor = authorComboBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText());

            // Validate modified livre details
            if (title.isEmpty() || genre.isEmpty() || selectedAuthor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            // Update livre in the database
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE livre SET titre = ?, prix = ?, genre = ?, Aut_num = ?, nmb_livre = ? WHERE ISBN = ?")) {
                // Find the author ID from the selected author's name
                int authorId = getAuthorId(selectedAuthor);
                
                statement.setString(1, title);
                statement.setInt(2, price);
                statement.setString(3, genre);
                statement.setInt(4, authorId);
                statement.setInt(5, quantity);
                statement.setInt(6, livre.getIsbn());
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Livre Modified", "The livre has been successfully modified.");
                    dialogStage.close();
                    // Refresh data in the TableView after successful modification
                    LivrePage.fetchData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Modify Livre", "Failed to modify the livre. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error:");
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace(); // Print the stack trace
            }
        });

        // Create layout for the modify livre dialog
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceField, 1, 1);
        gridPane.add(genreLabel, 0, 2);
        gridPane.add(genreField, 1, 2);
        gridPane.add(authorLabel, 0, 3);
        gridPane.add(authorComboBox, 1, 3);
        gridPane.add(quantityLabel, 0, 4);
        gridPane.add(quantityField, 1, 4);
        gridPane.add(modifyButton, 0, 5, 2, 1);

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        dialogStage.setScene(scene);

        // Display the dialog
        dialogStage.showAndWait();
    }

    private static void fetchAuthors(ComboBox<String> comboBox) {
        // Fetch authors from the database and populate the ComboBox
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT nom, prenom FROM auteur");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                comboBox.getItems().add(prenom + " " + nom);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    private static int getAuthorId(String selectedAuthor) {
        // Get the author ID from the selected author's name
        int authorId = -1;
        String[] parts = selectedAuthor.split(" ");
        String nom = parts[1]; // Index 1 corresponds to nom after reversing
        String prenom = parts[0]; // Index 0 corresponds to prenom after reversing

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT Aut_num FROM auteur WHERE nom = ? AND prenom = ?");
        ) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                authorId = resultSet.getInt("Aut_num");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }

        return authorId;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
