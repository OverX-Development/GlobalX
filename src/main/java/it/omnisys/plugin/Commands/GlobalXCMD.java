package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class GlobalXCMD extends Command {
    public GlobalXCMD () {
        super("globalx", "", "");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            // noinspection deprecation
            player.sendMessage(color("&c● &7This server is running GlobalX v" + INSTANCE.getDescription().getVersion() + " by &cSgattix & GX_Regent"));
        } else {
            INSTANCE.getProxy().getLogger().info("§c● &7This server is running GlobalX v" + INSTANCE.getDescription().getVersion() + " by §cSgattix & GX_Regent");
        }
    }
}
