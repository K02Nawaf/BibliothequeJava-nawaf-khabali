package com.library.pages.emprunt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Date;

public class Emprunt {
    private SimpleIntegerProperty idEmprunt;
    private SimpleObjectProperty<LocalDate> dateEmprunt;
    private SimpleObjectProperty<LocalDate> dateRetour;
    private SimpleIntegerProperty statutEmprunt;
    private SimpleIntegerProperty nmbEmprunt;
    private SimpleIntegerProperty AdhNum;
    private SimpleIntegerProperty ISBN;
    private SimpleStringProperty bookTitle;
    private SimpleStringProperty adherentName;

    public Emprunt(int idEmprunt, LocalDate dateEmprunt, LocalDate dateRetour, int statutEmprunt, int nmbEmprunt, int AdhNum, int ISBN, String bookTitle, String adherentName) {
        this.idEmprunt = new SimpleIntegerProperty(idEmprunt);
        this.dateEmprunt = new SimpleObjectProperty<>(dateEmprunt);
        this.dateRetour = new SimpleObjectProperty<>(dateRetour);
        this.statutEmprunt = new SimpleIntegerProperty(statutEmprunt);
        this.nmbEmprunt = new SimpleIntegerProperty(nmbEmprunt);
        this.AdhNum = new SimpleIntegerProperty(AdhNum);
        this.ISBN = new SimpleIntegerProperty(ISBN);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.adherentName = new SimpleStringProperty(adherentName);
    }

    // Getters and setters for all properties

    public Emprunt(int idEmprunt2, Date dateEmprunt2, Date dateRetour2, int statutEmprunt2, int nmbEmprunt2) {
        //TODO Auto-generated constructor stub
    }

    public SimpleIntegerProperty idEmpruntProperty() {
        return idEmprunt;
    }

    public SimpleObjectProperty<LocalDate> dateEmpruntProperty() {
        return dateEmprunt;
    }

    public SimpleObjectProperty<LocalDate> dateRetourProperty() {
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

    public LocalDate getDateEmprunt() {
        return dateEmprunt.get();
    }

    public LocalDate getDateRetour() {
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

    // You can also override the toString() method if needed
    @Override
    public String toString() {
        return "Emprunt{" +
                "idEmprunt=" + idEmprunt +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                ", statutEmprunt=" + statutEmprunt +
                ", nmbEmprunt=" + nmbEmprunt +
                ", AdhNum=" + AdhNum +
                ", ISBN=" + ISBN +
                ", bookTitle=" + bookTitle +
                ", adherentName=" + adherentName +
                '}';
    }
}
