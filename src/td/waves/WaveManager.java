package td.waves;

import java.awt.Graphics2D;
import td.util.Log;

public class WaveManager {
    private static Wave currentWave = null;
    private static Wave nextWave = null;
    
    public static Wave getCurrentWave() {
        return currentWave;
    }
    
    public static Wave getNextWave() {
        return nextWave;
    }
    
    public static int getWaveCount() {
        if(currentWave != null) {
            return currentWave.getId();
        }
        return 0;
    }
    
    public static void generateNext() {
        Log.info("[WaveManager] Generating next wave ..");
        Wave next = new Wave(getWaveCount() + 1);
        next.generate();
        nextWave = next;
        Log.info("[WaveManager] Next wave generated! id: " + nextWave.getId());
    }
    
    public static void launchNext() {
        if(nextWave != null) {
            Log.info("[WaveManager] Launching next wave .. (id: " + nextWave.getId() + ")");
            setCurrentWave(nextWave);
            getCurrentWave().launch();
            Log.info("[WaveManager] Wave launched!");
        } else {
            Log.error("[WaveManager] Attempted to launch an undefined wave!");
            // error
        }
    }
    
    public static void tickLiveWave() {
        if(currentWave != null) {
            currentWave.tick();
        }
    }
    
    public static void renderLiveWave(Graphics2D g) {
        if(currentWave != null) {
            currentWave.render(g);
        }
    }
    
    private static void setCurrentWave(Wave wave) {
        currentWave = wave;
    }
}
