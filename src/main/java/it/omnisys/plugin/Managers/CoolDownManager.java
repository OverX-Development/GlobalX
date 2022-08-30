package it.omnisys.plugin.Managers;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static it.omnisys.plugin.GlobalX.INSTANCE;
import static it.omnisys.plugin.Managers.ConfigManager.getMainConfig;

public class CoolDownManager {

    private static List<ProxiedPlayer> cooldown = new ArrayList<>();
    private static Integer coolDownSeconds;

    public static boolean isPlayerInCoolDown(ProxiedPlayer p) { return cooldown.contains(p); }

    public static void putPlayerInCoolDown(ProxiedPlayer p) {
        if(!cooldown.contains(p)) {
            cooldown.add(p);

            for (String str : getMainConfig().getSection("Groups").getKeys()) {
                for (String permission : getMainConfig().getSection("Permissions").getKeys()) {
                    if (str.equalsIgnoreCase(permission)) {
                        if (p.hasPermission(getMainConfig().getString("Permissions." + str))) {
                            coolDownSeconds = getMainConfig().getInt("Groups." + str + ".CoolDown");
                        }
                    }
                }
            }

            INSTANCE.getProxy().getScheduler().schedule(INSTANCE, () -> {
                cooldown.remove(p);
            }, coolDownSeconds, TimeUnit.SECONDS);
        }
    }
}
