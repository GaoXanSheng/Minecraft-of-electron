package top.yunmouren.electron;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;
import top.yunmouren.electron.Client.Client;
import top.yunmouren.electron.Server.Server;
import top.yunmouren.electron.Server.Tools.Log;

@Mod("electron")
public class Electron {
    public static final Log logger = new Log();
    public static final String MOD_ID = "electron";
    public Electron() {
        if (FMLLoader.getLaunchHandler().getDist() == Dist.CLIENT){
            new Client();
        }
        new Server();
    }
}
