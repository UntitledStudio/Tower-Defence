package td.towers;

import td.data.Block;

public class BasicTower extends Tower {
    private final String name = "Basic Tower";
    
    public BasicTower(Block block) {
        super(block);
        maxLevel                = BasicTowerDefaults.MAX_LEVEL;
        currentLevel            = 1;
        damage                  = BasicTowerDefaults.DAMAGE;
        critChance              = BasicTowerDefaults.CRIT_CHANCE;
        critMultiplier          = BasicTowerDefaults.CRIT_MULTIPLIER;
        buyCost                 = BasicTowerDefaults.BUY_COST;
        refundAmount            = BasicTowerDefaults.REFUND_AMOUNT;
        upgradeCostMultiplier   = BasicTowerDefaults.UPGRADE_COST_MULTIPLIER;
        upgradeDamageMultiplier = BasicTowerDefaults.UPGRADE_DAMAGE_MULTIPLIER;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
