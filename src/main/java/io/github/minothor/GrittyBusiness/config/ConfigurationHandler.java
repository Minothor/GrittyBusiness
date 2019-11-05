package io.github.minothor.GrittyBusiness.config;

import io.github.minothor.GrittyBusiness.GrittyBusiness;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Properties;

public class ConfigurationHandler {

    private static JavaPlugin plugin;
    private static Properties properties;
    private String nativeVersion;

    public ConfigurationHandler(@NotNull JavaPlugin plugin, @NotNull Properties properties){
        this.plugin = plugin;
        this.properties = properties;
        this.nativeVersion = properties.getProperty("gritty.config.version");
    }

    public FileConfiguration load(){
        GrittyBusiness.logger.info("Loading Config...");
        FileConfiguration loaded = plugin.getConfig();
        if(Objects.isNull(loaded.get("Config_Version"))) {
            GrittyBusiness.logger.info("Config not Valid - Saving Defaults");
            plugin.saveDefaultConfig();
            return plugin.getConfig();
        }
        checkVersion(loaded);
        return loaded;
    }

    public void save(){
        plugin.saveConfig();
    }

    private void checkVersion(FileConfiguration loaded){

    }

    private void migrateVersion(FileConfiguration loaded){

    }
}
