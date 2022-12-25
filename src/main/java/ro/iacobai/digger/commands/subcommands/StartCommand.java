package ro.iacobai.digger.commands.subcommands;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

import static java.lang.Math.abs;
import static ro.iacobai.digger.commands.subcommands.StatusCommand.location_send;
import static ro.iacobai.digger.data.DataHandler.save_price;

public class StartCommand extends SubCommand {
    NamespacedKey namespacedKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    NamespacedKey namespacedKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    NamespacedKey namespacedKey_Price = new NamespacedKey(DIGGER.getPlugin(),"task_price");
    NamespacedKey namespacedKey_Confirm = new NamespacedKey(DIGGER.getPlugin(),"task_await_confirm");
    NamespacedKey namespacedKey_Task_Blocks = new NamespacedKey(DIGGER.getPlugin(),"task_blocks");
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
        Location pos1 = DataHandler.get_position(namespacedKey_Pos1,data);
        Location pos2 = DataHandler.get_position(namespacedKey_Pos2,data);
        int h = (int) (abs(abs(pos1.getY()) - abs(pos2.getY())))+1;
        int l = (int)(abs(abs(pos1.getX()) - abs(pos2.getX())))+1;
        int w = (int)(abs(abs(pos1.getZ()) - abs(pos2.getZ())))+1;
        int number_of_blocks = h*l*w;
        double price = number_of_blocks * 5;
        save_price(namespacedKey_Price,data,price);
        player.sendMessage(ChatColor.AQUA+"---------------------");
        location_send(namespacedKey_Pos1,data,player,"Pos1 is: ");
        location_send(namespacedKey_Pos2,data,player,"Pos2 is: ");
        player.sendMessage("This will cost you: "+ ChatColor.GREEN+price+"$");
        player.sendMessage("This will take to finish: "+ ChatColor.GREEN+number_of_blocks*12+" seconds");
        player.sendMessage("This will remove : "+ ChatColor.GREEN+number_of_blocks+" blocks");
        data.set(namespacedKey_Task_Blocks, PersistentDataType.INTEGER,  number_of_blocks);
        player.sendMessage("Confirm this with /digger confirm!");
        player.sendMessage(ChatColor.AQUA+"---------------------");
        DataHandler.change_bool(namespacedKey_Confirm,data,player,null);

    }
}
