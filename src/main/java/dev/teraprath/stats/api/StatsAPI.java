package dev.teraprath.stats.api;

import dev.teraprath.points.api.PointsAPI;
import dev.teraprath.stats.sql.SQLAdapter;
import dev.teraprath.stats.sql.SQLAuthentication;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StatsAPI {

    private final JavaPlugin plugin;
    private static SQLAuthentication authentication;
    private static boolean usePointsAPI = false;

    public StatsAPI(@Nonnull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        Plugin depend = plugin.getServer().getPluginManager().getPlugin("StatsAPI");
        if (depend != null) {
            FileConfiguration cfg = depend.getConfig();
            authentication = new SQLAuthentication(cfg.getString("mysql.host"), cfg.getInt("mysql.port"), cfg.getString("mysql.database"), cfg.getString("mysql.user"), cfg.getString("mysql.password"));
            depend.getLogger().info("Plugin registered: " + plugin.getName() + "-" + plugin.getDescription().getVersion());
            if (cfg.getBoolean("use_points_api")) {
                if (plugin.getServer().getPluginManager().getPlugin("PointsAPI") != null) {
                    usePointsAPI = true;
                    new PointsAPI((JavaPlugin) depend).init();
                } else {
                    depend.getLogger().warning("Can't use PointsAPI. Please install PointsAPI on your server to fix this: https://github.com/teraprath/PointsAPI");
                }
            }
        } else {
            plugin.getLogger().warning("StatsAPI is not installed! Instructions: https://github.com/teraprath/StatsAPI/wiki/1.-Getting-Started");
            plugin.getLogger().warning("Download on GitHub: https://github.com/teraprath/StatsAPI/releases/latest");
        }
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task -> {
            new SQLAdapter(authentication).init();
        });
    }


    public static PlayerStats getPlayer(@Nonnull Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static PlayerStats getPlayer(@Nonnull UUID uuid) {
        SQLAdapter adapter = new SQLAdapter(authentication);

        adapter.connect();

        try {
            ResultSet res = adapter.query(String.format("SELECT * FROM stats_players WHERE uuid = '%s'", uuid));
            if (res.next()) {
                PlayerStats stats = new PlayerStats(uuid);
                stats.setKills(res.getInt("kills"));
                stats.setDeaths(res.getInt("deaths"));
                stats.setWins(res.getInt("wins"));
                stats.setLoses(res.getInt("loses"));
                stats.setGamePoints(res.getInt("games_played"));
                stats.setStreak(res.getInt("streak"));
                stats.setGamePoints(usePointsAPI ? PointsAPI.getPoints(uuid) : res.getInt("game_points"));
                return stats;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter.disconnect();
        return null;
    }

    public static void save(@Nonnull PlayerStats stats) {

        SQLAdapter adapter = new SQLAdapter(authentication);

        adapter.connect();
        adapter.update(String.format("UPDATE stats_players SET kills = %d WHERE uuid = '%s'", stats.getKills(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET deaths = %d WHERE uuid = '%s'", stats.getDeaths(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET wins = %d WHERE uuid = '%s'", stats.getWins(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET loses = %d WHERE uuid = '%s'", stats.getLoses(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET games_played = %d WHERE uuid = '%s'", stats.getGamesPlayed(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET streak = %d WHERE uuid = '%s'", stats.getStreak(), stats.getUniqueId()));
        adapter.update(String.format("UPDATE stats_players SET game_points = %d WHERE uuid = '%s'", stats.getGamePoints(), stats.getUniqueId()));

        if (usePointsAPI) { PointsAPI.setPoints(stats.getUniqueId(), stats.getGamePoints()); }

        adapter.disconnect();

    }

    public static boolean hasRegistered(@Nonnull Player player) {
        return hasRegistered(player.getUniqueId());
    }

    public static boolean hasRegistered(@Nonnull UUID uuid) {

        SQLAdapter adapter = new SQLAdapter(authentication);

        adapter.connect();

        try {
            ResultSet res = adapter.query(String.format("SELECT * FROM stats_players WHERE uuid = '%s'", uuid));
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter.disconnect();
        return false;
    }

    public static void register(@Nonnull UUID uuid) {

        SQLAdapter adapter = new SQLAdapter(authentication);

        adapter.connect();
        adapter.update(String.format("INSERT IGNORE INTO stats_players (uuid, kills, deaths, wins, loses, games_played, streak, game_points) VALUES ('%s', 0, 0, 0, 0, 0, 0, 0)", uuid));
        adapter.disconnect();

    }

}
