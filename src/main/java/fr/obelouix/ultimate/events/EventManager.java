package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.data.GlobalData;
import fr.obelouix.ultimate.feature.*;

public class EventManager extends EventsRegistration {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public static void init() {

        //Global data event
        addEvent(new GlobalData());
//        addEvent(new CustomServerListPing());
        // Coordinates feature
        if (plugin.getPluginConfig().isCoordinatesFeatureEnabled()) {
            addEvent(new Coordinates());
        }

        if (plugin.getPluginConfig().isInvisibleItemFrameEnabled()) {
            addEvent(new InvisibleItemFrameFeature());
        }

        if (plugin.getPluginConfig().isCancelJumpBoostFallDamageEnabled()) {
            addEvent(new CancelJumpBoostFallDamageFeature());
        }

        if (plugin.getPluginConfig().isCustomListPingEnabled()) {
            addEvent(new CustomServerListPing());
        }

        if (plugin.getPluginConfig().isFastFurnaceEnabled()) {
            addEvent(new FastFurnaceFeature());
        }

        if (plugin.getPluginConfig().isAlertOnVillagerDeathEnabled()) {
            addEvent(new VillagerDeathAlertFeature());
        }

        addEvent(new DeathMessages());
        registerEvents();
    }

}
