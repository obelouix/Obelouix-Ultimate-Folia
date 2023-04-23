package fr.obelouix.ultimate.events;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CustomServerListPing implements Listener {

    @EventHandler
    public void handlePing(final @NonNull PaperServerListPingEvent event) {

        // Set the version to show in the server list if the player use an incompatible version
        event.setVersion(ObelouixUltimate.getInstance().getPluginConfig().getCustomListPingVersion());
        event.motd(Component.text("Folia Dev Server", NamedTextColor.GOLD));
    }

}
