package org.example1.testgetworldmv;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
        Objects.requireNonNull(getCommand("getworld")).setExecutor(this);
        Objects.requireNonNull(getCommand("penis")).setExecutor(this);
        Objects.requireNonNull(getCommand("lobby")).setExecutor(this);
        Objects.requireNonNull(getCommand("hub")).setExecutor(this);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().split(" ");
        Player player = event.getPlayer();
        if (event.getPlayer().isOp()) {
            if (split[0].equalsIgnoreCase("/getworld"))
                if (split.length > 1) {
                    String nameWorld = split[1];
                    World world = getWorld(nameWorld);
                    if(world != null) event.getPlayer().sendMessage("Мир " + nameWorld + " успешно получен!");
                    else event.getPlayer().sendMessage("Мир " + nameWorld + " не существует!");
                }else event.getPlayer().sendMessage("Использование: /getworld <имя_мира>");
        }else event.getPlayer().sendMessage("Недостаточно прав!");
        if (split[0].equalsIgnoreCase("/penis") && !player.getWorld().getName().equals("world")) {
            String nameWorld = "world";
            World world = getWorld(nameWorld);
            if (world != null){
                final Location locationWorldBorder = world.getWorldBorder().getCenter();
                final double x = locationWorldBorder.getX();
                final double y = world.getHighestBlockYAt(locationWorldBorder);
                final double z = locationWorldBorder.getZ();
                final Location newLocation = new Location(world, x, y, z);
                player.teleport(newLocation);
                System.out.println("TestGetWorldVV: Игрок " + player.getName() + " телепортирован в мир World");
            }
        }
        if (split[0].equalsIgnoreCase("/lobby") || split[0].equalsIgnoreCase("/hub")) {
            final String nameWorld = "lobby";
            final World world = getWorld(nameWorld);
            final Location newLocation = new Location(world, 0, 65, 0);
            player.teleport(newLocation);
            System.out.println("TestGetWorldVV: Игрок " + player.getName() + " телепортирован в мир Lobby");
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(event.getPlayer().getName().equals("Mattstar")) event.getPlayer().setGameMode(GameMode.CREATIVE);
        teleportToLobby(event);
    }

    private void teleportToLobby(PlayerEvent event) {
        Player player = event.getPlayer();
        teleportToLobby(player);
        System.out.println("TestGetWorldVV: Игрок " + player.getName() + " телепортирован в лобби");
    }
    public World getWorld(String name) {
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
    private void teleportToLobby(Player player) {
        World lobbyWorld = getWorld("lobby");

        if (lobbyWorld != null) {
            double x = 0;
            double y = 65;
            double z = 0;

            Location lobbyLocation = new Location(lobbyWorld, x, y, z);
            player.teleport(lobbyLocation);

            player.sendMessage("Вы были телепортированы в лобби!");
        } else {
            player.sendMessage("Мир minecraft:lobby не найден.");
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
