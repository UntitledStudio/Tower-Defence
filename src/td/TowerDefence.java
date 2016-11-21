package td;

import javax.swing.SwingUtilities;
import td.util.Log;

public class TowerDefence {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Log.info("Loading ..");
        
        SwingUtilities.invokeLater(() -> {
            new GameWindow("TowerDefence v" + Configuration.GAME_VERSION).run();
            Log.info("TowerDefence v" + Configuration.GAME_VERSION + " loaded! Took " + (System.currentTimeMillis()-start) + "ms");
        });
    }
}
