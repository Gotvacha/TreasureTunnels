import lombok.*;

@Data
@NoArgsConstructor
public class ThreadClock extends Thread {
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private int days = 0;

    public static int SECOND = 1000;

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
