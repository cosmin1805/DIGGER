package ro.iacobai.digger.commands.subcommands;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;


import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

import static java.lang.Math.abs;
import static ro.iacobai.digger.commands.subcommands.StatusCommand.location_send;


public class StartCommand extends SubCommand {
    DataHandler dataHandler = new DataHandler();
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts the digger, but needs confirmation!";
    }

    @Override
    public String getSyntax() {
        return "/digger start";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
        int h = (int) (abs(abs(pos1.getY()) - abs(pos2.getY())))+1;
        int l = (int)(abs(abs(pos1.getX()) - abs(pos2.getX())))+1;
        int w = (int)(abs(abs(pos1.getZ()) - abs(pos2.getZ())))+1;
        double number_of_blocks = h*l*w;
        DataHandler.save_double(dataHandler.namespaceKey_Blocks_Remaining,data,number_of_blocks);
        player.sendMessage(ChatColor.AQUA+"---------------------");
        location_send(dataHandler.namespaceKey_Pos1,data,player,"Pos1 is: ");
        location_send(dataHandler.namespaceKey_Pos2,data,player,"Pos2 is: ");
        player.sendMessage("This will remove : "+ ChatColor.GREEN+number_of_blocks+" blocks");
        player.sendMessage("Confirm this with /digger confirm!");
        player.sendMessage(ChatColor.AQUA+"---------------------");
        DataHandler.change_bool(dataHandler.namespaceKey_Await_Confirm,data,player,null);
    }
}
