package ro.iacobai.digger.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;

import ro.iacobai.digger.data.DataHandler;

public class OnLeave implements Listener {
    DataHandler dataHandler = new DataHandler();
    @EventHandler
    public void onleave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1 && DataHandler.get_bool(dataHandler.namespaceKey_Pause,data)==0) {
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Id,data));
            System.out.println("Task of " +player.getName()+" canceled!");
        }
        if(DataHandler.get_bool(dataHandler.namespaceKey_Highlight,data)==1){
            Bukkit.getScheduler().cancelTask(DataHandler.get_int(dataHandler.namespaceKey_Task_Particle_Id,data));
            DataHandler.change_bool(dataHandler.namespaceKey_Highlight,data,player,null);
        }
        if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1){
            DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
        }
    }
}
