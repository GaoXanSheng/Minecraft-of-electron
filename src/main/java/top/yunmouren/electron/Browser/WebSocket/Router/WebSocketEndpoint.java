package top.yunmouren.electron.Browser.WebSocket.Router;


import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

public class WebSocketEndpoint extends AbstractReceiveListener{
    private static WebSocketChannel browser = null;
    @Override
    protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
        // 处理文本消息
        String receivedMessage = message.getData();
        if (receivedMessage.equals("ping")) {
            browser = channel;
        }
    }
    public static void socketSend(String handshake, String data){
        if (browser == null) return;
        WebSockets.sendText(handshake+" "+data, browser, null);
    }
    @Override
    protected void onError(WebSocketChannel channel, Throwable error) {
        error.printStackTrace();
    }
}