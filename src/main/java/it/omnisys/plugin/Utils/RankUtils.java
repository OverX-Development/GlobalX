package it.omnisys.plugin.Utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class RankUtils {
    public static String getPlayerGroup(ProxiedPlayer player, Collection<String> possibleGroups) {
        for (String group : possibleGroups) {
            if (player.hasPermission("group." + group)) {
                return group;
            }
        }
        return null;
    }
}
