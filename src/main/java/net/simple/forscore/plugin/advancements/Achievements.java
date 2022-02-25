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
            JSONMessage description = new JSONMessage(new TextComponent("Первый шаг в огромном мире..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Первый шаг в огромном мире...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Первый шаг в огромном мире...\n" + "Впервые зайти на сервер").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Первый шаг в огромном мире...", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Свинская анархичная революция"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(killer);

            TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Свинская анархичная революция]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Свинская анархичная революция\n" + "Убейте игрока EZRA_8").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " получил достижение Свинская анархичная революция", null, "https://mc-heads.net/avatar/" + killer.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Первое вложение"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Первое вложение]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Первое вложение\n" + "Получите свой первый АР").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Первое вложение", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Премия дарвина"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Премия дарвина]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Премия дарвина\n" + "Попасть в БАН самым тупым способом").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Премия дарвина", null, "https://mc-heads.net/avatar/" + p.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Готовил Гордон Рамзи"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Готовил Гордон Рамзи]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Готовил Гордон Рамзи\n" + "Съешьте ядовитый картофель").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Готовил Гордон Рамзи", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                        if (!event.getRightClicked().getName().equals("Леонид")) return;

                        ItemStack icon = new ItemStack(Material.CHICKEN);
                        JSONMessage description = new JSONMessage(new TextComponent("Леонид"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Леонид]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Леонид\n" + "Переименуйте курицу в \"Леонид\"").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " получил достижение Леонид", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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

            TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[/ban]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "/ban\n" + "Убейте кого-то одного из действующих админов").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " получил достижение /ban", null, "https://mc-heads.net/avatar/" + killer.getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("Ниже Каверна"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Ниже Каверна]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Ниже Каверна\n" + "Спуститесь на высоту бедрока").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " получил достижение Ниже Каверна", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("Не на те грибы подсел"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Не на те грибы подсел]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Не на те грибы подсел\n" + "Посетите грибной биом").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " получил достижение Не на те грибы подсел", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                                JSONMessage description = new JSONMessage(new TextComponent("Мать - анархии"));
                                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                                ToastNotification notification = new ToastNotification(icon, description, frame);
                                notification.send(p);

                                TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
                                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Мать - анархии]");
                                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Мать - анархии\n" + "Заспавните 100 визеров").create() ) );
                                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                                mainComponent.addExtra(hoverText);
                                mainComponent.addExtra(subsubcomponent);
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    pl.spigot().sendMessage(mainComponent);
                                }

                                EmbedBuilder emb = new EmbedBuilder();
                                emb.setColor(new Color(0xffd700));
                                emb.setAuthor(p.getName() + " получил достижение Мать - анархии", null, "https://mc-heads.net/avatar/" + p.getName());
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
                        JSONMessage description = new JSONMessage(new TextComponent("Отец - коммунизма"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(p);

                        TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
                        TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Отец - коммунизма]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Отец - коммунизма\n" + "Сожгите 64 АРа").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            pl.spigot().sendMessage(mainComponent);
                        }
                        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(p.getName() + " получил достижение Отец - коммунизма", null, "https://mc-heads.net/avatar/" + p.getName());
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
                            JSONMessage description = new JSONMessage(new TextComponent("Естественная среда обитания"));
                            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                            ToastNotification notification = new ToastNotification(icon, description, frame);
                            notification.send(p);

                            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
                            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Естественная среда обитания]");
                            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Естественная среда обитания\n" + "Призовите визера в аду").create() ) );
                            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                            mainComponent.addExtra(hoverText);
                            mainComponent.addExtra(subsubcomponent);
                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                pl.spigot().sendMessage(mainComponent);
                            }

                            EmbedBuilder emb = new EmbedBuilder();
                            emb.setColor(new Color(0xffd700));
                            emb.setAuthor(p.getName() + " получил достижение Естественная среда обитания", null, "https://mc-heads.net/avatar/" + p.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Жертва социального эксперимента"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Жертва социального эксперимента]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Жертва социального эксперимента\n" + "Умрите от игрока").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Жертва социального эксперимента", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
                JSONMessage description = new JSONMessage(new TextComponent("Тот кто во тьме скрывается"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Тот кто во тьме скрывается]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Тот кто во тьме скрывается\n" + "Убейте 10 игроков").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " получил достижение Тот кто во тьме скрывается", null, "https://mc-heads.net/avatar/" + killer.getName());
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
                        JSONMessage description = new JSONMessage(new TextComponent("Быстрее чем Скала"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Быстрее чем Скала]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Быстрее чем Скала\n" + "Прокатитесь на лодке по плотному льду").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " получил достижение Быстрее чем Скала", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Печеньки едят с молоком"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Печеньки едят с молоком]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Печеньки едят с молоком\n" + "Переименуйте тотем в \"Pechenka\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Печеньки едят с молоком", null, "https://mc-heads.net/avatar/" + p.getName());
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
                        JSONMessage description = new JSONMessage(new TextComponent("Начало пути"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Начало пути]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Начало пути\n" + "Отойдите дальше 1к от спавна по одной из сторон света").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " получил достижение Начало пути", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("В другой реальности..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[В другой реальности...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "В другой реальности...\n" + "Умрите от иссушения").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение В другой реальности...", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
                    meta.setDisplayName("кепка F.S.G");
                    icon.setItemMeta(meta);
                    JSONMessage description = new JSONMessage(new TextComponent("Риск на который стоит пойти"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(damager);

                    TextComponent mainComponent = new TextComponent(damager.getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Риск на который стоит пойти]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Риск на который стоит пойти\n" + "Ударьте главу F.S.G").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(damager.getName() + " получил достижение Риск на который стоит пойти", null, "https://mc-heads.net/avatar/" + damager.getName());
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
            if (!event.getMessage().equals("Пепер легенда!")) return;
            ItemStack icon = new ItemStack(Material.TOTEM_OF_UNDYING);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("PepperNoSalt");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("Он легенда!"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Он легенда!]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Он легенда!\n" + "Напишите в чат \"Пепер легенда!\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Он легенда!", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("плюшевый грид")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.CARVED_PUMPKIN);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("плюшевый грид");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("Замена Императору"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Замена Императору]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Замена Императору\n" + "Переименуйте вырезанную тыкву в \"плюшевый грид\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Замена Императору", null, "https://mc-heads.net/avatar/" + p.getName());
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
                JSONMessage description = new JSONMessage(new TextComponent("Свергнули короля ада"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Свергнули короля ада]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Свергнули короля ада\n" + "Убейте диктатора ада").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }
                killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " получил достижение Свергнули короля ада", null, "https://mc-heads.net/avatar/" + killer.getName());
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
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("меч-пила")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.NETHERITE_HELMET);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("Прото-шлем");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("DoomGuy"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[DoomGuy]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "DoomGuy\n" + "Переименуйте незеритовый меч с чарами:\nразящий клинок, острота в \"меч-пила\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение DoomGuy", null, "https://mc-heads.net/avatar/" + p.getName());
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
                JSONMessage description = new JSONMessage(new TextComponent("Пищевая революция"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Пищевая революция]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Пищевая революция\n" + "Убейти свинью").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " получил достижение Пищевая революция", null, "https://mc-heads.net/avatar/" + killer.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Отличный день для смерти"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Отличный день для смерти]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Отличный день для смерти\n" + "Добудьте тотем бессмертия").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Отличный день для смерти", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                        JSONMessage description = new JSONMessage(new TextComponent("ОП - Это акроним"));
                        AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                        ToastNotification notification = new ToastNotification(icon, description, frame);
                        notification.send(event.getPlayer());

                        TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                        TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[ОП - Это акроним]");
                        hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "ОП - Это акроним\n" + "Добудьте 100 уровень опыта").create() ) );
                        TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                        mainComponent.addExtra(hoverText);
                        mainComponent.addExtra(subsubcomponent);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(mainComponent);
                        }

                        EmbedBuilder emb = new EmbedBuilder();
                        emb.setColor(new Color(0xffd700));
                        emb.setAuthor(event.getPlayer().getName() + " получил достижение ОП - Это акроним", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Расщепление на атомы"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Расщепление на атомы]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Расщепление на атомы\n" + "Умрите от взрыва тнт").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Расщепление на атомы", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Магнитная буря"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Магнитная буря]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Магнитная буря\n" + "Добудьте стак магнетита").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Магнитная буря", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Эта темнота к себе манила..."));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Эта темнота к себе манила...]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Эта темнота к себе манила...\n" + "Умереть в пустоте").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Эта темнота к себе манила...", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Война миров Z"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Война миров Z]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Война миров Z\n" + "Умереть от зомби в аду").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Война миров Z", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[-100 Social Credit]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "-100 Social Credit\n" + "Выпейте бутылку мёда").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение -100 Social Credit", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Руина"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getEntity());

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Руина]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Руина\n" + "Умереть от падения с ведром воды в руке").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Руина", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Невероятная мощь"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Невероятная мощь]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Невероятная мощь\n" + "Сварите зелье силы").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Невероятная мощь", null, "https://mc-heads.net/avatar/" + p.getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("Могильщик"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(damager);

                    TextComponent mainComponent = new TextComponent(damager.getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Могильщик]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Могильщик\n" + "Ударьте скелета железной лопатой").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(damager.getName() + " получил достижение Могильщик", null, "https://mc-heads.net/avatar/" + damager.getName());
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

            TextComponent mainComponent = new TextComponent(event.getEntity().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Aquaman]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Aquaman\n" + "Задохнутся под водой").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getEntity().getName() + " получил достижение Aquaman", null, "https://mc-heads.net/avatar/" + event.getEntity().getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("В стратосферу"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[В стратосферу]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "В стратосферу\n" + "Поднимитесь выше 1000 блоков").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " получил достижение В стратосферу", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
        if (!event.getItem().getItemMeta().getDisplayName().equals("энергетик")) return;
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getName() +".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        boolean isget = adv.getBoolean("forscore.sonic");
        if (!isget) {
            ItemStack icon = new ItemStack(Material.LIGHT_BLUE_WOOL);
            ItemMeta meta1 = icon.getItemMeta();
            meta1.setDisplayName("sonic");
            icon.setItemMeta(meta1);
            JSONMessage description = new JSONMessage(new TextComponent("Быстрый как ёж"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Быстрый как ёж]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Быстрый как ёж\n" + "Выпейте зелье скорости переименованное в \"энергетик\"").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Быстрый как ёж", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                JSONMessage description = new JSONMessage(new TextComponent("Крыса! Где?"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Крыса! Где?]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Крыса! Где?\n" + "Убейте чешуйницу").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " получил достижение Крыса! Где?", null, "https://mc-heads.net/avatar/" + killer.getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("Идёшь как черепаха"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Идёшь как черепаха]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Идёшь как черепаха\n" + "Пройти 1000 блоков крадясь").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " получил достижение Идёшь как черепаха", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Сельский Ад"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Сельский Ад]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Сельский Ад\n" + "Сломать незеритовую мотыгу").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Сельский Ад", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
                JSONMessage description = new JSONMessage(new TextComponent("Злодей деревни"));
                AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                ToastNotification notification = new ToastNotification(icon, description, frame);
                notification.send(killer);

                TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
                TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Злодей деревни]");
                hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Злодей деревни\n" + "Убейте крестьянина").create() ) );
                TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                mainComponent.addExtra(hoverText);
                mainComponent.addExtra(subsubcomponent);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(mainComponent);
                }

                EmbedBuilder emb = new EmbedBuilder();
                emb.setColor(new Color(0xffd700));
                emb.setAuthor(killer.getName() + " получил достижение Злодей деревни", null, "https://mc-heads.net/avatar/" + killer.getName());
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

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Are you Winning Son?]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Are you Winning Son?\n" + "Скрафтите золотую мотыгу").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Are you Winning Son?", null, "https://mc-heads.net/avatar/" + p.getName());
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
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equals("кекс кукла печенька")) return;
            if (event.getInventory().getType() != InventoryType.ANVIL) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;

            ItemStack icon = new ItemStack(Material.CARVED_PUMPKIN);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("кекс кукла печенька");
            icon.setItemMeta(meta);
            JSONMessage description = new JSONMessage(new TextComponent("Печенька в молоке"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Печенька в молоке]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Печенька в молоке\n" + "Упустим детали...").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Печенька в молоке", null, "https://mc-heads.net/avatar/" + p.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Новогоднее чудо"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Новогоднее чудо]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Новогоднее чудо\n" + "Выдаётся на новый год").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Новогоднее чудо", null, "https://mc-heads.net/avatar/" + p.getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Страшнее и не придумаешь"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(p);

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Страшнее и не придумаешь]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Страшнее и не придумаешь\n" + "Выдаётся на хэллоуин").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение Страшнее и не придумаешь", null, "https://mc-heads.net/avatar/" + p.getName());
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

            TextComponent mainComponent = new TextComponent(p.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[My little-bag]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "My little-bag\n" + "Скрафтите мешочек").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(p.getName() + " получил достижение My little-bag", null, "https://mc-heads.net/avatar/" + p.getName());
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
                    JSONMessage description = new JSONMessage(new TextComponent("Как приятно пахнут цветы"));
                    AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
                    ToastNotification notification = new ToastNotification(icon, description, frame);
                    notification.send(event.getPlayer());

                    TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
                    TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Как приятно пахнут цветы]");
                    hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Как приятно пахнут цветы\n" + "Встать на визер розу").create() ) );
                    TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
                    mainComponent.addExtra(hoverText);
                    mainComponent.addExtra(subsubcomponent);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(mainComponent);
                    }

                    EmbedBuilder emb = new EmbedBuilder();
                    emb.setColor(new Color(0xffd700));
                    emb.setAuthor(event.getPlayer().getName() + " получил достижение Как приятно пахнут цветы", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Деньги правят миром"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.TASK;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(event.getPlayer());

            TextComponent mainComponent = new TextComponent(event.getPlayer().getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.GREEN + "[Деньги правят миром]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Деньги правят миром\n" + "Подобрать 64 АР").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(event.getPlayer().getName() + " получил достижение Деньги правят миром", null, "https://mc-heads.net/avatar/" + event.getPlayer().getName());
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
            JSONMessage description = new JSONMessage(new TextComponent("Колоссальный титан"));
            AdvancementDisplay.AdvancementFrame frame = AdvancementDisplay.AdvancementFrame.CHALLENGE;
            ToastNotification notification = new ToastNotification(icon, description, frame);
            notification.send(killer);

            TextComponent mainComponent = new TextComponent(killer.getName() + " получил достижение «");
            TextComponent hoverText = new TextComponent(ChatColor.DARK_PURPLE + "[Колоссальный титан]");
            hoverText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Колоссальный титан\n" + "Убейте игрока с расстояния 60 блоков в высоту и выше").create() ) );
            TextComponent subsubcomponent = new TextComponent(ChatColor.WHITE + "»");
            mainComponent.addExtra(hoverText);
            mainComponent.addExtra(subsubcomponent);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(mainComponent);
            }
            killer.playSound(killer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 0);

            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(new Color(0xffd700));
            emb.setAuthor(killer.getName() + " получил достижение Колоссальный титан", null, "https://mc-heads.net/avatar/" + killer.getName());
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
