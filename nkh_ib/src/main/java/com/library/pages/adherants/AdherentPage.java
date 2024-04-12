package com.library.pages.adherants;

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

public class AdherentPage extends BorderPane {

    private static TableView<Adherent> tableView;
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

    public AdherentPage() {
        initializeUI();
        // Fetch and display data from the database
        fetchData();
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {

        // Initialize TableView for displaying adherents
        tableView = new TableView<>();
        TableColumn<Adherent, Integer> adhNumColumn = new TableColumn<>("ID");
        adhNumColumn.setCellValueFactory(data -> data.getValue().adhNumProperty().asObject());

        TableColumn<Adherent, String> nomColumn = new TableColumn<>("Last Name");
        nomColumn.setCellValueFactory(data -> data.getValue().nomProperty());

        TableColumn<Adherent, String> prenomColumn = new TableColumn<>("First Name");
        prenomColumn.setCellValueFactory(data -> data.getValue().prenomProperty());

        TableColumn<Adherent, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Adherent, String> adresseColumn = new TableColumn<>("Address");
        adresseColumn.setCellValueFactory(data -> data.getValue().adresseProperty());

        tableView.getColumns().addAll(adhNumColumn, nomColumn, prenomColumn, emailColumn, adresseColumn);

        // Initialize buttons for add, modify, and delete actions
        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        // Add event handlers for button actions
        // Add Adherent button
        addButton.setOnAction(event -> AddAdherent.display(this));
        // Modify Adherent button
        modifyButton.setOnAction(event -> {
            // Get the selected adherent
            Adherent selectedAdherent = tableView.getSelectionModel().getSelectedItem();
            if (selectedAdherent == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Adherent Selected", "Please select an adherent to modify.");
                return;
            }
            // Call the display method of ModifyAdherent class
            ModifyAdherent.display(selectedAdherent);
        });
        // Delete Adherent button
        deleteButton.setOnAction(event -> {
            // Get the selected adherent
            Adherent selectedAdherent = tableView.getSelectionModel().getSelectedItem();
            if (selectedAdherent == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Adherent Selected", "Please select an adherent to delete.");
                return;
            }
            // Call the delete method of DeleteAdherent class
            DeleteAdherent.delete(selectedAdherent);
        });

        // Arrange UI elements using VBox
        VBox vbox = new VBox(tableView, addButton, modifyButton, deleteButton);
        vbox.setSpacing(10);

        // Set layout for AdherentPage
        this.setCenter(vbox);
    }

    public static void fetchData() {
        // Fetch data from the database and populate the TableView
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM adherent")) {

            ObservableList<Adherent> adherents = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int adhNum = resultSet.getInt("Adh_num");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String adresse = resultSet.getString("adresse");

                Adherent adherent = new Adherent(adhNum, nom, prenom, email, adresse);
                adherents.add(adherent);
            }

            tableView.setItems(adherents);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    public Parent getView() {
        return view;
    }
}
