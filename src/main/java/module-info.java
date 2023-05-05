module com.example.serverc {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires json.lib;
    requires Java.WebSocket;

    opens com.example.serverc to javafx.fxml;
    exports com.example.serverc;
}
