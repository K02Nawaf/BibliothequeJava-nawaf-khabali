package com.library.pages.adherants;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Adherent {
    private SimpleIntegerProperty adhNum;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleStringProperty email;
    private SimpleStringProperty adresse;

    public Adherent(int adhNum, String nom, String prenom, String email, String adresse) {
        this.adhNum = new SimpleIntegerProperty(adhNum);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.adresse = new SimpleStringProperty(adresse);
    }

    public SimpleIntegerProperty adhNumProperty() {
        return adhNum;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public int getAdhNum() {
        return adhNum.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAdresse() {
        return adresse.get();
    }
}
