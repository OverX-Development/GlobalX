package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.Commands.GlobalToggleCMD.globalToggledPlayers;
import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMainConfig;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Managers.CoolDownManager.isPlayerInCoolDown;
import static it.omnisys.plugin.Managers.CoolDownManager.putPlayerInCoolDown;
import static it.omnisys.plugin.Managers.PermissionManager.GLOBALX_GLOBALCHAT_TOGGLE;
import static it.omnisys.plugin.Managers.PermissionManager.GLOBALX_GLOBALCHAT_USE;
import static it.omnisys.plugin.Utils.ColorUtils.color;
import static it.omnisys.plugin.Utils.ColorUtils.colorLogs;

public class GlobalCMD extends Command {
    public GlobalCMD () {
        super("global", "", "gc", "globalchat");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if (!p.hasPermission(GLOBALX_GLOBALCHAT_USE)) {
                p.sendMessage(new TextComponent(color(getMessagesConfig().getString("NoPermsMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                return;
            }

                if (this.isNullArgument(args, 0)) {
                    if(p.hasPermission(GLOBALX_GLOBALCHAT_TOGGLE)) {
                        if(globalToggledPlayers.contains(p)) {
                            globalToggledPlayers.remove(p);
                            p.sendMessage(new TextComponent(color(getMessagesConfig().getString("GlobalChatToggledOff").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                            return;
                        } else {
                            globalToggledPlayers.add(p);
                            p.sendMessage(new TextComponent(color(getMessagesConfig().getString("GlobalChatToggledOn").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                            return;
                        }
                    } else {
                        TextComponent insuffArgs = new TextComponent(color(getMessagesConfig().getString("InsuffArgs").replace("%prefix%", getMessagesConfig().getString("Prefix"))));
                        insuffArgs.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMessagesConfig().getString("InsuffArgsSuggestionHover")))));
                        insuffArgs.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/global <message>"));

                        p.sendMessage(insuffArgs);
                        return;
                    }
                }

                String message = "";

                for (String arg : args) {
                    if (message.equals("")) {
                        message = arg;
                    } else {
                        message = message + " " + arg;
                    }
                }

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
                                                    .replace("%message%", message)));

                                    if (getMainConfig().getBoolean("HoverAndClickText.Enable")) {
                                        broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getMainConfig().getString("HoverAndClickText.Command").replace("%p%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                                        broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMainConfig().getString("HoverAndClickText.Text")))));
                                    }


                                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                        player.sendMessage(broadcast);
                                    }

                                    INSTANCE.getProxy().getLogger().info(colorLogs(broadcast.getText().replaceAll("&", "§")));
                                }
                            }
                        }
                    }
                } else {
                    p.sendMessage(new TextComponent(color(getMessagesConfig().getString("CantUseInCoolDown").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                }
        } else {
            if (this.isNullArgument(args, 0)) {
                INSTANCE.getProxy().getLogger().info(color(getMessagesConfig().getString("InsuffArgs").replace("%prefix%", getMessagesConfig().getString("Prefix"))));
                return;
            }

            String message = "";

            for (String arg : args) {
                if (message.equals("")) {
                    message = arg;
                } else {
                    message = message + " " + arg;
                }
            }

            TextComponent broadcast = new TextComponent(color(
                    getMessagesConfig().getString("GlobalFormat")
                            .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                            .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", getMessagesConfig().getString("ConsoleServer"))))
                            .replace("%player_name%", color(getMessagesConfig().getString("ConsoleNameFormat")))
                            .replace("%message%", color(message))));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(broadcast);
            }
            INSTANCE.getProxy().getLogger().info(colorLogs(broadcast.getText().replaceAll("&", "§")));
        }

    }

    private boolean isNullArgument(String[] args, int index) {
        try {
            String var10000 = args[index];
            return false;
        } catch (IndexOutOfBoundsException var4) {
            return true;
        }
    }
}
