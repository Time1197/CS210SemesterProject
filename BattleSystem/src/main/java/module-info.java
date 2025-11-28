module com.example.cs210battlemocks {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs210battlemocks to javafx.fxml;
    exports com.example.cs210battlemocks;
}