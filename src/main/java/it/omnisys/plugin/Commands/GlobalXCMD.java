package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.GlobalX.reload;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class GlobalXCMD extends Command {
    public GlobalXCMD () {
        super("globalx", "", "");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if(p.hasPermission("globalx.command.reload")) {
                    reload();
                    p.sendMessage(new TextComponent(color(getMessagesConfig().getString("ConfigReloadedMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                } else {
                    p.sendMessage(new TextComponent(color("&c● &7This server is running GlobalX v" + INSTANCE.getDescription().getVersion() + " by &cSgattix & GX_Regent")));
                }
            } else {
                p.sendMessage(new TextComponent(color("&c● &7This server is running GlobalX v" + INSTANCE.getDescription().getVersion() + " by &cSgattix & GX_Regent")));
            }
        } else {
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reload();
                INSTANCE.getProxy().getLogger().info(getMessagesConfig().getString("ConfigReloadedMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")).replaceAll("&", "§"));
            } else {
                INSTANCE.getProxy().getLogger().info("§c● &7This server is running GlobalX v" + INSTANCE.getDescription().getVersion() + " by §cSgattix & GX_Regent");
            }
        }
    }
}
