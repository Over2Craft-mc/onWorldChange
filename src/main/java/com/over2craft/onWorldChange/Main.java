package com.over2craft.onWorldChange;

import com.over2craft.onWorldChange.listeners.onWorldChange;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getLogger().info("[onWorldChange] Enabling plugin");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new onWorldChange(), this);

                Objects.requireNonNull(getCommand("onWorldChangereload")).setExecutor(
                (commandSender, command, s, strings) -> {
                    Main.plugin.reloadConfig();
                    commandSender.sendMessage(" ");
                    commandSender.sendMessage("[onWorldChange] Plugin reloaded.");
                    commandSender.sendMessage(" ");
                    return true;
                }
        );

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getLogger().info("[onWorldChange] Could not find PlaceholderAPI. You won't be able to use PlaceholderAPI placeholders.");
        }

    }


}
