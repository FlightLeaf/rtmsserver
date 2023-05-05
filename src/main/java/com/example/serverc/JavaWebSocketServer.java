package com.example.serverc;

import net.sf.json.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class JavaWebSocketServer extends WebSocketServer {

    // 客户端 WebSocket 连接池
    private Set<WebSocket> clientConnections;

    public JavaWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        this.clientConnections = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
        // 新客户端连接
        System.out.println("New client connected: " + connection.getRemoteSocketAddress().getAddress().getHostAddress());
        this.clientConnections.add(connection);
    }

    @Override
    public void onClose(WebSocket connection, int code, String reason, boolean remote) {
        // 客户端断开连接
        System.out.println("Client " + connection.getRemoteSocketAddress().getAddress().getHostAddress() + " disconnected");
        this.clientConnections.remove(connection);
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
        // 收到客户端发送的消息
        System.out.println("Message received from client: " + message);
        // 广播消息给所有客户端
        broadcast(message);
    }

    @Override
    public void onError(WebSocket connection, Exception ex) {
        // 发生错误
        System.err.println("An error occurred on connection " + connection.getRemoteSocketAddress().getAddress().getHostAddress() + ": " + ex.getMessage());
    }

    @Override
    public void onStart() {
        // 服务端启动完成
        System.out.println("WebSocket server started on port: " + this.getPort());
    }

    /**
     * 广播消息给所有客户端
     *
     * @param message 消息内容
     */
    public void broadcast(String message) {
        synchronized (this.clientConnections) {
            for (WebSocket connection : this.clientConnections) {
                connection.send(message);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JavaWebSocketServer server = new JavaWebSocketServer(88);
        server.start();
        while (true){
            JSONObject jsonObject = new JSONObject();
            Random rand = new Random();
            float randomNum = rand.nextInt(100);
            jsonObject.put("longitude","77");
            jsonObject.put("latitude","88");
            jsonObject.put("place","青岛市");
            jsonObject.put("temperature",randomNum*28);
            jsonObject.put("PH",randomNum);
            jsonObject.put("electrical",randomNum*100);
            jsonObject.put("O2",randomNum*20.8);
            jsonObject.put("dirty",randomNum*10.9);
            jsonObject.put("green",randomNum*1000);
            jsonObject.put("NHN",randomNum*0.1);
            jsonObject.put("oil",randomNum*0.1);
            server.broadcast(jsonObject.toString());
            Thread.sleep(1000);
        }
    }
}
