package td.towers;

import td.data.Block;

public class BasicTower extends Tower {
    private final String name = "Basic Tower";
    
    public BasicTower(Block block) {
        super(block);
        towerType               = TowerType.BASIC_TOWER.getDefaults().TOWER_TYPE;
        maxLevel                = TowerType.BASIC_TOWER.getDefaults().MAX_LEVEL;
        currentLevel            = TowerType.BASIC_TOWER.getDefaults().START_LEVEL;
        damage                  = TowerType.BASIC_TOWER.getDefaults().DAMAGE;
        shotsPerSecond          = TowerType.BASIC_TOWER.getDefaults().SHOTS_PER_SECOND;
        range                   = TowerType.BASIC_TOWER.getDefaults().RANGE;
        critChance              = TowerType.BASIC_TOWER.getDefaults().CRIT_CHANCE;
        critMultiplier          = TowerType.BASIC_TOWER.getDefaults().CRIT_MULTIPLIER;
        buyCost                 = TowerType.BASIC_TOWER.getDefaults().BUY_COST;
        refundAmount            = TowerType.BASIC_TOWER.getDefaults().REFUND_AMOUNT;
        upgradeCostMultiplier   = TowerType.BASIC_TOWER.getDefaults().UPGRADE_COST_MULTIPLIER;
        upgradeDamageMultiplier = TowerType.BASIC_TOWER.getDefaults().UPGRADE_DAMAGE_MULTIPLIER;
        updateRange();
    }
    
    @Override
    public String getName() {
        return name;
    }
}
