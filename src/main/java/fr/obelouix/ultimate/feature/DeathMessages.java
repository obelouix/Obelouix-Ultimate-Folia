package fr.obelouix.ultimate.feature;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

public class DeathMessages implements Listener {

    private static final Translator translator = ObelouixUltimate.getInstance().getTranslator();
    private static final Set<String> deathMessages = ObelouixUltimate.getInstance().getTranslator().getDeathMessages();

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event) {
        final Entity victim = event.getEntity();
        final ServerPlayer serverPlayerVictim = ((CraftPlayer) victim).getHandle();
        final Entity killer = event.getEntity().getKiller();
        final CombatTracker combatTracker = serverPlayerVictim.getCombatTracker();
        final CombatEntry combatEntry = combatTracker.getLastEntry();
        final DamageSource damageSource = combatTracker.getLastEntry().getSource();
        Optional<BlockPos> optional = serverPlayerVictim.getLastClimbablePos();
        List<String> filteredDeathMessages = null;
        String selectedDeathMessage = null;

        if (damageSource.is(DamageTypes.FALL)) {
            if (combatEntry.getLocation() == null) {
                filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.generic")).toList();
            } else {
                if (optional.isPresent() && io.papermc.paper.util.TickThread.isTickThreadFor((ServerLevel) serverPlayerVictim.level, optional.get())) {
                    BlockState blockState = serverPlayerVictim.level.getBlockState(optional.get());
                    if (blockState.is(Blocks.LADDER)) {
                        filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.ladder")).toList();
                    } else if (blockState.is(Blocks.VINE)) {
                        filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.vines")).toList();
                    }/* else if (blockState.is(Blocks.WEEPING_VINES)) {
                filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.weeping_vines")).toList();
                System.out.println(filteredDeathMessages);
            } else if (blockState.is(Blocks.TWISTING_VINES)) {
                filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.twisting_vines")).toList();
                System.out.println(filteredDeathMessages);
            }*/ else if (blockState.is(Blocks.SCAFFOLDING)) {
                        filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.fell.accident.scaffolding")).toList();
                    }
                }
            }
        } else if (damageSource.is(DamageTypes.STARVE)) {
            filteredDeathMessages = deathMessages.stream().filter(message -> message.contains("death.starvation")).toList();
        }

        if (filteredDeathMessages != null) {
            Random random = new Random();
            selectedDeathMessage = filteredDeathMessages.get(random.nextInt(filteredDeathMessages.size()));
        }
        //event.deathMessage(prepareDeathMessage(selectDeathMessage(victim, killer), selectedDeathMessage));
        event.deathMessage(filteredDeathMessages != null ? prepareDeathMessage(victim, killer, selectedDeathMessage) : null);
    }

    private List<String> filterDeathMessages(String deathMessage) {
        return deathMessages.stream().filter(message -> message.contains(deathMessage)).toList();
    }

    private String getRandomDeathMessage(List<String> filteredDeathMessages) {
        Random random = new Random();
        return filteredDeathMessages.get(random.nextInt(filteredDeathMessages.size()));
    }

    /*private String selectDeathMessage(Entity victim, Entity killer) {
        final ServerPlayer serverPlayerVictim = ((CraftPlayer) victim).getHandle();
        final ServerPlayer serverPlayerKiller = ((CraftPlayer) killer).getHandle();
        final CombatTracker combatTracker = serverPlayerVictim.getCombatTracker();
        final CombatEntry combatEntry = combatTracker.getLastEntry();
        final DamageSource damageSource = combatTracker.getLastEntry().getSource();

        Optional<BlockPos> optional = serverPlayerVictim.getLastClimbablePos();

        if(damageSource.is(DamageTypes.FALL)) {
            if(getFallLocation(combatEntry) == null) {
                return getRandomDeathMessage(filterDeathMessages("death.fell.accident.generic"));
            } else {
                if (optional.isPresent() && io.papermc.paper.util.TickThread.isTickThreadFor((ServerLevel) serverPlayerVictim.level, optional.get())) {
                    BlockState blockState = serverPlayerVictim.level.getBlockState(optional.get());
                    if (blockState.is(Blocks.LADDER)) {
                        return getRandomDeathMessage(filterDeathMessages("death.fell.accident.ladder"));
                    } else if (blockState.is(Blocks.VINE)) {
                        return getRandomDeathMessage(filterDeathMessages("death.fell.accident.vines"));
                    }

                }
            }
        }

        return selectedDeathMessage;
    }*/

    private String getFallLocation(CombatEntry combatEntry) {
        return combatEntry.getLocation();
    }


    private Component prepareDeathMessage(Entity victim, Entity killer, String selectedDeathMessage) {

        final Component hoverComponent = Component.translatable()
                .color(NamedTextColor.GRAY)
                .key("obelouix.message.click_to_send_private_message")
                .args(Component.text(victim.getName(), NamedTextColor.DARK_RED))
                .build();

        return Component.translatable()
                .color(NamedTextColor.DARK_AQUA)
                .key(selectedDeathMessage)
                .args(Component.text(victim.getName(), NamedTextColor.DARK_RED)
                        .hoverEvent(HoverEvent.showText(hoverComponent))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + victim.getName() + " ")))
                .build();
    }

    private Component translate(TranslatableComponent component, Entity victim) {

        return translator.getRenderer().render(
                Component.translatable().color(NamedTextColor.DARK_AQUA)
                        .key(component.key())
                        .args(
                                Component.text(victim.getName(), NamedTextColor.DARK_RED)
                        )
                        .build(), Locale.ENGLISH);
    }

}
