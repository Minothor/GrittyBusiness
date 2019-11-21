package io.github.minothor.GrittyBusiness.EventListener;

import io.github.minothor.GrittyBusiness.GrittyBusiness;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.*;
import java.util.stream.IntStream;


public class FlowListener implements Listener {

    private static Map<Material, Map<Material, Float>> blockReplacement;

    private static String CONFIG_SECTION = "Sluice_Rules";
//    private static Map<Material, Float> blockProbability;

    private static Random randomiser = new Random();

    public FlowListener(FileConfiguration config){
       /* blockReplacement = new HashMap<>();
            config.getConfigurationSection(CONFIG_SECTION).getKeys(false).forEach(key -> {
                Material targetBlock = Material.getMaterial(key);
                Map<String, Object> value = config.getConfigurationSection(String.format("%1$s.%2$s", CONFIG_SECTION, key)).getValues(true);
                Map<Material, Float> replacementData = new HashMap<>();
                value.forEach((blockName, probability) -> {
                    Material newBlock = Material.getMaterial(blockName);
                    Float chance = 1 - ((Double) probability).floatValue();
                    replacementData.put(newBlock, chance);
                });
                blockReplacement.put(targetBlock, replacementData);
            });*/
    }


    private static Block checkList(Float randomVal, Block block) {
        GrittyBusiness.logger.info("Block Check Triggered...");

        Material oldBlock = block.getType();

        GrittyBusiness.logger.info(String.format("Block Check for: %s", oldBlock.name()));

        Map<Material, Float> replacement = blockReplacement.getOrDefault(oldBlock,null);

        if(Objects.nonNull(replacement)) {
            GrittyBusiness.logger.info(String.format("Found Replacement", replacement.toString()));
            replacement.keySet().forEach(possibleBlock -> {
                GrittyBusiness.logger.info(String.format("Found Block Replacement: %s", possibleBlock.name()));

                GrittyBusiness.logger.info(String.format("Received Probability Value: %f", randomVal));

                GrittyBusiness.logger.info(String.format("Needs Probability Value: %f", replacement.get(possibleBlock)));

                if (Objects.nonNull(possibleBlock) && (randomVal >= replacement.get(possibleBlock))) {
                    block.setType(possibleBlock, true);
                }
            });


        }

        return block;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWaterPlace(PlayerBucketEmptyEvent event) {
        if(Objects.isNull(event.getBlock()) ||
                Objects.isNull(event.getBlock().getType()) ||
                event.getBlock().getType() != Material.SPRUCE_SLAB)
            return;
        GrittyBusiness.logger.info("Spruce Slab WaterLogged.. listening for flow.");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWaterFlow(BlockFromToEvent event) {
        GrittyBusiness.logger.info("Flow Check Check Triggered...");
        /*Float[] randomVals = new Float[8];
        Arrays.setAll(randomVals, (index) -> randomiser.nextFloat());
        IntStream.range(0, event.blockList().size())
                .forEach((blockIndex) ->{
                    GrittyBusiness.logger.info(String.format("Checking Index: %d", blockIndex));
                    event.blockList().set(blockIndex, checkList(randomVals[blockIndex % 8], event.blockList().get(blockIndex)));
                });*/
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {

    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {

    }

}
