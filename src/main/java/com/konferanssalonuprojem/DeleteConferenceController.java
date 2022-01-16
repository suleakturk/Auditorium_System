package com.konferanssalonuprojem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteConferenceController implements Initializable {
    ConferenceDatabase conferenceDatabase = new ConferenceDatabase();

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<String> conferenceComboBox;

    @FXML
    private ComboBox<String> sessionComboBox;

    public void showAlertWarn() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("UYARI");
        alert.setHeaderText("Lütfen tüm kutucukları doldurunuz!");
        alert.show();
    }

    public void showAlertInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BİLGİ");
        alert.setHeaderText("Silme işlemi başarıyla gerçekleşmiştir.");
        alert.show();
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {
        if (conferenceComboBox.getValue() == null || sessionComboBox.getValue() == null){
            showAlertWarn();
        }
        else {
            String query = "delete from konferansBilgileri where konferansAdi = ? and seans = ?";
            try {
                PreparedStatement pst = conferenceDatabase.getConnection().prepareStatement(query);
                pst.setString(1, conferenceComboBox.getValue());
                pst.setString(2, sessionComboBox.getValue());

                pst.executeUpdate();
                pst.close();

                deleteButton.setDisable(true);
                showAlertInfo();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Konferans combobox icerigini ayarlar
        ObservableList<String> konferansAdiList = FXCollections.observableArrayList();
        conferenceComboBox.setItems(konferansAdiList);

        String query = "select konferansAdi from konferansBilgileri";
        try {
            PreparedStatement pst = conferenceDatabase.getConnection().prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                if (!konferansAdiList.contains(rs.getString("konferansAdi"))){
                    konferansAdiList.add(rs.getString("konferansAdi"));
                }
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onSessionComboBoxClick(MouseEvent event) {
        //konferans bilgisine gore seans bilgilerini veritabanından cekerek comboboxa aktarır
        ObservableList<String> seansList = FXCollections.observableArrayList();

        String query = "select seans from konferansBilgileri where konferansAdi = ?";
        try {
            PreparedStatement pst = conferenceDatabase.getConnection().prepareStatement(query);
            pst.setString(1,conferenceComboBox.getValue());
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                if (!seansList.contains(rs.getString("seans"))){
                    seansList.add(rs.getString("seans"));
                }
                sessionComboBox.setItems(seansList);
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
