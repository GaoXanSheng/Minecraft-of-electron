package top.yunmouren.minecraftofelectron;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.yunmouren.minecraftofelectron.CMD.ElectronCommand;
import top.yunmouren.minecraftofelectron.tools.MinecraftNetWork.SimpleNetworkWrapper;


import static top.yunmouren.minecraftofelectron.ipc.*;

@Mod("minecraftofelectron")
public class main {
    private static final Logger LOGGER = LogManager.getLogger();

    public main() {
        runMinecraft_of_electron();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void onCommonSetup(FMLCommonSetupEvent event) {
        SimpleNetworkWrapper.registerMessage();
    }
    @OnlyIn(Dist.CLIENT)
    private void onClientSetup(final FMLClientSetupEvent event) {
        try {
            Thread.sleep(5000);
            sendClientInfo();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
