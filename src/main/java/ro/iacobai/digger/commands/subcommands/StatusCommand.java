package ro.iacobai.digger.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;



public class StatusCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();

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
            on_off_send(dataHandler.namespacesKey_Pos_Select,data,player,"Positions selector: ");
            on_off_send(dataHandler.namespaceKey_Use_Chest,data,player,"Use chest: ");
            on_off_send(dataHandler.namespaceKey_Use_Break,data,player,"Break tools: ");
            location_send(dataHandler.namespaceKey_Pos1,data,player,"Pos1 is: ");
            location_send(dataHandler.namespaceKey_Pos2,data,player,"Pos2 is: ");
            location_send(dataHandler.namespaceKey_PosChest,data,player,"Chest pos is: ");
            location_send(dataHandler.namespaceKey_PosHopper,data,player,"Hopper pos is: ");
            on_off_send(dataHandler.namespaceKey_Running,data,player,"Digger running: ");
            on_off_send(dataHandler.namespaceKey_Pause,data,player,"Digger pause: ");
            location_send(dataHandler.namespacesKey_PosCurrent,data,player,"Current pos is: ");
            double number_of_blocks = DataHandler.get_double(dataHandler.namespaceKey_Blocks_Remaining,data);
            player.sendMessage("Blocks remaining: "+ChatColor.GREEN+number_of_blocks+" blocks");
            player.sendMessage("Last message: "+ChatColor.GREEN+DataHandler.get_string(dataHandler.namespaceKey_Task_Last_Message,data));
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
