package td.maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import td.TowerDefence;
import td.data.Block;
import td.data.BlockType;
import td.util.Log;

public class PathData {
    private final Map map;
    private Properties data;
    private File dataFile;
    
    public PathData(Map map) {
        this.map = map;
    }
    
    public void loadFile() {
        long start = System.currentTimeMillis();
        
        File mapDir = new File(TowerDefence.MAPS_DIRECTORY, map.getName());
        mapDir.mkdirs();
        dataFile = new File(mapDir, "path_data.properties");
        Log.info("[PathData] Loading data for: " + dataFile.getPath());
        
        try {
            if(!TowerDefence.DIRECTORY.exists()) {
                TowerDefence.DIRECTORY.mkdirs();
            }
            
            if(!dataFile.exists()) {
                dataFile.createNewFile();
            }
            data = new Properties();
            data.load(new FileInputStream(dataFile));
            
            if(map.getName().equalsIgnoreCase("default")) {
                data.put("PATH_1", "0,400,80,400");
                data.put("PATH_2", "80,400,160,400");
                data.put("PATH_3", "160,400,240,400");
                data.put("PATH_4", "240,400,320,400");
                data.put("PATH_5", "320,400,320,320");
                data.put("PATH_6", "320,320,320,240");
                data.put("PATH_7", "320,240,400,240");
                data.put("PATH_8", "400,240,480,240");
                data.put("PATH_9", "480,240,560,240");
                data.put("PATH_10", "560,240,560,320");
                data.put("PATH_11", "560,320,560,400");
                data.put("PATH_12", "560,400,560,480");
                data.put("PATH_13", "560,480,640,480");
                data.put("PATH_14", "640,480,720,480");
                data.put("PATH_15", "720,480,800,480");
                data.put("PATH_16", "800,480,800,400");
                data.put("PATH_17", "800,400,800,320");
                data.put("PATH_18", "800,320,800,240");
                data.put("PATH_19", "800,240,800,160");
                data.put("PATH_20", "800,160,880,160");
                data.put("PATH_21", "880,160,960,160");
                data.put("PATH_22", "960,160,1040,160");
                data.put("PATH_23", "1040,160,1120,160");
                data.put("PATH_24", "1120,160,1200,160");
                data.put("PATH_25", "1200,160,1200,160");
                saveFile();
            }
            Log.info("[PathData] Loaded! Took " + (System.currentTimeMillis()-start) + "ms");
    	} catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveFile() {
        Log.info("[PathData] Saving data for: " + dataFile.getPath());
        long start = System.currentTimeMillis();
        
        try {
            if(!TowerDefence.DIRECTORY.exists()) {
                TowerDefence.DIRECTORY.mkdirs();
            }
            
            if(!dataFile.exists()) {
                dataFile.createNewFile();
            }
            
            if(data != null) {
                data.store(new FileOutputStream(dataFile), null);
            }
            Log.info("[PathData] Saved! Took " + (System.currentTimeMillis()-start) + "ms");
    	} catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Properties getData() {
        return data;
    }
    
    public File getDataFile() {
        return dataFile;
    }
    
    public int getBlockX(String pathValue) {
        return Integer.parseInt(pathValue.split(",")[0]);
    }
    
    public int getBlockY(String pathValue) {
        return Integer.parseInt(pathValue.split(",")[1]);
    }
    
    public int getNextBlockX(String pathValue) {
        return Integer.parseInt(pathValue.split(",")[2]);
    }
    
    public int getNextBlockY(String pathValue) {
        return Integer.parseInt(pathValue.split(",")[3]);
    }
    
    public Block getPath(int path) {
        String key = "PATH_" + path;
        
        if(data.containsKey(key)) {
            String value = data.getProperty(key);
            return map.getBlockAt(getBlockX(value), getBlockY(value));
        }
        return null;
    }
    
    public Block getNextPath(int path) {
        String key = "PATH_" + path;
        
        if(data.containsKey(key)) {
            String value = data.getProperty(key);
            return map.getBlockAt(getNextBlockX(value), getNextBlockY(value));
        }
        return null;
    }
    
    public int getID(Block pathBlock) {
        for(String key : data.stringPropertyNames()) {
            int id = Integer.parseInt(key.replace("PATH_", ""));
            Block path = getPath(id);
            
            if(path != null) {
                if(path.getX() == pathBlock.getX() && path.getY() == pathBlock.getY()) {
                    return id;
                }
            } else {
                Log.error("[PathData] Attempted to fetch path block '" + id + "' but failed to find it in the property file.");
            }
        }
        return -1;
    }
    
    public void injectIntoBlockList() {
        Log.info("[PathData] Injecting data into block list ..");
        long start = System.currentTimeMillis();
        
        for(Block b : map.getBlocks()) {
            if(b.getType() == BlockType.PATH) {
                int id = getID(b);
                
                if(id < 0) {
                    Log.error("[PathData] Failed to fetch ID of path block: " + b.getX() + "," + b.getY());
                } else {
                    Block nextPath = getNextPath(id);
                    
                    if(nextPath != null) {
                        b.injectPathData(id, nextPath);
                    } else {
                        Log.error("[PathData] Failed to detect next path block of path: " + b.getX() + "," + b.getY() + " (id: " + id + ")");
                    }
                }
            }
        }
        Log.info("[PathData] Injection finished. (" + (System.currentTimeMillis()-start) + "ms)");
    }
    
    public Block getSpawnPath() {
        return getPath(1);
    }
    
    public Block getDestinationPath() {
        int i = 0;
        
        for(String s : data.stringPropertyNames()) {
            if(s.startsWith("PATH_")) {
                i++;
            }
        }
        return getPath(i);
    }
}
