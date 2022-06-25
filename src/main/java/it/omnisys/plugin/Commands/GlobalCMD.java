package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.GlobalX.plugin;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class GlobalCMD extends Command {
    public GlobalCMD () {
        super("global", "", "gc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            if (!player.hasPermission("omnisys.omnidictation.globalchat")) {
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

            // noinspection deprecation
            ProxyServer.getInstance().broadcast(color(getMessagesConfig().getString("GlobalFormat")
                    .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                    .replace("%serverNameFormat%", color(getMessagesConfig().getString("ServerNameFormat").replace("%serverName%", player.getServer().getInfo().getName())))
                    .replace("%player_name%", color(player.getDisplayName()))
                    .replace("%message%", color(message))
                    ));
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

            // noinspection deprecation
            ProxyServer.getInstance().broadcast(color(getMessagesConfig().getString("GlobalFormat")
                    .replace("%prefix%", color(getMessagesConfig().getString("Prefix")))
                    .replace("%serverNameFormat%", color(getMessagesConfig().getString("ConsoleServer")))
                    .replace("%player_name%", color(getMessagesConfig().getString("ConsoleNameFormat")))
                    .replace("%message%", color(message))
            ));
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
