package td.waves;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import td.Configuration;
import td.entities.enemies.BasicEnemy;
import td.entities.enemies.EnemyEntity;
import td.util.Log;

public class Wave {
    private final int id;
    private final boolean bossWave;
    private boolean paused = true;
    private final List<EnemyEntity> ENEMY_INDEX;
    private final List<BasicEnemy> BASIC_ENEMIES;
    private long lastTick = 0;
    
    public Wave(int id) {
        this.id = id;
        this.bossWave = id % 10 == 0;
        this.BASIC_ENEMIES = new ArrayList<>();
        this.ENEMY_INDEX = new ArrayList<>();
    }
    
    /**
     * Generate this wave.
     * - The generation uses the id for stat scaling.
     * 
     * TODO: Make stats additionally scale with difficulty setting whenever that is implemented.
     */
    public void generate() {
        if(bossWave) {
            Log.info("[Wave, ID: " + id + "] Generating boss wave ..");
            
        } else {
            Log.info("[Wave, ID: " + id + "] Generating regular wave ..");
            BasicEnemy basicEnemy = new BasicEnemy(this, id, 5);
            BASIC_ENEMIES.add(basicEnemy);
            
            ENEMY_INDEX.addAll(BASIC_ENEMIES);
        }
    }
    
    /**
     * Launch the wave.
     */
    public void launch() {
        // temp, todo: spawn each entity with delay inbetween
        for(EnemyEntity e : ENEMY_INDEX) {
            e.spawn();
        }
        setPaused(false);
    }
    
    /**
     * Handles the ticking of this wave.
     */
    public void tick() {
        if(!paused) {
            if(ENEMY_INDEX.isEmpty()) {
                // wave finished
            } else {
                if(System.currentTimeMillis()-lastTick >= Configuration.ENTITY_TICK_INTERVAL) {
                    for(EnemyEntity e : ENEMY_INDEX) {
                        e.tick();
                    }
                    lastTick = System.currentTimeMillis();
                }
            }
        }
    }
    
    /**
     * Render the entities in this wave.
     */
    public void render(Graphics2D g) {
        for(EnemyEntity e : ENEMY_INDEX) {
            e.render(g);
        }
    }
    
    /**
     * Returns the id for this wave.
     * The id of the wave should represent the current wave the player is at.
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Pause or unpause this wave.
     * Pausing the wave basically freezes all wave activity.
     * @param paused 
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    /**
     * Returns true if the wave is paused.
     * @return 
     */
    public boolean isPaused() {
        return paused;
    }
}
