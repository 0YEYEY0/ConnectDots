module com.example.connectdots {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.fasterxml.jackson.databind;


    opens com.example.connectdots to javafx.fxml;
    exports com.example.connectdots;
}