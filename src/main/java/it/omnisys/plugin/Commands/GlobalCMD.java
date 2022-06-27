package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static it.omnisys.plugin.GlobalX.plugin;
import static it.omnisys.plugin.Managers.ConfigManager.getMainConfig;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class GlobalCMD extends Command {
    public GlobalCMD () {
        super("global", "", "gc");
    }

    public static List<UUID> GlobalToggle;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (!player.hasPermission("globalx.globalchat.use")) {
                // noinspection deprecation
                player.sendMessage(color(getMessagesConfig().getString("NoPermsMSG")));
                return;
            }

            if (this.isNullArgument(args, 0)) {
                // noinspection deprecation
                player.sendMessage(color(getMessagesConfig().getString("InsuffArgs")));
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

            BaseComponent broadcast = new TextComponent(color(
                    getMessagesConfig().getString("GlobalFormat")
                    .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                    .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", player.getServer().getInfo().getName())))
                    .replace("%player_name%", color(player.getDisplayName()))
                    .replace("%message%", message)));

            broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, getMainConfig().getString("CommandClickEvent").replace("%player%", player.getDisplayName()).replace("%target%", player.getServer().getInfo().getName())));
            broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color(getMainConfig().getString("TextHoverEvent")))));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(broadcast);
            }
            plugin.getProxy().getLogger().info(String.valueOf(broadcast));

        } else {
            if (this.isNullArgument(args, 0)) {
                plugin.getProxy().getLogger().info(color(getMessagesConfig().getString("InsuffArgs")));
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

            BaseComponent broadcast = new TextComponent(color(
                    getMessagesConfig().getString("GlobalFormat")
                            .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                            .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", getMessagesConfig().getString("ConsoleServer"))))
                            .replace("%player_name%", color(getMessagesConfig().getString("ConsoleNameFormat")))
                            .replace("%message%", color(message))));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(broadcast);
            }
            plugin.getProxy().getLogger().info(String.valueOf(broadcast));
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
