package ro.iacobai.digger.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ro.iacobai.digger.gui.SelectionScreen;

public class SelectionScreenEvent implements Listener {

    @EventHandler
    public  void onClick(InventoryClickEvent e){
        if(e.getClickedInventory()==null){return;}
        if(e.getClickedInventory().getHolder() instanceof SelectionScreen){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
        }
    }
}
