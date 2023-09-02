module com.example.connectdots {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.connectdots to javafx.fxml;
    exports com.example.connectdots;
}