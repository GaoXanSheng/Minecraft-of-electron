package top.yunmouren.minecraftofelectron;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.minecraftofelectron.Models.WindowInfo;
import top.yunmouren.minecraftofelectron.Models.loadUrl;
import top.yunmouren.minecraftofelectron.tools.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class ipc {
    private static final int port = 9090;

    @OnlyIn(Dist.CLIENT)
    public static void runMinecraft_of_electron() {
        new Thread(() -> {
            String command = getFileCmd();
            try {
                // 使用ProcessBuilder构建命令
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                // 设置环境变量，确保使用UTF-8编码
                Map<String, String> environment = processBuilder.environment();
                environment.put("LANG", "en_US.UTF-8");
                // 启动进程
                Process process = processBuilder.start();

                // 获取进程的输出流
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                // 使用流处理读取进程的输出
                String result = reader.lines().collect(Collectors.joining("\n"));

                // 打印结果
                System.out.println("Output: \n" + result);

                // 等待进程结束
                int exitCode = process.waitFor();
                System.out.println("Exit Code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendClientInfo() {
        http.post(api.init, new Gson().toJson(new WindowInfo()));
    }


    public static String getFileCmd() {
        String gameDir = Minecraft.getInstance().gameDirectory.getAbsolutePath();
        return gameDir.substring(0, gameDir.length() - 1) + "t\\minecraft_of_electron\\minecraft_of_electron.exe";
    }

    public static void LoadUrl(String url) {
        http.post(api.loadUrl, new Gson().toJson(new loadUrl(url)));
    }

    public static void joinGui() {
        http.post(api.joinGui,"{}");
    }

    public static void exitGui() {
        http.post(api.exitGui, "{}");
    }
    public static void openDevTools() {
        http.post(api.openDevTools, "{}");
    }
    public static void loadInit() {
        http.post(api.loadInit, "{}");
    }

    public static class api {
        public static String init = "http://127.0.0.1:" + port + "/api/init";
        public static String loadUrl = "http://127.0.0.1:" + port + "/api/loadUrl";
        public static String joinGui = "http://127.0.0.1:" + port + "/api/joinGui";
        public static String exitGui = "http://127.0.0.1:" + port + "/api/exitGui";
        public static String loadInit = "http://127.0.0.1:" + port + "/api/loadInit";
        public static String openDevTools = "http://127.0.0.1:" + port + "/api/openDevTools";
    }
}
