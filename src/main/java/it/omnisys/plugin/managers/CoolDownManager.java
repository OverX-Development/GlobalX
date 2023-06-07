package it.omnisys.plugin.managers;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.omnisys.plugin.GlobalX;
import net.md_5.bungee.config.Configuration;

import static it.omnisys.plugin.managers.PermissionManager.GLOBALX_GLOBALCHAT_COOLDOWN_BYPASS;

public class CoolDownManager {

    private static List<ProxiedPlayer> cooldown = new ArrayList<>();
    private static Integer coolDownSeconds;

    public static boolean isPlayerInCoolDown(ProxiedPlayer p) {
        return cooldown.contains(p);
    }

    private static final Configuration mainConfig = GlobalX.getMainConfig();

    public static void putPlayerInCoolDown(ProxiedPlayer p) {
        if(p.hasPermission(GLOBALX_GLOBALCHAT_COOLDOWN_BYPASS)) return;
        if (!cooldown.contains(p)) {
            cooldown.add(p);

            for (String str : mainConfig.getSection("Groups").getKeys()) {
                String groupPerm = mainConfig.getString("Groups." + str + ".Permission");
                int groupCoolDown = mainConfig.getInt("Groups." + str + ".CoolDown");

                if (p.hasPermission(groupPerm)) {
                    coolDownSeconds = groupCoolDown;

                    GlobalX.getPlugin().getProxy().getScheduler().schedule(GlobalX.getPlugin(), () -> {
                        cooldown.remove(p);
                    }, coolDownSeconds, TimeUnit.SECONDS);
                    return;
                }
            }
        }
    }
}
