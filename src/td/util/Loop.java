package td.util;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Version 1.3
 * @author Magnus
 */
public class Loop {
    private final Runnable runnable;
    private Runnable end_runnable = null;
    private int scheduled_loops;
    private final long delay;
    private final long interval;
    private String task_name = null;
    
    private Timer timer;
    private int loops_done;
    
    private static Set<String> stop_schedule = new HashSet<>();
    
    public Loop(Runnable runnable, long delay, long interval) {
        this.runnable = runnable;
        this.delay = delay;
        this.interval = interval;
        this.scheduled_loops = -1;
    }
    
    public Loop(Runnable runnable, long delay, long interval, String task_name) {
        this.runnable = runnable;
        this.delay = delay;
        this.interval = interval;
        this.scheduled_loops = -1;
        this.task_name = task_name.toLowerCase();
    }
    
    public void start() {
        if(scheduled_loops == 0 || runnable == null || delay < 0 || interval < 1) {
            return;
        } else if(stop_schedule.contains(task_name)) {
            stop_schedule.remove(task_name);
        }
        timer = new Timer();
        loops_done = 0;
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(stop_schedule.contains(task_name)) {
                    stop();
                } else {
                    if(scheduled_loops > 0) {
                        runnable.run();

                        loops_done++;
                        if(loops_done >= scheduled_loops) {
                            stop();
                        }
                    } else {
                        loops_done++;
                        runnable.run();
                    }
                }
            }
        }, delay, interval);
    }
    
    public void stop() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        loops_done = 0;
        
        if(end_runnable != null) {
            end_runnable.run();
        }
        stop_schedule.remove(task_name);
    }
    
    /**
     * Will run the stop() before the next loop goes through.
     * Designed to be used in order to cancel loops from inside the loop runnable itself.
     * The task_name is case-insensitive.
     * 
     * @param task_name 
     */
    public static void scheduleStop(String task_name) {
        if(task_name != null && !task_name.isEmpty()) {
            stop_schedule.add(task_name.toLowerCase());
        }
    }
    
    public int getScheduledLoops() {
        return scheduled_loops;
    }
    
    public int getLoopsDone() {
        return loops_done;
    }
    
    public void setScheduledLoops(int amount) {
        this.scheduled_loops = amount;
    }
    
    public void setEndRunnable(Runnable runnable) {
        this.end_runnable = runnable;
    }
    
    public void setTaskName(String task_name) {
        this.task_name = task_name.toLowerCase();
    }
}
