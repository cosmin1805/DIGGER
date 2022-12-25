package ro.iacobai.digger.commands;


import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.subcommands.*;
import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    NamespacedKey namespacedKey_Confirm = new NamespacedKey(DIGGER.getPlugin(),"task_await_confirm");
    NamespacedKey namespacedKey_Task_Running= new NamespacedKey(DIGGER.getPlugin(),"task_running");
    private ArrayList<SubCommand> subcommands = new ArrayList<>();
    public  CommandManager(){
        subcommands.add(new SelectCommand());
        subcommands.add(new ChestCommand());
        subcommands.add(new StatusCommand());
        subcommands.add(new StartCommand());
        subcommands.add(new ConfirmCommand());
        subcommands.add(new CancelCommand());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            PersistentDataContainer data = p.getPersistentDataContainer();
            if(args.length == 0){
                p.sendMessage(ChatColor.AQUA+"---------------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax()+" - "+getSubcommands().get(i).getDescription());
                }
                p.sendMessage(ChatColor.AQUA+"---------------------");
            } else if (args.length > 0) {
                int confirm = DataHandler.get_bool(namespacedKey_Confirm,data);
                int task_running = DataHandler.get_bool(namespacedKey_Task_Running,data);
                if(confirm == 1){
                    if(args[0].equalsIgnoreCase(getSubcommands().get(3).getName())){
                        getSubcommands().get(3).perform(p,args);
                    }
                    if(args[0].equalsIgnoreCase(getSubcommands().get(4).getName())){
                        getSubcommands().get(4).perform(p,args);
                    }
                    else if(args[0].equalsIgnoreCase(getSubcommands().get(5).getName())){
                        getSubcommands().get(5).perform(p,args);
                    }
                    else if(task_running==1){
                        p.sendMessage(ChatColor.RED+"Can't run this command! Please cancel your current digger with /digger cancel or wait for it to finish!");
                        return true;
                    }
                    else {
                        p.sendMessage(ChatColor.RED+"Can't run this command! Please confirm yor current selection with /digger confirm or cancel it with /digger cancel !");
                    }
                    return true;
                }
                for (int i = 0; i < getSubcommands().size(); i++){
                    if(args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p,args);
                    }
                }
            }
        }
        return true;
    }
    public ArrayList<SubCommand> getSubcommands(){return  subcommands;}

}
