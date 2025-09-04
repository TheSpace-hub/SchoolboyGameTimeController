package hub.thespace.schoolboygametimecontroller;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
        plugin.getLogger().info(playersTime.toString());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int gameTime = plugin.getConfig().getInt("game-time");

        playersTime.put(player, gameTime);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playersTime.remove(player);
    }
}
