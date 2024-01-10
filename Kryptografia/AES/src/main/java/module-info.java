module com.example.aes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.aes to javafx.fxml;
    exports com.example.aes;
}