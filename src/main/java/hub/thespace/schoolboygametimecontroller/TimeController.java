package hub.thespace.schoolboygametimecontroller;

import org.bukkit.Bukkit;
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

    /**
     * Updates the players' time.
     */
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int time = playersTime.get(player);
            time -= 1;

            if (time <= 0) {
                endOfTimeNotification(player);
                time = plugin.getConfig().getInt("game-time");
            }

            playersTime.put(player, time);
        }
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

    /**
     * Notify the player of the end of time.
     *
     * @param player Player.
     */
    private void endOfTimeNotification(Player player) {
        player.sendTitle(
                plugin.getConfig().getString("end-of-time-broadcast.title").replace("&", "§"),
                plugin.getConfig().getString("end-of-time-broadcast.subtitle").replace("&", "§"),
                10, 70, 20
        );
    }

}
