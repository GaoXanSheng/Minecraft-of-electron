package top.yunmouren.electron;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import top.yunmouren.electron.Client.Client;
import top.yunmouren.electron.Server.Server;

@Mod("electron")
public class Electron {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "electron";
    public Electron() {
        new Client();
        new Server();
    }
}
