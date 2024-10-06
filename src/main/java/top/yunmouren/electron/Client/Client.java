package top.yunmouren.electron.Client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.yunmouren.electron.Browser.Browser;
import top.yunmouren.electron.Browser.Handler.Handler;
import top.yunmouren.electron.Browser.Handler.entry.*;
import top.yunmouren.electron.Electron;


@OnlyIn(Dist.CLIENT)
public class Client {

    public static Browser browser = new Browser();
    public Client(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        RegisterHandler();
    }
    public void RegisterHandler(){
        Handler.register("ExitGui",ExitGui.class);
        Handler.register("JoinGui", JoinGui.class);
        Handler.register("LoadFile", LoadFile.class);
        Handler.register("EnterFullScreen", EnterFullScreen.class);
        Handler.register("LeaveFullScreen", LeaveFullScreen.class);
    }
    public void onClientSetup(final FMLClientSetupEvent event) {
        try {
            Thread.sleep(3000);
            browser.Api.overlapWindows();
            browser.NodeJs.start("localhost", 9090);
//            browser.NodeJs.start("localhost", browser.BrowserPort);
        } catch (InterruptedException e) {
            Electron.logger.error(e.getMessage());
        }
    }
}
