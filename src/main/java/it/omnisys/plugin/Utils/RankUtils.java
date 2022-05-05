package it.omnisys.plugin.Utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.nullness.qual.Nullable;

import static it.omnisys.plugin.GlobalX.LPapi;

public class RankUtils {
    public static @Nullable String getPlayerGroup(ProxiedPlayer player) {
        return LPapi.getGroupManager().getGroup(player.getName()).getDisplayName();
    }


}
