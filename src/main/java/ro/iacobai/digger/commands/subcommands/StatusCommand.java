package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

public class StatusCommand extends SubCommand {
    NamespacedKey namespacedKey_Pos = new NamespacedKey(DIGGER.getPlugin(),"pos_select");
    NamespacedKey namespacedKey_Chest = new NamespacedKey(DIGGER.getPlugin(),"chest_select");
    NamespacedKey namespacedKey_Use_Chest = new NamespacedKey(DIGGER.getPlugin(),"use_chest");
    NamespacedKey namespacedKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    NamespacedKey namespacedKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    NamespacedKey namespacedKey_PosChest = new NamespacedKey(DIGGER.getPlugin(),"chest_pos");
    NamespacedKey namespacedKey_PosCurrent = new NamespacedKey(DIGGER.getPlugin(),"current_pos");
    NamespacedKey namespacedKey_Price = new NamespacedKey(DIGGER.getPlugin(),"task_price");
    NamespacedKey namespacedKey_Task_Running= new NamespacedKey(DIGGER.getPlugin(),"task_running");
    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getDescription() {
        return "Get the status of everything in the digger plugin";
    }

    @Override
    public String getSyntax() {
        return "/digger status";
    }

    @Override
    public void perform(Player player, String[] args) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            player.sendMessage(ChatColor.AQUA+"---------------------");
            on_off_send(namespacedKey_Pos,data,player,"Positions selector: ");
            on_off_send(namespacedKey_Chest,data,player,"Chest selector: ");
            on_off_send(namespacedKey_Use_Chest,data,player,"Use chest: ");
            location_send(namespacedKey_Pos1,data,player,"Pos1 is: ");
            location_send(namespacedKey_Pos2,data,player,"Pos2 is: ");
            location_send(namespacedKey_PosChest,data,player,"Chest pos is: ");
            on_off_send(namespacedKey_Task_Running,data,player,"Digger running: ");
            location_send(namespacedKey_PosCurrent,data,player,"Current pos is: ");;
            player.sendMessage("Digger price: "+ChatColor.GREEN+DataHandler.get_price(namespacedKey_Price,data)+"$");
            player.sendMessage(ChatColor.AQUA+"---------------------");
    }
    public static void on_off_send(NamespacedKey namespacedKey,PersistentDataContainer data,Player player,String message){
        if(DataHandler.get_bool(namespacedKey,data)==1){
            player.sendMessage(message+ ChatColor.GREEN+"ON");
        }else {
            player.sendMessage(message+ ChatColor.RED+"OFF");
        }
    }
    public  static void location_send(NamespacedKey namespacedKey,PersistentDataContainer data,Player player,String message){
        Location location = DataHandler.get_position(namespacedKey,data);
        player.sendMessage(message+ ChatColor.GOLD+"x:"+ChatColor.LIGHT_PURPLE+location.getX()+ChatColor.GOLD+" y:"+ChatColor.LIGHT_PURPLE+location.getY()+ChatColor.GOLD+" z:"+ChatColor.LIGHT_PURPLE+location.getZ());
    }
}
