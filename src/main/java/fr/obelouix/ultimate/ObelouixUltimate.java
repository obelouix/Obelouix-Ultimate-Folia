package fr.obelouix.ultimate;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.commands.manager.ObelouixCommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.events.EventManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;

public class ObelouixUltimate extends JavaPlugin {

    private static ObelouixUltimate instance;
    private static Config config;
    private static Translator translator;

    public static ObelouixUltimate getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        config = new Config();
        translator = new Translator();

        config.loadConfig();
        translator.init();
        EventManager.init();
        ObelouixCommandManager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        config.saveConfig();
        config = null;
        translator = null;
    }

    public Config getPluginConfig() {
        return config;
    }

    public Translator getTranslator() {
        return translator;
    }

    public FloodgateApi getFloodgateApi() {
        return FloodgateApi.getInstance();
    }

    public boolean isFloodGateInstalled() {
        try {
            Class.forName("org.geysermc.floodgate.api.FloodgateApi");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean isWorldEditInstalled() {
        try {
            Class.forName("com.sk89q.worldedit.bukkit.WorldEditPlugin");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}
