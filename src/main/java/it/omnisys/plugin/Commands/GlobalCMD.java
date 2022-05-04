package it.omnisys.plugin.Commands;

import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.GlobalX.messagesConfig;
import static it.omnisys.plugin.GlobalX.plugin;

public class GlobalCMD extends Command {
    public GlobalCMD () {
        super("global", "", "gc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            User user = luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(p);

            if(p.hasPermission("globalx.command.globalchat")) {
                TextComponent clickable = new TextComponent();
                plugin.getProxy().broadcast(
                        messagesConfig.getString("Prefix") +
                        messagesConfig.getString("GlobalFormat")
                        .replaceAll("%serverName%", p.getServer().getInfo().getName())
                        .replaceAll("%luckperms_prefix%", net.luckperms.api.));

            }
        } else {

        }
    }
}
