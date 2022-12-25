package ro.iacobai.digger.commands.subcommands;


import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

import java.util.Objects;

public class SelectCommand extends SubCommand{
    NamespacedKey namespacedKey_Pos = new NamespacedKey(DIGGER.getPlugin(),"pos_select");
    NamespacedKey namespacedKey_Chest = new NamespacedKey(DIGGER.getPlugin(),"chest_select");
    @Override
    public String getName() {
        return "select";
    }

    @Override
    public String getDescription() {
        return "Scan with a stick the positions for digger.";
    }

    @Override
    public String getSyntax() {
        return "/digger select <pos/chest>";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(args.length==1){
            player.sendMessage("Use /digger select <pos/chest>");
            return;
        }
        if(Objects.equals(args[1], "chest"))
        {
            DataHandler.change_bool(namespacedKey_Chest,data,player,"Digger chest selector ");
        }
        else if(Objects.equals(args[1], "pos")){
            DataHandler.change_bool(namespacedKey_Pos,data,player,"Digger position selector ");
        }
    }
}





