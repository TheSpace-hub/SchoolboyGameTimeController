package hub.thespace.schoolboygametimecontroller;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
    private final Map<Player, BossBar> playersBossbar = new HashMap<>();

    public TimeController(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Updates the players' time.
     */
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int remainingTime = playersTime.get(player);
            remainingTime -= 1;
            playersTime.put(player, remainingTime);

            updateBossBar(player);

            if (remainingTime <= 0) {
                endOfTimeNotification(player);
                playersTime.put(player, plugin.getConfig().getInt("game-time"));
            }


        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int gameTime = plugin.getConfig().getInt("game-time");

        playersTime.put(player, gameTime);

        BossBar bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        bossBar.addPlayer(player);
        playersBossbar.put(player, bossBar);
        updateBossBar(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playersTime.remove(player);
        playersBossbar.remove(player);
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

    /**
     * Update the player's time score.
     *
     * @param player Player.
     */
    private void updateBossBar(Player player) {
        Integer remainingTime = playersTime.get(player);
        int gameTime = plugin.getConfig().getInt("game-time");

        BossBar bossBar = playersBossbar.get(player);
        bossBar.setTitle(plugin.getConfig().getString("time-indicator.title")
                .replace("&", "§")
                .replace("{time-left}", remainingTime.toString()));
        bossBar.setColor(getBarColorByTime(remainingTime));
        bossBar.setProgress((double) remainingTime / gameTime);

        if (plugin.getConfig().getBoolean("time-indicator.show"))
            bossBar.addPlayer(player);
        else
            bossBar.removeAll();
    }

    /**
     * Get bossbar color by remaining time.
     *
     * @param time Time.
     * @return Color.
     */
    private BarColor getBarColorByTime(int time) {
        if (time >= 60)
            return BarColor.GREEN;
        else if (time >= 30)
            return BarColor.YELLOW;
        else
            return BarColor.RED;
    }

}
