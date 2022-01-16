package com.konferanssalonuprojem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartController {

    @FXML
    void onAdminEnterButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminFile.fxml")));
        stage.setTitle("Admin Girişi");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void onCustomerEnterButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customerFile.fxml")));
        stage.setTitle("Müşteri Girişi");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void onExitButtonClick(ActionEvent event) {
        System.exit(0);
    }
}