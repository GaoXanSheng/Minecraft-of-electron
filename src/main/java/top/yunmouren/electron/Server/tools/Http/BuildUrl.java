package top.yunmouren.electron.Server.tools.Http;


import com.google.gson.JsonObject;

import static top.yunmouren.electron.Client.Tools.IPC.Port;

public class BuildUrl {

    public static String CombiningURL(String url) {
        return "http://127.0.0.1:" + Port + url;
    }

    public static void sendInit() {
        Http.post(CombiningURL("/api/init"), "{}");
    }

    public static void loadUrl(String url) {
        JsonObject json = new JsonObject();
        json.addProperty("url", url);
        Http.post(CombiningURL("/api/loadUrl"), json.toString());
    }

    public static void joinGui() {
        Http.post(CombiningURL("/api/joinGui"), "{}");
    }

    public static void exitGui() {
        Http.post(CombiningURL("/api/exitGui"), "{}");
    }

    public static void openDevTools() {
        Http.post(CombiningURL("/api/openDevTools"), "{}");
    }

    public static String getTitle() {
        return Http.post(CombiningURL("/api/getTitle"), "{}");
    }


    public static void setPosition(Number width, Number height) {
        JsonObject json = new JsonObject();
        json.addProperty("width", width);
        json.addProperty("height", height);
        Http.post(CombiningURL("/api/setPosition"), json.toString());
    }
}
