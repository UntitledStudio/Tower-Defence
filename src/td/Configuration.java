package td;

public class Configuration {
    /**
     * Game version.
     */
    public static final String GAME_VERSION = "0.1";
    
    /**
     * Width of the game world in pixels.
     */
    public static final int GAME_WIDTH = 1280;
    
    /**
     * Height of the game world in pixels.
     */
    public static final int GAME_HEIGHT = 720;
    
    /**
     * Size of each block in the world in pixels.
     */
    public static final int BLOCK_SIZE = 80;
    
    /**
     * Whether to render animations or not.
     */
    public static final boolean ANIMATIONS_ENABLED = true;
    
    /**
     * The interval(ms) between two entity tick cycles.
     */
    public static final long ENTITY_TICK_INTERVAL = 22;
    
    /**
     * The amount of pixels the enemy will move per tick.
     */
    public static final int ENEMY_PIXELS_PER_TICK = 3;
    
    /**
     * How many pixels behind the spawn path block the enemy will be spawned at.
     */
    public static final int ENEMY_SPAWN_OFFSET = 100;
}
