package top.yunmouren.electron.Client.Network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Browser.Api.Api;
import top.yunmouren.electron.Client.GUI.CustomGUIScreen;

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
                openGUIOnClient(body);
                Api.joinGui();
                break;
            case "RecalculateArea":
                onScreenResize();
                break;
            case "closeGUIOnClient":
                closeGUIOnClient();
                break;
        }
    }
    private static final Minecraft minecraft = Minecraft.getInstance();
    public static void openGUIOnClient(String url) {
        minecraft.setScreen(new CustomGUIScreen("TEST", url));
    }

    public static void closeGUIOnClient() {
        Minecraft.getInstance().setScreen(null);
    }
}
