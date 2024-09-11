package top.yunmouren.electron.Browser;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.yunmouren.electron.Browser.tools.WindowsApi;
import top.yunmouren.electron.Electron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Map;
import java.util.stream.Collectors;


@OnlyIn(Dist.CLIENT)
public class Browser {
    public WindowsApi Api = new WindowsApi();
    private Process process;
    private final String BroswerPath = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "\\minecraft_of_electron\\minecraft_of_electron.exe";
    public int BrowserPort = RandomPort();
    public int ServerPort = RandomPort();

    public Browser() {
        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(BroswerPath);
                Map<String, String> environment = processBuilder.environment();
                environment.put("LANG", "en_US.UTF-8");
                environment.put("BrowserPort", String.valueOf(BrowserPort));
                environment.put("ServerPort", String.valueOf(ServerPort));
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
}
