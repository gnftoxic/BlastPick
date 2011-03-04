package net.hailxenu.pick;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class bppl extends PlayerListener
{
    private bpm plugin;
    public bppl(bpm plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerChatEvent event)
    {
        String[] split = event.getMessage().split(" ");
        String command = split[0];

        if(command.equalsIgnoreCase("/bp"))
        {
            boolean canUse = false;
            if(plugin.perms != null)
            {
                canUse = plugin.ph.has(event.getPlayer(), "blastpick.usepick");
            } else {
                canUse = event.getPlayer().isOp();
            }

            String msg = "";
            if(canUse)
            {
                if(plugin.playersUsing.containsKey(event.getPlayer().getName()))
                {
                    plugin.playersUsing.remove(event.getPlayer().getName());
                    msg = ChatColor.GREEN + "BlastPick disabled";
                } else {
                    int length = 10;

                    if(split.length > 2)
                    {
                        length = Integer.parseInt(split[2]);
                    }

                    msg = ChatColor.GREEN + "BlastPick length set to " + length;
                    plugin.playersUsing.put(event.getPlayer().getName(), length);
                }
            } else {
                msg = ChatColor.RED + "You cannot use this command.";
            }

            event.getPlayer().sendMessage(msg);
        }
    }
}
