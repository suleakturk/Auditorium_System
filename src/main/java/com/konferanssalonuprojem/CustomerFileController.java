package com.konferanssalonuprojem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerFileController implements Initializable {
    ConferenceDatabase database = new ConferenceDatabase();
    CustomerDatabase customerDatabase = new CustomerDatabase();
    Customer customer = new Customer();

    @FXML
    private TextField nameText;

    @FXML
    private TextField surnameText;

    @FXML
    private Button continueButton;

    @FXML
    private CheckBox fullPayCheckBox;

    @FXML
    private CheckBox studentPayCheckBox;

    @FXML
    private ComboBox<String> selectSeatComboBox;

    @FXML
    private ComboBox<String> selectConferenceComboBox;

    @FXML
    private ComboBox<String> selectSessionComboBox;

    @FXML
    void onContinueButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (selectConferenceComboBox.getValue() == null || selectSessionComboBox.getValue() == null ||
                selectSeatComboBox.getValue() == null || Objects.equals(nameText.getText(), "") ||
                Objects.equals(surnameText.getText(), "") || (!fullPayCheckBox.isSelected() && !studentPayCheckBox.isSelected())) {
            showAlertWarn();
        } else { // satin alinan veriyi musteri veritabanina ekleme islemi:
            customerDatabase.getConnection();
            String query4 = "insert into musteriBilgileri(musteriAdi, musteriSoyadi, konferans, seans, koltukNo, ucret) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement pst = customerDatabase.getConnection().prepareStatement(query4);
                pst.setString(1, nameText.getText());
                pst.setString(2, surnameText.getText());
                pst.setString(3, selectConferenceComboBox.getValue());
                pst.setString(4, selectSessionComboBox.getValue());
                pst.setString(5, selectSeatComboBox.getValue());
                pst.setInt(6, customer.getPay());

                pst.executeUpdate();
                pst.close();

                showAlertInfo();
                continueButton.setDisable(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Secilen koltugu bosKoltuklar db den silip doluKoltuk db ye ekleme - update islemi:
            database.getConnection();
            String query5="update konferansBilgileri set bosKoltuklar = ?,doluKoltuklar = ? where konferansAdi = ? and seans = ? and bosKoltuklar = ?";
            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(query5);
                preparedStatement.setString(1, "");
                preparedStatement.setString(2, selectSeatComboBox.getValue());
                preparedStatement.setString(3, selectConferenceComboBox.getValue());
                preparedStatement.setString(4, selectSessionComboBox.getValue());
                preparedStatement.setString(5,selectSeatComboBox.getValue());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void showAlertWarn() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("UYARI");
        alert.setHeaderText("Lütfen tüm kutucukları doldurunuz!");
        alert.show();
    }

    public void showAlertInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BİLGİ");
        alert.setHeaderText("Alıcı ad-soyad: "+nameText.getText()+" "+surnameText.getText()+
                "\nGidilecek konferans: "+selectConferenceComboBox.getValue()+
                "\nSeçili seans: "+selectSessionComboBox.getValue()+
                "\nSeçili koltuk: "+selectSeatComboBox.getValue()+
                "\nÖdenen ücret: "+customer.getPay());
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Konferans adları combobox i databaseden cekilen verilerle doldurma
        database.getConnection();

        ObservableList<String> konferansAdiList = FXCollections.observableArrayList();
        selectConferenceComboBox.setItems(konferansAdiList);


        String query = "select konferansAdi from konferansBilgileri";
        try {
            PreparedStatement pst = database.getConnection().prepareStatement(query);
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
    void onSessionButtonClick(MouseEvent event) {
        //Seans bilgilerini secilen konferansa gore databaseden cekme
        ObservableList<String> seansList = FXCollections.observableArrayList();
        selectSessionComboBox.setItems(seansList);
        String temp = selectConferenceComboBox.getValue();
        String query2 = String.format("select seans from konferansBilgileri where konferansAdi = '%s'",(temp));

        try {
            PreparedStatement pst = database.getConnection().prepareStatement(query2);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                //tekrarlanan isimleri combobox a almama
                if (!seansList.contains(rs.getString("seans"))){
                    seansList.add(rs.getString("seans"));
                }
            }

            pst.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSeatButtonClick(MouseEvent event) {
        //Secilen konferans ve seansa gore uygun bos koltuklari db den cekme ve ilgili combobox a yazdirma
        ObservableList<String> bosKoltukList = FXCollections.observableArrayList();
        String conference = selectConferenceComboBox.getValue();
        String seans = selectSessionComboBox.getValue();
        String query3 = "select bosKoltuklar from konferansBilgileri where konferansAdi = ? and seans = ?;";

        try {
            PreparedStatement pst = database.getConnection().prepareStatement(query3);
            pst.setString(1, conference);
            pst.setString(2, seans);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                bosKoltukList.add(rs.getString("bosKoltuklar"));
                //db den alinan boskoltuklari combobox a ekleme
                selectSeatComboBox.setItems(bosKoltukList);
            }

            pst.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onPayButtonClick(ActionEvent event) {
        //Ucret checkboxlarina deger atama
        studentPayCheckBox.setText("15");
        fullPayCheckBox.setText("30");

        if (fullPayCheckBox.isSelected()){
            customer.setPay(Integer.parseInt(String.valueOf(fullPayCheckBox.getText())));
        }
        else{
            customer.setPay(Integer.parseInt(String.valueOf(studentPayCheckBox.getText())));
        }
    }
}




