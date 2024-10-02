package top.yunmouren.electron.Client.Network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Browser.Api.Api;

import static top.yunmouren.electron.Browser.tools.WindowsApi.WindowResizeListener.onScreenResize;


@OnlyIn(Dist.CLIENT)
public class ClientEnumeration {
    public ClientEnumeration(String ctx, String body) {
        switch (ctx) {
            case "joinGUI":
                Api.joinGui();
                break;
            case "exitGUI":
                Api.exitGui();
                break;
            case "openDevTools":
                Api.openDevTools();
                break;
            case "loadUrl":
                Api.loadUrl(body);
                break;
            case "RecalculateArea":
                onScreenResize();
                break;
        }
    }
}
