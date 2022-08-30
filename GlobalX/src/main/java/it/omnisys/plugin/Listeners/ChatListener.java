package it.omnisys.plugin.Listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMainConfig;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;
import static it.omnisys.plugin.Utils.ColorUtils.colorLogs;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if (getMainConfig().getBoolean("GlobalChatPrefix.Enable")) {
            if (e.getSender() instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) e.getSender();

                if (e.getMessage().startsWith(getMainConfig().getString("GlobalChatPrefix.Prefix"))) {
                    if (!e.getMessage().contentEquals(getMainConfig().getString("GlobalChatPrefix.Prefix"))) {
                        if (p.hasPermission("globalx.globalchat.use")) {
                            e.setCancelled(true);
                            TextComponent broadcast = new TextComponent(color(
                                    getMessagesConfig().getString("GlobalFormat")
                                            .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                                            .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", p.getServer().getInfo().getName())))
                                            .replace("%player_name%", color(p.getDisplayName()))
                                            .replace("%message%", color(e.getMessage().replace("!", "")))));

                            broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getMainConfig().getString("CommandClickEvent").replace("%player%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                            broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMainConfig().getString("TextHoverEvent")))));

                            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                player.sendMessage(broadcast);
                            }

                            INSTANCE.getProxy().getLogger().info(colorLogs(broadcast.getText()));
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(new TextComponent(color(getMessagesConfig().getString("NoPermsMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(new TextComponent(color(getMessagesConfig().getString("OnlyPrefixMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                    }
                }
            }
        }
    }
}
