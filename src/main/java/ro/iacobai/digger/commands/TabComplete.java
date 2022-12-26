package ro.iacobai.digger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==1){
            List<String> tabs =Arrays.asList("select","chest","status","start","confirm","cancel","particle");
            return tabs;
        }
        else if (args.length==2 && args[1].equals("select")){
            List<String> tabs = Arrays.asList("pos","chest");
            return tabs;
        }
        return null;
    }
}
