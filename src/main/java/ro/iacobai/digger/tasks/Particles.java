package ro.iacobai.digger.tasks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;

import java.util.ArrayList;
import java.util.List;

public class Particles {
    DataHandler dataHandler = new DataHandler();
    public void run_t(Player player) {
        player.sendMessage(ChatColor.GREEN+"The particle highlight has started!");
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);
        List<Location> hollowCube = getHollowCube(pos1, pos2, 0.25);

        DIGGER plugin = DIGGER.getPlugin();
        int ID = new BukkitRunnable(){
            @Override
            public void run(){
                if(DataHandler.get_bool(dataHandler.namespaceKey_Task_Highlight,data)==0){
                    this.cancel();
                }
                for (Location element : hollowCube) {
                    World world = Bukkit.getWorld("world");
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.BLUE,1.1F);
                    world.spawnParticle(Particle.REDSTONE,element,3,dustOptions);
                }

            }
        }.runTaskTimer(plugin, 1, 9).getTaskId();
        new BukkitRunnable(){
            @Override
            public void run(){
                player.sendMessage(ChatColor.RED+"The particle highlight has stopped!");
                Bukkit.getScheduler().cancelTask(ID);
                DataHandler.change_bool(dataHandler.namespaceKey_Task_Highlight,data,player,null);
            }
        }.runTaskLater(plugin, 120*20).getTaskId();

    }
    public List<Location> getHollowCube(Location corner1, Location corner2, double particleDistance) {
        corner1.set(corner1.getX(),corner1.getY(),corner1.getZ());
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX())+1;
        double maxY = Math.max(corner1.getY(), corner2.getY())+1;
        double maxZ = Math.max(corner1.getZ(), corner2.getZ())+1;
        for (double x = minX; x <= maxX; x+=particleDistance) {
            for (double y = minY; y <= maxY; y+=particleDistance) {
                for (double z = minZ; z <= maxZ; z+=particleDistance) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }
}
