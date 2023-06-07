package it.omnisys.plugin.commands;

import it.omnisys.plugin.managers.ConfigManager;
import it.omnisys.plugin.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.config.Configuration;

import static it.omnisys.plugin.managers.PermissionManager.GLOBALX_GLOBALCHAT_TOGGLE;

public class GlobalToggleCMD extends Command {

    public static List<ProxiedPlayer> globalToggledPlayers = new ArrayList<>();

    private static final Configuration messagesConfig = new ConfigManager("messages.yml").getConfig();

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
                    p.sendMessage(new TextComponent(ChatUtils.chat(messagesConfig.getString("GlobalChatToggledOff"))));
                } else {
                    globalToggledPlayers.add(p);
                    p.sendMessage(new TextComponent(ChatUtils.chat(messagesConfig.getString("GlobalChatToggledOn"))));
                }
            } else {
                p.sendMessage(new TextComponent(ChatUtils.chat(messagesConfig.getString("NoPermsMSG"))));
            }
        }
    }
}
