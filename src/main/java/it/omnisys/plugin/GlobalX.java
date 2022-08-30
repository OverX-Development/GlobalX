package it.omnisys.plugin;

import it.omnisys.plugin.Commands.GlobalCMD;
import it.omnisys.plugin.Commands.GlobalToggleCMD;
import it.omnisys.plugin.Commands.GlobalXCMD;
import it.omnisys.plugin.Listeners.ChatListener;
import it.omnisys.plugin.Listeners.JoinListener;
import it.omnisys.plugin.Managers.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import static it.omnisys.plugin.Managers.ConfigManager.*;

public final class GlobalX extends Plugin {
    public static GlobalX INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        createMainConfig();
        registerMainConfig();

        createMessageConfig();
        registerMessageConfig();

        new UpdateChecker(this, 102941).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("§aWe're up to date!");
            } else {
                getLogger().info("§aThere's a new Update available (" + version + ")");
            }
        });
        
        getProxy().getLogger().info("   §b________      __          __   _  __");
        getProxy().getLogger().info("  §b/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §b/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §7Running on Version §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§b/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §7Plugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§b\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalCMD());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalXCMD());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalToggleCMD());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinListener());
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

    public static void reload() { INSTANCE.onDisable(); INSTANCE.onEnable(); }
}
