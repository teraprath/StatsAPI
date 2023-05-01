package dev.teraprath.stats.utils;

import dev.teraprath.stats.Main;
import dev.teraprath.stats.api.PlayerStats;
import dev.teraprath.stats.api.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class MessageUtils {

    public void show(CommandSender sender, UUID target, List<String> list, String notFound) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), task -> {
            PlayerStats stats = StatsAPI.getPlayer(target);
            if (stats != null) {
                list.forEach(s -> {
                    String msg = s
                            .replace("%player%", Bukkit.getOfflinePlayer(target).getName() + "")
                            .replace("%kills%", stats.getKills() + "")
                            .replace("%deaths%", stats.getDeaths() + "")
                            .replace("%wins%", stats.getWins() + "")
                            .replace("%loses%", stats.getLoses() + "")
                            .replace("%games_played%", stats.getGamesPlayed() + "")
                            .replace("%streak%", stats.getStreak() + "")
                            .replace("%game_points%", stats.getGamePoints() + "");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                });
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', notFound));
            }
        });
    }

}
