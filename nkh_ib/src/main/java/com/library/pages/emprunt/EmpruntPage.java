package com.library.pages.emprunt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.library.DBConnection;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EmpruntPage extends BorderPane {

    private static TableView<Emprunt> tableView;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private Parent view;

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public EmpruntPage() {
        initializeUI();
        // Fetch and display data from the database
        fetchData();
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {

        // Initialize TableView for displaying loans
        tableView = new TableView<>();

        TableColumn<Emprunt, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idEmpruntProperty().asObject());

        TableColumn<Emprunt, String> dateEmpruntColumn = new TableColumn<>("Date Emprunt");
        dateEmpruntColumn.setCellValueFactory(data -> data.getValue().dateEmpruntProperty());

        TableColumn<Emprunt, String> dateRetourColumn = new TableColumn<>("Date Retour");
        dateRetourColumn.setCellValueFactory(data -> data.getValue().dateRetourProperty());

        TableColumn<Emprunt, String> statutEmpruntColumn = new TableColumn<>("est-il prêté?");
        statutEmpruntColumn.setCellValueFactory(data -> {
            String value = (data.getValue().getStatutEmprunt() == 1) ? "Oui" : "Non";
            return new SimpleStringProperty(value);
        });

        TableColumn<Emprunt, Integer> nmbEmpruntColumn = new TableColumn<>("Copies");
        nmbEmpruntColumn.setCellValueFactory(data -> data.getValue().nmbEmpruntProperty().asObject());

        TableColumn<Emprunt, Integer> adhNumColumn = new TableColumn<>("ID_adh");
        adhNumColumn.setCellValueFactory(data -> data.getValue().AdhNumProperty().asObject());

        TableColumn<Emprunt, Integer> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(data -> data.getValue().ISBNProperty().asObject());

        TableColumn<Emprunt, String> bookTitle = new TableColumn<>("Livre");
        bookTitle.setCellValueFactory(data -> data.getValue().bookTitleProperty());

        TableColumn<Emprunt, String> adherentName = new TableColumn<>("Adherent");
        adherentName.setCellValueFactory(data -> data.getValue().adherentNameProperty());

        tableView.getColumns().addAll(idColumn, dateEmpruntColumn, dateRetourColumn, statutEmpruntColumn, adherentName, bookTitle);
        // Initialize buttons for add, modify, and delete actions
        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        // Add event handlers for button actions
        // Add Loan button
        addButton.setOnAction(event -> AddEmprunt.display());
        // Modify Loan button
        modifyButton.setOnAction(event -> {
            // Get the selected loan
            Emprunt selectedEmprunt = tableView.getSelectionModel().getSelectedItem();
            if (selectedEmprunt == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Loan Selected", "Please select a loan to modify.");
                return;
            }
        
            // Call the display method of ModifyEmprunt class
            ModifyEmprunt.display(selectedEmprunt.getISBN());
        });
        
        // Delete Loan button
        deleteButton.setOnAction(event -> {
            // Get the selected loan
            Emprunt selectedEmprunt = tableView.getSelectionModel().getSelectedItem();
            if (selectedEmprunt == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Loan Selected", "Please select a loan to delete.");
                return;
            }
            // Call the delete method of DeleteEmprunt class
            DeleteEmprunt.delete(selectedEmprunt);
        });

        // Arrange UI elements using VBox
        VBox vbox = new VBox(tableView, addButton, modifyButton, deleteButton);
        vbox.setSpacing(10);

        // Set layout for EmpruntPage
        this.setCenter(vbox);
    }

    public static void fetchData() {
        // Fetch data from the database and populate the TableView

        try (Connection connection = DBConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT e.id_emprunt, e.date_emprunt, e.date_retour, e.statut_emprunt, e.nmb_emprunt, e.Adh_num, e.ISBN, l.titre, a.nom, a.prenom FROM emprunt e INNER JOIN livre l ON e.ISBN = l.ISBN INNER JOIN adherent a ON e.Adh_num = a.Adh_num")) {

            ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int idEmprunt = resultSet.getInt("id_emprunt");
                String dateEmprunt = resultSet.getString("date_emprunt");
                String dateRetour = resultSet.getString("date_retour");
                int statutEmprunt = resultSet.getInt("statut_emprunt");
                int nmbEmprunt = resultSet.getInt("nmb_emprunt");
                int isbn = resultSet.getInt("ISBN");
                int adhNum = resultSet.getInt("Adh_num");
                String titre = resultSet.getString("titre");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adherentName = adhNum + " | " + nom + " " + prenom;
                String bookTitle = isbn + " | " + titre;

                Emprunt emprunt = new Emprunt(idEmprunt, dateEmprunt, dateRetour, statutEmprunt, nmbEmprunt, adhNum, isbn, bookTitle, adherentName);
                emprunts.add(emprunt);
            }

            tableView.setItems(emprunts);

        } catch (SQLException e) {
            // Handle database errors

        }
    }

    public Parent getView() {
        return view;
    }
}
