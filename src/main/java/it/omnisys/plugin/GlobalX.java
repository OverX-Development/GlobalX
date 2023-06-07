package it.omnisys.plugin;

import it.omnisys.plugin.commands.GlobalCMD;
import it.omnisys.plugin.commands.GlobalToggleCMD;
import it.omnisys.plugin.commands.GlobalXCMD;
import it.omnisys.plugin.listeners.ChatListener;
import it.omnisys.plugin.listeners.JoinListener;
import it.omnisys.plugin.managers.ConfigManager;
import it.omnisys.plugin.utils.ReflectionUtils;
import it.omnisys.plugin.utils.UpdateChecker;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public final class GlobalX extends Plugin {
    @Getter
    static GlobalX plugin;
    @Getter
    static Configuration mainConfig;
    @Getter
    static Configuration messagesConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        mainConfig = new ConfigManager("config.yml").create();
        messagesConfig = new ConfigManager("messages.yml").create();

        new UpdateChecker(this, 102941).checkVersion();
        
        getProxy().getLogger().info("   §b________      __          __   _  __");
        getProxy().getLogger().info("  §b/ ____/ /___  / /_  ____ _/ /  | |/ /");
        getProxy().getLogger().info(" §b/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §7Running on Version §8v" + getDescription().getVersion());
        getProxy().getLogger().info("§b/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §7Plugin by §8" + getDescription().getAuthor());
        getProxy().getLogger().info("§b\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");

        ReflectionUtils.registerCommands(this);
        ReflectionUtils.registerListeners(this);
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

    public static void reload() { plugin.onDisable(); plugin.onEnable(); }
}
