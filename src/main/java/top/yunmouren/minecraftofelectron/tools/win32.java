package top.yunmouren.minecraftofelectron.tools;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class win32 {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.loadLibrary("user32", User32.class);

        // 定义 FindWindowA 函数
        WinDef.HWND FindWindowA(String lpClassName, String lpWindowName);
    }

    private static final MainWindow mainWindow = Minecraft.getInstance().getWindow();

    public static String getWindowHandle() {
        mainWindow.setTitle("FindWindow 1.16.5");
        return "0";
    }

    public static int getWindowWidth() {
        return mainWindow.getWidth();
    }

    public static int getWindowHeight() {
        return mainWindow.getHeight();
    }

    public static int getWindowX() {
        return mainWindow.getX();
    }

    public static int getWindowY() {
        return mainWindow.getY();
    }
}