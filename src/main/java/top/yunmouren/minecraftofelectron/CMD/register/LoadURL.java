package top.yunmouren.minecraftofelectron.CMD.register;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import static top.yunmouren.minecraftofelectron.tools.MinecraftNetWork.SendPack.serverSendPack;

public class LoadURL implements Command<CommandSource> {
    public static LoadURL instance = new LoadURL();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Entity onlinePlayerNames = context.getSource().getEntity();
        String url = StringArgumentType.getString(context, "url");
        if (onlinePlayerNames instanceof PlayerEntity) {
            serverSendPack(onlinePlayerNames.getDisplayName().getString(), "LoadURL " + url);
        }
        return 0;
    }
}
