package ro.iacobai.digger.commands.subcommands;

import org.bukkit.entity.Player;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.gui.SelectionScreen;

public class GuiCommand extends SubCommand {
    @Override
    public String getName() {
        return "gui";
    }

    @Override
    public String getDescription() {
        return "opens the digger gui";
    }

    @Override
    public String getSyntax() {
        return "/digger gui";
    }

    @Override
    public void perform(Player player, String[] args) {
        SelectionScreen gui = new SelectionScreen(player);
        player.openInventory(gui.getInventory());
    }
}
