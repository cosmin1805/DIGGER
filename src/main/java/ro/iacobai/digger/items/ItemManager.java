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
import ro.iacobai.digger.commands.subcommands.StatusCommand;
import ro.iacobai.digger.data.DataHandler;

import java.util.HashMap;
import java.util.UUID;

public class ItemManager implements Listener {
    DataHandler dataHandler = new DataHandler();
    private HashMap<UUID, Long> cooldown=new HashMap<>();
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(block == null)
        {
            return;
        }
        Location blockLocation = block.getLocation();
        Material material = block.getBlockData().getMaterial();
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
            World world = Bukkit.getWorld("world");
            if(!player.getWorld().equals(world)){
                player.sendMessage(ChatColor.RED + "The digger only works in the overworld!");
                return;
            }
            PersistentDataContainer data = player.getPersistentDataContainer();
            if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Pause,data) == 0){
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Highlight,data)==1){
                    player.sendMessage(ChatColor.RED+"Can't do this action! Please cancel your current Highlight with /digger particle !");
                    return;
                }
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data)==1){
                    player.sendMessage(ChatColor.RED+"Can't do this action! Please cancel your current digger with /digger cancel or wait for it to finish!");
                    return;
                }
                if(DataHandler.get_bool(dataHandler.namespaceKey_Confirm,data) == 1){
                    player.sendMessage(ChatColor.RED+"Can't do this action! Please confirm yor current selection with /digger confirm or cancel it with /digger cancel !");
                    return;
                }
            }
            else if (!material.equals(Material.CHEST) && !material.equals(Material.HOPPER)){
                player.sendMessage(ChatColor.RED+"Can't do this action! Please cancel your current digger with /digger cancel or wait for it to finish!");
                return;
            }
            if(DataHandler.get_bool(dataHandler.namespacesKey_Pos,data) == 1){
                if (material.equals(Material.CHEST)) {
                        DataHandler.save_position(dataHandler.namespaceKey_PosChest,data,blockLocation);
                        player.sendMessage(ChatColor.AQUA+"---------------------");
                        player.sendMessage(ChatColor.GREEN + "CHEST SELECTED!");
                        player.sendMessage(ChatColor.WHITE + "CHEST POS: " + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ());
                        player.sendMessage(ChatColor.AQUA+"---------------------");
                        return;
                }
                if (material.equals(Material.HOPPER)) {
                    DataHandler.save_position(dataHandler.namespaceKey_PosHopper,data,blockLocation);
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    player.sendMessage(ChatColor.GREEN + "HOPPER SELECTED!");
                    player.sendMessage(ChatColor.WHITE + "HOPPER POS: " + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ());
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    return;
                }
                if(action.equals(Action.LEFT_CLICK_BLOCK)){
                    DataHandler.save_position(dataHandler.namespaceKey_Pos1,data,blockLocation);
                    StatusCommand.location_send(dataHandler.namespaceKey_Pos1,data,player,"Pos1: ");
                }
                else {
                    DataHandler.save_position(dataHandler.namespaceKey_Pos2,data,blockLocation);
                    StatusCommand.location_send(dataHandler.namespaceKey_Pos2,data,player,"Pos2: ");
                }
            }
        }
    }
}
