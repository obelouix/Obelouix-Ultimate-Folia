package fr.obelouix.ultimate.data;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.PlayerConfig;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to store data that will be used by multiple classes
 * to avoid reading the config file every time and reduce disk access
 */
public class GlobalData implements Listener {

    /**
     * a set of players that want to see their coordinates on the action bar
     */
    private static final Set<Player> PLAYER_TO_SHOW_COORDINATES = new HashSet<>();
    private static PlayerConfig playerConfig;

    /**
     * Return a set of players that want to see their coordinates on the action bar
     *
     * @return a set of players that want to see their coordinates
     */
    public static Set<Player> getPlayerCoordinatesSet() {
        return PLAYER_TO_SHOW_COORDINATES;
    }

    /**
     * Store various data when a player joins the server
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        playerConfig = new PlayerConfig(player);

        if (ObelouixUltimate.getInstance().getPluginConfig().isCustomServerNameEnabled()) {
            ByteArrayDataOutput data = ByteStreams.newDataOutput();
            data.writeUTF("minecraft:brand");
            data.writeUTF(ObelouixUltimate.getInstance().getPluginConfig().getCustomServerName());

            ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(
                    new ResourceLocation("minecraft:brand"),
                    new FriendlyByteBuf(Unpooled.buffer()).writeUtf(ObelouixUltimate.getInstance().getPluginConfig().getCustomServerName()));

            ((CraftPlayer) player).getHandle().connection.send(packet);
        }

        // load the player config file or create it first if it doesn't exist
        playerConfig.init();
        /* add the player to the set if he wants to see his coordinates
         * to avoid reading the config file every time and reduce disk access
         */
        if (playerConfig.showCoordinates()) {
            PLAYER_TO_SHOW_COORDINATES.add(player);
        }
    }

    /**
     * Remove a player everywhere he is stored when he leaves the server
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        boolean shouldSaveConfig = false;

        // update the config file with the new value if it has changed
        if (playerConfig.showCoordinates() != PLAYER_TO_SHOW_COORDINATES.contains(player)) {
            playerConfig.setShowCoordinates(PLAYER_TO_SHOW_COORDINATES.contains(player));
            shouldSaveConfig = true;
        }

        // save the config file if it has been modified
        if (shouldSaveConfig) {
            playerConfig.saveConfig();
        }
        PLAYER_TO_SHOW_COORDINATES.removeIf(player1 -> player1.equals(player));
    }

}
