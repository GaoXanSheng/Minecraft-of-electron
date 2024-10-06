package top.yunmouren.electron.Browser.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Browser.Handler.inherit.IHandler;
import top.yunmouren.electron.Electron;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class Handler {
    private static final HashMap<String, Class> handlerMap = new HashMap<>();
    public Handler(String receiveMessage) {
        Gson gson = new Gson();
        // 将 JSON 字符串解析为 JsonObject
        JsonObject jsonObject = gson.fromJson(receiveMessage, JsonObject.class);
        // 获取 JSON 对象中的字段
        String type = jsonObject.get("type").getAsString();
        if (handlerMap.containsKey(type)) {
            try {
                Class<?> IHandlerClass = handlerMap.get(type);
                if (IHandler.class.isAssignableFrom(IHandlerClass)) {
                    IHandler handler = (IHandler) IHandlerClass.getDeclaredConstructor().newInstance();
                    handler.Handler(jsonObject);
                }
            } catch (Exception e) {
                Electron.logger.error(e.getMessage());
            }
        }
    }

    public static void register(String type, Class handler) {
        handlerMap.put(type, handler);
    }
}
