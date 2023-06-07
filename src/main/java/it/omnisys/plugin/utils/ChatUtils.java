package it.omnisys.plugin.utils;

import it.omnisys.plugin.GlobalX;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class ChatUtils {

    private static final Configuration messagesConfig = GlobalX.getMessagesConfig();
    private static final Configuration mainConfig = GlobalX.getMainConfig();
    private static final String prefix = messagesConfig.getString("Prefix");

    public static String chat(String message) {
        if(message.contains("%prefix%")) {
            return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", prefix));
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(ProxiedPlayer player, String message) {
        player.sendMessage(new TextComponent(chat(message)));
    }

    public static String colorLogs(String message) { return ChatColor.translateAlternateColorCodes('§', message); }

    public static String formatGlobalPlaceholders(ProxiedPlayer p, String message, String group) {
        String globalFormat = chat(mainConfig.getString("Groups." + group + ".ChatFormat"));
        String groupPrefix = chat(mainConfig.getString("Groups." + group + ".Prefix"));
        String serverNameFormat = chat(messagesConfig.getString("ServerNameFormat"));
        String serverName = chat(p.getServer().getInfo().getName());

        return chat(globalFormat
                .replace("%prefix%", prefix)
                .replace("%message%", message))
                .replace("%player_prefix%", chat(groupPrefix))
                .replace("%serverNameFormat%", serverNameFormat.replace("%serverName%", serverName))
                .replace("%player_name%", p.getDisplayName());
    }

    public static String formatGlobalPlaceholders(String message) {
        String globalFormat = chat(messagesConfig.getString("GlobalFormat"));
        String serverNameFormat = chat(messagesConfig.getString("ServerNameFormat"));
        String consoleNameFormat = chat(messagesConfig.getString("ConsoleNameFormat"));
        String consoleServerFormat = chat(messagesConfig.getString("ConsoleServer"));

        return chat(globalFormat
                .replace("%prefix%", prefix)
                .replace("%message%", message))
                .replace("%serverNameFormat%", serverNameFormat.replace("%serverName%", consoleServerFormat))
                .replace("%player_name%", consoleNameFormat);
    }
}
