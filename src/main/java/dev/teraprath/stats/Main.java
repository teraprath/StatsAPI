package dev.teraprath.stats;

import dev.teraprath.stats.api.StatsAPI;
import dev.teraprath.stats.command.AdminCommand;
import dev.teraprath.stats.command.StatsCommand;
import dev.teraprath.stats.listener.LoginListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        new StatsAPI(this).init();

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("astats").setExecutor(new AdminCommand());
    }

    private void registerEvents() {
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new LoginListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

}
