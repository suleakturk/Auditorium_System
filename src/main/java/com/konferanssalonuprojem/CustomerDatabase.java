package com.konferanssalonuprojem;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDatabase {
    public Connection getConnection() {
        Connection baglanti;
        try {
            baglanti = DriverManager.getConnection("jdbc:sqlite:customerDB.db");
            return baglanti;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
