package hub.thespace.schoolboygametimecontroller;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class TimeController implements Listener, Runnable {
    private final Plugin plugin;
    private final Map<Player, Integer> playersTime = new HashMap<>();

    public TimeController(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

    }
}
