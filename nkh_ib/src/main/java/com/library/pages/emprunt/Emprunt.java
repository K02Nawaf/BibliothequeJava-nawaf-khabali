package com.library.pages.emprunt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Emprunt {
    private SimpleIntegerProperty idEmprunt;
    private SimpleStringProperty dateEmprunt;
    private SimpleStringProperty dateRetour;
    private SimpleIntegerProperty statutEmprunt;
    private SimpleIntegerProperty nmbEmprunt;
    private SimpleIntegerProperty AdhNum;
    private SimpleIntegerProperty ISBN;
    private SimpleStringProperty bookTitle;
    private SimpleStringProperty adherentName;

    public Emprunt(int idEmprunt, String dateEmprunt, String dateRetour, int statutEmprunt, int nmbEmprunt, int AdhNum, int ISBN, String bookTitle, String adherentName) {
        this.idEmprunt = new SimpleIntegerProperty(idEmprunt);
        this.dateEmprunt = new SimpleStringProperty(dateEmprunt);
        this.dateRetour = new SimpleStringProperty(dateRetour);
        this.statutEmprunt = new SimpleIntegerProperty(statutEmprunt);
        this.nmbEmprunt = new SimpleIntegerProperty(nmbEmprunt);
        this.AdhNum = new SimpleIntegerProperty(AdhNum);
        this.ISBN = new SimpleIntegerProperty(ISBN);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.adherentName = new SimpleStringProperty(adherentName);
    }

    // Getters and setters for all properties
    public SimpleIntegerProperty idEmpruntProperty() {
        return idEmprunt;
    }

    public SimpleStringProperty dateEmpruntProperty() {
        return dateEmprunt;
    }

    public SimpleStringProperty dateRetourProperty() {
        return dateRetour;
    }

    public SimpleIntegerProperty statutEmpruntProperty() {
        return statutEmprunt;
    }

    public SimpleIntegerProperty nmbEmpruntProperty() {
        return nmbEmprunt;
    }

    public SimpleIntegerProperty AdhNumProperty() {
        return AdhNum;
    }

    public SimpleIntegerProperty ISBNProperty() {
        return ISBN;
    }

    public SimpleStringProperty bookTitleProperty() {
        return bookTitle;
    }

    public SimpleStringProperty adherentNameProperty() {
        return adherentName;
    }

    public int getIdEmprunt() {
        return idEmprunt.get();
    }

    public String getDateEmprunt() {
        return dateEmprunt.get();
    }

    public String getDateRetour() {
        return dateRetour.get();
    }

    public int getStatutEmprunt() {
        return statutEmprunt.get();
    }

    public int getNmbEmprunt() {
        return nmbEmprunt.get();
    }

    public int getAdhNum() {
        return AdhNum.get();
    }

    public int getISBN() {
        return ISBN.get();
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public String getAdherentName() {
        return adherentName.get();
    }
}
