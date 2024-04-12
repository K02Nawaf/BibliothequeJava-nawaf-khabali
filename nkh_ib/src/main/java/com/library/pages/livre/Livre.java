package com.library.pages.livre;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Livre {
    private SimpleIntegerProperty isbn;
    private SimpleStringProperty titre;
    private SimpleIntegerProperty prix;
    private SimpleStringProperty genre;
    private SimpleStringProperty auteur;
    private SimpleIntegerProperty nmbLivre;

    public Livre(int isbn, String titre, int prix, String genre, String auteur, int nmbLivre) {
        this.isbn = new SimpleIntegerProperty(isbn);
        this.titre = new SimpleStringProperty(titre);
        this.prix = new SimpleIntegerProperty(prix);
        this.genre = new SimpleStringProperty(genre);
        this.auteur = new SimpleStringProperty(auteur);
        this.nmbLivre = new SimpleIntegerProperty(nmbLivre);
    }

    public SimpleIntegerProperty isbnProperty() {
        return isbn;
    }

    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public SimpleIntegerProperty prixProperty() {
        return prix;
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public SimpleStringProperty auteurProperty() {
        return auteur;
    }

    public SimpleIntegerProperty nmbLivreProperty() {
        return nmbLivre;
    }

    public int getIsbn() {
        return isbn.get();
    }

    public String getTitre() {
        return titre.get();
    }

    public int getPrix() {
        return prix.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public String getAuteur() {
        return auteur.get();
    }

    public int getNmbLivre() {
        return nmbLivre.get();
    }

    public int getAutNum() {
        String auteurValue = auteur.get(); // Get the value of the auteur property
        int startIndex = auteurValue.indexOf("("); // Find the index of the opening parenthesis
        int endIndex = auteurValue.indexOf(")"); // Find the index of the closing parenthesis
        if (startIndex != -1 && endIndex != -1) { // Check if both parentheses are found
            String autNumString = auteurValue.substring(startIndex + 1, endIndex); // Extract the substring between parentheses
            return Integer.parseInt(autNumString.trim()); // Convert the substring to an integer and return it
        } else {
            // Aut_num not found, return a default value or handle the case accordingly
            return -1; // Default value indicating that Aut_num is missing
        }
    }

}
