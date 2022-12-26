package ro.iacobai.digger.player;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;

import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.DigPlace;

public class OnJoin implements Listener {
    DataHandler dataHandler = new DataHandler();

    @EventHandler
    public void onjoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Running,data)==1){
            DigPlace digPlace = new DigPlace();
            digPlace.run_t(player);
            System.out.println("Task of " +player.getName()+" resumed!");
            player.sendMessage(ChatColor.GREEN+"Your Digger Task was resumed!");
        }
    }
}
