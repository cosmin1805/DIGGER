package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ro.iacobai.digger.DIGGER;



public class Hopper {
    DIGGER digger = DIGGER.getPlugin();
    public ItemStack check_inventory_tools_durability(org.bukkit.block.Hopper hopper_data, int use_break){
        for(int i = 0;i<5;i++)
        {
            ItemStack tool = hopper_data.getInventory().getItem(i);
            if(tool != null)
            {
                Damageable meta = (Damageable) tool.getItemMeta();
                Material material = tool.getData().getItemType();
                
                if(material.getMaxDurability()  < digger.getConfig().getInt("Damage_taken_item") + meta.getDamage() && material.getCreativeCategory().name() == "TOOLS")
                {
                    if(use_break == 1){
                        hopper_data.getInventory().remove(tool);
                        return tool;
                    }
                }
                else if (material.getCreativeCategory().name() == "TOOLS"){
                    meta.setDamage(meta.getDamage() + digger.getConfig().getInt("Damage_taken_item"));
                    tool.setItemMeta(meta);
                    return tool;
                }
            }
        }
        return null;
    }
    public ItemStack check_most_efficient(org.bukkit.block.Hopper hopper_data, Location current_pos, int use_break) {
        int time_small = 9999999;
        ItemStack best_tool = null;
        for(int i = 0;i<5;i++)
        {
            ItemStack tool = hopper_data.getInventory().getItem(i);
            if(tool != null)
            {
                    Damageable meta = (Damageable) tool.getItemMeta();
                    Material material = tool.getData().getItemType();
                    if(material.getMaxDurability()  <= digger.getConfig().getInt("Damage_taken_item") + meta.getDamage() && material.getCreativeCategory().name() == "TOOLS" && use_break == 1)
                    {
                        int time = new TimeToBreakBlock().calculate(tool,current_pos);
                        if(time < time_small)
                        {
                            time_small = time;
                            best_tool=tool;
                        }
                    } else if (material.getMaxDurability()  > digger.getConfig().getInt("Damage_taken_item") + meta.getDamage() && material.getCreativeCategory().name() == "TOOLS") {
                        int time = new TimeToBreakBlock().calculate(tool,current_pos);
                        if(time < time_small)
                        {
                            time_small = time;
                            best_tool=tool;
                        }
                    }
            }
        }
        return best_tool;
    }
    public void damage_tool(ItemStack tool,int use_break,org.bukkit.block.Hopper hopper_data)
    {
        Damageable meta = (Damageable) tool.getItemMeta();
        Material material = tool.getData().getItemType();
        if(material.getMaxDurability()  <= digger.getConfig().getInt("Damage_taken_item") + meta.getDamage() && material.getCreativeCategory().name() == "TOOLS")
        {
            if(use_break == 1){
                hopper_data.getInventory().remove(tool);
            }
        }
        else if (material.getCreativeCategory().name() == "TOOLS"){
            meta.setDamage(meta.getDamage() + digger.getConfig().getInt("Damage_taken_item") - meta.getEnchantLevel(Enchantment.DURABILITY));
            tool.setItemMeta(meta);
        }
    }
}
