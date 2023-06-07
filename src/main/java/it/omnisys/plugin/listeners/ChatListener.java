package it.omnisys.plugin.listeners;

import it.omnisys.plugin.managers.CoolDownManager;
import it.omnisys.plugin.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import it.omnisys.plugin.GlobalX;

import java.util.Objects;

import static it.omnisys.plugin.managers.PermissionManager.GLOBALX_GLOBALCHAT_USE;

public class ChatListener implements Listener {

    private static final Configuration mainConfig = GlobalX.getMainConfig();
    private static final Configuration messagesConfig = GlobalX.getMessagesConfig();

    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if (e.getMessage().startsWith("/")) return;

        if (!mainConfig.getBoolean("GlobalChatPrefix.Enable")) {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            ChatUtils.sendMessage(p, messagesConfig.getString("PrefixChattingDisabled"));
            return;
        }

        if (e.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();

            String prefix = mainConfig.getString("GlobalChatPrefix.Prefix");

            if (!e.getMessage().startsWith(prefix)) return;
            String message = e.getMessage().replace(prefix, "");

            e.setCancelled(true);

            if (Objects.equals(e.getMessage(), prefix)) {
                ChatUtils.sendMessage(p, messagesConfig.getString("OnlyPrefixMSG"));
                return;
            }

            if (!p.hasPermission(GLOBALX_GLOBALCHAT_USE)) {
                ChatUtils.sendMessage(p, messagesConfig.getString("NoPermsMSG"));
                return;
            }

            if (CoolDownManager.isPlayerInCoolDown(p)) {
                ChatUtils.sendMessage(p, messagesConfig.getString("CantUseInCoolDown"));
                return;
            }

            for (String str : mainConfig.getSection("Groups").getKeys()) {
                String groupPerm = mainConfig.getString("Groups." + str + ".Permission");

                if (p.hasPermission(groupPerm)) {
                    TextComponent broadcast = new TextComponent(ChatUtils.formatGlobalPlaceholders(p, message, str));

                    if (mainConfig.getBoolean("HoverAndClickText.Enable")) {
                        broadcast.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, mainConfig.getString("HoverAndClickText.Command").replace("%player%", p.getDisplayName()).replace("%target%", p.getServer().getInfo().getName())));
                        broadcast.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtils.chat(mainConfig.getString("HoverAndClickText.Text")))));
                    }


                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                        player.sendMessage(broadcast);
                        break;
                    }

                    GlobalX.getPlugin().getProxy().getLogger().info(ChatUtils.colorLogs(broadcast.getText().replaceAll("&", "§")));

                    CoolDownManager.putPlayerInCoolDown(p);
                    return;
                }
            }

            ChatUtils.sendMessage(p, messagesConfig.getString("NoPermsMSG"));
        }

    }
}
