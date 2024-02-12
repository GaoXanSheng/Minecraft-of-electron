package top.yunmouren.minecraftofelectron.Models;

import static top.yunmouren.minecraftofelectron.tools.win32.*;

public class WindowInfo {
    private int width;
    private int height;
    private int x;
    private int y;
    private String windowHandle;
    public WindowInfo() {
        this.width = getWindowWidth();
        this.height = getWindowHeight();
        this.x = getWindowX();
        this.y = getWindowY();
        this.windowHandle = getWindowHandle();
    }
}
