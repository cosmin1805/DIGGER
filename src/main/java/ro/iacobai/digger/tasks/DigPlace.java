package ro.iacobai.digger.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import ro.iacobai.digger.DIGGER;
import ro.iacobai.digger.blocks.Chest_;
import ro.iacobai.digger.blocks.TimeToBreakBlock;
import ro.iacobai.digger.data.DataHandler;


public class DigPlace  {
    DataHandler dataHandler = new DataHandler();

    public void run_t(Player player, int time) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Location pos1 = DataHandler.get_position(dataHandler.namespaceKey_Pos1,data);
        Location pos2 = DataHandler.get_position(dataHandler.namespaceKey_Pos2,data);

        Location chest_pos = DataHandler.get_position(dataHandler.namespaceKey_PosChest,data);
        Location hopper_pos = DataHandler.get_position(dataHandler.namespaceKey_PosHopper,data);
        DIGGER plugin = DIGGER.getPlugin();
        int ID = new BukkitRunnable(){
            @Override
            public void run(){
                int use_chest = DataHandler.get_bool(dataHandler.namespaceKey_Use_Chest,data);
                int use_break = DataHandler.get_bool(dataHandler.namespaceKey_Use_Break,data);
                Location current_pos = DataHandler.get_position(dataHandler.namespacesKey_PosCurrent,data);
                Block current_block = current_pos.getBlock();
                Material material_current_block = current_block.getBlockData().getMaterial();
                Block hopper = hopper_pos.getBlock();
                Material material_hopper = hopper.getBlockData().getMaterial();
                ItemStack tool = null;
                if(material_hopper.equals(Material.HOPPER)){
                    Hopper hopper_data = (Hopper) hopper.getState();
                    if(material_current_block.getHardness()!=-1 && material_current_block.getHardness() <= 50 && !material_current_block.isAir()){
                        tool = new ro.iacobai.digger.blocks.Hopper().check_most_efficient(hopper_data,current_pos,use_break);
                        if(tool == null){
                            DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                            player.sendMessage(ChatColor.RED+"There are no tools in the hopper! So digger was paused!");
                            return;
                        }
                        else {
                            new ro.iacobai.digger.blocks.Hopper().damage_tool(tool,use_break,hopper_data);
                        }
                    }
                    if(use_chest==1) {
                        boolean chest_exists = new Chest_().exists(chest_pos);
                        if(!chest_exists)
                        {
                            DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                            player.sendMessage(ChatColor.RED+"The chest is missing! So digger was paused!");
                            return;
                        }
                        boolean chest_has_Space = new Chest_().has_space(chest_pos);
                        if(!chest_has_Space){
                            DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                            player.sendMessage(ChatColor.RED+"The chest has no space left! So digger was paused!");
                            return;
                        }
                    }
                }
                else {
                    DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                    player.sendMessage(ChatColor.RED+"The hopper is missing! So digger was paused!");
                    return;
                }
                if(material_current_block.getHardness()!=-1 && material_current_block.getHardness() <= 50 && !material_current_block.isAir() && tool != null){
                    current_block.breakNaturally(tool);
                    if(use_chest==1) {
                        new Chest_().get_nearby_entities(current_pos,chest_pos);
                    }
                }
                double blocks = DataHandler.get_double(dataHandler.namespaceKey_Task_Blocks,data) - 1;
                DataHandler.save_double(dataHandler.namespaceKey_Task_Blocks,data,blocks);
                //STOP IF CURRENT POS IS POS2
                if(current_pos.getX()==pos2.getX() && current_pos.getY()==pos2.getY() && current_pos.getZ()==pos2.getZ()) {
                    DataHandler.change_bool(dataHandler.namespaceKey_Task_Running,data,player,null);
                    player.sendMessage(ChatColor.GREEN+"Digger has finished!");
                    return;
                }
                //MOKVE THE CURRENT BLOCK
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
                int ticks= 0;
                if(tool != null){
                    Hopper hopper_data = (Hopper) hopper.getState();
                    tool = new ro.iacobai.digger.blocks.Hopper().check_most_efficient(hopper_data,current_pos,use_break);
                    if(tool != null){
                        ticks = new TimeToBreakBlock().calculate(tool,current_pos);
                    }
                    else{
                        DataHandler.change_bool(dataHandler.namespaceKey_Task_Pause,data,player,null);
                        player.sendMessage(ChatColor.RED+"There are no tools in the hopper! So digger was paused!");
                        return;
                    }
                }
                DataHandler.save_int(dataHandler.namespaceKey_Task_Next_Time,data,ticks);
                run_t(player,ticks);
            }
        }.runTaskLater(plugin, time).getTaskId();
        DataHandler.save_int(dataHandler.namespaceKey_Task_Id,data,ID);
    }

}
