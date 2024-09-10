package top.yunmouren.electron.Browser.Api;


import com.google.gson.JsonObject;
import top.yunmouren.electron.Browser.WebSocket.Router.WebSocketEndpoint;

import java.util.Timer;
import java.util.TimerTask;

public class Api {
    public static void initBrowser() {
        WebSocketEndpoint.socketSend(EumHand.initBrowser.name(),"{}");
    }

    public static void loadUrl(String url) {
        JsonObject json = new JsonObject();
        json.addProperty("url", url);
        WebSocketEndpoint.socketSend(EumHand.loadUrl.name(),json.toString());
    }

    public static void joinGui() {
        WebSocketEndpoint.socketSend(EumHand.joinGui.name(),"{}");
    }

    public static void exitGui() {
        WebSocketEndpoint.socketSend(EumHand.exitGui.name(),"{}");
    }

    public static void openDevTools() {
        WebSocketEndpoint.socketSend(EumHand.openDevTools.name(),"{}");
    }

    public static void getTitle() {
        WebSocketEndpoint.socketSend(EumHand.getTitle.name(),"{}");
    }

    private static Timer debounceTimer;
    public static void setPosition(Number width, Number height) {
        // 如果之前的定时任务未完成，则取消它
        if (debounceTimer != null) {
            debounceTimer.cancel();
        }
        // 创建新的定时器任务
        debounceTimer = new Timer();
        debounceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 要执行的操作
                JsonObject json = new JsonObject();
                json.addProperty("width", width);
                json.addProperty("height", height);
                WebSocketEndpoint.socketSend(EumHand.setPosition.name(), json.toString());
            }
        }, 100);
    }
}
