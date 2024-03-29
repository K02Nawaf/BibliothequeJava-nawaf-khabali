package com.library.pages.auteur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.library.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AuteurPage extends BorderPane {

    private static TableView<Auteur> tableView;
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

    public AuteurPage() {
        initializeUI();
        // Fetch and display data from the database
        fetchData();
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {

        // Initialize TableView for displaying authors
        tableView = new TableView<>();
        TableColumn<Auteur, Integer> autNumColumn = new TableColumn<>("ID");
        autNumColumn.setCellValueFactory(data -> data.getValue().autNumProperty().asObject());

        TableColumn<Auteur, String> nomColumn = new TableColumn<>("Last Name");
        nomColumn.setCellValueFactory(data -> data.getValue().nomProperty());

        TableColumn<Auteur, String> prenomColumn = new TableColumn<>("First Name");
        prenomColumn.setCellValueFactory(data -> data.getValue().prenomProperty());

        TableColumn<Auteur, String> dateNaissanceColumn = new TableColumn<>("Date of Birth");
        dateNaissanceColumn.setCellValueFactory(data -> data.getValue().dateNaissanceProperty());

        TableColumn<Auteur, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        tableView.getColumns().addAll(autNumColumn, nomColumn, prenomColumn, dateNaissanceColumn, descriptionColumn);

        // Initialize buttons for add, modify, and delete actions
        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        // Add event handlers for button actions
        // Add Author button
        addButton.setOnAction(event -> AddAuthor.display(this));
        // Modify Author button
        modifyButton.setOnAction(event -> {
            // Get the selected author
            Auteur selectedAuthor = tableView.getSelectionModel().getSelectedItem();
            if (selectedAuthor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Author Selected", "Please select an author to modify.");
                return;
            }

            // Call the display method of ModifyAuthor class
            ModifyAuthor.display(selectedAuthor);
        });
        // Delete Author button
        deleteButton.setOnAction(event -> {
            // Get the selected author
            Auteur selectedAuthor = tableView.getSelectionModel().getSelectedItem();
            if (selectedAuthor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Author Selected", "Please select an author to delete.");
                return;
            }
            // Call the delete method of DeleteAuthor class
            DeleteAuthor.delete(selectedAuthor);
        });

        // Arrange UI elements using VBox
        VBox vbox = new VBox(tableView, addButton, modifyButton, deleteButton);
        vbox.setSpacing(10);

        // Set layout for AuteurPage
        this.setCenter(vbox);
    }

    public static void fetchData() {
        // Fetch data from the database and populate the TableView

        try (Connection connection = DBConnection.getConnection(); 
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery("SELECT * FROM auteur")) {

            ObservableList<Auteur> auteurs = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int autNum = resultSet.getInt("aut_num");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String dateNaissance = resultSet.getString("date_naissance");
                String description = resultSet.getString("description");

                Auteur auteur = new Auteur(autNum, nom, prenom, dateNaissance, description);
                auteurs.add(auteur);
            }

            tableView.setItems(auteurs);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    public Parent getView() {
        return view;
    }
}
