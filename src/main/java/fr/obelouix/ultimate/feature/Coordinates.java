package fr.obelouix.ultimate.feature;

import fr.obelouix.ultimate.I18N.TranslationRegistry;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.GlobalData;
import fr.obelouix.ultimate.task.Task;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Coordinates implements Listener {

    private static final Config config = ObelouixUltimate.getInstance().getPluginConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // if the player is the first one to join the server, we start the task
        if (!Bukkit.getOnlinePlayers().isEmpty() && Bukkit.getOnlinePlayers().size() == 1) {

            ObelouixUltimate.getInstance().getComponentLogger().info(Component.text("First player online, starting coordinates task", NamedTextColor.GREEN));

            Bukkit.getGlobalRegionScheduler().runAtFixedRate(ObelouixUltimate.getInstance(), task -> {
                // if there is no player online, we cancel the task
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    ObelouixUltimate.getInstance().getComponentLogger().info(Component.text("No player online, stopping coordinates task", NamedTextColor.GREEN));
                    Task.wrapFolia(task).cancel();

                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (GlobalData.getPlayerCoordinatesSet().contains(player) && !config.getCoordinatesBlacklist().contains(player.getWorld())) {
                            final String playerFacing = PlainTextComponentSerializer.plainText().serialize(GlobalTranslator.render(Component.translatable(facing(player)), player.locale()));

                            final Component actionBar = Component.text("X: ", NamedTextColor.DARK_RED)
                                    .append(Component.text(player.getLocation().getBlockX(), NamedTextColor.WHITE))
                                    .append(Component.text(" Y: ", NamedTextColor.GREEN))
                                    .append(Component.text(player.getLocation().getBlockY(), NamedTextColor.WHITE))
                                    .append(Component.text(" Z: ", NamedTextColor.DARK_BLUE))
                                    .append(Component.text(player.getLocation().getBlockZ(), NamedTextColor.WHITE))
                                    .append(Component.text(" " + timeTo24(player.getWorld().getTime()) + " ", NamedTextColor.GOLD))
                                    .append(Component.translatable(TranslationRegistry.DIRECTION.getTranslationKey(), NamedTextColor.AQUA)
                                            .append(Component.text(": ")))
                                    .append(Component.text(StringUtils.capitalize(playerFacing), NamedTextColor.WHITE));

                            player.sendActionBar(actionBar);
                        }
                    });

                }
            }, 1, 5);
        }
    }

    /**
     * Get the direction the player is facing
     *
     * @param p the player
     * @return the direction the player is facing
     */
    private String facing(final Player p) {
        final double yaw = p.getLocation().getYaw();

        if (yaw >= 337.5 || yaw <= 22.5 && yaw >= 0.0 || yaw >= -22.5 && yaw <= 0.0 || yaw <= -337.5) {
            return TranslationRegistry.DIRECTION_SOUTH.getTranslationKey();
        }
        if (yaw >= 22.5 && yaw <= 67.5 || yaw <= -292.5) {
            return TranslationRegistry.DIRECTION_SOUTH_WEST.getTranslationKey();
        }
        if (yaw >= 67.5 && yaw <= 112.5 || yaw <= -247.5) {
            return TranslationRegistry.DIRECTION_WEST.getTranslationKey();
        }
        if (yaw >= 112.5 && yaw <= 157.5 || yaw <= -202.5) {
            return TranslationRegistry.DIRECTION_NORTH_WEST.getTranslationKey();
        }
        if (yaw >= 157.5 && yaw <= 202.5 || yaw <= -157.5) {
            return TranslationRegistry.DIRECTION_NORTH.getTranslationKey();
        }
        if (yaw >= 202.5 && yaw <= 247.5 || yaw <= -112.5) {
            return TranslationRegistry.DIRECTION_NORTH_EAST.getTranslationKey();
        }
        if (yaw >= 247.5 && yaw <= 292.5 || yaw <= -67.5) {
            return TranslationRegistry.DIRECTION_EAST.getTranslationKey();
        }
        if (yaw >= 292.5 || yaw <= -22.5) {
            return TranslationRegistry.DIRECTION_SOUTH_EAST.getTranslationKey();
        }
        return "error";
    }

    /**
     * Convert Minecraft time to 24h format
     *
     * @param time the time in ticks
     * @return the time in 24h format
     */
    private String timeTo24(long time) {
        long hours = (time / 1000 + 6) % 24;
        long minutes = (time % 1000) * 60 / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }


}
