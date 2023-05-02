package dev.teraprath.stats.command;

import dev.teraprath.stats.Main;
import dev.teraprath.stats.api.DataType;
import dev.teraprath.stats.utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private final FileConfiguration cfg = Main.getInstance().getConfig();
    final String prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("language.prefix") + "");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            final Player target = Bukkit.getPlayer(args[0]);
            final DataType type = DataType.valueOf(args[1]);
            final int amount = Integer.parseInt(args[2]);
            String message;
            if (target != null && amount >= 0) {
                new SQLUtils().update(target.getUniqueId(), type, amount, sender, prefix + cfg.getString("language.player_stats_update").replace("%player%", target.getName()).replace("%datatype%", type.name().toLowerCase().replace("_", " ")));
            } else {
                wrongUsage(sender);
            }
        } else {
            wrongUsage(sender);
        }

        return false;
    }

    private void wrongUsage(@Nonnull CommandSender sender) {
        String wrongUsage = cfg.getString("language.wrong_usage");
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', wrongUsage.replace("%usage%", "/astats <player> <datatype> <amount>")));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();

        switch (args.length) {
            case 1:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    list.add(player.getName());
                });
                break;
            case 2:
                for (DataType type : DataType.values()) {
                    list.add(type.name());
                }
                break;
            case 3:
                list.add("amount");
            default:
                break;
        }

        list.removeIf(s -> !s.toLowerCase().startsWith(current));
        return list;
    }
}