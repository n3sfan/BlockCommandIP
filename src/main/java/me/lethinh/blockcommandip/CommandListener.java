package me.lethinh.blockcommandip;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.List;
import java.util.Locale;

public class CommandListener implements Listener {
    private final BlockCommandIP plugin;
    private final FileConfiguration config;

    public CommandListener(BlockCommandIP plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        // Shouldn't be ?
        if (player.getAddress() == null)
            return;

        String ip = player.getAddress().getAddress().getHostAddress();
        while (ip.endsWith(".") || ip.endsWith(":")) {
            ip = ip.substring(0, ip.length() - 1);
        }
//        plugin.getLogger().info(ip);

        if (config.getStringList("BlockIPs").contains(ip)) {
            String command = event.getMessage().toLowerCase(Locale.ROOT);

            List<String> blockedList = config.getStringList("BlockCommands");
            for (String blocked : blockedList) {
                String b = blocked.toLowerCase(Locale.ROOT);
                if (command.startsWith(b)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Message.CommandBlocked")));
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
