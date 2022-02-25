package net.simple.forscore.plugin.raidfix;

import net.simple.forscore.plugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

import java.util.HashMap;

public class RaidFix implements Listener {
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

    @EventHandler
    public void onTriggerRaid(RaidTriggerEvent event) {
        int cooldownTime = 21600;
        if(cooldowns.containsKey(event.getPlayer().getName())) {
            long secondsLeft = ((cooldowns.get(event.getPlayer().getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0) {
                event.setCancelled(true);
                return;
            }
        }
        cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis());
    }
}
