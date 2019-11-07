package td.towers;

import td.data.Block;

public class MachineGunTower extends Tower {
    private final String name = "Machine Gun";
    
    public MachineGunTower(Block block) {
        super(block);
        towerType               = TowerType.MACHINE_GUN.getDefaults().TOWER_TYPE;
        maxLevel                = TowerType.MACHINE_GUN.getDefaults().MAX_LEVEL;
        currentLevel            = TowerType.MACHINE_GUN.getDefaults().START_LEVEL;
        damage                  = TowerType.MACHINE_GUN.getDefaults().DAMAGE;
        range                   = TowerType.MACHINE_GUN.getDefaults().RANGE;
        critChance              = TowerType.MACHINE_GUN.getDefaults().CRIT_CHANCE;
        critMultiplier          = TowerType.MACHINE_GUN.getDefaults().CRIT_MULTIPLIER;
        buyCost                 = TowerType.MACHINE_GUN.getDefaults().BUY_COST;
        refundAmount            = TowerType.MACHINE_GUN.getDefaults().REFUND_AMOUNT;
        upgradeCostMultiplier   = TowerType.MACHINE_GUN.getDefaults().UPGRADE_COST_MULTIPLIER;
        upgradeDamageMultiplier = TowerType.MACHINE_GUN.getDefaults().UPGRADE_DAMAGE_MULTIPLIER;
        fireInterval            = TowerType.MACHINE_GUN.getDefaults().FIRE_DELAY;
        fireSpeed               = TowerType.MACHINE_GUN.getDefaults().SPEED;
        updateRange();
    }
    
    @Override
    public String getName() {
        return name;
    }
}
