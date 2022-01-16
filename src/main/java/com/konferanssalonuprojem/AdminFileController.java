package com.konferanssalonuprojem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminFileController {

    @FXML
    private Button enterButton;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    void onCleanButtonClick(ActionEvent event) {
        userNameText.setText("");
        passwordText.setText("");
    }

    @FXML
    void onEnterButtonClick(ActionEvent event) throws IOException {
        if (Objects.equals(userNameText.getText(), "suleakturk") && Objects.equals(passwordText.getText(), "123")){
            //Yeni sayfaya gecer
            Stage stage = new Stage();
            Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminFile2.fxml")));
            stage.setTitle("Admin Ekranı");
            stage.setScene(new Scene(root2));
            stage.show();

            //bu sayfayı kapatır yenisini acar:
            Stage adminFileStage = (Stage)enterButton.getScene().getWindow();
            adminFileStage.close();
        }
        else {
            //Kullanici adi ya da sifre yanlistir, uyari verir!
            showAlertWarn();
        }
    }
    public void showAlertWarn() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("HATA");
        alert.setHeaderText("Yanlış kullanıcı adı ya da parola!\nTekrar deneyiniz...");
        alert.show();
    }

}
