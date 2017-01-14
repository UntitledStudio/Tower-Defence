package td.towers.strategies;

import java.util.List;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;
import td.waves.WaveManager;

public class LastUnitStrategy extends TargetStrategy {
    @Override
    public EnemyUnit findTarget(Tower tower) {
        List<EnemyUnit> index = WaveManager.getWave().getEnemyIndex();
        
        for(int i = index.size()-1; i >= 0; i--) {
            EnemyUnit unit = index.get(i);
            
            if(unit.isWithinTowerRange(tower)) {
                return unit;
            }
        }
        return null;
    }
    
    @Override
    public String getName() {
        return "Last";
    }
}
