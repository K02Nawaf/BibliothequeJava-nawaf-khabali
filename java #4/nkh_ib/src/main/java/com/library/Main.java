package com.library;

import com.library.pages.adherants.AdherentPage;
import com.library.pages.auteur.AuteurPage;
import com.library.pages.emprunt.EmpruntPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Scene livreScene;
    private Scene adherentScene;
    private Scene empruntScene;
    private Scene auteurScene;

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        // Create scenes for each page
        // LivrePage livrePage = new LivrePage();
        AdherentPage adherentPage = new AdherentPage();
        EmpruntPage empruntPage = new EmpruntPage();
        AuteurPage auteurPage = new AuteurPage();

        // Initialize scenes
        // livreScene = new Scene(createLayout(livrePage), 800, 600);
        adherentScene = new Scene(createLayout(adherentPage), 800, 600);
        empruntScene = new Scene(createLayout(empruntPage), 800, 600);
        auteurScene = new Scene(createLayout(auteurPage), 800, 600);

        // Create buttons for navigation
        Button livreButton = new Button("Livre");
        Button adherentButton = new Button("Adherant");
        Button empruntButton = new Button("Emprunt");
        Button auteurButton = new Button("Auteur");

        // Set action event handlers for buttons
        livreButton.setOnAction(e -> primaryStage.setScene(livreScene));
        adherentButton.setOnAction(e -> primaryStage.setScene(adherentScene));
        empruntButton.setOnAction(e -> primaryStage.setScene(empruntScene));
        auteurButton.setOnAction(e -> primaryStage.setScene(auteurScene));

        
        // Create a horizontal box for the navigation bar
        HBox navigationBar = new HBox();
        navigationBar.getChildren().addAll(livreButton, adherentButton, empruntButton, auteurButton);
        navigationBar.setSpacing(20);

        // Create a border pane to hold the navigation bar and content
        BorderPane root = new BorderPane();
        root.setTop(navigationBar);

        // Set initial scene to Livre page
        primaryStage.setScene(livreScene);

        // Create the scene and set it on the stage
        primaryStage.setTitle("Library Management Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    BorderPane createLayout(AdherentPage page) {
        BorderPane layout = new BorderPane();
        layout.setTop(createNavBar());
        layout.setCenter(page);
        return layout;
    }
    
    BorderPane createLayout(AuteurPage page) {
        BorderPane layout = new BorderPane();
        layout.setTop(createNavBar());
        layout.setCenter(page);
        return layout;
    }

    BorderPane createLayout(EmpruntPage page) {
        BorderPane layout = new BorderPane();
        layout.setTop(createNavBar());
        layout.setCenter(page);
        return layout;
    }

    private HBox createNavBar() {
        // Create buttons for navigation
        Button livreButton = new Button("Livre");
        Button adherentButton = new Button("Adherant");
        Button empruntButton = new Button("Emprunt");
        Button auteurButton = new Button("Auteur");

        // Set action event handlers for buttons
        livreButton.setOnAction(e -> ((Stage) livreButton.getScene().getWindow()).setScene(livreScene));
        adherentButton.setOnAction(e -> ((Stage) adherentButton.getScene().getWindow()).setScene(adherentScene));
        empruntButton.setOnAction(e -> ((Stage) empruntButton.getScene().getWindow()).setScene(empruntScene));
        auteurButton.setOnAction(e -> ((Stage) auteurButton.getScene().getWindow()).setScene(auteurScene));

        // Create a horizontal box for the navigation bar
        HBox navigationBar = new HBox();
        navigationBar.getChildren().addAll(livreButton, adherentButton, empruntButton, auteurButton);
        navigationBar.setSpacing(20);

        return navigationBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
