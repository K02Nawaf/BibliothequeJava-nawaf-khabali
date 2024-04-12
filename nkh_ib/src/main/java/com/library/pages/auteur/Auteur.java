package com.library.pages.auteur;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Auteur {
    private SimpleIntegerProperty autNum;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleStringProperty dateNaissance;
    private SimpleStringProperty description;

    public Auteur(int autNum, String nom, String prenom, String dateNaissance, String description) {
        this.autNum = new SimpleIntegerProperty(autNum);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.dateNaissance = new SimpleStringProperty(dateNaissance);
        this.description = new SimpleStringProperty(description);
    }

    public SimpleIntegerProperty autNumProperty() {
        return autNum;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public SimpleStringProperty dateNaissanceProperty() {
        return dateNaissance;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public int getAutNum() {
        return autNum.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getDateNaissance() {
        return dateNaissance.get();
    }

    public String getDescription() {
        return description.get();
    }
}
