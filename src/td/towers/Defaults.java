package td.towers;

import td.util.Log;

public class Defaults {
    public TowerType   TOWER_TYPE                  = TowerType.UNKNOWN;
    public int         MAX_LEVEL                   = -1;
    public int         START_LEVEL                 = -1;
    public int         DAMAGE                      = -1;
    public int         SHOTS_PER_SECOND            = -1;
    public int         RANGE                       = -1;
    public int         CRIT_CHANCE                 = -1;
    public double      CRIT_MULTIPLIER             = -1;
    public int         BUY_COST                    = -1;
    public int         REFUND_AMOUNT               = -1;
    public double      UPGRADE_COST_MULTIPLIER     = -1;
    public double      UPGRADE_DAMAGE_MULTIPLIER   = -1;
    
    private static Defaults DEFAULTS_BASIC = null;
    
    public Defaults(TowerType tower) {
        switch(tower) {
            case MACHINE_GUN: {
                TOWER_TYPE                  = TowerType.MACHINE_GUN;
                MAX_LEVEL                   = 1;
                START_LEVEL                 = 1;
                DAMAGE                      = 5;
                SHOTS_PER_SECOND            = 10;
                RANGE                       = 350;
                CRIT_CHANCE                 = 0;
                CRIT_MULTIPLIER             = 0.0;
                BUY_COST                    = 500;
                REFUND_AMOUNT               = 300;
                UPGRADE_COST_MULTIPLIER     = 1.7;
                UPGRADE_DAMAGE_MULTIPLIER   = 2.0;
            }
        }
    }
    
    /**
     * Cache default configurations to optimize lookups.
     */
    public static void cacheDefaults() {
        DEFAULTS_BASIC = new Defaults(TowerType.MACHINE_GUN);
        Log.info("[Defaults] Tower defaults cached.");
    }
    
    /**
     * Returns cached defaults of given tower type.
     * @param type
     * @return 
     */
    public static Defaults getDefaults(TowerType type) {
        switch(type) {
            case MACHINE_GUN: return DEFAULTS_BASIC;
            default: return new Defaults(TowerType.UNKNOWN);
        }
    }
}
