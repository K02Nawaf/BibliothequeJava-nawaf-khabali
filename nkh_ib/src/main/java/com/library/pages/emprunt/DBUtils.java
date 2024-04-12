package com.library.pages.emprunt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.DBConnection;

public class DBUtils {

    public static String getBookTitle(int ISBN) {
        String bookTitle = null;
        String query = "SELECT titre FROM livre WHERE ISBN = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ISBN);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    bookTitle = resultSet.getString("titre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookTitle;
    }

    public static String getAdherentName(int AdhNum) {
        String adherentName = null;
        String query = "SELECT nom, prenom FROM adherent WHERE Adh_num = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, AdhNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    adherentName = nom + " " + prenom;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherentName;
    }
}
