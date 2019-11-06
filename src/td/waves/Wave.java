package td.waves;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import td.Configuration;
import td.entities.EntityRemoveReason;
import td.entities.enemies.BasicEnemy;
import td.util.Log;
import td.util.Loop;
import td.entities.enemies.EnemyUnit;
import td.screens.PlayScreen;

public class Wave {
    private final int id;
    private boolean isActive = false;
    private final boolean bossWave;
    private boolean paused = true;
    private final CopyOnWriteArrayList<EnemyUnit> ENEMY_INDEX;
    private final List<BasicEnemy> BASIC_ENEMIES;
    private long lastTick = 0;
    
    public Wave(int id) {
        this.id = id;
        this.bossWave = id % 10 == 0;
        this.BASIC_ENEMIES = new ArrayList<>();
        this.ENEMY_INDEX = new CopyOnWriteArrayList<>();
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
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            /*BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 100, 2));*/
            
            ENEMY_INDEX.addAll(BASIC_ENEMIES);
        }
    }
    
    /**
     * Launch the wave.
     */
    public void launch() {
        setPaused(false);
        isActive = true;
        List<EnemyUnit> toSpawn = new ArrayList<>(ENEMY_INDEX);
        
        Loop l = new Loop(() -> {
            toSpawn.get(0).spawn();
            toSpawn.remove(0);
            
            if(toSpawn.isEmpty()) {
                Loop.scheduleStop("waveLaunch");
            }
        }, 0, 1250, "waveLaunch");
        l.start();
    }
    
    /**
     * End this wave.
     */
    public void end() {
        isActive = false;
        ENEMY_INDEX.clear();
        BASIC_ENEMIES.clear();
        Log.info("[Wave] Wave '" + id + "' ended!");
        WaveManager.generateNext();
    }
    
    /**
     * Fired whenever an enemy is marked for removal.
     * @param unit 
     * @param reason 
     */
    public void onEnemyRemoval(EnemyUnit unit, EntityRemoveReason reason) {
        getEnemyIndex().remove(unit);
        
        if(getEnemyIndex().isEmpty()) {
            end();
        }
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
                    for(EnemyUnit e : ENEMY_INDEX) {
                        e.tick();
                    }
                    lastTick = System.currentTimeMillis();
                }
            }
        }
    }
    
    /**
     * Render the entities in this wave.
     * @param g
     */
    public void render(Graphics2D g) {
        for(EnemyUnit e : ENEMY_INDEX) {
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
    
    /**
     * Returns the enemy index.
     * @return 
     */
    public List<EnemyUnit> getEnemyIndex() {
        return ENEMY_INDEX;
    }
    
    /**
     * If this wave is currently being played.
     * @return 
     */
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
}
