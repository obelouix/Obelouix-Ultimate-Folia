package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Path;

public class PlayerConfig {

    private static final Path configPathFolder = Path.of(ObelouixUltimate.getInstance().getDataFolder().getPath(), "data", "players");
    private static CommentedConfigurationNode rootNode;
    private static HoconConfigurationLoader configLoader;
    private final Player player;

    public PlayerConfig(Player player) {
        this.player = player;
    }

    public final void init() {
        try {
            configLoader = HoconConfigurationLoader.builder()
                    .path(configPathFolder.resolve(player.getUniqueId() + ".conf"))
                    .build();

            rootNode = configLoader.load();

            addData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addData() throws SerializationException {
        // coordinates are enabled by default
        if (rootNode.node("show-coordinates").empty()) {
            rootNode.node("show-coordinates").set(true);
        }
        saveConfig();
    }

    public final void saveConfig() {
        try {
            configLoader.save(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final CommentedConfigurationNode getRootNode() {
        return rootNode;
    }

    public final boolean showCoordinates() {
        return rootNode.node("show-coordinates").getBoolean();
    }

    public final void setShowCoordinates(boolean showCoordinates) {
        try {
            rootNode.node("show-coordinates").set(showCoordinates);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
        saveConfig();
    }

}
