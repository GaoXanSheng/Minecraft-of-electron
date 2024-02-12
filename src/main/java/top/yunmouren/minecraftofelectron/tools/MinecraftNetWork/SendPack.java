package top.yunmouren.minecraftofelectron.tools.MinecraftNetWork;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

import static top.yunmouren.minecraftofelectron.ipc.*;

public class SendPack {
    private String message;
    private static final Logger LOGGER = LogManager.getLogger();

    public SendPack(PacketBuffer buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public SendPack(String message) {
        this.message = message;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(this.message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DeCode(this.message);
        });
        ctx.get().setPacketHandled(true);
    }

    public void DeCode(String code) {
        String[] strings = code.split(" ");
        String handler = strings[0];
        switch (handler) {
            case "LoadURL":
                LoadUrl(strings[1]);
                break;
            case "exitGui":
                exitGui();
                break;
            case "joinGui":
                joinGui();
                break;
            case "loadInit":
                loadInit();
                break;
            case "openDevTools":
                openDevTools();
                break;
            default:
                LOGGER.info("未知的处理器：" + code);
                break;
        }
    }

    public static boolean serverSendPack(String playerName, String message) {
        // 获取服务器实例
        net.minecraft.server.MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) {
            // 服务器实例不存在，无法发送数据包
            return false;
        }

        // 获取所有在线玩家
        List<ServerPlayerEntity> players = server.getPlayerList().getPlayers();

        for (ServerPlayerEntity player : players) {
            if (player.getName().getString().equals(playerName)) {
                // 找到匹配的玩家，向其发送数据包
                SendPack packet = new SendPack(message);
                SimpleNetworkWrapper.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
                return true;
            }
        }

        // 如果未找到匹配的玩家，则返回false
        return false;
    }
}
