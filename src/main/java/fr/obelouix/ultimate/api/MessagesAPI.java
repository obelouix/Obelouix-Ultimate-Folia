package fr.obelouix.ultimate.api;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class MessagesAPI {

    /**
     * Send a message to a command sender
     *
     * @param sender  the command sender
     * @param message a component
     * @see Audience#sendMessage(Component)
     */
    public static void sendMessage(@NotNull CommandSender sender, Component message) {
        final Audience audience;
        if (sender instanceof Player player)
            audience = Audience.audience(Objects.requireNonNull(Bukkit.getPlayer(player.getName())));
        else {
            final ConsoleCommandSender consoleCommandSender = (ConsoleCommandSender) sender;
            audience = Audience.audience(consoleCommandSender);
        }
        audience.sendMessage(message);
    }

    /**
     * Send a message to every connected players
     *
     * @param message a component
     * @see Audience#sendMessage(Component)
     */
    public static void broadcast(Component message) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.sendMessage(message);
    }

    /**
     * Send a message to every connected players that as the required permission
     *
     * @param permission the permission the player need to receive the message
     * @param message    a component
     * @see Audience#sendMessage(Component)
     */
    public static void broadcast(String permission, Component message) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.sendMessage(message);
    }

    /**
     * Send a message to every opped players
     *
     * @param message a component
     * @see Audience#sendMessage(Component)
     */
    public static void broadcastToOP(Component message) {
        final Collection<Player> OppedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) OppedPlayers.add(player);
        }
        final Audience audience = Audience.audience(OppedPlayers);
        audience.sendMessage(message);
    }

    /**
     * show a boss bar to a specific player
     *
     * @param player  the player that will see the boss bar
     * @param bossBar the boss bar to show
     * @see Audience#showBossBar(BossBar)
     */
    public static void showBossBar(Player player, BossBar bossBar) {
        final Audience audience = Audience.audience(player);
        audience.showBossBar(bossBar);
    }

    /**
     * hide a boss bar to a specific player
     *
     * @param player  the player that has the boss bar
     * @param bossBar the boss bar to hide
     * @see Audience#hideBossBar(BossBar)
     */
    public static void hideBossBar(Player player, BossBar bossBar) {
        final Audience audience = Audience.audience(player);
        audience.hideBossBar(bossBar);
    }

    /**
     * show a boss bar to every player
     *
     * @param bossBar the boss bar to show
     * @see Audience#showBossBar(BossBar)
     */
    public static void showBossBar(BossBar bossBar) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.showBossBar(bossBar);
    }

    /**
     * hide a boss bar to every specific player
     *
     * @param bossBar the boss bar to hide
     * @see Audience#hideBossBar(BossBar)
     */
    public static void hideBossBar(BossBar bossBar) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.hideBossBar(bossBar);
    }

    /**
     * show a boss bar to a specific player
     *
     * @param permission the permission the player need to see the boss bar
     * @param bossBar    the boss bar to show
     * @see Audience#showBossBar(BossBar)
     */
    public static void showBossBar(String permission, BossBar bossBar) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.showBossBar(bossBar);
    }

    /**
     * hide a boss bar to a specific player
     *
     * @param permission the permission the player need to see the boss bar
     * @param bossBar    the boss bar to hide
     * @see Audience#hideBossBar(BossBar)
     */
    public static void hideBossBar(String permission, BossBar bossBar) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.hideBossBar(bossBar);
    }

    /**
     * show a title to a specific player
     *
     * @param player the player that will see the boss bar
     * @param title  the title to send
     * @see Audience#showTitle(Title)
     */
    public static void showTitle(Player player, Title title) {
        final Audience audience = Audience.audience(player);
        audience.showTitle(title);
    }

    /**
     * clear titles to a specific player
     *
     * @param player the player that has the titles shown
     * @see Audience#clearTitle()
     */
    public static void clearTitle(Player player) {
        final Audience audience = Audience.audience(player);
        audience.clearTitle();
    }

    /**
     * show a title to every player
     *
     * @param title the title to send
     * @see Audience#showTitle(Title)
     */
    public static void showTitle(Title title) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.showTitle(title);
    }

    /**
     * clear titles to every specific player
     *
     * @see Audience#clearTitle()
     */
    public static void clearTitle() {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.clearTitle();
    }

    /**
     * show a title to every player that have the given permission
     *
     * @param permission the permission the player need to see the boss bar
     * @param title      the title to send
     * @see Audience#showTitle(Title)
     */
    public static void showTitle(String permission, Title title) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.showTitle(title);
    }

    /**
     * clear titles to every player that have the given permission
     *
     * @param permission the permission the player need to see the boss bar
     * @see Audience#clearTitle()
     */
    public static void clearTitle(String permission) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.clearTitle();
    }

    /**
     * reset titles to a specific player
     *
     * @param player the player that has the titles shown
     * @see Audience#resetTitle()
     */
    public static void resetTitle(Player player) {
        final Audience audience = Audience.audience(player);
        audience.resetTitle();
    }

    /**
     * reset titles to a specific player
     *
     * @param permission the permission the player need to see the boss bar
     * @see Audience#resetTitle()
     */
    public static void resetTitle(String permission) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.resetTitle();
    }

    /**
     * reset titles to every specific player
     *
     * @see Audience#resetTitle()
     */
    public static void resetTitle() {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.clearTitle();
    }

    /**
     * Open a book for every player
     *
     * @param book the book to show
     * @see Audience#openBook(Book)
     */
    public static void openBook(Book book) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.openBook(book);
    }

    /**
     * Open a book for a player
     *
     * @param player the player that will see the book
     * @param book   the book to show
     * @see Audience#openBook(Book)
     */
    public static void openBook(Player player, Book book) {
        final Audience audience = Audience.audience(player);
        audience.openBook(book);
    }

    /**
     * Open a book for every player that has the permission
     *
     * @param permission the permission needed for this book
     * @param book       the book to show
     * @see Audience#openBook(Book)
     */
    public static void openBook(String permission, Book book) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        audience.openBook(book);
    }

    /**
     * send a component on the action bar to every player
     *
     * @param component the message to send
     * @see Audience#sendActionBar(Component)
     */
    public static void sendActionBar(Component component) {
        final Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.sendActionBar(component);
    }

    /**
     * send a component on the action bar to a player
     *
     * @param player    the player that will see the book
     * @param component the message to send
     * @see Audience#sendActionBar(Component)
     */
    public static void sendActionBar(Player player, Component component) {
        final Audience audience = Audience.audience(player);
        audience.sendActionBar(GlobalTranslator.render(component, player.locale()));
    }

    /**
     * send a component on the action bar to every player that has the permission
     *
     * @param permission the permission needed for this book
     * @param component  the message to send
     * @see Audience#sendActionBar(Component)
     */
    public static void sendActionBar(String permission, Component component) {
        final Collection<Player> authorizedPlayers = new ArrayList<>(Collections.emptyList());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        final Audience audience = Audience.audience(authorizedPlayers);
        authorizedPlayers.forEach(player -> audience.sendActionBar(GlobalTranslator.render(component, player.locale())));
    }

    /**
     * This allows you to kick a player with a message in his language
     * (this message must be defined in language properties files)
     *
     * @param player    the player to kick
     * @param component the component to send to the player
     * @param reason    the kick reason
     */
    public static void sendKickMessage(final Player player, final Component component, final PlayerKickEvent.Cause reason) {
        player.kick(component, reason);
    }
}
