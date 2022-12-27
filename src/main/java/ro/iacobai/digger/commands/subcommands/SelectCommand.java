package ro.iacobai.digger.commands.subcommands;


import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;



public class SelectCommand extends SubCommand{
    DataHandler dataHandler = new DataHandler();
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
        DataHandler.change_bool(dataHandler.namespacesKey_Pos,data,player,"Digger position selector ");

    }
}





