package it.omnisys.plugin.Managers;

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
    public static Configuration MessageConfig;

    public static Configuration getMessagesConfig() {
        return MessageConfig;
    }

    public void createMessagesConfig() {
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

    public static void registerMessagesConfig() {
        try {
            MessageConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}
