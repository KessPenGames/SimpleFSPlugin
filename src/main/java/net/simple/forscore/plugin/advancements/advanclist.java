package net.simple.forscore.plugin.advancements;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class advanclist {
    public static FileConfiguration advlist(FileConfiguration f) {
        f.set("forscore.start", false);
        f.set("forscore.ezra8", false);
        f.set("forscore.arstart", false);
        f.set("forscore.darvin", false);
        f.set("forscore.gordon", false);
        f.set("forscore.leonid", false);
        f.set("forscore.ban", false);
        f.set("forscore.cavern", false);
        f.set("forscore.gribi", false);
        f.set("forscore.momsanarchy", false);
        f.set("forscore.fathercommunism", false);
        f.set("forscore.enviroment", false);
        f.set("forscore.socialexperement", false);
        f.set("forscore.nightinvise", false);
        f.set("forscore.forsazh", false);
        f.set("forscore.pechenkaone", false);
        f.set("forscore.onek", false);
        f.set("forscore.diedwither", false);
        f.set("forscore.punchfsg", false);
        f.set("forscore.peper", false);
        f.set("forscore.plushimperator", false);
        f.set("forscore.afk", false);
        f.set("forscore.kingnether", false);
        f.set("forscore.doomguy", false);
        f.set("forscore.eatrevolution", false);
        f.set("forscore.totem", false);
        f.set("forscore.reznya", false);
        f.set("forscore.atom", false);
        f.set("forscore.magnit", false);
        f.set("forscore.diedvoid", false);
        f.set("forscore.diedzombie", false);
        f.set("forscore.socialcredit", false);
        f.set("forscore.zakviel", false);
        f.set("forscore.power", false);
        f.set("forscore.mogilshik", false);
        f.set("forscore.aquaman", false);
        f.set("forscore.sky", false);
        f.set("forscore.sonic", false);
        f.set("forscore.rat", false);
        f.set("forscore.turtle", false);
        f.set("forscore.selskiiad", false);
        f.set("forscore.villain", false);
        f.set("forscore.areyouwinningson", false);
        f.set("forscore.pechenkatwo", false);
        f.set("forscore.newyear", false);
        f.set("forscore.halloween", false);
        f.set("forscore.mylittlebag", false);
        f.set("forscore.flower", false);
        f.set("forscore.moneyworld", false);
        f.set("forscore.colosstitan", false);

        return f;
    }

    public static FileConfiguration prglist(FileConfiguration f) {
        f.set("wither.spawncount", 0);
        List<String> ab = new ArrayList<String>();
        f.set("player.killed", ab);

        return f;
    }
}
