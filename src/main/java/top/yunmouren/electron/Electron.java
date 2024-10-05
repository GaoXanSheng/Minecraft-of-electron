package top.yunmouren.electron;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;
import top.yunmouren.electron.Client.Client;
import top.yunmouren.electron.Server.Server;

@Mod("electron")
public class Electron {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MOD_ID = "electron";
    public Electron() {
        if (FMLLoader.getLaunchHandler().getDist() == Dist.CLIENT){
            new Client();
        }
        new Server();
    }
}
