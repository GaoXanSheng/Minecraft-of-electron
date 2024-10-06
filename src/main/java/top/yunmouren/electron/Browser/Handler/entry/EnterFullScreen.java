package top.yunmouren.electron.Browser.Handler.entry;

import com.google.gson.JsonObject;
import top.yunmouren.electron.Browser.Handler.inherit.IHandler;

import static top.yunmouren.electron.Browser.Tools.WindowsApi.WindowResizeListener.onScreenResize;

public class EnterFullScreen extends IHandler {
    @Override
    public void Handler(JsonObject receiveMessage) {
        onScreenResize();
    }
}
