package com.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Navbar extends HBox {

    @SuppressWarnings("exports")
    public Navbar(Stage primaryStage, Scene livreScene, Scene adherantScene, Scene empruntScene, Scene auteurPage) {
        // Create buttons for navigation
        Button livreButton = new Button("Livre");
        Button adherantButton = new Button("Adherant");
        Button empruntButton = new Button("Emprunt");
        Button auteurButton = new Button("Auteur");

        // Set action event handlers for buttons
        livreButton.setOnAction(e -> primaryStage.setScene(livreScene));
        adherantButton.setOnAction(e -> primaryStage.setScene(adherantScene));
        empruntButton.setOnAction(e -> primaryStage.setScene(empruntScene));
        auteurButton.setOnAction(e -> primaryStage.setScene(auteurPage));

        // Create a horizontal box for the navigation bar
        this.getChildren().addAll(livreButton, adherantButton, empruntButton, auteurButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-background-color: lightgrey; -fx-padding: 10px;");
    }
}
