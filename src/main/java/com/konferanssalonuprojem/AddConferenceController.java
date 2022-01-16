package com.konferanssalonuprojem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AddConferenceController {

    @FXML
    private Button addConferenceButton;

    @FXML
    private TextField conferenceName;

    @FXML
    private TextField seatName;

    @FXML
    private TextField sessionName;

    public void showAlertWarn() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("UYARI");
        alert.setHeaderText("Lütfen tüm kutucukları doldurunuz!");
        alert.show();
    }

    public void showAlertInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BİLGİ");
        alert.setHeaderText("Yeni konferans başarıyla eklenmiştir.");
        alert.show();
    }

    @FXML
    void onAddButtonClick(ActionEvent event){
        //Eger entrylerden herhangi biri bossa uyari verir:
        if(Objects.equals(conferenceName.getText(), "") || Objects.equals(sessionName.getText(), "") ||
                Objects.equals(seatName.getText(), "")){
            showAlertWarn();
        }//Tum girdiler girildiyse, database e ekleme islemi yapilir:
        else {
            ConferenceDatabase conferenceDatabase = new ConferenceDatabase();
            conferenceDatabase.getConnection();

            String query = "insert into konferansBilgileri(konferansAdi, seans, doluKoltuklar, bosKoltuklar) VALUES (?,?,?,?)";
            try {
                PreparedStatement pst = conferenceDatabase.getConnection().prepareStatement(query);
                pst.setString(1,conferenceName.getText());
                pst.setString(2,sessionName.getText());
                pst.setString(3,"");
                pst.setString(4,seatName.getText());

                pst.executeUpdate();
                pst.close();

                addConferenceButton.setDisable(true);
                showAlertInfo();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
