package top.yunmouren.minecraftofelectron.CMD.register;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import static top.yunmouren.minecraftofelectron.tools.MinecraftNetWork.SendPack.serverSendPack;

public class exitGui  implements Command<CommandSource> {
    public static final exitGui instance = new exitGui();
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Entity onlinePlayerNames = context.getSource().getEntity();
        if (onlinePlayerNames instanceof PlayerEntity) {
            serverSendPack(onlinePlayerNames.getDisplayName().getString(), "exitGui ");
        }
        return 0;
    }
}
