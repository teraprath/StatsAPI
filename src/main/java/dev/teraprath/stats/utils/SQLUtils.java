package dev.teraprath.stats.utils;

import dev.teraprath.stats.Main;
import dev.teraprath.stats.api.DataType;
import dev.teraprath.stats.api.PlayerStats;
import dev.teraprath.stats.api.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.UUID;

public class SQLUtils {

    public void register(@Nonnull UUID uuid) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), task -> {
            StatsAPI.register(uuid);
        });
    }

    public void update(@Nonnull UUID uuid, DataType type, int amount, CommandSender sender, String message) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), task -> {
            PlayerStats stats = StatsAPI.getPlayer(uuid);
            if (stats == null) { return; }
            switch (type) {
                case KILLS:
                    stats.setKills(amount);
                    break;
                case DEATHS:
                    stats.setDeaths(amount);
                    break;
                case WINS:
                    stats.setWins(amount);
                    break;
                case LOSES:
                    stats.setLoses(amount);
                    break;
                case GAMES_PLAYED:
                    stats.setGamesPlayed(amount);
                    break;
                case STREAK:
                    stats.setStreak(amount);
                    break;
                case GAME_POINTS:
                    stats.setGamePoints(amount);
                    break;
                default:
                    return;
            }
            StatsAPI.save(stats);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", amount + "")));
        });

    }

}
