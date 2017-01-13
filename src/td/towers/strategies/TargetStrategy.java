package td.towers.strategies;

import java.util.List;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;

public interface TargetStrategy {
    public EnemyUnit findTarget(Tower tower);
    public String getName();
}
