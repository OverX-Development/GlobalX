package it.omnisys.plugin.Listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static it.omnisys.plugin.Commands.GlobalCMD.GlobalToggle;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if(e.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            if (GlobalToggle.contains(p.getUniqueId())) {
                ProxyServer.getInstance().broadcast(color(getMessagesConfig().getString("GlobalFormat")
                        .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                        .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", p.getServer().getInfo().getName())))
                        .replace("%player_name%", color(p.getDisplayName()))
                        .replace("%message%", color(e.getMessage()))
                ));
            }
        }
    }
}
