package ro.iacobai.digger.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfirmScreen implements InventoryHolder {

    DataHandler dataHandler = new DataHandler();
    private Inventory inv;
    private Player player;
    private String tittle;
    public ConfirmScreen(Player p,String name){
        inv = Bukkit.createInventory(this,9,name);
        tittle = name;
        player = p;
        init();
    }
    private  void init() {
        ItemStack item;
        PersistentDataContainer data = player.getPersistentDataContainer();
        //ACCEPT
        for(int i = 0;i < 4;i++){
            item = createItem(ChatColor.GREEN+"CONFIRM", Material.GREEN_STAINED_GLASS_PANE, null);
            inv.setItem(i,item);
        }
        //INFO
        if(tittle.equals("START THE DIGGER")){
            //STATUS
            List<String> lore = new ArrayList<>();
            Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
            Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
            double blocks_remaining = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining,data);
            lore.add(ChatColor.WHITE+"Pos 1: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+pos1.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+pos1.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+pos1.getZ());
            lore.add(ChatColor.WHITE+"Pos 2: "+ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+pos2.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+pos2.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+pos2.getZ());
            lore.add(ChatColor.WHITE+"Blocks to break: "+ChatColor.GREEN+blocks_remaining);
            inv.setItem(4,createItem(ChatColor.WHITE+"DO YOU WANT TO START THE DIGGER?",Material.PAPER,lore));
        } else if (tittle.equals("STOP THE DIGGER")) {
            inv.setItem(4,createItem(ChatColor.WHITE+"DO YOU WANT TO STOP THE DIGGER?",Material.PAPER,null));
        }
        //DENY
        for(int i = 5;i < 9;i++){
            item = createItem(ChatColor.RED+"DENY", Material.RED_STAINED_GLASS_PANE, null);
            inv.setItem(i,item);
        }
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
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
