package net.simple.forscore.plugin;

import github.scarsz.discordsrv.DiscordSRV;
import net.simple.forscore.plugin.advancements.Achievements;
import net.simple.forscore.plugin.advancements.AchievementsAFK;
import net.simple.forscore.plugin.advancements.CreateFile;
import net.simple.forscore.plugin.armorstand.ArmorPose;
import net.simple.forscore.plugin.armorstand.ArmorSpawn;
import net.simple.forscore.plugin.discordsrv.LinkEvent;
import net.simple.forscore.plugin.experience.drop.ExpDrop;
import net.simple.forscore.plugin.raidfix.RaidFix;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbrikster.chatty.Chatty;
import ru.mrbrikster.chatty.api.ChattyApi;

import java.io.File;

public final class Main extends JavaPlugin {
    public ChattyApi chattyApi;

    @Override
    public void onEnable() {
        File path = new File(getDataFolder() + File.separator + "/advancements/");
        if(!path.exists()) {
            path.mkdirs();
        }

        File path_2 = new File(getDataFolder() + File.separator + "/progress/");
        if(!path_2.exists()) {
            path_2.mkdirs();
        }
        try {
            chattyApi = ((Chatty) Bukkit.getPluginManager().getPlugin("Chatty")).api();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        // Advancements
        Bukkit.getPluginManager().registerEvents(new CreateFile(this), this);
        Bukkit.getPluginManager().registerEvents(new Achievements(this), this);
        Bukkit.getPluginManager().registerEvents(new AchievementsAFK(this), this);
        // DiscordSRV
        DiscordSRV.api.subscribe(new LinkEvent(this));
        // ArmorStand
        Bukkit.getPluginManager().registerEvents(new ArmorPose(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorSpawn(), this);
        // RaidFix
        Bukkit.getPluginManager().registerEvents(new RaidFix(), this);
        // Death Experience Drop
        Bukkit.getPluginManager().registerEvents(new ExpDrop(), this);

        getLogger().info("Plugin successfully started up!");
    }

    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(new LinkEvent(this));
        Bukkit.getScheduler().cancelTasks(this);

        getLogger().info("Plugin successfully disabled up!");
    }
}
