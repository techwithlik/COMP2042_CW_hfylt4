package main.model;

import java.util.TimerTask;
import java.util.Timer;


/** Timer feature */
public class GameTimer {

    private int gameTime;
    private int seconds;
    private int minutes;
    private boolean gaming = false;

    public GameTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
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

    public int getMinutes() {
        return minutes;
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
