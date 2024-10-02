package top.yunmouren.electron.Browser;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Browser.tools.SimpleTcpClient;
import top.yunmouren.electron.Browser.tools.WindowsApi;
import top.yunmouren.electron.Electron;

import java.io.*;
import java.net.ServerSocket;
import java.util.Map;


@OnlyIn(Dist.CLIENT)
public class Browser {
    public WindowsApi Api = new WindowsApi();
    private Process process;
    private final String BroswerPath = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "\\minecraft_of_electron\\minecraft_of_electron.exe";
    public int BrowserPort = RandomPort();

    public SimpleTcpClient NodeJs = new SimpleTcpClient();
    public Browser() {
        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(BroswerPath);
                Map<String, String> environment = processBuilder.environment();
                environment.put("LANG", "en_US.UTF-8");
                environment.put("BrowserPort", String.valueOf(BrowserPort));
                process = processBuilder.start();
                handleOutput(process);
            } catch (IOException e) {
                Electron.logger.info(e.getMessage());
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (process != null) process.destroyForcibly();
        }));
    }

    private void handleOutput(Process process) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Electron.logger.info(line);
                }
            } catch (IOException e) {
                Electron.logger.info("Error reading process output: " + e.getMessage());
            }
        }).start();
    }


    private int RandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            Electron.logger.info(e.getMessage());
        }
        return RandomPort();
    }
}
