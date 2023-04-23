package fr.obelouix.ultimate.feature;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FastFurnaceFeature implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        final double multiplier = ObelouixUltimate.getInstance().getPluginConfig().getFastFurnaceSpeedMultiplier();
        Furnace furnace = (Furnace) event.getBlock().getState();
        furnace.setCookSpeedMultiplier((float) multiplier);

    }

}
