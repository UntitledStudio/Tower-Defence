package td.towers.strategies;

import java.util.List;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;
import td.waves.WaveManager;

public class LastUnitStrategy extends TargetStrategy {
    @Override
    public EnemyUnit findTarget(Tower tower) {
        List<EnemyUnit> index = WaveManager.getWave().getEnemyIndex();
        EnemyUnit target = null;
        
        for(EnemyUnit unit : index) {
            if(unit.isWithinTowerRange(tower)) {
                target = unit;
                
                for(EnemyUnit potential : tower.getTargetIndex()) {
                    if(potential.getAI().getDistanceTravelled() < target.getAI().getDistanceTravelled()) {
                        target = potential;
                    }
                }
            }
        }
        return target;
    }
    
    @Override
    public String getName() {
        return "Last";
    }
}
