package top.yunmouren.electron.Client.Tools;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static top.yunmouren.electron.Server.tools.Http.BuildUrl.setPosition;

@OnlyIn(Dist.CLIENT)
public class overlapWindows {
    // 窗口置顶
    private final HWND HWND_TOP = new HWND(Pointer.NULL);
    private final String Minecraft_Title = "overlapWindows";
    private final String Web_Title = "TestApp";
    private static HWND MinecrafthWndParent = null;
    private static HWND browserhWndParent = null;
    private static final User32 user32 = User32.INSTANCE;
    private static WinDef.RECT rect = new WinDef.RECT();
    public static boolean init = false;

    private final int SWP_NOZORDER = 0x0004;
    private final int SWP_SHOWWINDOW = 0x0040;

    public overlapWindows() {
        // 设置title
        Minecraft.getInstance().getWindow().setTitle(Minecraft_Title);
        // 查找两个父窗口的句柄，通过标题
        MinecrafthWndParent = user32.FindWindow(null, Minecraft_Title);
        browserhWndParent = user32.FindWindow(null, Web_Title);
        if (MinecrafthWndParent != null && browserhWndParent != null) {
            CalculateFrameArea();
            OverlapWindows(MinecrafthWndParent, browserhWndParent);
            user32.SetWindowPos(browserhWndParent, HWND_TOP, 0, 0, rect.right - rect.left, rect.bottom - rect.top, SWP_NOZORDER | SWP_SHOWWINDOW);
        }
        init = true;
    }

    private void CalculateFrameArea() {
        // 获取窗口的整体大小
        user32.GetWindowRect(MinecrafthWndParent, rect);
        // 获取客户端区域大小
        WinDef.RECT clientRect = new WinDef.RECT();
        user32.GetClientRect(MinecrafthWndParent, clientRect);
        // 设置可视化区域
        WindowResizeListener.Windows_Frame_Height = (rect.bottom - rect.top) - (clientRect.bottom - clientRect.top);
        WindowResizeListener.Windows_Frame_Width = (rect.left - rect.right) - (clientRect.left - clientRect.right);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class WindowResizeListener {
        private static int height = 0;
        private static int width = 0;
        private static int Windows_Frame_Width = 0;
        private static int Windows_Frame_Height = 0;

        /**
         * 同步窗口大小
         *
         * @param event
         */
        @SubscribeEvent
        public static void onScreenResize(ScreenEvent event) {
            if ((height != event.getScreen().height || width != event.getScreen().width) && overlapWindows.init) {
                height = event.getScreen().height;
                width = event.getScreen().width;
                user32.GetWindowRect(MinecrafthWndParent, rect);
                CalculateScaling((rect.right - rect.left), (rect.bottom - rect.top));
            }
        }

        /**
         * 自动计算框架面积
         *
         * @param Width
         * @param Height
         */
        private static void CalculateScaling(int Width, int Height) {
            boolean isFullscreen = Minecraft.getInstance().getWindow().isFullscreen();
            new Thread(() -> {
                // 如果不是全屏，计算可视区域
                if (isFullscreen) {
                    setPosition(Width, Height);
                } else {
                    setPosition(Width - Windows_Frame_Width, Height - Windows_Frame_Height);
                }
            }).start();

        }

        /**
         * 手动计算框架面积
         */
        public static void onScreenResize() {
            user32.GetWindowRect(MinecrafthWndParent, rect);
            CalculateScaling((rect.right - rect.left), (rect.bottom - rect.top));
        }

    }

    /**
     * 重叠窗口
     *
     * @param childWindowHandle
     * @param parentWindowHandle
     */
    private void OverlapWindows(HWND childWindowHandle, HWND parentWindowHandle) {
        User32.WNDENUMPROC enumChildWindowsCallback = (browserWinHWND, lParam) -> {
            overlapWindows.user32.SetParent(parentWindowHandle, childWindowHandle);
            return true;
        };
        overlapWindows.user32.EnumChildWindows(parentWindowHandle, enumChildWindowsCallback, Pointer.NULL);
    }
}
