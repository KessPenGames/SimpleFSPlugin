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
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import ru.mrbrikster.chatty.api.events.ChattyMessageEvent;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Achievements implements Listener {
    private final Main plugin;
    public Achievements(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void start(PlayerJoinEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.start");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.BLUE_WOOL);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("forscore");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ?????? ?? ???????????????? ????????..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ?????? ?? ???????????????? ????????...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ?????? ?? ???????????????? ????????...\n" + "?????????????? ?????????? ???? ????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????? ?????? ?? ???????????????? ????????...", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.start", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void ezra8(PlayerDeathEvent event) {
        String nick = event.getEntity().getName();
        if (!nick.equals("EZRA_8")) return;

        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.ezra8");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.PORKCHOP);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("salo");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ???????????????????? ??????????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(killer);

            TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????? ???????????????????? ??????????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????? ???????????????????? ??????????????????\n" + "???????????? ???????????? EZRA_8").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ???????????????? ???????????????????? ??????????????????", null, "https://mc-heads.net/avatar/" + killer.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.ezra8", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void arstart(PlayerPickupItemEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.arstart");
        if (!isget) {
            Material item = event.getItem().getItemStack().getType();
            switch (item) {
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    break;
                default:
                    return;
            }

            ItemStack icon = new ItemStack(Material.DEEPSLATE_DIAMOND_ORE);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ????????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ????????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ????????????????\n" + "???????????????? ???????? ???????????? ????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????? ????????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.arstart", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void darvin(Player p) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.darvin");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.GOLD_INGOT);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("darvinpremia");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ??????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????? ??????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????? ??????????????\n" + "?????????????? ?? ?????? ?????????? ?????????? ????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????? ??????????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.darvin", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void gordon(PlayerItemConsumeEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.gordon");
        if (!isget) {
            if (!event.getItem().getType().equals(Material.POISONOUS_POTATO)) return;

            ItemStack icon = new ItemStack(Material.POISONOUS_POTATO);
            JSONMessage description = new JSONMessage(new TextComponent("?????????????? ???????????? ??????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????? ???????????? ??????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????? ???????????? ??????????\n" + "?????????????? ???????????????? ??????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????????????? ???????????? ??????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.gordon", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void leonid(PlayerInteractEntityEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.leonid");
        if (!isget) {
            if (event.getRightClicked() instanceof Chicken) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    synchronized (this) {
                        if (!event.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG)) return;
                        if (!event.getRightClicked().getName().equals("????????????")) return;

                        ItemStack icon = new ItemStack(Material.CHICKEN);
                        JSONMessage description = new JSONMessage(new TextComponent("????????????"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[????????????]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "????????????\n" + "???????????????????????? ???????????? ?? \"????????????\"").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                        adv.set("forscore.leonid", true);

                        try {
                            adv.save(adv_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 10);
            }
        }
    }

    @EventHandler
    public void ban(PlayerDeathEvent event) {
        String nick = event.getEntity().getName();
        switch (nick) {
            case "MrGridlock":
            case "MindLooker":
            case "gangster848":
                break;
            default:
                return;
        }

        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.ban");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.PAPER);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("ban");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("/ban"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(killer);

            TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[/ban]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "/ban\n" + "???????????? ????????-???? ???????????? ???? ?????????????????????? ??????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? /ban", null, "https://mc-heads.net/avatar/" + killer.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.ban", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void cavern(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.cavern");
                if (!isget) {
                    int y = event.getPlayer().getLocation().getBlockY();
                    if (y != -60) return;

                    ItemStack icon = new ItemStack(Material.DEEPSLATE);
                    JSONMessage description = new JSONMessage(new TextComponent("???????? ??????????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????? ??????????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????? ??????????????\n" + "???????????????????? ???? ???????????? ??????????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????? ??????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.cavern", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @EventHandler
    public void gribi(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.gribi");
                if (!isget) {
                    Location loc = event.getPlayer().getLocation();
                    String biome = event.getPlayer().getWorld().getBiome(loc).toString();
                    if (!biome.equals("MUSHROOM_FIELDS")) return;

                    ItemStack icon = new ItemStack(Material.RED_MUSHROOM);
                    JSONMessage description = new JSONMessage(new TextComponent("???? ???? ???? ?????????? ????????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???? ???? ???? ?????????? ????????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???? ???? ???? ?????????? ????????????\n" + "???????????????? ?????????????? ????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???? ???? ???? ?????????? ????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.gribi", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @EventHandler
    public void momsanarchy(CreatureSpawnEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (!event.getSpawnReason().toString().equals("BUILD_WITHER")) return;

                double maxDist = 10;// whatever
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(event.getEntity().getLocation()) <= maxDist) {
                        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
                        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                        boolean isget = adv.getBoolean("forscore.momsanarchy");
                        if (!isget) {
                            File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + p.getName() +".yml");
                            FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
                            int count = prg.getInt("wither.spawncount");
                            if (count == 99) {
                                ItemStack icon = new ItemStack(Material.WITHER_SKELETON_SKULL);
                                JSONMessage description = new JSONMessage(new TextComponent("???????? - ??????????????"));
                                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                                ToastNotification notification = new ToastNotification(icon, description, frame);
                                notification.send(p);

                                TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
                                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????? - ??????????????]");
                                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????? - ??????????????\n" + "???????????????????? 100 ??????????????").create() ) );
                                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                                mainComponent.addExtra(hoverText);
                                mainComponent.addExtra(subsubcomponent);
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    pl.spigot().sendMessage(mainComponent);
                                }

                                EmbedBuilder emb = new EmbedBuilder();
                                emb.setColor(new Color(0xffd700));
                                emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????? - ??????????????", null, "https://mc-heads.net/avatar/" + p.getName());
                                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                                adv.set("forscore.momsanarchy", true);

                                try {
                                    adv.save(adv_file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                prg.set("wither.spawncount", count + 1);
                                try {
                                    prg.save(prg_file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void fathercommunism(EntityCombustEvent event) {
        if (event.getEntity() instanceof Item) {
            ItemStack item = ((Item) event.getEntity()).getItemStack();
            if (item.getAmount() != 64) return;
            switch (item.getType()) {
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    break;
                default:
                    return;
            }

            double maxDist = 10;// whatever
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getLocation().distance(event.getEntity().getLocation()) <= maxDist) {
                    File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
                    FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                    boolean isget = adv.getBoolean("forscore.fathercommunism");
                    if (!isget) {
                        ItemStack icon = new ItemStack(Material.IRON_PICKAXE);
                        ItemMeta meta = icon.getItemMeta();
                        meta.setDisplayName("communism");
                        icon.setItemMeta(meta);
                        JSONMessage description = new JSONMessage(new TextComponent("???????? - ????????????????????"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(p);

                        TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
                        TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????? - ????????????????????]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????? - ????????????????????\n" + "?????????????? 64 ??????").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            pl.spigot().sendMessage(mainComponent);
                        }
                        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????? - ????????????????????", null, "https://mc-heads.net/avatar/" + p.getName());
                        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                        adv.set("forscore.fathercommunism", true);

                        try {
                            adv.save(adv_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void enviroment(CreatureSpawnEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (!event.getSpawnReason().toString().equals("BUILD_WITHER")) return;
                if (!event.getEntity().getWorld().getName().equals("world_nether")) return;

                double maxDist = 10;// whatever
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(event.getEntity().getLocation()) <= maxDist) {
                        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
                        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                        boolean isget = adv.getBoolean("forscore.enviroment");
                        if (!isget) {
                            ItemStack icon = new ItemStack(Material.NETHERRACK);
                            JSONMessage description = new JSONMessage(new TextComponent("???????????????????????? ?????????? ????????????????"));
                            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                            ToastNotification notification = new ToastNotification(icon, description, frame);
                            notification.send(p);

                            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
                            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????????????????? ?????????? ????????????????]");
                            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????????????????? ?????????? ????????????????\n" + "?????????????????? ???????????? ?? ??????").create() ) );
                            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                            mainComponent.addExtra(hoverText);
                            mainComponent.addExtra(subsubcomponent);
                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                pl.spigot().sendMessage(mainComponent);
                            }

                            EmbedBuilder emb = new EmbedBuilder();
                            emb.setColor(new Color(0xffd700));
                            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????????????????? ?????????? ????????????????", null, "https://mc-heads.net/avatar/" + p.getName());
                            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                            adv.set("forscore.enviroment", true);

                            try {
                                adv.save(adv_file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void socialexperement(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.socialexperement");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.IRON_SWORD);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("socialexperement");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ?????????????????????? ????????????????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ?????????????????????? ????????????????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ?????????????????????? ????????????????????????\n" + "???????????? ???? ????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ???????????? ?????????????????????? ????????????????????????", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.socialexperement", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void nightinvise(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.nightinvise");
        if (!isget) {
            File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + killer.getName() +".yml");
            FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
            List<String> count = prg.getStringList("player.killed");
            if (count.size() == 9) {
                ItemStack icon = new ItemStack(Material.IRON_SWORD);
                ItemMeta meta = icon.getItemMeta();
                meta.setDisplayName("bloodsword");
                icon.setItemMeta(meta);
                JSONMessage description = new JSONMessage(new TextComponent("?????? ?????? ???? ???????? ????????????????????"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????? ?????? ???? ???????? ????????????????????]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????? ?????? ???? ???????? ????????????????????\n" + "???????????? 10 ??????????????").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ?????? ?????? ???? ???????? ????????????????????", null, "https://mc-heads.net/avatar/" + killer.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.nightinvise", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (count.contains(event.getEntity().getName())) return;
                count.add(event.getEntity().getName());
                prg.set("player.killed", count);
                try {
                    prg.save(prg_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void forsazh(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.forsazh");
                if (!isget) {
                    if (!event.getPlayer().isInsideVehicle()) return;
                    if (event.getPlayer().getVehicle() instanceof Boat) {
                        Location loc = event.getPlayer().getLocation();
                        if (event.getPlayer().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType() != Material.PACKED_ICE) return;
                        ItemStack icon = new ItemStack(Material.OAK_BOAT);
                        JSONMessage description = new JSONMessage(new TextComponent("?????????????? ?????? ??????????"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????? ?????? ??????????]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????? ?????? ??????????\n" + "?????????????????????? ???? ?????????? ???? ???????????????? ????????").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????????????? ?????? ??????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                        adv.set("forscore.forsazh", true);

                        try {
                            adv.save(adv_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void pechenkaone(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.pechenkaone");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() != Material.TOTEM_OF_UNDYING) return;
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("Pechenka")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.TOTEM_OF_UNDYING);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("Pechenka");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ???????? ?? ??????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????? ???????? ?? ??????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????? ???????? ?? ??????????????\n" + "???????????????????????? ?????????? ?? \"Pechenka\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????????? ???????? ?? ??????????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.pechenkaone", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onek(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.onek");
                if (!isget) {
                    Location loc = event.getPlayer().getLocation();
                    if (loc.getX() > 1000 || loc.getX() < -1000 || loc.getZ() > 1000 || loc.getZ() < -1000) {
                        ItemStack icon = new ItemStack(Material.LEATHER_BOOTS);
                        JSONMessage description = new JSONMessage(new TextComponent("???????????? ????????"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ????????]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ????????\n" + "???????????????? ???????????? 1?? ???? ???????????? ???? ?????????? ???? ???????????? ??????????").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????? ????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                        adv.set("forscore.onek", true);

                        try {
                            adv.save(adv_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void diedwither(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.diedwither");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " withered away")) return;
            ItemStack icon = new ItemStack(Material.WITHER_ROSE);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("witherheart");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("?? ???????????? ????????????????????..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?? ???????????? ????????????????????...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?? ???????????? ????????????????????...\n" + "???????????? ???? ??????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ?? ???????????? ????????????????????...", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.diedwither", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void punchfsg(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player maks = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + damager.getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.punchfsg");
                if (!isget) {
                    if (!maks.getName().equals("MaksMaruS_")) return;
                    ItemStack icon = new ItemStack(Material.CARVED_PUMPKIN);
                    ItemMeta meta = icon.getItemMeta();
                    meta.setDisplayName("?????????? F.S.G");
                    icon.setItemMeta(meta);
                    JSONMessage description = new JSONMessage(new TextComponent("???????? ???? ?????????????? ?????????? ??????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(damager);

                    TextComponent mainComponent = new TextComponent(damager.getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????? ???? ?????????????? ?????????? ??????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????? ???? ?????????????? ?????????? ??????????\n" + "?????????????? ?????????? F.S.G").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(damager.getName() + " ?????????????? ???????????????????? ???????? ???? ?????????????? ?????????? ??????????", null, "https://mc-heads.net/avatar/" + damager.getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.punchfsg", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @EventHandler
    public void peper(ChattyMessageEvent event) {
        if (!event.getChat().getName().equals("global")) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.peper");
        if (!isget) {
            if (!event.getMessage().equals("?????????? ??????????????!")) return;
            ItemStack icon = new ItemStack(Material.TOTEM_OF_UNDYING);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("PepperNoSalt");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???? ??????????????!"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???? ??????????????!]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???? ??????????????!\n" + "???????????????? ?? ?????? \"?????????? ??????????????!\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???? ??????????????!", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.peper", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void plushimperator(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.plushimperator");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() != Material.CARVED_PUMPKIN) return;
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("???????????????? ????????")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.CARVED_PUMPKIN);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("???????????????? ????????");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ????????????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ????????????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ????????????????????\n" + "???????????????????????? ???????????????????? ?????????? ?? \"???????????????? ????????\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????? ????????????????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.plushimperator", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void kingnether(EntityDeathEvent event) {
        if (event.getEntity() instanceof Strider) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) return;

            File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
            FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
            boolean isget = adv.getBoolean("forscore.kingnether");
            if (!isget) {
                ItemStack icon = new ItemStack(Material.STRING);
                JSONMessage description = new JSONMessage(new TextComponent("?????????????????? ???????????? ??????"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
                TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[?????????????????? ???????????? ??????]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "?????????????????? ???????????? ??????\n" + "???????????? ?????????????????? ??????").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }
                killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ?????????????????? ???????????? ??????", null, "https://mc-heads.net/avatar/" + killer.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.kingnether", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void doomguy(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.doomguy");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() != Material.NETHERITE_SWORD) return;
            Map<Enchantment, Integer> b = event.getCurrentItem().getEnchantments();
            if (b.get(Enchantment.SWEEPING_EDGE) == null || b.get(Enchantment.DAMAGE_ALL) == null) return;
            if (b.get(Enchantment.SWEEPING_EDGE) < 1) return;
            if (b.get(Enchantment.DAMAGE_ALL) < 1) return;
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("??????-????????")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.NETHERITE_HELMET);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("??????????-????????");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("DoomGuy"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[DoomGuy]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "DoomGuy\n" + "???????????????????????? ?????????????????????? ?????? ?? ????????????:\n?????????????? ????????????, ?????????????? ?? \"??????-????????\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? DoomGuy", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.doomguy", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void eatrevolution(EntityDeathEvent event) {
        if (event.getEntity() instanceof Pig) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) return;

            File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
            FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
            boolean isget = adv.getBoolean("forscore.eatrevolution");
            if (!isget) {
                ItemStack icon = new ItemStack(Material.PORKCHOP);
                JSONMessage description = new JSONMessage(new TextComponent("?????????????? ??????????????????"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????? ??????????????????]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????? ??????????????????\n" + "???????????? ????????????").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ?????????????? ??????????????????", null, "https://mc-heads.net/avatar/" + killer.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.eatrevolution", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void totem(PlayerPickupItemEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.totem");
        if (!isget) {
            Material item = event.getItem().getItemStack().getType();
            if (item != Material.TOTEM_OF_UNDYING) return;

            ItemStack icon = new ItemStack(Material.TOTEM_OF_UNDYING);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("JustEgor");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ???????? ?????? ????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????????? ???????? ?????? ????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????????? ???????? ?????? ????????????\n" + "???????????????? ?????????? ????????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????????? ???????? ?????? ????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.totem", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void reznya(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (event.getPlayer().getLevel() > 99) {
                    File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                    FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                    boolean isget = adv.getBoolean("forscore.reznya");
                    if (!isget) {
                        ItemStack icon = new ItemStack(Material.BONE);
                        JSONMessage description = new JSONMessage(new TextComponent("???? - ?????? ??????????????"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???? - ?????? ??????????????]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???? - ?????? ??????????????\n" + "???????????????? 100 ?????????????? ??????????").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???? - ?????? ??????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                        adv.set("forscore.reznya", true);

                        try {
                            adv.save(adv_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void atom(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.atom");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " blew up")) return;
            ItemStack icon = new ItemStack(Material.TNT);
            JSONMessage description = new JSONMessage(new TextComponent("?????????????????????? ???? ??????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????????????? ???? ??????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????????????? ???? ??????????\n" + "???????????? ???? ???????????? ??????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ?????????????????????? ???? ??????????", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.atom", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void magnit(PlayerPickupItemEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.magnit");
        if (!isget) {
            Material item = event.getItem().getItemStack().getType();
            if (item != Material.LODESTONE) return;
            if (event.getItem().getItemStack().getAmount() != 64) return;

            ItemStack icon = new ItemStack(Material.LODESTONE);
            JSONMessage description = new JSONMessage(new TextComponent("?????????????????? ????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????????? ????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????????? ????????\n" + "???????????????? ???????? ??????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????????????????? ????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.magnit", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void diedvoid(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.diedvoid");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " fell out of the world")) return;
            ItemStack icon = new ItemStack(Material.BLACK_CONCRETE);
            JSONMessage description = new JSONMessage(new TextComponent("?????? ?????????????? ?? ???????? ????????????..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????? ?????????????? ?? ???????? ????????????...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????? ?????????????? ?? ???????? ????????????...\n" + "?????????????? ?? ??????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ?????? ?????????????? ?? ???????? ????????????...", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.diedvoid", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void diedzombie(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.diedzombie");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " was slain by Zombie")) return;
            if (!event.getEntity().getWorld().equals(Bukkit.getWorld("world_nether"))) return;
            ItemStack icon = new ItemStack(Material.ZOMBIE_HEAD);
            JSONMessage description = new JSONMessage(new TextComponent("?????????? ?????????? Z"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????? ?????????? Z]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????? ?????????? Z\n" + "?????????????? ???? ?????????? ?? ??????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ?????????? ?????????? Z", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.diedzombie", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler()
    public void socialcredit(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.HONEY_BOTTLE) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.socialcredit");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.HONEY_BOTTLE);
            JSONMessage description = new JSONMessage(new TextComponent("-100 Social Credit"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[-100 Social Credit]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "-100 Social Credit\n" + "?????????????? ?????????????? ????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? -100 Social Credit", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.socialcredit", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void zakviel(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.zakviel");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " fell from a high place")) return;
            if (event.getEntity().getItemInHand().getType() != Material.WATER_BUCKET) return;
            ItemStack icon = new ItemStack(Material.CRACKED_STONE_BRICKS);
            JSONMessage description = new JSONMessage(new TextComponent("??????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[??????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "??????????\n" + "?????????????? ???? ?????????????? ?? ???????????? ???????? ?? ????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? ??????????", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.zakviel", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void power(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.power");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.POTION) return;
            if (event.getInventory().getType() != InventoryType.BREWING) return;
            if(event.getSlotType() != InventoryType.SlotType.CRAFTING) return;
            PotionMeta meta = (PotionMeta) event.getCurrentItem().getItemMeta();
            if (meta == null) return;
            PotionData data = meta.getBasePotionData();
            if (data.getType() != PotionType.STRENGTH) return;

            ItemStack icon = new ItemStack(Material.POTION);
            JSONMessage description = new JSONMessage(new TextComponent("?????????????????????? ????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????????????? ????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????????????? ????????\n" + "?????????????? ?????????? ????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ?????????????????????? ????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.power", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void mogilshik(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Skeleton) {
            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + damager.getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.mogilshik");
                if (!isget) {
                    if (damager.getItemInHand().getType() != Material.IRON_SHOVEL) return;
                    ItemStack icon = new ItemStack(Material.PODZOL);
                    JSONMessage description = new JSONMessage(new TextComponent("??????????????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(damager);

                    TextComponent mainComponent = new TextComponent(damager.getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[??????????????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "??????????????????\n" + "?????????????? ?????????????? ???????????????? ??????????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(damager.getName() + " ?????????????? ???????????????????? ??????????????????", null, "https://mc-heads.net/avatar/" + damager.getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.mogilshik", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @EventHandler
    public void aquaman(PlayerDeathEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getEntity().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.aquaman");
        if (!isget) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " drowned")) return;
            ItemStack icon = new ItemStack(Material.TRIDENT);
            JSONMessage description = new JSONMessage(new TextComponent("Aquaman"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Aquaman]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Aquaman\n" + "???????????????????? ?????? ??????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " ?????????????? ???????????????????? Aquaman", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.aquaman", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void sky(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.sky");
                if (!isget) {
                    int y = event.getPlayer().getLocation().getBlockY();
                    if (y < 1000) return;

                    ItemStack icon = new ItemStack(Material.FEATHER);
                    JSONMessage description = new JSONMessage(new TextComponent("?? ??????????????????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?? ??????????????????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?? ??????????????????????\n" + "?????????????????????? ???????? 1000 ????????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?? ??????????????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.sky", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @EventHandler()
    public void sonic(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION) return;
        PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
        if (meta == null) return;
        PotionData data = meta.getBasePotionData();
        if (data.getType() != PotionType.SPEED) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals("??????????????????")) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.sonic");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.LIGHT_BLUE_WOOL);
            ItemMeta meta1 = icon.getItemMeta();
            meta1.setDisplayName("sonic");
            icon.setItemMeta(meta1);
            JSONMessage description = new JSONMessage(new TextComponent("?????????????? ?????? ????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????????? ?????? ????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????????? ?????? ????\n" + "?????????????? ?????????? ???????????????? ?????????????????????????????? ?? \"??????????????????\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????????????? ?????? ????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.sonic", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void rat(EntityDeathEvent event) {
        if (event.getEntity() instanceof Silverfish) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) return;

            File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
            FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
            boolean isget = adv.getBoolean("forscore.rat");
            if (!isget) {
                ItemStack icon = new ItemStack(Material.ROTTEN_FLESH);
                JSONMessage description = new JSONMessage(new TextComponent("??????????! ???????"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[??????????! ???????]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "??????????! ???????\n" + "???????????? ??????????????????").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ??????????! ???????", null, "https://mc-heads.net/avatar/" + killer.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.rat", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void turtle(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.turtle");
                if (!isget) {
                    if (!event.getPlayer().isSneaking()) return;
                    int sneaking = event.getPlayer().getStatistic(Statistic.CROUCH_ONE_CM);
                    if (sneaking < 100000) return;

                    ItemStack icon = new ItemStack(Material.TURTLE_EGG);
                    JSONMessage description = new JSONMessage(new TextComponent("?????????? ?????? ????????????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????????? ?????? ????????????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????????? ?????? ????????????????\n" + "???????????? 1000 ???????????? ??????????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????????? ?????? ????????????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.turtle", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @EventHandler
    public void selskiiad(PlayerItemBreakEvent event) {
        if (event.getBrokenItem().getType() != Material.NETHERITE_HOE) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.selskiiad");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.WHEAT_SEEDS);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????????? ????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????????? ????\n" + "?????????????? ?????????????????????? ????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????????? ????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.selskiiad", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void villain(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) return;

            File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
            FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
            boolean isget = adv.getBoolean("forscore.villain");
            if (!isget) {
                ItemStack icon = new ItemStack(Material.GRAY_CONCRETE);
                ItemMeta meta = icon.getItemMeta();
                meta.setDisplayName("villain");
                icon.setItemMeta(meta);
                JSONMessage description = new JSONMessage(new TextComponent("???????????? ??????????????"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ??????????????]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ??????????????\n" + "???????????? ??????????????????????").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ???????????? ??????????????", null, "https://mc-heads.net/avatar/" + killer.getName());
                DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                adv.set("forscore.villain", true);

                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void areyouwinningson(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.areyouwinningson");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.GOLDEN_HOE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.DARK_OAK_DOOR);
            JSONMessage description = new JSONMessage(new TextComponent("Are you Winning Son?"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Are you Winning Son?]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Are you Winning Son?\n" + "?????????????????? ?????????????? ????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? Are you Winning Son?", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.areyouwinningson", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void pechenkatwo(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.pechenkatwo");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() != Material.CARVED_PUMPKIN) return;
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("???????? ?????????? ????????????????")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.CARVED_PUMPKIN);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("???????? ?????????? ????????????????");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ?? ????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????? ?? ????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????? ?? ????????????\n" + "?????????????? ????????????...").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????????? ?? ????????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.pechenkatwo", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void newyear(Player p) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.newyear");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.SNOWBALL);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("sneginka");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????????? ????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????????? ????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????????? ????????\n" + "???????????????? ???? ?????????? ??????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????????????? ????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.newyear", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void halloween(Player p) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.halloween");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.PUMPKIN);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("halloween_pumpkin");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????? ?? ???? ????????????????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????? ?? ???? ????????????????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????? ?? ???? ????????????????????\n" + "???????????????? ???? ????????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? ???????????????? ?? ???? ????????????????????", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.halloween", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void mylittlebag(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.mylittlebag");
        if (!isget) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.BUNDLE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.BUNDLE);
            JSONMessage description = new JSONMessage(new TextComponent("My little-bag"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[My little-bag]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "My little-bag\n" + "?????????????????? ??????????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " ?????????????? ???????????????????? My little-bag", null, "https://mc-heads.net/avatar/" + p.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.mylittlebag", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void flower(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                boolean isget = adv.getBoolean("forscore.flower");
                if (!isget) {
                    int x = event.getPlayer().getLocation().getBlockX();
                    int y = event.getPlayer().getLocation().getBlockY();
                    int z = event.getPlayer().getLocation().getBlockZ();
                    if (event.getPlayer().getWorld().getBlockAt(x, y, z).getType() != Material.WITHER_ROSE) return;

                    ItemStack icon = new ItemStack(Material.WITHER_ROSE);
                    JSONMessage description = new JSONMessage(new TextComponent("?????? ?????????????? ???????????? ??????????"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[?????? ?????????????? ???????????? ??????????]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "?????? ?????????????? ???????????? ??????????\n" + "???????????? ???? ?????????? ????????").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ?????? ?????????????? ???????????? ??????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
                    DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

                    adv.set("forscore.flower", true);

                    try {
                        adv.save(adv_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @EventHandler
    public void moneyworld(PlayerPickupItemEvent event) {
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.moneyworld");
        if (!isget) {
            Material item = event.getItem().getItemStack().getType();
            switch (item) {
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    break;
                default:
                    return;
            }
            if (event.getItem().getItemStack().getAmount() != 64) return;

            ItemStack icon = new ItemStack(Material.DIAMOND_ORE);
            JSONMessage description = new JSONMessage(new TextComponent("???????????? ???????????? ??????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[???????????? ???????????? ??????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "???????????? ???????????? ??????????\n" + "?????????????????? 64 ????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " ?????????????? ???????????????????? ???????????? ???????????? ??????????", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.moneyworld", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void colosstitan(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        int one = event.getEntity().getLocation().getBlockY();
        int two = killer.getLocation().getBlockY();
        int three = 0;
        if (one > two) three = one - two;
        else if (two > one) three = two - one;
        if (59 > three) return;

        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + killer.getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.colosstitan");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.RED_CONCRETE);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("coloss_titan");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("???????????????????????? ??????????"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(killer);

            TextComponent mainComponent = new TextComponent(killer.getName() + " ?????????????? ???????????????????? ??");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[???????????????????????? ??????????]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "???????????????????????? ??????????\n" + "???????????? ???????????? ?? ???????????????????? 60 ???????????? ?? ???????????? ?? ????????").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "??");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " ?????????????? ???????????????????? ???????????????????????? ??????????", null, "https://mc-heads.net/avatar/" + killer.getName());
            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(emb.build()).queue();

            adv.set("forscore.colosstitan", true);

            try {
                adv.save(adv_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
