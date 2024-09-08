package top.yunmouren.electron.Client.Network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Client.GUI.CustomGUIScreen;
import top.yunmouren.electron.Server.tools.Http.BuildUrl;

import static top.yunmouren.electron.Client.Tools.overlapWindows.WindowResizeListener.onScreenResize;
@OnlyIn(Dist.CLIENT)
public class ClientEnumeration {
    public ClientEnumeration(String ctx, String body) {
        switch (ctx) {
            case "joinGUI":
                BuildUrl.joinGui();
                break;
            case "exitGUI":
                BuildUrl.exitGui();
                break;
            case "openDevTools":
                BuildUrl.openDevTools();
                break;
            case "loadUrl":
                openGUIOnClient(body);
                BuildUrl.joinGui();
                break;
            case "RecalculateArea":
                onScreenResize();
                break;
            case "closeGUIOnClient":
                closeGUIOnClient();
                break;
        }
    }

    private static void openGUIOnClient(String url) {
        Minecraft.getInstance().setScreen(new CustomGUIScreen("TEST",url));
    }

    public static void closeGUIOnClient() {
        Minecraft.getInstance().setScreen(null);
    }
}
