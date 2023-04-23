package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.I18N.TranslationRegistry;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.CommandRegistration;
import fr.obelouix.ultimate.data.GlobalData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CoordinatesCommand extends CommandRegistration {

    private static final StringArgument.Builder<CommandSender> STATE = StringArgument.builder("state");

    @Override
    public void register() {

        STATE.withSuggestionsProvider((context, input) -> ImmutableList.of("on", "off"));

        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("coordinates", "coords")
                        .meta(CommandMeta.DESCRIPTION, "enable/disable coordinates HUD")
                        .permission("obelouix.commands.coordinates")
                        .argument(STATE, ArgumentDescription.of("Enable or disable coordinates HUD"))
                        .handler(this::execute)
                        .senderType(Player.class)
                        .build()
        );
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        Player sender = (Player) context.getSender();

        if (context.contains("state")) {
            String arg = context.get("state");
            switch (arg) {
                case "on" -> showCoordsActionBar(sender, true);
                case "off" -> showCoordsActionBar(sender, false);
            }
        }
    }

    private void showCoordsActionBar(Player player, boolean state) {
        if (state) {
            if (GlobalData.getPlayerCoordinatesSet().contains(player)) {
                MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(TranslationRegistry.COMMAND_COORDS_ALREADY_ENABLED.getTranslationKey(),
                        NamedTextColor.DARK_RED), player.locale()));
            } else {
                GlobalData.getPlayerCoordinatesSet().add(player);
                MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(TranslationRegistry.COMMAND_COORDS_ENABLED.getTranslationKey(),
                        NamedTextColor.GREEN), player.locale()));
            }
        } else {
            if (!GlobalData.getPlayerCoordinatesSet().contains(player)) {
                MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(TranslationRegistry.COMMAND_COORDS_ALREADY_DISABLED.getTranslationKey(),
                        NamedTextColor.DARK_RED), player.locale()));
            } else {
                GlobalData.getPlayerCoordinatesSet().remove(player);
                MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(TranslationRegistry.COMMAND_COORDS_DISABLED.getTranslationKey(),
                        NamedTextColor.GREEN), player.locale()));
            }
        }
    }

}