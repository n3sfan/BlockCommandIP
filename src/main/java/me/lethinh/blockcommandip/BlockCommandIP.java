package me.lethinh.blockcommandip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class BlockCommandIP extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getCommand("blockcommandip").setExecutor(this);

        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockcommandip.admin")) {
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "reload":
                    sender.sendMessage("Reloading plugin...");
                    Bukkit.getPluginManager().disablePlugin(this);
                    Bukkit.getPluginManager().enablePlugin(this);
                    sender.sendMessage(ChatColor.GREEN + "Reloaded!");
                    break;
                default:
                    break;
            }
        }

        return true;
    }
}
