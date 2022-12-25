package ro.iacobai.digger.commands.subcommands;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.data.DataHandler;
import ro.iacobai.digger.tasks.DigPlace;

public class ConfirmCommand extends SubCommand {
    NamespacedKey namespacedKey_Confirm = new NamespacedKey(DIGGER.getPlugin(),"task_await_confirm");
    NamespacedKey namespacedKey_Price = new NamespacedKey(DIGGER.getPlugin(),"task_price");
    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public String getDescription() {
        return "Confirm a digger action";
    }

    @Override
    public String getSyntax() {
        return "/digger confirm";
    }

    @Override
    public void perform(Player player, String[] args) {
        Economy economy = DIGGER.getEconomy();
        PersistentDataContainer data = player.getPersistentDataContainer();
        int confirm = DataHandler.get_bool(namespacedKey_Confirm,data);
        if(confirm == 1){
            double price = DataHandler.get_price(namespacedKey_Price,data);
            if(economy.getBalance(player)-price>=0)
            {
                EconomyResponse response = economy.withdrawPlayer(player,price);
                if(response.transactionSuccess())
                {
                    player.sendMessage("Taken "+ChatColor.GREEN+ price+"$");
                    player.sendMessage(ChatColor.GREEN+"DIGGER HAS STARTED!");
                    DigPlace digPlace = new DigPlace();
                    digPlace.run_t(player);
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED+"Not enough founds!");
            }
            DataHandler.change_bool(namespacedKey_Confirm,data,player,null);
        }else {
            player.sendMessage(ChatColor.RED+"First do /digger start or /digger stop !");
        }
    }
}
