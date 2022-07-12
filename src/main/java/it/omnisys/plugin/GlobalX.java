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
        
        getProxy().getLogger().info("   §b________      __          __   _  __");
        getProxy().getLogger().info("  §b/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §b/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §7Running on Version §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§b/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §7Plugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§b\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalCMD());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalXCMD());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getProxy().getLogger().info("   §b________      __          __   _  __");
        getProxy().getLogger().info("  §b/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §b/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §cDisabling §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§b/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §7Plugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§b\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");
    }
}
