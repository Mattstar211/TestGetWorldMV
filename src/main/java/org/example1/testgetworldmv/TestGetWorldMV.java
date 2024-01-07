package org.example1.testgetworldmv;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;

import java.util.Objects;

public final class TestGetWorldMV extends JavaPlugin implements  Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("getWorld")).setExecutor(this);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().split(" ");
        if (event.getPlayer().isOp()) {
            if (split.length > 0 && split[0].equalsIgnoreCase("/setBorderRadius")) {
                if (split.length>1){
                    String nameWorld = split[1];
                    World world = getLobbyWorld(nameWorld);
                    if(world != null) event.getPlayer().sendMessage("Мир " + nameWorld + " успешно получен!");
                    else event.getPlayer().sendMessage("Мир " + nameWorld + " не существует!");
                }
            }else event.getPlayer().sendMessage("Использование: /getWorld <имя_мира>");
        }else event.getPlayer().sendMessage("Недостаточно прав!");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public World getLobbyWorld(String name) {
        // Получаем экземпляр MultiverseCore
        MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");

        // Проверяем, что MultiverseCore установлен
        if (multiverseCore == null) {
            // Обработка ошибки - MultiverseCore не установлен
            return null;
        }

        // Получаем мир (lobby) по его имени
        MultiverseWorld nameWorld = multiverseCore.getMVWorldManager().getMVWorld(name);

        // Проверяем, что мир найден
        if (nameWorld == null) {
            // Обработка ошибки - мир не найден
            return null;
        }

        // Возвращаем объект World
        return nameWorld.getCBWorld();
    }

}
