package top.yunmouren.electron.Browser.Api;


import com.google.gson.JsonObject;
import top.yunmouren.electron.Browser.tools.Http;
import top.yunmouren.electron.Client.Client;

import java.util.Timer;
import java.util.TimerTask;

public class Api {
    public static String CombiningURL(String url, String json) {
        return Http.post("http://127.0.0.1:" + Client.browser.BrowserPort + url, json);
    }

    public static void sendInit() {
        CombiningURL("/api/init", "{}");
    }

    public static void loadUrl(String url) {
        JsonObject json = new JsonObject();
        json.addProperty("url", url);
        CombiningURL("/api/loadUrl", json.toString());
    }

    public static void joinGui() {
        CombiningURL("/api/joinGui", "{}");
        Client.browser.Api.SetFocus(Client.browser.Api.getBrowserhWndParent());
    }

    public static void exitGui() {
        CombiningURL("/api/exitGui", "{}");
        Client.browser.Api.SetFocus(Client.browser.Api.getMinecrafthWndParent());
    }

    public static void openDevTools() {
        CombiningURL("/api/openDevTools", "{}");
    }

    public static String getTitle() {
        return CombiningURL("/api/getTitle", "{}");
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
                json.addProperty("type", "setPosition");
                json.addProperty("width", width);
                json.addProperty("height", height);
                CombiningURL("/api/setPosition", json.toString());
            }
        }, 200);
    }
}
