package main.model;

import java.util.TimerTask;
import java.util.Timer;


public class GameTimer {

    private int gameTime;
    private int seconds;
    private int minutes;
    private Timer timer;
    private TimerTask task;
    private boolean gaming = false;

    public GameTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (isGaming()) {
                    gameTime++;
                    minutes = gameTime / 60;
                    seconds = gameTime % 60;
                }
            }
        };

        timer.schedule(task, 0, 1000);
    }


    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isGaming() {
        return gaming;
    }

    public void setGaming(boolean gaming) {
        this.gaming = gaming;
    }

    public void resetTimer(){
        gameTime = 0;
    }
}
