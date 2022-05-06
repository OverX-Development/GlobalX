package it.omnisys.plugin.Managers;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static it.omnisys.plugin.GlobalX.plugin;

public class ConfigManager {
    public static Configuration messagesConfig;

    public static void createMessagesConfig() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "messages.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Configuration getMessagesConfig() {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveMessagesConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesConfig, new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
