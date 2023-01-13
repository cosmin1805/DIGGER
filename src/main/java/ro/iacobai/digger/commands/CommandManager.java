package ro.iacobai.digger.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.persistence.PersistentDataContainer;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.commands.subcommands.*;
import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    DataHandler dataHandler = new DataHandler();
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public  CommandManager(){
        subcommands.add(new SelectCommand());
        subcommands.add(new ChestCommand());
        subcommands.add(new StatusCommand());
        subcommands.add(new StartCommand());
        subcommands.add(new ConfirmCommand());
        subcommands.add(new CancelCommand());
        subcommands.add(new ParticleCommand());
        subcommands.add(new ResumeCommand());
        subcommands.add(new PauseCommand());
        subcommands.add(new BreakCommand());
        subcommands.add(new GuiCommand());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
                if(DataHandler.get_bool(dataHandler.namespaceKey_Await_Confirm,data) == 1 || DataHandler.get_bool(dataHandler.namespaceKey_Running,data) == 1){
                    String[] blocked_commands= {"select","start"};
                    for (int i = 0; i < blocked_commands.length; i++){
                        if(args[0].equalsIgnoreCase(blocked_commands[i])){
                            if(DataHandler.get_bool(dataHandler.namespaceKey_Running,data)==1){
                                p.sendMessage(ChatColor.RED+"Can't run this command! Please cancel your current digger with /digger cancel or wait for it to finish!");
                            }
                            else {
                                p.sendMessage(ChatColor.RED+"Can't run this command! Please confirm yor current selection with /digger confirm or cancel it with /digger cancel !");
                            }
                            return true;
                        }
                    }
                }
                for (int i = 0; i < getSubcommands().size(); i++){
                    if(args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p,args);
                    }
                }
            }
        }else {
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("reload")){
                    DIGGER plugin = DIGGER.getPlugin();
                    System.out.println("RELOADING PLUGIN CONFIG!");
                    plugin.reloadConfig();
                }
            }
        }
        return true;
    }
    public ArrayList<SubCommand> getSubcommands(){return  subcommands;}

}
