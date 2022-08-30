package it.omnisys.plugin.Listeners;

import it.omnisys.plugin.Managers.UpdateChecker;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMessagesConfig;
import static it.omnisys.plugin.Utils.ColorUtils.color;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        if(e.getPlayer().hasPermission("globalx.admin")) {
            new UpdateChecker(INSTANCE, 102941).getVersion(version -> {
                if (!INSTANCE.getDescription().getVersion().equals(version)) {
                    e.getPlayer().sendMessage(new TextComponent(color(getMessagesConfig().getString("Prefix") + " §aAn update was found! (" + version + ")")));
                }
            });
        }
    }
}
