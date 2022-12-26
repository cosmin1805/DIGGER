package ro.iacobai.digger.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;


public class DigPlace  {
    DataHandler dataHandler = new DataHandler();
    DIGGER digger = DIGGER.getPlugin();
    public void run_t(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);

        int use_chest = DataHandler.get_bool(dataHandler.namespaceKey_Use_Chest,data);
        Location chest_pos = DataHandler.get_position(dataHandler.namespaceKey_PosChest,data);

        DIGGER plugin = DIGGER.getPlugin();
        int ID = new BukkitRunnable(){
            @Override
            public void run(){
                Location current_pos = DataHandler.get_position(dataHandler.namespacesKey_PosCurrent,data);
                Block block = current_pos.getBlock();
                Material material_b = block.getBlockData().getMaterial();
                if (material_b.getHardness()!=-1){
                    if(use_chest==1) {
                        ItemStack item = new ItemStack(block.getBlockData().getMaterial());
                        Material material = chest_pos.getBlock().getBlockData().getMaterial();
                        if(material.equals(Material.CHEST)){
                            Chest chest = (Chest) chest_pos.getBlock().getState();
                            chest.getInventory().addItem(item);
                        }
                    }
                    block.setType(Material.AIR);
                }
                double blocks = DataHandler.get_double(dataHandler.namespaceKey_Task_Blocks,data) - 1;
                DataHandler.save_double(dataHandler.namespaceKey_Task_Blocks,data,blocks);
                if(current_pos.getX()==pos2.getX() && current_pos.getY()==pos2.getY() && current_pos.getZ()==pos2.getZ()) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);
                    player.sendMessage(ChatColor.GREEN+"Digger has finished!");
                    this.cancel();
                }
                else if(current_pos.getX()==pos2.getX()  && current_pos.getZ()==pos2.getZ()){
                    current_pos.setX(pos1.getX());
                    if(pos1.getY()>pos2.getY()){
                        current_pos.set(pos1.getX(),current_pos.getY()-1,pos1.getZ());
                    }
                    else{
                        current_pos.set(pos1.getX(),current_pos.getY()+1,pos1.getZ());
                    }
                }
                else if(current_pos.getZ()==pos2.getZ()){

                    if(pos1.getX()>pos2.getX()){
                        current_pos.set(current_pos.getX()-1,current_pos.getY(),pos1.getZ());
                    }
                    else{
                        current_pos.set(current_pos.getX()+1,current_pos.getY(),pos1.getZ());
                    }
                }
                else {
                    if(pos1.getZ()>pos2.getZ()){
                        current_pos.setZ(current_pos.getZ()-1);
                    }
                    else{
                        current_pos.setZ(current_pos.getZ()+1);
                    }
                }
                DataHandler.save_position(dataHandler.namespacesKey_PosCurrent,data,current_pos);
            }
        }.runTaskTimer(plugin, 1, digger.getConfig().getInt("Time") *20).getTaskId();
        DataHandler.save_int(dataHandler.namespaceKey_Task_Id,data,ID);
    }

}
