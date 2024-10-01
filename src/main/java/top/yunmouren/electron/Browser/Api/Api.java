package top.yunmouren.electron.Browser.Api;


import com.google.gson.JsonObject;
import top.yunmouren.electron.Browser.tools.Http;
import top.yunmouren.electron.Client.Client;

import java.util.Timer;
import java.util.TimerTask;

public class Api {
    public static String CombiningURL(JsonObject json) {
        json.addProperty("form", "Minecraft");
        Client.browser.NodeJs.sendMessage(json.toString());
    }

    public static void loadUrl(String url) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "LoadUrl");
        json.addProperty("data", url);
        CombiningURL(json);
    }

    public static void joinGui() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "JoinGui");
        json.addProperty("data", "");
        CombiningURL(json);
    }

    public static void exitGui() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "ExitGui");
        json.addProperty("data", "");
        CombiningURL(json);
//        Client.browser.Api.SetFocus(Client.browser.Api.getMinecrafthWndParent());
    }

    public static void openDevTools() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "OpenDevTools");
        json.addProperty("data", "");
        CombiningURL(json);
    }

    public static String getTitle() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "GetTitle");
        json.addProperty("data", "");
        CombiningURL(json);
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
                var data = new JsonObject();
                data.addProperty("width", width);
                data.addProperty("height", height);
                json.add("data",data);
                json.addProperty("type", "SetPosition");
                CombiningURL(json);
            }
        }, 200);
    }
}
