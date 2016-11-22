package td.maps;

import java.util.ArrayList;
import java.util.List;
import td.Configuration;
import td.util.Log;

public class MapManager {
    /**
     * Index of all maps.
     */
    protected final static List<Map> maps = new ArrayList<>();
    
    /**
     * 
     */
    public static int[] ROWS = null;
    
    /**
     * 
     */
    public static int[] COLUMNS = null;
    
    /**
     * The current map.
     */
    protected static Map currentMap = null;
    
    /**
     * Set the current map.
     * Do not call this while a game is in progress as you cannot change a map once it has been loaded.
     * @param map 
     * @param build 
     */
    public static void setMap(Map map, boolean build) {
        Log.info("[MapManager] Setting map to: " + map.getName());
        currentMap = map;
        
        if(build) {
            currentMap.buildMap();
        }
    }
    
    /**
     * Returns the current map.
     * @return 
     */
    public static Map getCurrentMap() {
        return currentMap;
    }
    
    public static void loadDefaultMaps() {
        Log.info("[MapManager] Loading default maps ..");
        long start = System.currentTimeMillis();
        
        int[][] structure1 = new int[][]{
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
          {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
          {1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        maps.add(new Map("default", structure1));
        
        Log.info("[MapManager] Finished loading " + maps.size() + " default map(s)! (" + (System.currentTimeMillis()-start) + "ms)");
    }
    
    public static void addMap(Map map) {
        maps.add(map);
    }
    
    public static Map getDefaultMap() {
        return maps.get(0);
    }
    
    public static List<Map> getMaps() {
        return maps;
    }
    
    public static void loadRowsAndColumns() {
        COLUMNS = new int[16]; // x
        ROWS = new int[9]; // y
        
        for(int i = 0; i < COLUMNS.length; i++) {
            COLUMNS[i] = Configuration.BLOCK_SIZE * i;
        }
        
        for(int i = 0; i < ROWS.length; i++) {
            ROWS[i] = Configuration.BLOCK_SIZE * i;
        }
    }
}
