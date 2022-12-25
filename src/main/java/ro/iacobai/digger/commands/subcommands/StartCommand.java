package ro.iacobai.digger.commands.subcommands;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;

import static java.lang.Math.abs;

public class StartCommand extends SubCommand {
    NamespacedKey namespacedKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    NamespacedKey namespacedKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts the digger, but need confirmation!";
    }

    @Override
    public String getSyntax() {
        return "/digger start";
    }

    @Override
    public void perform(Player player, String[] args) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Economy economy = DIGGER.getEconomy();
        Location pos1 = DataHandler.get_position(namespacedKey_Pos1,data);
        Location pos2 = DataHandler.get_position(namespacedKey_Pos2,data);
        int h = (int) (abs(abs(pos1.getY()) - abs(pos2.getY())))+1;
        int l = (int)(abs(abs(pos1.getX()) - abs(pos2.getX())))+1;
        int w = (int)(abs(abs(pos1.getZ()) - abs(pos2.getZ())))+1;
        int number_of_blocks = h*l*w;
        EconomyResponse response = economy.withdrawPlayer(player,20);
        if(response.transactionSuccess())
        {
            player.sendMessage("Taken 20 dollars!");
        }
        else
        {
            player.sendMessage("Not taken 20 dollars!");
        }
    }
}
