package td.towers;

import td.data.Block;

public class BasicTower extends Tower {
    private final String name = "Basic Tower";
    
    public BasicTower(Block block) {
        super(block);
        towerType               = Defaults.getDefaults(TowerType.BASIC_TOWER).TOWER_TYPE;
        maxLevel                = Defaults.getDefaults(TowerType.BASIC_TOWER).MAX_LEVEL;
        currentLevel            = Defaults.getDefaults(TowerType.BASIC_TOWER).START_LEVEL;
        damage                  = Defaults.getDefaults(TowerType.BASIC_TOWER).DAMAGE;
        shotsPerSecond          = Defaults.getDefaults(TowerType.BASIC_TOWER).SHOTS_PER_SECOND;
        range                   = Defaults.getDefaults(TowerType.BASIC_TOWER).RANGE;
        critChance              = Defaults.getDefaults(TowerType.BASIC_TOWER).CRIT_CHANCE;
        critMultiplier          = Defaults.getDefaults(TowerType.BASIC_TOWER).CRIT_MULTIPLIER;
        buyCost                 = Defaults.getDefaults(TowerType.BASIC_TOWER).BUY_COST;
        refundAmount            = Defaults.getDefaults(TowerType.BASIC_TOWER).REFUND_AMOUNT;
        upgradeCostMultiplier   = Defaults.getDefaults(TowerType.BASIC_TOWER).UPGRADE_COST_MULTIPLIER;
        upgradeDamageMultiplier = Defaults.getDefaults(TowerType.BASIC_TOWER).UPGRADE_DAMAGE_MULTIPLIER;
        updateRange();
    }
    
    @Override
    public String getName() {
        return name;
    }
}
