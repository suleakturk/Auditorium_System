package com.konferanssalonuprojem;
import java.sql.*;
import java.util.ArrayList;

public class ConferenceDatabase {
    public Connection getConnection() {
        Connection baglanti;
        try {
            baglanti = DriverManager.getConnection("jdbc:sqlite:konferansDB.db");
            return baglanti;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
