package it.omnisys.plugin;

import it.omnisys.plugin.Commands.GlobalCMD;
import it.omnisys.plugin.Commands.GlobalXCMD;
import it.omnisys.plugin.Listeners.ChatListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import static it.omnisys.plugin.Managers.ConfigManager.*;

public final class GlobalX extends Plugin {
    public static GlobalX plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        createMainConfig();
        registerMainConfig();

        createMessageConfig();
        registerMessageConfig();
        
        getProxy().getLogger().info("   §c________      __          __   _  __");
        getProxy().getLogger().info("  §c/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §c/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §bRunning on Version §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§c/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §bPlugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§c\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalCMD());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalXCMD());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getProxy().getLogger().info("   §c________      __          __   _  __");
        getProxy().getLogger().info("  §c/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §c/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §cDisabling §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§c/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §bPlugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§c\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");
    }
}
