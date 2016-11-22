package td.maps;

public class MapManager {
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
}
