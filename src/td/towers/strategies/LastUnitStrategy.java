package td.towers.strategies;

import java.util.List;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;

public class LastUnitStrategy extends TargetStrategy {
    @Override
    public EnemyUnit findTarget(Tower tower) {
        List<EnemyUnit> index = tower.getTargetIndex();
        
        for(int i = index.size()-1; i >= 0; i--) {
            return index.get(i);
        }
        return null;
    }
    
    @Override
    public String getName() {
        return "Last";
    }
}
