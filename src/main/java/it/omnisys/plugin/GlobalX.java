package it.omnisys.plugin;

import net.md_5.bungee.api.plugin.Plugin;

public final class GlobalX extends Plugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getProxy().getLogger().info("\n" +
                "   §c________      __          __   _  __      \n" +
                "  §c/ ____/ /___  / /_  ____ _/ /  | |/ /      \n" +
                " §c/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   Running on Version §8v" + getDescription().getVersion() + "\n" +
                "§c/ /_/ / / /_/ / /_/ / /_/ / /   /   |        Pluign by " + getDescription().getAuthor().toString()  + "\n" +
                "§c\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
