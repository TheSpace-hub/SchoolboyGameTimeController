package hub.thespace.schoolboygametimecontroller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class SGTCCommand implements CommandExecutor {
    private final Plugin plugin;

    public SGTCCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("§aSuccessful reloaded.");
        return false;
    }
}
