package com.library.pages.emprunt;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyEmprunt {

    public static void display(int empruntId) {
        // Create a new stage for the modify loan dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Modify Loan");

        // Create UI components for the modify loan form
        Label dateEmpruntLabel = new Label("Date Emprunt:");
        DatePicker dateEmpruntPicker = new DatePicker();

        Label dateRetourLabel = new Label("Date Retour:");
        DatePicker dateRetourPicker = new DatePicker();

        Label statutEmpruntLabel = new Label("Est-il prêté:");
        ComboBox<String> statutEmpruntComboBox = new ComboBox<>();
        statutEmpruntComboBox.getItems().addAll("Yes", "No");

        Label adherantLabel = new Label("Adherant:");
        ComboBox<String> adherantComboBox = new ComboBox<>();
        fetchAdherants(adherantComboBox);

        Label livreLabel = new Label("Livre:");
        ComboBox<String> livreComboBox = new ComboBox<>();
        fetchLivres(livreComboBox);

        // Fetch loan details for the selected empruntId
        fetchLoanDetails(empruntId, dateEmpruntPicker, dateRetourPicker, statutEmpruntComboBox, adherantComboBox, livreComboBox);

        // Create the "Modify" button
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> {
            // Extract modified loan details from the form
            String dateEmprunt = dateEmpruntPicker.getValue().toString();
            String dateRetour = dateRetourPicker.getValue().toString();
            int statutEmprunt = (statutEmpruntComboBox.getValue().equals("Yes")) ? 1 : 0;
            String selectedAdherant = adherantComboBox.getValue();
            String selectedLivre = livreComboBox.getValue();

            // Validate loan details
            if (dateEmprunt.isEmpty() || dateRetour.isEmpty() || selectedAdherant == null || selectedLivre == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
                return;
            }

            // Find the adherant number from the selected adherant's name
            int adhNum = getAdherantNumber(selectedAdherant);

            // Find the ISBN from the selected book title
            int isbn = getBookISBN(selectedLivre);

            // Update loan in the database
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE emprunt SET date_emprunt = ?, date_retour = ?, statut_emprunt = ?, Adh_num = ?, ISBN = ? WHERE id_emprunt = ?")) {
                statement.setString(1, dateEmprunt);
                statement.setString(2, dateRetour);
                statement.setInt(3, statutEmprunt);
                statement.setInt(4, adhNum);
                statement.setInt(5, isbn);
                statement.setInt(6, empruntId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Loan Modified", "The loan has been successfully modified.");
                    dialogStage.close();
                    // Refresh data in the TableView after modification
                    EmpruntPage.fetchData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Modify Loan", "Failed to modify the loan. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error:");
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace(); // Print the stack trace
            }
        });

        // Create layout for the modify loan dialog
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);

        gridPane.add(dateEmpruntLabel, 0, 0);
        gridPane.add(dateEmpruntPicker, 1, 0);
        gridPane.add(dateRetourLabel, 0, 1);
        gridPane.add(dateRetourPicker, 1, 1);
        gridPane.add(statutEmpruntLabel, 0, 2);
        gridPane.add(statutEmpruntComboBox, 1, 2);
        gridPane.add(adherantLabel, 0, 3);
        gridPane.add(adherantComboBox, 1, 3);
        gridPane.add(livreLabel, 0, 4);
        gridPane.add(livreComboBox, 1, 4);
        gridPane.add(modifyButton, 0, 5, 2, 1);

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        dialogStage.setScene(scene);

        // Display the dialog
        dialogStage.showAndWait();
    }

    private static void fetchAdherants(ComboBox<String> comboBox) {
        // Fetch adherants from the database and populate the ComboBox
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT CONCAT(prenom, ' ', nom) AS adherant_name FROM adherent");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String adherantName = resultSet.getString("adherant_name");
                comboBox.getItems().add(adherantName);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    private static void fetchLivres(ComboBox<String> comboBox) {
        // Fetch books from the database and populate the ComboBox
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT titre FROM livre");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                comboBox.getItems().add(titre);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    private static void fetchLoanDetails(int empruntId, DatePicker dateEmpruntPicker, DatePicker dateRetourPicker, ComboBox<String> statutEmpruntComboBox, ComboBox<String> adherantComboBox, ComboBox<String> livreComboBox) {
        // Fetch loan details for the given empruntId
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT date_emprunt, date_retour, statut_emprunt, CONCAT(a.prenom, ' ', a.nom) AS adherant_name, l.titre FROM emprunt e JOIN adherent a ON e.Adh_num = a.Adh_num JOIN livre l ON e.ISBN = l.ISBN WHERE id_emprunt = ?");
        ) {
            statement.setInt(1, empruntId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dateEmpruntPicker.setValue(resultSet.getDate("date_emprunt").toLocalDate());
                dateRetourPicker.setValue(resultSet.getDate("date_retour").toLocalDate());
                statutEmpruntComboBox.setValue(resultSet.getInt("statut_emprunt") == 1 ? "Yes" : "No");
                adherantComboBox.setValue(resultSet.getString("adherant_name"));
                livreComboBox.setValue(resultSet.getString("titre"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    private static int getAdherantNumber(String selectedAdherant) {
        // Get the adherant number from the selected adherant's name
        int adhNum = -1;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT Adh_num FROM adherent WHERE CONCAT(prenom, ' ', nom) = ?");
        ) {
            statement.setString(1, selectedAdherant);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                adhNum = resultSet.getInt("Adh_num");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
        return adhNum;
    }

    private static int getBookISBN(String selectedLivre) {
        // Get the ISBN from the selected book's title
        int isbn = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT ISBN FROM livre WHERE titre = ?");
        ) {
            statement.setString(1, selectedLivre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isbn = resultSet.getInt("ISBN");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }

        return isbn;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
