module com.example.connectdots {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.fasterxml.jackson.databind;

    opens com.example.connectdots to javafx.fxml;

    // Exporta los paquetes necesarios desde tu módulo
    exports com.example.connectdots;
    // exports otro_paquete; // Si tienes otro paquete para exportar, agrégalo aquí
}
