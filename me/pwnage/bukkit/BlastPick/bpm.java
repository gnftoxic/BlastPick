package me.pwnage.bukkit.BlastPick;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class bpm extends JavaPlugin
{
    public Logger log = Logger.getLogger("Minecraft");
    public Permissions perms = null;
    public PermissionHandler ph;
    public bpbl blockListener = new bpbl(this);
    public HashMap<String, Integer> playersUsing = new HashMap<String, Integer>();
    public int bplimit = 20;
    
    private boolean hasRegistered = false;

    @Override
    public void onEnable()
    {
        if ( !this.hasRegistered ) {
        	// only enable once
        	getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, this.blockListener, Priority.Normal, this);
        	this.hasRegistered = true;
        }

        registerPermissions();
        
        this.log(this.getDescription().getFullName() + " enabled successfully.");
    }

    public void registerPermissions()
    {
        PluginManager pm = getServer().getPluginManager();

        try
        {
            perms = (Permissions)pm.getPlugin("Permissions");
            ph = perms.getHandler();
            this.log("Permissions enabled for use.");
            this.log("Permissions have priority; being an op will do nothing.");
        } catch(NullPointerException npe)
        {
            ph = null;
        }
    }

    @Override
    public void onDisable()
    {
        this.log(this.getDescription().getFullName() + " disabled successfully.");
    }


    @Override
    public boolean onCommand(CommandSender cs, Command c, String command, String[] args)
    {
        if ( this.isEnabled() && command.equalsIgnoreCase("bp") )
        {
          boolean canUse = false;
          if (this.perms != null)
          {
            canUse = this.ph.has((Player)cs, "blastpick.usepick");
          }
          else canUse = cs.isOp();

          String msg = "";
          if (canUse)
          {
            if ((this.playersUsing.containsKey(((Player)cs).getName())) && (args.length == 0))
            {
              this.playersUsing.remove(((Player)cs).getName());
              msg = ChatColor.GREEN + "BlastPick disabled";
            } else {
              int length = 10;

              if (args.length > 0)
              {
                length = Integer.parseInt(args[0]);
                if (length > this.bplimit)
                {
                  length = this.bplimit;
                }
              }

              msg = ChatColor.GREEN + "BlastPick length set to " + length;
              this.playersUsing.remove(((Player)cs).getName());
              this.playersUsing.put(((Player)cs).getName(), Integer.valueOf(length));
            }
          }
          else msg = ChatColor.RED + "You cannot use this command.";

          cs.sendMessage(msg);
          return true;
        }
        return false;
    }
    
    public void log(String string) {
    	this.log.log(Level.INFO, "[" + this.getDescription().getName() + "] "+ string);
    }
}