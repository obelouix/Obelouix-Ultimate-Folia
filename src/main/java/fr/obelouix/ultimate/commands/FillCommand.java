package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.bukkit.parsers.location.LocationArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.sk89q.worldedit.WorldEdit;
import fr.obelouix.ultimate.commands.manager.CommandRegistration;
import fr.obelouix.ultimate.commands.parsers.BlockArgument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class FillCommand extends CommandRegistration {

    private static final WorldEdit worldEdit = plugin.isWorldEditInstalled() ? WorldEdit.getInstance() : null;

    @Override
    protected void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("fill")
                        .meta(CommandMeta.DESCRIPTION, "fill a region with a block")
                        .permission("obelouix.commands.fill")
                        .handler(this::execute)
                        .senderType(Player.class)
                        .argument(BlockArgument.of("block"))
                        .argument(LocationArgument.of("from"), ArgumentDescription.of("location"))
                        .argument(LocationArgument.of("to"), ArgumentDescription.of("location"))
                        .build()
        );
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        final Player player = (Player) context.getSender();
        final Material material = context.get("block");
        final Location from = context.get("from");
        final Location to = context.get("to");

        Bukkit.getRegionScheduler().execute(plugin, player.getWorld(), player.getLocation().getChunk().getX(),
                player.getLocation().getChunk().getX(), () -> {

                    if (worldEdit != null) {

/*                        final World world =  BukkitAdapter.adapt(player.getWorld());
                        final Region region;
                        try (EditSession editSession = worldEdit.newEditSessionBuilder().world(world).build()) {
                            region = new CuboidRegion(world, BukkitAdapter.asBlockVector(from), BukkitAdapter.asBlockVector(to));
                            editSession.setBlocks(region, BukkitAdapter.asBlockType(material));
                            player.sendMessage("Filled " + region.getVolume() + " blocks");
                            editSession.flushQueue();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    } else {
                        int count = 0;

                        for (int x = from.getBlockX(); x <= to.getBlockX(); x++) {
                            for (int y = from.getBlockY(); y <= to.getBlockY(); y++) {
                                for (int z = from.getBlockZ(); z <= to.getBlockZ(); z++) {
                                    final Block block = player.getWorld().getBlockAt(x, y, z);
                                    if (block.getType() != material) {
                                        block.setType(material);
                                        count++;
                                    }
                                }
                            }
                        }
                        player.sendMessage("Filled " + count + " blocks");
                    }


                });


    }
}
