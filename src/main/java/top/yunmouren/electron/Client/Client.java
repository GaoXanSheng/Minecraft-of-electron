package top.yunmouren.electron.Client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.yunmouren.electron.Browser.Browser;
import top.yunmouren.electron.Electron;


@OnlyIn(Dist.CLIENT)
public class Client {

    public static Browser browser = new Browser();
    public Client(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    public void onClientSetup(final FMLClientSetupEvent event) {
        try {
            Thread.sleep(3000);
            browser.Api.overlapWindows();
            browser.NodeJs.start("localhost", browser.BrowserPort);
        } catch (InterruptedException e) {
            Electron.logger.error(e.getMessage());
        }
    }
}
