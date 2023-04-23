package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.CommandTree;
import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.CoordinatesCommand;
import fr.obelouix.ultimate.commands.FillCommand;
import fr.obelouix.ultimate.commands.PVPCommand;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Function;

public class ObelouixCommandManager {

    private static final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
            AsynchronousCommandExecutionCoordinator.<CommandSender>builder().build();
    private static final Function<CommandSender, CommandSender> mapperFunction = Function.identity();
    private static PaperCommandManager<CommandSender> manager;

    public ObelouixCommandManager() {
    }

    public static void init() {
        try {
            manager = new PaperCommandManager<>(
                    ObelouixUltimate.getInstance(),
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        manager.registerBrigadier();
        manager.registerAsynchronousCompletions();

        final CloudBrigadierManager<?, ?> brigadierManager = manager.brigadierManager();
        if (brigadierManager != null) {
            brigadierManager.setNativeNumberSuggestions(false);
        }

        registerCommands();

    }

    private static void registerCommands() {
        List.of(
                new CoordinatesCommand(),
                new FillCommand(),
                new PVPCommand()
                //new OptionsCommand()
        ).forEach(CommandRegistration::register);
    }

    public static PaperCommandManager<CommandSender> getManager() {
        return manager;
    }

}
