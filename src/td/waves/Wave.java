package td.waves;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import td.Configuration;
import td.entities.EntityRemoveReason;
import td.entities.enemies.BasicEnemy;
import td.util.Log;
import td.util.Loop;
import td.entities.enemies.EnemyUnit;
import td.entities.projectile.TowerProjectile;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.towers.Tower;

public class Wave {
    private final int id;
    private boolean isActive = false;
    private boolean launched = false;
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
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 4));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            BASIC_ENEMIES.add(new BasicEnemy(this, 50, 2));
            
            ENEMY_INDEX.addAll(BASIC_ENEMIES);
        }
    }
    
    /**
     * Launch the wave.
     */
    public void launch() {
        launched = true;
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
        
        /**
         * Responsible for searching for targets(enemies) and registering them.
         */
        Loop towerTargetSearcher = new Loop(() -> {
            if(isActive() && !isPaused()) {
                for(EnemyUnit unit : getEnemyIndex()) {
                    for(Tower t : MapManager.getCurrentMap().getTowers()) {
                        /*for(EnemyUnit target : t.getTargetIndex()) {
                            if(!target.isAlive()) {
                                t.unregisterTarget(target);
                                return;
                            }
                        }*/
                        if(unit.isWithinTowerRange(t)) {
                            if(!t.isTargetRegistered(unit)) {
                                t.registerTarget(unit);
                            }
                        } else {
                            if(t.isTargetRegistered(unit)) {
                                t.unregisterTarget(unit);
                            }
                        }
                    }
                }
            }
        }, 100, 100, "towerTargetSearcher");
        towerTargetSearcher.start();
    }
    
    /**
     * End this wave.
     */
    public void end() {
        isActive = false;
        ENEMY_INDEX.clear();
        BASIC_ENEMIES.clear();
        Loop.scheduleStop("waveLaunch");
        Loop.scheduleStop("towerTargetSearcher");
        
        Log.info("[Wave] Wave '" + id + "' ended!");
        WaveManager.generateNext();
    }
    
    /**
     * Fired whenever an enemy is marked for removal.
     * @param unit 
     * @param reason 
     */
    public void onEnemyRemoval(EnemyUnit unit, EntityRemoveReason reason) {
        Log.info("[Wave] Removing enemy entity ..");
        getEnemyIndex().remove(unit);
        
        // Remove this unit from towers that have registered it as a target.
        for(Tower t : MapManager.getCurrentMap().getTowers()) {
            if(t.hasTarget() && t.getTargetIndex().contains(unit)) {
                t.unregisterTarget(unit);
            }
        }
        
        for(Object o : PlayScreen.instance.getProjectileManager().getProjectiles()) {
            TowerProjectile proj = (TowerProjectile)o;
            
            if(proj.getTarget() == unit) {
                // todo: instead of removing, make the projectiles keep traveling in the same direction until they out of bounds.
                proj.remove();
            }
        }
    }
    
    /**
     * Handles the ticking of this wave.
     */
    public void tick() {
        if(!paused && isActive) {
            if(ENEMY_INDEX.isEmpty()) {
                end();
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
        ENEMY_INDEX.forEach((e) -> {
            e.render(g);
        });
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
    
    public boolean isLaunched() {
        return launched;
    }
}
