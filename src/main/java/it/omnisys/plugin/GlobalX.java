package it.omnisys.plugin;

import it.omnisys.plugin.Commands.GlobalCMD;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import static it.omnisys.plugin.Managers.ConfigManager.*;

public final class GlobalX extends Plugin {
    public static GlobalX plugin;

    public static LuckPerms LPapi = LuckPermsProvider.get();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Configuration messagesConfig = getXConfig("messages");
        Configuration mainConfig = getXConfig("config");

        createConfig("messages");
        createConfig("config");
        saveConfig(messagesConfig, "messages");
        saveConfig(messagesConfig, "config");

        getXConfig("messages").set("Prefix", "&c&lGLOBALX &7»");
        getXConfig("messages").set("ServerNameFormat", "&7&o[%serverName%]");
        getXConfig("messages").set("ConsoleServer", "&8[&cNo Server&8]");
        getXConfig("messages").set("ConsoleNameFormat", "&c&lCONSOLE &f");
        getXConfig("messages").set("InsuffArgs", "&cInsufficient Arguments! Please use:\n&7/global <message>");
        getXConfig("messages").set("GlobalFormat", "%prefix% %serverNameFormat% - %luckperms_prefix% %player_name% - &f%message%");


        getProxy().getLogger().info("\n" +
                "   §c________      __          __   _  __      \n" +
                "  §c/ ____/ /___  / /_  ____ _/ /  | |/ /      \n" +
                " §c/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §bRunning on Version §8v" + getDescription().getVersion() + "\n" +
                "§c/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §bPluign by §8" + getDescription().getAuthor()  + "\n" +
                "§c\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalCMD());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getProxy().getLogger().info("\n" +
                "   §c________      __          __   _  __      \n" +
                "  §c/ ____/ /___  / /_  ____ _/ /  | |/ /      \n" +
                " §c/ / __/ / __ \\/ __ \\/ __ `/ /   |   /   §cDisabling §8v" + getDescription().getVersion() + "\n" +
                "§c/ /_/ / / /_/ / /_/ / /_/ / /   /   |        §bPluign by §8" + getDescription().getAuthor()  + "\n" +
                "§c\\____/_/\\____/_.___/\\__,_/_/   /_/|_|     \n");
    }
}
