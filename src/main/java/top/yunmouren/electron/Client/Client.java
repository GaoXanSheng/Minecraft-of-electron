package top.yunmouren.electron.Client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.yunmouren.electron.Client.Tools.SystemHook;
import top.yunmouren.electron.Client.Tools.overlapWindows;
import top.yunmouren.electron.Electron;

import static top.yunmouren.electron.Client.Tools.IPC.runMinecraft_of_electron;

@OnlyIn(Dist.CLIENT)
public class Client {
    public Client(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }
    public void onClientSetup(final FMLClientSetupEvent event) {
        runMinecraft_of_electron();
        new SystemHook();
        try {
            Thread.sleep(3000);
            new overlapWindows();
        } catch (InterruptedException e) {
            Electron.logger.error(e.getMessage());
        }
    }
}
