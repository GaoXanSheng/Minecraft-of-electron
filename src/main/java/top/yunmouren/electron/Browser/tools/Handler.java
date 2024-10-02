package top.yunmouren.electron.Browser.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Handler {
    public Handler(String receiveMessage) {
        Gson gson = new Gson();

        // 将 JSON 字符串解析为 JsonObject
        JsonObject jsonObject = gson.fromJson(receiveMessage, JsonObject.class);

        // 获取 JSON 对象中的字段
        String from = jsonObject.get("from").getAsString();
        String type = jsonObject.get("type").getAsString();
        if (jsonObject.get("data").isJsonPrimitive()){
            switch (type){
                default:break;
            }
        }else {
            JsonObject data = jsonObject.getAsJsonObject("data");

        }
    }
}
