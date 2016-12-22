package td.maps;

import td.util.Log;

public class LocationData {
    private final int spawnRow;
    private final int spawnColumn;
    private final int destinationRow;
    private final int destinationColumn;
    
    public LocationData(int spawnRow, int spawnColumn, int destinationRow, int destinationColumn) {
        this.spawnRow = spawnRow;
        this.spawnColumn = spawnColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        Log.info("[LocationData] New data object loaded. (spawnRow: " + spawnRow + ", spawnColumn: " + spawnColumn + ", destRow: " + destinationRow + ", destColumn: " + destinationColumn);
    }
    
    public int getSpawnRow() {
        return spawnRow;
    }
    
    public int getSpawnColumn() {
        return spawnColumn;
    }
    
    public int getDestinationRow() {
        return destinationRow;
    }
    
    public int getDestinationColumn() {
        return destinationColumn;
    }
}
