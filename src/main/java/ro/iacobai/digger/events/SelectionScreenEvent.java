package ro.iacobai.digger.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.gui.ConfirmScreen;
import ro.iacobai.digger.gui.SelectionScreen;
import ro.iacobai.digger.tasks.DigPlace;
import ro.iacobai.digger.tasks.Particles;

import static java.lang.Math.abs;

public class SelectionScreenEvent implements Listener {
    DataHandler dataHandler = new DataHandler();
    DigPlace digPlace = new DigPlace();
    @EventHandler
    public  void onClick(InventoryClickEvent e){
        Inventory inv = e.getClickedInventory();
        if(inv==null){return;}
        if(inv.getHolder() instanceof SelectionScreen){

            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            PersistentDataContainer data = player.getPersistentDataContainer();
            SelectionScreen selectionScreen = new SelectionScreen(player);

            //ALL THE BOOLS
            if(e.getSlot() == 4) {
                //PAUSE or resume
                if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                    digPlace.run_t(player,DataHandler.get_int(dataHandler.namespaceKey_Task_Next_Time,data));
                }
                else if(DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0 && DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
                    Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
                    DataHandler.change_bool(dataHandler.namespaceKey_Pause,data,player,null);
                }
                else if (DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==0){
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    player.sendMessage(ChatColor.RED+"Nothing to pause!");
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                }
                inv.setItem(e.getSlot(), selectionScreen.getItem(e.getSlot(), data));
            } else if(e.getSlot() == 19){
                //POS ON OR OFF
                DataHandler.change_bool(dataHandler.namespacesKey_Pos_Select,data,player,null);
                inv.setItem(e.getSlot(),selectionScreen.getItem(e.getSlot(),data));
            } else if (e.getSlot()== 37) {
                //BREAK ITEM OR NOT
                DataHandler.change_bool(dataHandler.namespaceKey_Use_Break,data,player,null);
                inv.setItem(e.getSlot(),selectionScreen.getItem(e.getSlot(),data));
            }
            else if (e.getSlot()== 40) {
                //USE CHEST OR NOT
                DataHandler.change_bool(dataHandler.namespaceKey_Use_Chest,data,player,null);
                inv.setItem(e.getSlot(),selectionScreen.getItem(e.getSlot(),data));
            }
            else if (e.getSlot()== 43) {
                //PARTICLE ON OR OFF
                if(DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data)==0){
                    Particles particles = new Particles();
                    particles.run_t(player);
                    DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
                }
                else {
                    Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Particle_Id,data));
                    DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
                }
                inv.setItem(e.getSlot(),selectionScreen.getItem(e.getSlot(),data));
            }
            //THE HOPPER
            else if (e.getSlot() == 7) {
                Location hopper_pos = DataHandler.get_position(dataHandler.namespaceKey_PosHopper,data);
                Material material_hopper = hopper_pos.getBlock().getBlockData().getMaterial();
                if(material_hopper.equals(Material.HOPPER)) {
                    Hopper hopper = (Hopper) hopper_pos.getBlock().getState();
                    player.openInventory(hopper.getInventory());
                }
                else {
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                    player.sendMessage(ChatColor.RED+"The hopper is missing!");
                    player.sendMessage(ChatColor.AQUA+"---------------------");
                }
            }
            //START AND STOP
            else if (e.getSlot() == 1) {
                if (DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==0){
                    Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
                    Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
                    int h = (int) (abs(abs(pos1.getY()) - abs(pos2.getY())))+1;
                    int l = (int)(abs(abs(pos1.getX()) - abs(pos2.getX())))+1;
                    int w = (int)(abs(abs(pos1.getZ()) - abs(pos2.getZ())))+1;
                    double number_of_blocks = h*l*w;
                    DataHandler.save_double(dataHandler.namespaceKey_Blocks_Remaining,data,number_of_blocks);
                    DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
                    ConfirmScreen gui = new ConfirmScreen(player,"START THE DIGGER");
                    player.openInventory(gui.getInventory());
                }
                else{
                    DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
                    ConfirmScreen gui = new ConfirmScreen(player,"STOP THE DIGGER");
                    player.openInventory(gui.getInventory());
                }
            }
        }
    }
}
