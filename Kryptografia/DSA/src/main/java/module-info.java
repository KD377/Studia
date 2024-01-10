module com.dsa.dsa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dsa.dsa to javafx.fxml;
    exports com.dsa.dsa;
}