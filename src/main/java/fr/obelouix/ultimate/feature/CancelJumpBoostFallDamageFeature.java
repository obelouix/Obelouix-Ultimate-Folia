package fr.obelouix.ultimate.feature;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class CancelJumpBoostFallDamageFeature implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJumpBoostFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && entity.hasPotionEffect(PotionEffectType.JUMP)) {
            event.setCancelled(true);
        }
    }

}
