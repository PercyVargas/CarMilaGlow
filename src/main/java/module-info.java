module org.example.carmilaglow {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.example.carmilaglow.controller to javafx.fxml;
    opens org.example.carmilaglow.model to javafx.base;

    exports org.example.carmilaglow;
    exports org.example.carmilaglow.controller;
    exports org.example.carmilaglow.model;
    opens org.example.carmilaglow to javafx.base, javafx.fxml;

}
