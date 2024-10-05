package top.yunmouren.electron.Browser.Api;


import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Client.Client;

import java.util.Timer;
import java.util.TimerTask;
@OnlyIn(Dist.CLIENT)
public class Api {
    public static void CombiningURL(String type, JsonObject data) {
        JsonObject json = new JsonObject();
        json.addProperty("from", "Minecraft");
        json.addProperty("type", type);
        json.add("data", data);
        Client.browser.NodeJs.sendMessage(json);
    }

    public static void CombiningURL(String type, String data) {
        JsonObject json = new JsonObject();
        json.addProperty("from", "Minecraft");
        json.addProperty("type", type);
        json.addProperty("data", data);
        Client.browser.NodeJs.sendMessage(json);
    }

    public static void loadUrl(String url) {
        CombiningURL("LoadUrl", url);
    }

    public static void joinGui() {
        CombiningURL("JoinGui", "");
    }

    public static void exitGui() {
        CombiningURL("ExitGui", "");
        Client.browser.Api.SetFocus(Client.browser.Api.getMinecrafthWndParent());
    }

    public static void openDevTools() {
        CombiningURL("OpenDevTools", "");
    }

    public static void getTitle() {
        CombiningURL("GetTitle", "");
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
                var data = new JsonObject();
                data.addProperty("width", width);
                data.addProperty("height", height);
                CombiningURL("SetPosition", data);
            }
        }, 200);
    }
}
