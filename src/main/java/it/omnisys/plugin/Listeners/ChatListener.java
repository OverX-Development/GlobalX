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

import static it.omnisys.plugin.Commands.GlobalToggleCMD.globalToggledPlayers;
import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMainConfig;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Managers.CoolDownManager.isPlayerInCoolDown;
import static it.omnisys.plugin.Managers.CoolDownManager.putPlayerInCoolDown;
import static it.omnisys.plugin.Managers.PermissionManager.GLOBALX_GLOBALCHAT_USE;
import static it.omnisys.plugin.Utils.ColorUtils.color;
import static it.omnisys.plugin.Utils.ColorUtils.colorLogs;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if(!e.getMessage().startsWith("/")) {
            if (getMainConfig().getBoolean("GlobalChatPrefix.Enable")) {
                if (e.getSender() instanceof ProxiedPlayer) {
                    ProxiedPlayer p = (ProxiedPlayer) e.getSender();

                    if (e.getMessage().startsWith(getMainConfig().getString("GlobalChatPrefix.Prefix"))) {
                        if (!e.getMessage().contentEquals(getMainConfig().getString("GlobalChatPrefix.Prefix"))) {
                            if (p.hasPermission(GLOBALX_GLOBALCHAT_USE)) {
                                e.setCancelled(true);
                                if(!isPlayerInCoolDown(p)) {
                                    for (String str : getMainConfig().getSection("Groups").getKeys()) {
                                        for (String permission : getMainConfig().getSection("Permissions").getKeys()) {
                                            if (str.equalsIgnoreCase(permission)) {
                                                if (p.hasPermission(getMainConfig().getString("Permissions." + str))) {

                                                    putPlayerInCoolDown(p);

                                                    TextComponent broadcast = new TextComponent(color(
                                                            getMainConfig().getString("Groups." + str + ".ChatFormat")
                                                                    .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                                                                    .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", p.getServer().getInfo().getName())))
                                                                    .replace("%player_name%", color(p.getDisplayName()))
                                                                    .replace("%player_prefix%", color(getMainConfig().getString("Groups." + str + ".Prefix")))
                                                                    .replace("%message%", e.getMessage().replace(getMainConfig().getString("GlobalChatPrefix.Prefix"), ""))));

                                                    if (getMainConfig().getBoolean("HoverAndClickText.Enable")) {
                                                        broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getMainConfig().getString("HoverAndClickText.Command").replace("%player%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                                                        broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMainConfig().getString("HoverAndClickText.Text")))));
                                                    }


                                                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                                        player.sendMessage(broadcast);
                                                        break;
                                                    }

                                                    INSTANCE.getProxy().getLogger().info(colorLogs(broadcast.getText().replaceAll("&", "§")));
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    p.sendMessage(new TextComponent(color(getMessagesConfig().getString("CantUseInCoolDown").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                                    return;
                                }
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(new TextComponent(color(getMessagesConfig().getString("NoPermsMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                                return;
                            }
                        } else {
                            e.setCancelled(true);
                            p.sendMessage(new TextComponent(color(getMessagesConfig().getString("OnlyPrefixMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                            return;
                        }
                    }
                }
            } else {
                ProxiedPlayer p = (ProxiedPlayer) e.getSender();

                p.sendMessage(new TextComponent(color(getMessagesConfig().getString("PrefixChattingDisabled").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
            }


            if (e.getSender() instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) e.getSender();

                if (globalToggledPlayers.contains(p)) {

                    if (!isPlayerInCoolDown(p)) {
                        for (String str : getMainConfig().getSection("Groups").getKeys()) {
                            for (String permission : getMainConfig().getSection("Permissions").getKeys()) {
                                if (str.equalsIgnoreCase(permission)) {
                                    if (p.hasPermission(getMainConfig().getString("Permissions." + str))) {
                                        putPlayerInCoolDown(p);

                                        e.setCancelled(true);
                                        TextComponent broadcast = new TextComponent(color(
                                                getMainConfig().getString("Groups." + str + ".ChatFormat")
                                                        .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                                                        .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", p.getServer().getInfo().getName())))
                                                        .replace("%player_name%", color(p.getDisplayName()))
                                                        .replace("%player_prefix%", color(getMainConfig().getString("Groups." + str + ".Prefix")))
                                                        .replace("%message%", e.getMessage().replace(getMainConfig().getString("GlobalChatPrefix.Prefix"), ""))));

                                        if (getMainConfig().getBoolean("HoverAndClickText.Enable")) {
                                            broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getMainConfig().getString("HoverAndClickText.Command").replace("%player%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                                            broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMainConfig().getString("HoverAndClickText.Text")))));
                                        }


                                        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                            player.sendMessage(broadcast);
                                        }

                                        INSTANCE.getProxy().getLogger().info(colorLogs(broadcast.getText().replaceAll("&", "§")));
                                        return;

                                    }
                                }
                            }
                        }
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(new TextComponent(color(getMessagesConfig().getString("CantUseInCoolDown").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                    }
                }
            }
        }
    }
}
