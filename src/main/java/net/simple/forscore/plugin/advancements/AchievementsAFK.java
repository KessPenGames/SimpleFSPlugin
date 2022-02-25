package net.simple.forscore.plugin.advancements;

import eu.endercentral.crazy_advancements.JSONMessage;
import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.simple.forscore.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AchievementsAFK implements Listener {
    private Main plugin;
    public AchievementsAFK(Main plugin) {
        this.plugin = plugin;
    }

    Map<Player, Integer> afkTimers = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        resetTimer(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.afk");
                if (!isget) {
                    Player player = event.getPlayer();
                    Bukkit.getScheduler().cancelTask(afkTimers.get(player));
                    resetTimer(player);
                }
            }
        });
    }

    public void resetTimer(final Player p) {
        afkTimers.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (Bukkit.getPlayer(p.getName()) == null) return;

            File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
            FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
            boolean isget = adv.getBoolean("forscore.afk");
            if (!isget) {
                ItemStack icon = new ItemStack(Material.NETHERITE_BOOTS);
                JSONMessage description = new JSONMessage(new TextComponent("Сильные ноги"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(p);

                TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Сильные ноги]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Сильные ноги\n" + "Простоять в АФК 1 час").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(p.getName() + " получил достижение Сильные ноги", null, "https://mc-heads.net/avatar/" + p.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.afk", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 72000)); // Time
    }
}
