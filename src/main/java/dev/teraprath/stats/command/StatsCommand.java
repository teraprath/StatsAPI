package dev.teraprath.stats.command;

import dev.teraprath.stats.Main;
import dev.teraprath.stats.api.PlayerStats;
import dev.teraprath.stats.api.StatsAPI;
import dev.teraprath.stats.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand implements CommandExecutor, TabCompleter {

    private final FileConfiguration cfg = Main.getInstance().getConfig();
    final String prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("language.prefix") + "");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            new MessageUtils().show(sender, target.getUniqueId(), cfg.getStringList("language.stats"), prefix + cfg.getString("language.player_not_found").replace("%player%", args[0]));
        } else {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                new MessageUtils().show(sender, player.getUniqueId(), cfg.getStringList("language.stats"), prefix + cfg.getString("language.player_not_found"));
            } else {
                sender.sendMessage("stats <Player>");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();

        if (args.length == 1) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                list.add(player.getName());
            });
        }

        list.removeIf(s -> !s.toLowerCase().startsWith(current));
        return list;
    }

}
