package it.omnisys.plugin.commands;

import it.omnisys.plugin.managers.ConfigManager;
import it.omnisys.plugin.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import it.omnisys.plugin.GlobalX;
import net.md_5.bungee.config.Configuration;

public class GlobalXCMD extends Command {
    public GlobalXCMD () {
        super("globalx", "", "");
    }

    private static final Configuration messagesConfig = new ConfigManager("messages.yml").getConfig();


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if(p.hasPermission("globalx.command.reload")) {
                    GlobalX.reload();
                    p.sendMessage(new TextComponent(ChatUtils.chat(messagesConfig.getString("ConfigReloadedMSG"))));
                } else {
                    p.sendMessage(new TextComponent(ChatUtils.chat("&c● &7This server is running GlobalX v" + GlobalX.getPlugin().getDescription().getVersion() + " by &cSgattix")));
                }
            } else {
                p.sendMessage(new TextComponent(ChatUtils.chat("&c● &7This server is running GlobalX v" + GlobalX.getPlugin().getDescription().getVersion() + " by &cSgattix")));
            }
        } else {
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                GlobalX.reload();
                GlobalX.getPlugin().getProxy().getLogger().info(messagesConfig.getString("ConfigReloadedMSG").replaceAll("&", "§"));
            } else {
                GlobalX.getPlugin().getProxy().getLogger().info("§c● &7This server is running GlobalX v" + GlobalX.getPlugin().getDescription().getVersion() + " by §cSgattix");
            }
        }
    }
}
