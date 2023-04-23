package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import fr.obelouix.ultimate.commands.manager.CommandRegistration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PVPCommand extends CommandRegistration {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("pvp")
                        .meta(CommandMeta.DESCRIPTION, "enable/disable pvp")
                        .permission("obelouix.commands.pvp")
                        .handler(this::execute)
                        .senderType(Player.class)
                        .build()
        );
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        final Player player = (Player) context.getSender();

    }
}
