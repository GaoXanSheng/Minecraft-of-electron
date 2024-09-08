package top.yunmouren.electron.Browser;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yunmouren.electron.Electron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import static top.yunmouren.electron.Browser.Api.Api.setPosition;


@OnlyIn(Dist.CLIENT)
public class Browser {
    private Process process;
    private final String BroswerPath = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "\\minecraft_of_electron\\minecraft_of_electron.exe";
    public int Port = RandomPort();

    public Browser() {
        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(BroswerPath);
                Map<String, String> environment = processBuilder.environment();
                environment.put("LANG", "en_US.UTF-8");
                environment.put("Port", String.valueOf(Port));
                process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String result = reader.lines().collect(Collectors.joining("\n"));
                System.out.println("Output: \n" + result);
                int exitCode = process.waitFor();
                System.out.println("Exit Code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                Electron.logger.info(e.getMessage());
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            process.destroyForcibly();
        }));
    }

    private int RandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            Electron.logger.info(e.getMessage());
        }
        return RandomPort();
    }

    public String post(String webUrl, String json) {
        String charset = "UTF-8";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn;
        try {
            URL url = new URL(webUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            conn.setRequestProperty("Accept", "application/json");
            //获取输出流
            out = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            out.write(json);
            out.flush();
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                System.out.println("在运行中发生错误:" + conn.getResponseCode());
                System.out.println(webUrl);
            }
        } catch (Exception e) {
            System.out.println("在运行中发生错误:" + e.getMessage());
            System.out.println(webUrl);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println("在运行中发生错误:" + ioe.getMessage());
                System.out.println(webUrl);
            }
        }
        return result.toString();
    }

    @OnlyIn(Dist.CLIENT)
    public static class overlapWindows {
        // 窗口置顶
        private final WinDef.HWND HWND_TOP = new WinDef.HWND(Pointer.NULL);
        private final String Minecraft_Title = "overlapWindows";
        private final String Web_Title = "TestApp";
        private static WinDef.HWND MinecrafthWndParent = null;
        private static WinDef.HWND browserhWndParent = null;
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
        private void OverlapWindows(WinDef.HWND childWindowHandle, WinDef.HWND parentWindowHandle) {
            User32.WNDENUMPROC enumChildWindowsCallback = (browserWinHWND, lParam) -> {
                overlapWindows.user32.SetParent(parentWindowHandle, childWindowHandle);
                return true;
            };
            overlapWindows.user32.EnumChildWindows(parentWindowHandle, enumChildWindowsCallback, Pointer.NULL);
        }
    }
}
