package model;

import model.enums.Season;
import lombok.*;

@Getter
public class ThreadClock extends Thread {
    private static final ThreadClock INSTANCE = new ThreadClock();
    private final int SECOND = 1000;

    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private int days = 0;
    private Season seasons = Season.SPRING;

    private ThreadClock() {
        super();
    }

    public static ThreadClock getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        while (true) {
            try {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                    if (minutes == 60) {
                        minutes = 0;
                        hours++;
                        if (hours == 24) {
                            hours = 0;
                            days++;
                            if (days % 10 == 0) {
                                seasons = seasons.moveToNextSeason();
                            }
                        }
                    }
                }
                Thread.sleep(SECOND);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
    }
}
