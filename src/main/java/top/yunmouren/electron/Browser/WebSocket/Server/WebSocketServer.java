package top.yunmouren.electron.Browser.WebSocket.Server;


import io.undertow.Handlers;
import io.undertow.Undertow;
import top.yunmouren.electron.Browser.WebSocket.Router.WebSocketEndpoint;
import top.yunmouren.electron.Client.Client;

public class WebSocketServer {
    public WebSocketServer(){
        Undertow server = Undertow.builder()
                .addHttpListener(Client.browser.Port, "localhost")
                .setHandler(Handlers.websocket((exchange, channel) -> {
                    channel.getReceiveSetter().set(new WebSocketEndpoint());
                    channel.resumeReceives();
                }))
                .build();
        server.start();
    }
    public static void main(String[] args) {
        new WebSocketServer();
    }
}