package td.waves;

import java.util.ArrayList;
import java.util.List;
import td.entities.monsters.BasicEnemy;

public class Wave {
    private final int id;
    private final boolean bossWave;
    private final List<BasicEnemy> BASIC_ENEMIES;
    
    public Wave(int id) {
        this.id = id;
        this.bossWave = id % 10 == 0;
        this.BASIC_ENEMIES = new ArrayList<>();
    }
    
    /**
     * Generate this wave.
     * - The generation uses the id for stat scaling.
     * 
     * TODO: Make stats additionally scale with difficulty setting whenever that is implemented.
     */
    public void generate() {
        if(bossWave) {
            
        } else {
            BasicEnemy basicEnemy = new BasicEnemy(id, 5);
            BASIC_ENEMIES.add(basicEnemy);
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
}
