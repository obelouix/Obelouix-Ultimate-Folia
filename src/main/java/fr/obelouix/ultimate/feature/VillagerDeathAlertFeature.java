package fr.obelouix.ultimate.feature;

import fr.obelouix.ultimate.I18N.BedrockTranslations;
import fr.obelouix.ultimate.I18N.MinecraftTranslations;
import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VillagerDeathAlertFeature implements Listener {

    private static final ObelouixUltimate PLUGIN = ObelouixUltimate.getInstance();

    private static final boolean PER_WORLD_ALERT = ObelouixUltimate.getInstance().getPluginConfig().perWorldAlertVillagerDeath();
    private static final List<World> WORLDS_BLACKLIST = new ArrayList<>();
    private static final String VILLAGER_DEATH_ALERT_PERMISSION = "obelouix.ultimate.alert.villagerdeath";

    public VillagerDeathAlertFeature() {
        for (String worldName : ObelouixUltimate.getInstance().getPluginConfig().getVillagerDeathWorldBlacklist()) {
            if (ObelouixUltimate.getInstance().getServer().getWorld(worldName) != null) {
                WORLDS_BLACKLIST.add(ObelouixUltimate.getInstance().getServer().getWorld(worldName));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVillagerDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof final Villager villager)) return;

        if (PER_WORLD_ALERT) {
            ObelouixUltimate.getInstance().getServer().getWorlds().forEach(world -> {
                if (!WORLDS_BLACKLIST.contains(world)) {
                    world.getPlayers()
                            .stream().filter(player -> player.hasPermission(VILLAGER_DEATH_ALERT_PERMISSION))
                            .forEach(player -> {
                                boolean isBedrockPlayer = PLUGIN.isFloodGateInstalled() && PLUGIN.getFloodgateApi().isFloodgateId(player.getUniqueId());
                                player.sendMessage(villagerDeathMessageComponent(villager, isBedrockPlayer));
                            });
                }
            });
        }
    }

    private Component villagerDeathMessageComponent(Villager villager, boolean isBedrockPlayer) {

        final TranslatableComponent.@NotNull Builder builder = Component.translatable().color(NamedTextColor.DARK_RED);
        // Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(villagerName.asComponent()));

        return Component.translatable()
                .color(NamedTextColor.DARK_RED)
                .key(selectTranslationKey(villager))
                .args(addArguments(villager, isBedrockPlayer))
                .build();
    }

    private String selectTranslationKey(Villager villager) {
        // if the villager has a custom name, we use the translation key with the custom name else we use the translation key without the custom name
        return villager.customName() != null ? "obelouix.messages.villager_death_alert.custom_name" : "obelouix.messages.villager_death_alert";
    }

    private List<ComponentLike> addArguments(Villager villager, boolean isBedrockPlayer) {
        final List<ComponentLike> arguments = new ArrayList<>();

        // if the villager has a custom name, we add it to the arguments list
        if (villager.customName() != null) arguments.add(villager.customName());

        // Select the key based on the platform (bedrock or java)
        final TranslatableComponent villagerPlatformTranslation = isBedrockPlayer ? BedrockTranslations.ENTITY_VILLAGER
                : MinecraftTranslations.ENTITY_VILLAGER;

        arguments.add(villagerPlatformTranslation.color(NamedTextColor.DARK_GREEN));
        arguments.add(Component.text(villager.getWorld().getName(), NamedTextColor.GOLD));
        arguments.add(Component.text(villager.getLocation().getBlockX(), NamedTextColor.GOLD));
        arguments.add(Component.text(villager.getLocation().getBlockY(), NamedTextColor.GOLD));
        arguments.add(Component.text(villager.getLocation().getBlockZ(), NamedTextColor.GOLD));
        return arguments;
    }
}
