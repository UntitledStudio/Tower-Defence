package td.waves;

import java.awt.Graphics2D;
import td.util.Log;

public class WaveManager {
    private static Wave wave = null;
    
    public static Wave getWave() {
        return wave;
    }
    
    public static int getWaveCount() {
        if(wave != null) {
            return wave.getId();
        }
        return 0;
    }
    
    public static boolean isWaveActive() {
        return wave != null && wave.isActive();
    }
    
    public static void generateNext() {
        if(isWaveActive()) {
            Log.error("[WaveManager] Could not generate new wave as there already exists an active wave.");
            return;
        }
        wave = new Wave(getWaveCount() + 1);
        wave.generate();
        Log.info("[WaveManager] A new wave has been generated! ID: " + wave.getId());
    }
    
    public static void launchNext() {
        if(wave != null) {
            if(wave.isActive()) {
                Log.error("[WaveManager] Attempted to launch an already active wave!");
            } else {
                wave.launch();
                Log.info("[WaveManager] Launched wave! (" + wave.getId() + ")");
            }
        } else {
            Log.error("[WaveManager] Failed to launch wave. Could not detect generated wave. (null)");
        }
    }
    
    public static void tickLiveWave() {
        if(wave != null) {
            wave.tick();
        }
    }
    
    public static void renderLiveWave(Graphics2D g) {
        if(wave != null) {
            wave.render(g);
        }
    }
}
