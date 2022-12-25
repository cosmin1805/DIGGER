package ro.iacobai.digger.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.data.DataHandler;





public class DigPlace  {

    NamespacedKey namespacedKey_Task_Running= new NamespacedKey(DIGGER.getPlugin(),"task_running");
    NamespacedKey namespacedKey_Task_Id= new NamespacedKey(DIGGER.getPlugin(),"task_id");
    NamespacedKey namespacedKey_Task_Blocks = new NamespacedKey(DIGGER.getPlugin(),"task_blocks");
    NamespacedKey namespacedKey_Use_Chest = new NamespacedKey(DIGGER.getPlugin(),"use_chest");
    NamespacedKey namespacedKey_Pos1 = new NamespacedKey(DIGGER.getPlugin(),"task_pos1");
    NamespacedKey namespacedKey_Pos2 = new NamespacedKey(DIGGER.getPlugin(),"task_pos2");
    NamespacedKey namespacedKey_PosChest = new NamespacedKey(DIGGER.getPlugin(),"chest_pos");
    NamespacedKey namespacedKey_PosCurrent = new NamespacedKey(DIGGER.getPlugin(),"current_pos");

    public void run_t(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        DataHandler.change_bool(namespacedKey_Task_Running,data,player,null);

        Location pos1 = DataHandler.get_position(namespacedKey_Pos1,data);
        DataHandler.save_position(namespacedKey_PosCurrent,data,pos1);
        Location pos2 = DataHandler.get_position(namespacedKey_Pos2,data);

        int use_chest = DataHandler.get_bool(namespacedKey_Use_Chest,data);
        Location chest_pos = DataHandler.get_position(namespacedKey_PosChest,data);

        DIGGER plugin = DIGGER.getPlugin();
        int ID = new BukkitRunnable(){
            @Override
            public void run(){
                Location current_pos = DataHandler.get_position(namespacedKey_PosCurrent,data);
                Block block = current_pos.getBlock();
                Material material_b = block.getBlockData().getMaterial();
                if (!material_b.equals(Material.BEDROCK)){
                    if(use_chest==1) {
                        ItemStack item = new ItemStack(block.getBlockData().getMaterial());
                        Material material = chest_pos.getBlock().getBlockData().getMaterial();
                        if(material.equals(Material.CHEST)){
                            Chest chest = (Chest) chest_pos.getBlock().getState();
                            chest.getInventory().addItem(item);
                        }
                    }
                    block.setType(Material.AIR);
                    data.set(namespacedKey_Task_Blocks, PersistentDataType.INTEGER, data.get(namespacedKey_Task_Blocks, PersistentDataType.INTEGER) - 1);
                }
                if(current_pos.getX()==pos2.getX() && current_pos.getY()==pos2.getY() && current_pos.getZ()==pos2.getZ()) {
                    DataHandler.change_bool(namespacedKey_Task_Running,data,player,null);
                    player.sendMessage("Digger has finished!");
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
                DataHandler.save_position(namespacedKey_PosCurrent,data,current_pos);
            }
        }.runTaskTimer(plugin, 1,12 *20).getTaskId();
        data.set(namespacedKey_Task_Id, PersistentDataType.INTEGER, ID);
    }

}
