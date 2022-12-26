package ro.iacobai.digger.commands.subcommands;

import org.bukkit.entity.Player;
import ro.iacobai.digger.commands.SubCommand;
import ro.iacobai.digger.tasks.Particles;

public class ParticleCommand extends SubCommand {
    @Override
    public String getName() {
        return "particle";
    }

    @Override
    public String getDescription() {
        return "Highlight the selected digger area for 120 seconds";
    }

    @Override
    public String getSyntax() {
        return "/digger particle";
    }

    @Override
    public void perform(Player player, String[] args) {
        Particles particles = new Particles();
        particles.run_t(player);
    }
}
