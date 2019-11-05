package io.github.minothor.GrittyBusiness.EventListener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class CompostListener implements Listener {

    public CompostListener(FileConfiguration config){

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if(block.getType()==Material.COMPOSTER){
                Levelled levelData = (Levelled) block.getBlockData();
                if(levelData.getMaximumLevel() == levelData.getLevel()){
                    event.getPlayer()
                            .getWorld()
                            .dropItem(block.getLocation(),new ItemStack(Material.DIRT,1));
                }
            }
        }
    }
}
