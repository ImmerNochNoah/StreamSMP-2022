package net.senoxplays.backup;

import com.google.gson.Gson;
import net.senoxplays.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupSystem {

    public static boolean creatingABackup = false;

    public static void createBackup() {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            if (!Main.maintenance) {
                File directory = new File(Main.getPluginPath()+ "StreamSMP/Backups/");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                backup();
            }
        } else {
            Main.sendGlobalMessage("§eBackup wurde nicht gemacht, da der Server in Wartung ist!");
        }
    }

    private static void backup() {
        creatingABackup = true;
        Main.sendGlobalMessage("§aServer macht jetzt ein Backup! Bitte chill kurz");
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy_HH.mm");
        Date date = new Date(System.currentTimeMillis());
        String backup_folder_name = formatter.format(date);
        String backup_directory = Main.getPluginPath()+ "StreamSMP/Backups/" + backup_folder_name + "/";

        File directory = new File(backup_directory);
        directory.mkdir();

        File backup_world_directory = new File(backup_directory+ "world/");
        File world = new File(Main.getServerPath()+ "world");

        try {
            System.out.println("Kopiere Welten Ordner.");
            DirectoryCopy.copyDirectoryCompatibityMode(world, backup_world_directory);
            Main.sendGlobalMessageSound("§6Backup ist nun fertig! Viel Spaß beim Spielen.", Sound.ENTITY_CHICKEN_EGG);

            creatingABackup = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startAutoBackUp() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInsatnce(), new Runnable() {
            @Override
            public void run() {
                createBackup();
            }
        }, 0L, 2*36000L);
    }
}
