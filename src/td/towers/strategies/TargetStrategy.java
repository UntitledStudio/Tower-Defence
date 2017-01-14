package td.towers.strategies;

import java.util.List;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;

public abstract class TargetStrategy {
    public abstract EnemyUnit findTarget(Tower tower);
    public abstract String getName();
}
