package net.hailxenu.pick;

import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

public class bpbl extends BlockListener
{
    private bpm plugin;
    public bpbl(bpm plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onBlockRightClick(BlockRightClickEvent event)
    {
        Player p = event.getPlayer();
        if(p.getItemInHand().getTypeId() == 278)
        {
            boolean canUse = false;
            if(plugin.perms != null)
            {
                canUse = plugin.ph.has(p, "blastpick.usepick");
            } else {
                canUse = event.getPlayer().isOp();
            }

            if(canUse && plugin.playersUsing.containsKey(p.getName()))
            {
                int x = (int)p.getLocation().getPitch();

                Location bl = event.getBlock().getLocation();

                if(x >= 35)
                {
                    // DOWN
                    for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                    {
                        Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - pos, bl.getBlockZ()));

                        replaceBlock(nb);
                    }
                } else if(x > -55)
                {
                    // STRAIGHT
                    int dir = (int)p.getLocation().getYaw();
                    if(dir < 0)
                    {
                        dir *= -1;
                    }
                    dir %= 360;
                    if(dir >= 300 || (dir >= 0 && dir <= 60))
                    {
                        for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() + pos));

                            replaceBlock(nb);
                        }
                    }
                    if(dir > 60 && dir <= 120)
                    {
                        for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY(), bl.getBlockZ()));

                            replaceBlock(nb);
                        }
                    }
                    if(dir > 120 && dir <= 210)
                    {
                        for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() - pos));

                            replaceBlock(nb);
                        }
                    }
                    if(dir > 210 && dir <= 300)
                    {
                        for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY(), bl.getBlockZ()));

                            replaceBlock(nb);
                        }
                    }
                } else if(x <= -35)
                {
                    // UP
                    for(int pos = 0; pos < plugin.playersUsing.get(p.getName()); pos++)
                    {
                        Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + pos, bl.getBlockZ()));

                        replaceBlock(nb);
                    }
                }
            }
        }
    }

    public void replaceBlock(Block b)
    {
        switch(b.getType())
        {
            case CHEST:
            case FURNACE:
            case WORKBENCH:
                return;
            default:
                b.setType(Material.AIR);
        }
    }
}
