package com.library.pages.livre;

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

public class LivrePage extends BorderPane {

    private static TableView<Livre> tableView;
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

    public LivrePage() {
        initializeUI();
        // Fetch and display data from the database
        fetchData();
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {

        // Initialize TableView for displaying books
        tableView = new TableView<>();
        TableColumn<Livre, Integer> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(data -> data.getValue().isbnProperty().asObject());

        TableColumn<Livre, String> titreColumn = new TableColumn<>("Title");
        titreColumn.setCellValueFactory(data -> data.getValue().titreProperty());

        TableColumn<Livre, Integer> prixColumn = new TableColumn<>("Price");
        prixColumn.setCellValueFactory(data -> data.getValue().prixProperty().asObject());

        TableColumn<Livre, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(data -> data.getValue().genreProperty());

        TableColumn<Livre, String> auteurColumn = new TableColumn<>("Author");
        auteurColumn.setCellValueFactory(data -> data.getValue().auteurProperty());

        TableColumn<Livre, Integer> nmbLivreColumn = new TableColumn<>("Number of Copies");
        nmbLivreColumn.setCellValueFactory(data -> data.getValue().nmbLivreProperty().asObject());

        tableView.getColumns().addAll(isbnColumn, titreColumn, prixColumn, genreColumn, auteurColumn, nmbLivreColumn);

        // Initialize buttons for add, modify, and delete actions
        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        // Add event handlers for button actions
        // Add Book button
        addButton.setOnAction(event -> AddLivre.display());
        // Modify Book button
        modifyButton.setOnAction(event -> {
            // Get the selected book
            Livre selectedLivre = tableView.getSelectionModel().getSelectedItem();
            if (selectedLivre == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Book Selected", "Please select a book to modify.");
                return;
            }

            // Call the display method of ModifyLivre class
            ModifyLivre.display(selectedLivre);
        });
        // Delete Book button
        deleteButton.setOnAction(event -> {
            // Get the selected book
            Livre selectedLivre = tableView.getSelectionModel().getSelectedItem();
            if (selectedLivre == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Book Selected", "Please select a book to delete.");
                return;
            }
            // Call the delete method of DeleteLivre class
            DeleteLivre.delete(selectedLivre);
        });

        // Arrange UI elements using VBox
        VBox vbox = new VBox(tableView, addButton, modifyButton, deleteButton);
        vbox.setSpacing(10);

        // Set layout for LivrePage
        this.setCenter(vbox);
    }

    public static void fetchData() {
        // Fetch data from the database and populate the TableView

        try (Connection connection = DBConnection.getConnection(); 
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery("SELECT l.ISBN, l.titre, l.prix, l.genre, CONCAT(a.prenom, ' ', a.nom) AS auteur, l.nmb_livre FROM livre l JOIN auteur a ON l.Aut_num = a.Aut_num")) {

            ObservableList<Livre> livres = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int isbn = resultSet.getInt("ISBN");
                String titre = resultSet.getString("titre");
                int prix = resultSet.getInt("prix");
                String genre = resultSet.getString("genre");
                String auteur = resultSet.getString("auteur");
                int nmbLivre = resultSet.getInt("nmb_livre");

                Livre livre = new Livre(isbn, titre, prix, genre, auteur, nmbLivre);
                livres.add(livre);
            }

            tableView.setItems(livres);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    public Parent getView() {
        return view;
    }
}
