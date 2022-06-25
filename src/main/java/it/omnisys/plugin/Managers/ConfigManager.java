/*
    GlobalX Created by Sgattix & GX_Regent2.0
    Public GNU License 2022

    All rights reserved
 */



package it.omnisys.plugin.Managers;


import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import static it.omnisys.plugin.GlobalX.plugin;

public class ConfigManager {
    static Configuration config;

    public static void createMainConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                InputStream in = plugin.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void registerMainConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public static Configuration getMainConfig() {
        return config;
    }

    static Configuration messagesConfig;

    public static void createMessageConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            try {
                InputStream in = plugin.getResourceAsStream("messages.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void registerMessageConfig() {
        try {
            messagesConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public static Configuration getMessagesConfig() { return messagesConfig; }
}
