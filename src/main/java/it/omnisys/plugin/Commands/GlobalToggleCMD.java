package it.omnisys.plugin.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Managers.PermissionManager.GLOBALX_GLOBALCHAT_TOGGLE;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class GlobalToggleCMD extends Command {

    public static List<ProxiedPlayer> globalToggledPlayers = new ArrayList<>();

    public GlobalToggleCMD() {
        super("globaltoggle", "", "gtoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(p.hasPermission(GLOBALX_GLOBALCHAT_TOGGLE)) {
                if(globalToggledPlayers.contains(p)) {
                    globalToggledPlayers.remove(p);
                    p.sendMessage(new TextComponent(color(getMessagesConfig().getString("GlobalChatToggledOff").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                } else {
                    globalToggledPlayers.add(p);
                    p.sendMessage(new TextComponent(color(getMessagesConfig().getString("GlobalChatToggledOn").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
                }
            } else {
                p.sendMessage(new TextComponent(color(getMessagesConfig().getString("NoPermsMSG").replace("%prefix%", getMessagesConfig().getString("Prefix")))));
            }
        }
    }
}
