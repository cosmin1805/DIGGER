package ro.iacobai.digger.items;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;

import java.util.HashMap;
import java.util.UUID;

public class ItemManager implements Listener {
    NamespacedKey namespacedKey_Pos = new NamespacedKey(DIGGER.getPlugin(),"pos_select");
    NamespacedKey namespacedKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    NamespacedKey namespacedKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    NamespacedKey namespacedKey_Chest = new NamespacedKey(DIGGER.getPlugin(),"chest_select");
    NamespacedKey getNamespacedKey_PosChest = new NamespacedKey(DIGGER.getPlugin(),"chest_pos");
    private HashMap<UUID, Long> cooldown=new HashMap<>();
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");
        if(!player.getWorld().equals(world)){
            player.sendMessage(ChatColor.RED + "The digger only works in the overworld!");
            return;
        }
        if(action.equals(Action.LEFT_CLICK_BLOCK)||action.equals(Action.RIGHT_CLICK_BLOCK)){
            if(player.getInventory().getItemInMainHand().equals(new ItemStack(Material.STICK))==false)
            {
                return;
            }
            if (this.cooldown.containsKey(player.getUniqueId())==true) {
                long timeE = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
                if (timeE < 1000) {
                    return;
                }
                else {
                    this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                }
            } else {
                this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
            }
            PersistentDataContainer data = player.getPersistentDataContainer();
            Block block = event.getClickedBlock();
            Location blockLocation = block.getLocation();
            int chest = DataHandler.get_bool(namespacedKey_Chest,data);
            if(chest == 1){
                Material material = block.getBlockData().getMaterial();
                if (material.equals(Material.CHEST)) {
                    DataHandler.save_position(getNamespacedKey_PosChest,data,blockLocation);
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    player.sendMessage(ChatColor.GREEN + "CHEST SELECTED!");
                    player.sendMessage(ChatColor.WHITE + "CHEST POS: " + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ());
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    DataHandler.change_bool(namespacedKey_Chest,data,player,"Digger chest selector ");
                }
                return;
            }
            int pos = DataHandler.get_bool(namespacedKey_Pos,data);
            if(pos == 1){
                if(action.equals(Action.LEFT_CLICK_BLOCK)){
                    DataHandler.save_position(namespacedKey_Pos1,data,blockLocation);
                    player.sendMessage(ChatColor.WHITE + "Pos1: " + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ());
                }
                else {
                    DataHandler.save_position(namespacedKey_Pos2,data,blockLocation);
                    player.sendMessage(ChatColor.WHITE + "Pos2: " + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ());
                }
            }
        }
    }
}
