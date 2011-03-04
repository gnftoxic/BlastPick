package net.hailxenu.pick;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class bpm extends JavaPlugin
{
    public Logger log = Logger.getLogger("Minecraft");
    public String name = "BlastPick";
    public String ver = "1.0.0";
    public Permissions perms = null;
    public PermissionHandler ph;
    public bpbl blockListener = new bpbl(this);
    public bppl playerListener = new bppl(this);
    public HashMap<String, Integer> playersUsing = new HashMap<String, Integer>();
    
    public void onEnable()
    {
        PluginManager pm = getServer().getPluginManager();
        
        try
        {
            if(pm.getPlugin("Permissions").isEnabled())
            {
                perms = ((Permissions)pm.getPlugin("Permissions"));
                ph = perms.getHandler();
                log.log(Level.INFO, "[" + name + "] Permissions " + perms.getDescription().getVersion() + " enabled for use.");
            }
        } catch(NullPointerException npe)
        {
            perms = null;
            log.log(Level.INFO, "[" + name + "] Permissions not enabled.");
        }

        pm.registerEvent(Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
        pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);
        
        log.log(Level.INFO, "[" + name + "] " + name + " " + ver + " enabled successfully.");
    }

    public void onDisable()
    {
        log.log(Level.INFO, "[" + name + "] " + name + " " + ver + " disabled successfully.");
    }
}
