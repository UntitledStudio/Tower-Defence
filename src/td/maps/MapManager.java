package td.maps;

public class MapManager {
    protected static Map currentMap = null;
    
    public static void setMap(Map map) {
        currentMap = map;
    }
}
