package com.example.serverc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        String[] args = null;
        welcomeText.setText("========服务端已启动========");
        JavaWebSocketServer.main(args);

    }
}
