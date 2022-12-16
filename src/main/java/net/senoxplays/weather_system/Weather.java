package net.senoxplays.weather_system;

import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Weather {

    public static boolean weatherClear = false;
    private static int secondsRun;
    private static ScheduledExecutorService timer;

    public void setWeatherClear(int seconds) {
        if (!weatherClear) {
            Bukkit.getWorld("world").setStorm(false);
            startClearWeatherTimer(seconds);
            weatherClear = true;
        }
    }

    private void startClearWeatherTimer(int seconds) {
        int secondsToRun = seconds;
        Runnable runnable = new Runnable() {
            public void run() {
                secondsRun++;
                if (secondsRun >= secondsToRun) {
                    stopClearWeatherTimer();
                }
            }
        };
        timer = Executors.newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);;
    }

    private void stopClearWeatherTimer() {
        weatherClear = false;
        timer.shutdown();
    }

}
