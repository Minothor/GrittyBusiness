package io.github.minothor.GrittyBusiness.EventListener;

import io.github.minothor.GrittyBusiness.GrittyBusiness;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.*;
import java.util.stream.IntStream;


public class TNTListener implements Listener {

    private static Map<Material, Map<Material, Float>> blockReplacement;
//    private static Map<Material, Float> blockProbability;

    private static Random randomiser = new Random();

    public TNTListener(FileConfiguration config){
        blockReplacement = new HashMap<>();
            config.getConfigurationSection("TNT_Rules").getKeys(false).forEach(key -> {
                Material targetBlock = Material.getMaterial(key);
                Map<String, Object> value = config.getConfigurationSection(String.format("TNT_Rules.%s", key)).getValues(true);
                Map<Material, Float> replacementData = new HashMap<>();
                value.forEach((blockName, probability) -> {
                    Material newBlock = Material.getMaterial(blockName);
                    Float chance = 1 - ((Double) probability).floatValue();
                    replacementData.put(newBlock, chance);
                });
                blockReplacement.put(targetBlock, replacementData);
            });
    }


    private static Block checkList(Float randomVal, Block block) {

        GrittyBusiness.logger.info("Block Check Triggered...");

        Material oldBlock = block.getType();

        GrittyBusiness.logger.info(String.format("Block Check for: %s", oldBlock.name()));

        Map<Material, Float> replacement = blockReplacement.get(oldBlock);

        GrittyBusiness.logger.info(String.format("Found Replacement", replacement.toString()));

        if(Objects.nonNull(replacement)) {
            GrittyBusiness.logger.info("Found Block Replacement...");
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

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent event) {
        GrittyBusiness.logger.info("Explosion Check Triggered...");
        Float[] randomVals = new Float[8];
        Arrays.setAll(randomVals, (index) -> randomiser.nextFloat());
        IntStream.range(0, event.blockList().size())
                .forEach((blockIndex) ->{
                    GrittyBusiness.logger.info(String.format("Checking Index: %d", blockIndex));
                    event.blockList().set(blockIndex, checkList(randomVals[blockIndex % 8], event.blockList().get(blockIndex)));
                });
    }
}
