module com.konferanssalonuprojem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.konferanssalonuprojem to javafx.fxml;
    exports com.konferanssalonuprojem;
}