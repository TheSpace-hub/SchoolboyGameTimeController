package hub.thespace.schoolboygametimecontroller;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SchoolboyGameTimeController extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        TimeController timeController = new TimeController(this);
        getServer().getPluginManager().registerEvents(timeController, this);
        Bukkit.getScheduler().runTaskTimer(this, timeController, 20L, 20L);

        Bukkit.getPluginCommand("sgtc-reload").setExecutor(new SGTCCommand(this));
    }
}
