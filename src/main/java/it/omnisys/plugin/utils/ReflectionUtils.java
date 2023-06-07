package it.omnisys.plugin.utils;

import it.omnisys.plugin.GlobalX;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {
    public static void registerListeners(GlobalX plugin) {
        String packageName = "it.omnisys.plugin.listeners";
        for(Class<?> cl : new Reflections(packageName).getSubTypesOf(Listener.class)) {
            try {
                Listener ls = (Listener) cl.getDeclaredConstructor().newInstance();
                plugin.getProxy().getPluginManager().registerListener(plugin, ls);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void registerCommands(GlobalX plugin) {
        String packageName = "it.omnisys.plugin.commands";
        for(Class<?> cl : new Reflections(packageName).getSubTypesOf(Command.class)) {
            try {
                Command cmd = (Command) cl.getDeclaredConstructor().newInstance();
                plugin.getProxy().getPluginManager().registerCommand(plugin, cmd);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
