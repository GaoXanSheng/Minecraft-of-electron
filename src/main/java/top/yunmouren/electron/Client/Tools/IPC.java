package top.yunmouren.electron.Client.Tools;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Server.tools.Http.BuildUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Map;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class IPC {
    public static BuildUrl buildUrl = new BuildUrl();
    public static Process process;
    public static int Port = RandomPort();
    public static void runMinecraft_of_electron() {
        new Thread(() -> {
            String command = getFileCmd();
            try {
                // 使用ProcessBuilder构建命令
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                // 设置环境变量，确保使用UTF-8编码
                Map<String, String> environment = processBuilder.environment();
                environment.put("LANG", "en_US.UTF-8");
                environment.put("Port", String.valueOf(Port));
                // 启动进程
                process = processBuilder.start();

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

    public static int RandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RandomPort();
    }

    public static String getFileCmd() {
        String gameDir = Minecraft.getInstance().gameDirectory.getAbsolutePath();
        return gameDir + "\\minecraft_of_electron\\minecraft_of_electron.exe";
    }

}
