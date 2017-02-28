package com.banteruhc.BanterFlowerPower;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Brad on 2/14/2017.
 */
public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(this, this);
        registerConfig();
        getLogger().info(getDescription().getName() + " booted to go!");
    }
    @Override
    public void onDisable() {
        getLogger().info(getDescription() + " shut down like a boss.");
    }
    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        ItemStack droplist;
        Iterator var;
        if (b.getType() == Material.YELLOW_FLOWER || b.getType() == Material.RED_ROSE || b.getType() == Material.DEAD_BUSH || b.getType() == Material.DOUBLE_PLANT) {
            Random randomAmt = new Random();
            int amt = randomAmt.nextInt(16) + 1;
            Random randomItem = new Random();
            List<String> list = getConfig().getStringList("items");
            String randomString = list.get(randomItem.nextInt(list.size()));
            Material material = Material.matchMaterial(randomString);
            if (material == null) {
                getLogger().info(" Material was null for " + randomString);
            }
            if (material.getMaxStackSize() == 1) {
                amt = 1;
            }
            if (material == Material.DIAMOND_BLOCK || material == Material.ENDER_PEARL || material == Material.BOOKSHELF) {
                Random diamRand = new Random();
                amt = diamRand.nextInt(3) + 1;
            }
            if (material == Material.GOLD_BLOCK || material == Material.GOLD_ORE) {
                Random goldRand = new Random();
                amt = goldRand.nextInt(4) + 1;
            }
            ItemStack drop = new ItemStack(material, amt);
            var = b.getDrops().iterator();
            while(var.hasNext()) {
                droplist = (ItemStack) var.next();
                if (droplist.getType() == Material.YELLOW_FLOWER || b.getType() == Material.RED_ROSE || b.getType() == Material.DEAD_BUSH || b.getType() == Material.DOUBLE_PLANT) {
                    droplist.setType(Material.STONE);
                }
            b.getWorld().dropItemNaturally(b.getLocation(), drop);
            p.giveExp(1);
            b.setType(Material.AIR);

            }
        }
    }
}