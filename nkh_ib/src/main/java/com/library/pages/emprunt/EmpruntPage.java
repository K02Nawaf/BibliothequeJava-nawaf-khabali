package com.library.pages.emprunt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
        TableColumn<Emprunt, Integer> idEmpruntColumn = new TableColumn<>("Loan ID");
        idEmpruntColumn.setCellValueFactory(data -> data.getValue().idEmpruntProperty().asObject());

        // Add more TableColumn definitions as needed for loan date, return date, status, book title, adherent name
        
        // Initialize buttons for add, modify, and delete actions
        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        // Add event handlers for button actions
        // Add button
        addButton.setOnAction(event -> AddEmprunt.display(this));
        // Modify button
        modifyButton.setOnAction(event -> {
            Emprunt selectedEmprunt = tableView.getSelectionModel().getSelectedItem();
            if (selectedEmprunt == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Loan Selected", "Please select a loan to modify.");
                return;
            }
            ModifyEmprunt.display(selectedEmprunt);
        });
        // Delete button
        deleteButton.setOnAction(event -> {
            Emprunt selectedEmprunt = tableView.getSelectionModel().getSelectedItem();
            if (selectedEmprunt == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "No Loan Selected", "Please select a loan to delete.");
                return;
            }
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
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM emprunt")) {

            ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

            while (resultSet.next()) {
                // Extract loan information from the ResultSet
                int idEmprunt = resultSet.getInt("id_emprunt");
                Date dateEmprunt = resultSet.getDate("date_emprunt");
                Date dateRetour = resultSet.getDate("date_retour");
                int statutEmprunt = resultSet.getInt("statut_emprunt");
                int nmbEmprunt = resultSet.getInt("nmb_emprunt");
                int AdhNum = resultSet.getInt("Adh_num");
                int ISBN = resultSet.getInt("ISBN");
            
                // Use additional queries to fetch book titles and adherent names based on foreign keys (ISBN and Adh_num)
                // String bookTitle = getBookTitle(ISBN);
                // String adherentName = getAdherentName(AdhNum);
            
                // Create Emprunt object and add to the list
                Emprunt emprunt = new Emprunt(idEmprunt, dateEmprunt, dateRetour, statutEmprunt, nmbEmprunt);
                emprunts.add(emprunt);
            }
            
            tableView.setItems(emprunts);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    public Parent getView() {
        return view;
    }
}
