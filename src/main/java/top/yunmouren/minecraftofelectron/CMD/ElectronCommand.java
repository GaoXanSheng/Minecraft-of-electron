package top.yunmouren.minecraftofelectron.CMD;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yunmouren.minecraftofelectron.CMD.register.*;


@Mod.EventBusSubscriber
public class ElectronCommand {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("electron")
                        .then(loadURLBuild())
                        .then(joinGuiBuild())
                        .then(exitGuiBuild())
                        .then(loadInitBuild())
                        .then(openDevToolsBuild()));

    }

    public static LiteralArgumentBuilder<CommandSource> loadURLBuild() {
        return Commands.literal("loadUrl")
                .then(Commands.argument("url", StringArgumentType.string())
                        .executes(LoadURL.instance).requires((commandSource) -> commandSource.hasPermission(4))
                        .build()
                );
    }

    public static LiteralArgumentBuilder<CommandSource> joinGuiBuild() {
        return Commands.literal("joinGui").executes(joinGui.instance).requires((commandSource) -> commandSource.hasPermission(4)).build().createBuilder();
    }
    public static LiteralArgumentBuilder<CommandSource> exitGuiBuild() {
        return Commands.literal("exitGui").executes(exitGui.instance).requires((commandSource) -> commandSource.hasPermission(4)).build().createBuilder();
    }
    public static LiteralArgumentBuilder<CommandSource> loadInitBuild() {
        return Commands.literal("loadInit").executes(loadInit.instance).requires((commandSource) -> commandSource.hasPermission(4)).build().createBuilder();
    }
    public static LiteralArgumentBuilder<CommandSource> openDevToolsBuild() {
        return Commands.literal("openDevTools").executes(openDevTools.instance).requires((commandSource) -> commandSource.hasPermission(4)).build().createBuilder();
    }
}