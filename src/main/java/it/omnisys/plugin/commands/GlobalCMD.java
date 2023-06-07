package it.omnisys.plugin.commands;

import it.omnisys.plugin.GlobalX;
import it.omnisys.plugin.managers.CoolDownManager;
import it.omnisys.plugin.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import static it.omnisys.plugin.commands.GlobalToggleCMD.globalToggledPlayers;
import static it.omnisys.plugin.managers.PermissionManager.GLOBALX_GLOBALCHAT_TOGGLE;
import static it.omnisys.plugin.managers.PermissionManager.GLOBALX_GLOBALCHAT_USE;

public class GlobalCMD extends Command {
    public GlobalCMD() {
        super("global", "", "gc", "globalchat");
    }

    private static final Configuration messagesConfig = GlobalX.getMessagesConfig();
    private static final Configuration mainConfig = GlobalX.getMainConfig();


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if (!p.hasPermission(GLOBALX_GLOBALCHAT_USE)) {
                ChatUtils.sendMessage(p, messagesConfig.getString("NoPermsMSG"));
                return;
            }

            if (!this.isNullArg(args, 0)) {
                if(p.hasPermission(GLOBALX_GLOBALCHAT_TOGGLE)) {
                    if (globalToggledPlayers.contains(p)) {
                        globalToggledPlayers.remove(p);
                        ChatUtils.sendMessage(p, messagesConfig.getString("GlobalChatToggledOff"));
                    } else {
                        globalToggledPlayers.add(p);
                        ChatUtils.sendMessage(p, messagesConfig.getString("GlobalChatToggledOn"));
                    }
                } else {
                    TextComponent insuffArgs = new TextComponent(ChatUtils.chat(messagesConfig.getString("InsuffArgs")));
                    insuffArgs.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtils.chat(messagesConfig.getString("InsuffArgsSuggestionHover")))));
                    insuffArgs.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/global <message>"));

                    p.sendMessage(insuffArgs);
                }
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

            if (CoolDownManager.isPlayerInCoolDown(p)) {
                p.sendMessage(new TextComponent(ChatUtils.chat(messagesConfig.getString("CantUseInCoolDown"))));
                return;
            }

            for (String str : mainConfig.getSection("Groups").getKeys()) {
                for (String permission : mainConfig.getSection("Permissions").getKeys()) {
                    if (!str.equalsIgnoreCase(permission)) break;
                    if (!p.hasPermission(mainConfig.getString("Permissions." + str))) break;

                    CoolDownManager.putPlayerInCoolDown(p);

                    TextComponent broadcast = new TextComponent(ChatUtils.formatGlobalPlaceholders(p, message, mainConfig.getString("Groups." + str + ".Prefix")));

                    if (mainConfig.getBoolean("HoverAndClickText.Enable")) {
                        broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, mainConfig.getString("HoverAndClickText.Command").replace("%p%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                        broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtils.chat(mainConfig.getString("HoverAndClickText.Text")))));
                    }


                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                        player.sendMessage(broadcast);
                    }

                    GlobalX.getPlugin().getProxy().getLogger().info(ChatUtils.colorLogs(broadcast.getText().replaceAll("&", "§")));
                    return;
                }
            }
        } else {
            if (this.isNullArg(args, 0)) {
                GlobalX.getPlugin().getProxy().getLogger().info(ChatUtils.chat(messagesConfig.getString("InsuffArgs")));
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

            TextComponent broadcast = new TextComponent(ChatUtils.formatGlobalPlaceholders(message));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(broadcast);
            }
            GlobalX.getPlugin().getProxy().getLogger().info(ChatUtils.colorLogs(broadcast.getText().replaceAll("&", "§")));
        }
    }

    public boolean isNullArg(String[] args, int index) {
        try {
            String str = args[index];
            return false;
        } catch (IndexOutOfBoundsException ex) {
            return true;
        }
    }
}
