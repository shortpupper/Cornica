package org.example.cornica.cornica;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cornica extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().warning("Cornica Loaded.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().fine("Bye~!");
    }
}
