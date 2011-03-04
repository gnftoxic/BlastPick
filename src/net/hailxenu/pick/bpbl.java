package net.hailxenu.pick;

import java.util.logging.Level;
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

                if(x >= 45)
                {
                    // DOWN
                } else if(x > -45)
                {
                    // STRAIGHT
                } else if(x <= -45)
                {
                    // UP
                }
            } else {
                plugin.log.log(Level.INFO, p.getName() + " was not allowed to use /bp");
            }
        }
    }
}
