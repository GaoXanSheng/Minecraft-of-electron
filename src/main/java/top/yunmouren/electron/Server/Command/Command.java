package top.yunmouren.electron.Server.Command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import top.yunmouren.electron.Server.Server;
import top.yunmouren.electron.Server.Network.SimpleNetwork;

public class Command {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("electron")
                        .requires(source -> source.hasPermission(2))
                        .then(
                                Commands.argument("PlayerName", EntityArgument.player())
                                        .then(Commands.literal("joinGUI").executes(Command::joinGUI))
                                        .then(Commands.literal("exitGUI").executes(Command::exitGUI))
                                        .then(Commands.literal("openDevTools").executes(Command::openDevTools))
                                        .then(Commands.literal("RecalculateArea").executes(Command::RecalculateArea))
                                        .then(
                                                Commands.literal("loadUrl").then(Commands.argument("url", StringArgumentType.string())
                                                        .executes(Command::loadUrl)
                                                )
                                        )
                        )
        );
    }
    private static ServerPlayer getPlayer (CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return EntityArgument.getPlayer(context, "PlayerName").connection.getPlayer();
    }

    private static int RecalculateArea(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = getPlayer(context);
        Server.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SimpleNetwork("RecalculateArea"));
        return 1;
    }

    private static int loadUrl(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String argument = StringArgumentType.getString(context, "url");
        ServerPlayer player = getPlayer(context);
        Server.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SimpleNetwork("loadUrl", argument));
        return 1;
    }

    private static int openDevTools(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = getPlayer(context);
        Server.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SimpleNetwork("openDevTools"));
        return 1;
    }

    private static int exitGUI(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = getPlayer(context);
        Server.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SimpleNetwork("exitGUI"));
        return 1;
    }

    private static int joinGUI(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = getPlayer(context);
        Server.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SimpleNetwork("joinGUI"));
        return 1;
    }
}
