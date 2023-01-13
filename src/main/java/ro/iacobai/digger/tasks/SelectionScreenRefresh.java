package ro.iacobai.digger.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.digger.DIGGER;

import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionScreenRefresh {
    DataHandler dataHandler = new DataHandler();
    DIGGER digger = DIGGER.getPlugin();

    public void run_t(Player player) {
        DIGGER plugin = DIGGER.getPlugin();
        int ID = new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.isOnline()){
                    this.cancel();
                }
                if (player.getOpenInventory().getTitle().equals("DIGGER GUI")) {
                    PersistentDataContainer data = player.getPersistentDataContainer();

                    //RUNNING AND PAUSE
                    player.getOpenInventory().setItem(1, getItem(1, data));
                    player.getOpenInventory().setItem(4, getItem(4, data));
                    //LAST MESSAGE
                    String last_message = DataHandler.get_string(dataHandler.namespaceKey_Task_Last_Message, data);
                    player.getOpenInventory().setItem(25, createItem(ChatColor.WHITE + "LAST MESSAGE", Material.PAPER, Collections.singletonList(ChatColor.GREEN + last_message)));
                    //STATUS
                    List<String> lore = new ArrayList<>();
                    Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1, data);
                    Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2, data);
                    Location current_pos = DataHandler.get_position(dataHandler.namespacesKey_PosCurrent, data);
                    double blocks_remaining = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining, data);
                    lore.add(ChatColor.WHITE + "Pos 1: " + ChatColor.GOLD + "x:" + ChatColor.LIGHT_PURPLE + pos1.getX() + ChatColor.GOLD + " y:" + ChatColor.LIGHT_PURPLE + pos1.getY() + ChatColor.GOLD + " z:" + ChatColor.LIGHT_PURPLE + pos1.getZ());
                    lore.add(ChatColor.WHITE + "Pos 2: " + ChatColor.GOLD + "x:" + ChatColor.LIGHT_PURPLE + pos2.getX() + ChatColor.GOLD + " y:" + ChatColor.LIGHT_PURPLE + pos2.getY() + ChatColor.GOLD + " z:" + ChatColor.LIGHT_PURPLE + pos2.getZ());
                    lore.add(ChatColor.WHITE + "Current pos: " + ChatColor.GOLD + "x:" + ChatColor.LIGHT_PURPLE + current_pos.getX() + ChatColor.GOLD + " y:" + ChatColor.LIGHT_PURPLE + current_pos.getY() + ChatColor.GOLD + " z:" + ChatColor.LIGHT_PURPLE + current_pos.getZ());
                    lore.add(ChatColor.WHITE + "Blocks remaining: " + ChatColor.GREEN + blocks_remaining);
                    player.getOpenInventory().setItem(22, createItem(ChatColor.WHITE + "STATUS", Material.NAME_TAG, lore));
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 60, 20).getTaskId();
    }

    private ItemStack createItem(String name, Material material, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getItem(int position,PersistentDataContainer data){
        int cond;
        if(position == 1){
            cond = DataHandler.get_bool(dataHandler.namespaceKey_Running,data);
            //RUNNING OR NOT
            if(cond == 1){
                return createItem(ChatColor.GREEN+"RUNNING",Material.EMERALD_BLOCK,null);
            }else {
                return createItem(ChatColor.RED+"NOT RUNNING",Material.REDSTONE_BLOCK,null);
            }
        } else if (position == 4) {
            //PAUSED OR NOT
            cond = DataHandler.get_bool(dataHandler.namespaceKey_Pause,data);
            if(cond == 1){
                return createItem(ChatColor.RED+"PAUSED",Material.BARRIER,null);
            }else {
                return createItem(ChatColor.GREEN+"NOT PAUSED",Material.MAGENTA_GLAZED_TERRACOTTA,null);
            }
        }
        return null;
    }
}
