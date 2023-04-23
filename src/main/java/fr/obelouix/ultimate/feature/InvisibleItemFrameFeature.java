package fr.obelouix.ultimate.feature;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InvisibleItemFrameFeature implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemFrameInteract(PlayerInteractEntityEvent event) {

        if (event.getHand().equals(EquipmentSlot.HAND)) {
            final Player player = event.getPlayer();
            if (!player.hasPermission("obelouixultimate.feature.invisibleitemframe") || !player.isSneaking()) return;

            if (event.getRightClicked() instanceof final ItemFrame itemFrame) {
                itemFrame.setVisible(!itemFrame.isVisible());
                itemFrame.setFixed(!itemFrame.isFixed());
            }

        }
    }

}
