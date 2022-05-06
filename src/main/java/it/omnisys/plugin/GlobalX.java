package it.omnisys.plugin;

import it.omnisys.plugin.Commands.GlobalCMD;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class GlobalX extends Plugin {
    public static GlobalX plugin;

    private File mainConfigFile;

    public static Configuration mainConfig;

    private File messageConfigFile;
    private Configuration messageConfig;

    public static LuckPerms LPapi = LuckPermsProvider.get();

    @Override
    public void onEnable() {
        // Plugin startup logic
        createMainConfig();
        createMesssageConfig();


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

    public static Configuration getMessageConfig() {
        return plugin.messageConfig;
    }

    public void createMesssageConfig() {
        messageConfigFile = new File(ProxyServer.getInstance().getPluginsFolder()+ "/GlobalX/messages.yml");

        try {
            if (!messageConfigFile.exists()) {
                messageConfigFile.createNewFile();
            }
            messageConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messageConfigFile);

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(messageConfig, messageConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMainConfig() {
        mainConfigFile = new File(ProxyServer.getInstance().getPluginsFolder()+ "/GlobalX/config.yml");

        try {
            if (!mainConfigFile.exists()) {
                mainConfigFile.createNewFile();
            }
            mainConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(mainConfigFile);

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(mainConfig, mainConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
