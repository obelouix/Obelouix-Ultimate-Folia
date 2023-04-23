package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class EventsRegistration {

    private static final Set<Listener> events = new HashSet<>();


    /**
     * register all events from the events set
     */
    protected static void registerEvents() {
        events.forEach(listener -> {
            ObelouixUltimate.getInstance().getServer().getPluginManager().registerEvents(listener, ObelouixUltimate.getInstance());
/*            if (Config.isDebugMode())
                plugin.getComponentLogger().info("Registered " + listener.getClass().getSimpleName() + " event");*/
        });
    }

    /**
     * add a list of event into the set that will register all of this
     *
     * @param eventList a list of events
     */
    public static void addEvents(Collection<Listener> eventList) {
        events.addAll(eventList);
    }

    /**
     * add an event into the set that will register it
     *
     * @param listener an event
     */
    public static void addEvent(Listener listener) {
        events.add(listener);
    }

}
