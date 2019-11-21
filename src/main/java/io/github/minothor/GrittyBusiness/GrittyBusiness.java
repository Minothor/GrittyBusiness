package io.github.minothor.GrittyBusiness;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.minothor.GrittyBusiness.EventListener.*;

public class GrittyBusiness extends JavaPlugin {

    private static TNTListener tntListener;

    private static FlowListener flowListener;

    private static FileConfiguration config;

    public static Logger logger;


    @Override
    public void onEnable() {
        logger = getLogger();
        this.saveDefaultConfig();

        config = this.getConfig();

        logger.info(String.format("Config Loaded: %s", config.getName()));

        tntListener = new TNTListener(config);
        getServer().getPluginManager().registerEvents(tntListener, this);

        flowListener = new FlowListener(config);
        getServer().getPluginManager().registerEvents(flowListener, this);

        logger.info("GrittyBusiness Loaded!");

    }

    @Override
    public void onDisable() {
        if (this.isEnabled()) {
            HandlerList.unregisterAll(tntListener);
            this.saveConfig();
            logger.info("GrittyBusiness Unloaded!");
        }
    }
}
