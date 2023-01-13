package ro.iacobai.digger.gui;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionScreen implements InventoryHolder {
    DataHandler dataHandler = new DataHandler();
    private Inventory inv;
    private Player player;
    public SelectionScreen(Player p){
        inv = Bukkit.createInventory(this,45,"DIGGER GUI");
        player = p;
        init();
    }
    private  void init() {
        ItemStack item;
        for(int i = 0;i < inv.getSize();i++){
            item = createItem(null,Material.LIGHT_GRAY_STAINED_GLASS_PANE, null);
            inv.setItem(i,item);
        }
        PersistentDataContainer data = player.getPersistentDataContainer();
        inv.setItem(1,getItem(1,data));
        inv.setItem(4,getItem(4,data));
        inv.setItem(19,getItem(19,data));
        inv.setItem(37,getItem(37,data));
        inv.setItem(40,getItem(40,data));
        inv.setItem(43,getItem(43,data));

        //HOPPER ACCESS
        Location hopper_pos = DataHandler.get_position(dataHandler.namespaceKey_PosHopper,data);
        inv.setItem(7,createItem(ChatColor.LIGHT_PURPLE+"ACCESS THE HOPPER",Material.HOPPER, Collections.singletonList(ChatColor.WHITE+"Hopper pos: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+hopper_pos.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+hopper_pos.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+hopper_pos.getZ())));
        //LAST MESSAGE
        String last_message = DataHandler.get_string(dataHandler.namespaceKey_Task_Last_Message,data);
        inv.setItem(25,createItem(ChatColor.WHITE+"LAST MESSAGE",Material.PAPER,Collections.singletonList(ChatColor.GREEN+last_message)));
        //STATUS
        List<String> lore = new ArrayList<>();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
        Location current_pos = DataHandler.get_position(dataHandler.namespacesKey_PosCurrent,data);
        double blocks_remaining = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining,data);
        lore.add(ChatColor.WHITE+"Pos 1: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+pos1.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+pos1.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+pos1.getZ());
        lore.add(ChatColor.WHITE+"Pos 2: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+pos2.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+pos2.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+pos2.getZ());
        lore.add(ChatColor.WHITE+"Current pos: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+current_pos.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+current_pos.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+current_pos.getZ());
        lore.add(ChatColor.WHITE+"Blocks remaining: "+ChatColor.GREEN+blocks_remaining);
        inv.setItem(22,createItem(ChatColor.WHITE+"STATUS",Material.NAME_TAG,lore));
    }

    private ItemStack createItem(String name, Material material, List<String> lore){
        ItemStack item = new ItemStack(material,1);
        ItemMeta meta = item.getItemMeta();
        if(name != null)
            meta.setDisplayName(name);
        if(lore != null)
            meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
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
        else if (position == 19) {
            //POS ON OR OFF
            cond = DataHandler.get_bool(dataHandler.namespacesKey_Pos_Select,data);
            if(cond == 1){
                return createItem(ChatColor.GREEN+"POS SELECT ON",Material.STICK,null);
            }else {
                return createItem(ChatColor.RED+"POS SELECT OFF",Material.RED_WOOL,null);
            }
        }
        else if (position == 37) {
            //BREAK ITEM OR NOT
            cond = DataHandler.get_bool(dataHandler.namespaceKey_Use_Break,data);
            if(cond == 1){
                return createItem(ChatColor.GREEN+"BREAK ITEMS",Material.WOODEN_PICKAXE,null);
            }else {
                return createItem(ChatColor.RED+"DON'T BREAK ITEMS",Material.DIAMOND_PICKAXE,null);
            }
        } else if (position == 40) {
            //USE CHEST OR NOT
            cond = DataHandler.get_bool(dataHandler.namespaceKey_Use_Chest,data);
            if(cond == 1){
                Location chest_pos = DataHandler.get_position(dataHandler.namespaceKey_PosChest,data);
                return createItem(ChatColor.GREEN+"USE A CHEST",Material.CHEST,Collections.singletonList(ChatColor.WHITE+"Chest pos: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+chest_pos.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+chest_pos.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+chest_pos.getZ()));
            }else {
                return createItem(ChatColor.RED+"DON'T USE A CHEST",Material.ENDER_CHEST,null);
            }
        }
        else if (position == 43) {
            //PARTICLE ON OR OFF
            cond = DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data);
            if(cond == 1){
                return createItem(ChatColor.GREEN+"PARTICLE ON",Material.GREEN_STAINED_GLASS_PANE,null);
            }else {
                return createItem(ChatColor.RED+"PARTICLE OFF",Material.RED_STAINED_GLASS_PANE,null);
            }
        }
        return null;
    }
}
