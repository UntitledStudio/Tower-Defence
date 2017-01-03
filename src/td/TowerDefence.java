package td;

import java.io.File;
import javax.swing.SwingUtilities;
import td.util.Log;

public class TowerDefence {
    public static final File DIRECTORY = new File(System.getProperty("user.home") + File.separator + "TowerDefence");
    public static final File MAPS_DIRECTORY = new File(DIRECTORY, "maps");
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Log.info("Loading ..");
        
        if(!DIRECTORY.exists()) {
            DIRECTORY.mkdirs();
            MAPS_DIRECTORY.mkdir();
            Log.info("The install directory was not found! A new one has been made.");
        }
        
        SwingUtilities.invokeLater(() -> {
            new GameWindow("TowerDefence v" + Configuration.GAME_VERSION).run();
            Log.info("TowerDefence v" + Configuration.GAME_VERSION + " loaded! Took " + (System.currentTimeMillis()-start) + "ms");
        });
    }
}
