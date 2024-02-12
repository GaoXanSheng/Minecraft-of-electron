package top.yunmouren.minecraftofelectron.tools.MinecraftNetWork;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SimpleNetworkWrapper {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("neutrino", "first_networking"),
                () -> {
                    return VERSION;
                },
                (version) -> {
                    return version.equals(VERSION);
                },
                (version) -> {
                    return version.equals(VERSION);
                });
        INSTANCE.registerMessage(
                nextID(),
                SendPack.class,
                SendPack::toBytes,
                SendPack::new,
                SendPack::handler
        );
    }

}