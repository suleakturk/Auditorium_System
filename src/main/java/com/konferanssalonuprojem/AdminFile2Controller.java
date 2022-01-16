package com.konferanssalonuprojem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminFile2Controller implements Initializable {
    CustomerDatabase customerDatabase = new CustomerDatabase();
    ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    String sum;


    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer,String> columnConference;

    @FXML
    private TableColumn<Customer,String> columnName;

    @FXML
    private TableColumn<Customer,Integer> columnPay;

    @FXML
    private TableColumn<Customer,String> columnSeat;

    @FXML
    private TableColumn<Customer,String> columnSession;

    @FXML
    private TableColumn<Customer,String> columnSurname;

    public void showAlertInfo() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("BİLGİ");
        alert.setHeaderText("Anlık Hasılat: "+sum);
        alert.show();
    }

    @FXML
    void onAddConferenceButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addConference.fxml")));
        stage.setTitle("Konferans Ekleme Ekranı");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void onDeleteConferenceButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("deleteConference.fxml")));
        stage.setTitle("Konferans Silme Ekranı");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onIncomeButtonClick(ActionEvent event) {
        String query = "select sum(ucret) from musteriBilgileri";
        try {
            PreparedStatement  pst = customerDatabase.getConnection().prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                sum = rs.getString("sum(ucret)");
            }
            showAlertInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TableView yapisinda musteriDB yi gosterme:
        customerDatabase.getConnection();
        try {
            ResultSet resultSet = customerDatabase.getConnection().createStatement().executeQuery("SELECT * from musteriBilgileri");

            while (resultSet.next()){
                //Customer sinifinin constructorini kullanarak obje uretme ve listeye atama
                customerObservableList.add(new Customer(resultSet.getString("konferans"),resultSet.getString("seans"),
                        resultSet.getString("koltukNo"),resultSet.getString("musteriAdi"),resultSet.getString("musteriSoyadi"),
                        resultSet.getInt("ucret")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnConference.setCellValueFactory(new PropertyValueFactory<>("conferenceName"));
        columnSession.setCellValueFactory(new PropertyValueFactory<>("conferenceTime"));
        columnSeat.setCellValueFactory(new PropertyValueFactory<>("fullSeat"));
        columnPay.setCellValueFactory(new PropertyValueFactory<>("pay"));

        table.setItems(customerObservableList);
    }
}
