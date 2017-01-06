package td.towers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import td.Configuration;
import td.assets.ImageCache;
import td.data.Block;
import td.entities.Entity;
import td.entities.EntityType;
import td.util.Util;

public abstract class Tower implements Entity {
    /**
     * Max number of levels this tower can have.
     */
    int maxLevel = 5;
    
    /**
     * Current level of the tower.
     */
    int currentLevel = 1;
    
    /**
     * Tower damage to monsters.
     */
    int damage = 1;
    
    /**
     * 
     */
    int shotsPerSecond = 10;
    
    /**
     * Tower range in radius.
     */
    int range = 160;
    
    /**
     * The chance (%) that this tower will critical strike a monster.
     */
    int critChance = 0;
    
    /**
     * Defines the critical strike damage multiplier relative to the default damage of this tower.
     * A critMultiplier of 2.0 means that the tower deals double the normal damage.
     */
    double critMultiplier = 2.0;
    
    /**
     * The cost of this tower.
     */
    int buyCost = -1;
    
    /**
     * Returns the amount of cash you get from refunding the tower.
     */
    int refundAmount = -1;
    
    /**
     * How much the cost (relative to buy cost) of upgrading this tower increases.
     * So a upgradeMultiplier of 2.0 would make the next upgrade of this tower double the buy cost.
     */
    double upgradeCostMultiplier = 2;
    
    /**
     * How much the default damage of this tower increases on each upgrade. 
     */
    double upgradeDamageMultiplier = 2;
    
    /**
     * What type of tower is this?
     */
    TowerType towerType = TowerType.UNKNOWN;
    
    private boolean isSelected = false;
    private TowerState state = TowerState.DISABLED;
    private Block block;
    private Ellipse2D rangeIndicator = null;
    private double angleRad = 0;
    
    public Tower(Block block) {
        this.block = block;
    }
    
    /**
     * Instantly rotate the tower so that it points towards target location.
     * @param x
     * @param y 
     */
    public void lookAt(int x, int y) {
        double dx = x - block.getX() - (Configuration.BLOCK_SIZE / 2);
        double dy = y - block.getY() - (Configuration.BLOCK_SIZE / 2);
        angleRad = Math.atan2(dy, dx);
    }
    
    public void draw(Graphics2D g) {
        //if()
        
        int cx = towerType.getImage().getWidth() / 2;
        int cy = towerType.getImage().getHeight() / 2;
        AffineTransform backup = g.getTransform();
        g.translate(cx + block.getX(), cy + block.getY());
        g.rotate(angleRad);
        g.translate(-cx, -cy);
        g.drawImage(ImageCache.TOWER_BASIC, 0, 0, null);
        g.setTransform(backup);
    }
    
    public void setBlock(Block block) {
        this.block = block;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public TowerState getState() {
        return state;
    }
    
    public void setTowerState(TowerState state) {
        this.state = state;
    }
    
    public int getMaxLevel() {
        return maxLevel;
    }
    
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public int getRange() {
        return range;
    }
    
    public void setRange(int range) {
        this.range = range;
        updateRange();
    }
    
    public void updateRange() {
        int x = block.getX() + (Configuration.BLOCK_SIZE / 2);
        int y = block.getY() + (Configuration.BLOCK_SIZE / 2);
        this.rangeIndicator = Util.getEllipseFromCenter(x, y, range, range);
    }
    
    public Ellipse2D getRangeIndicator() {
        return rangeIndicator;
    }
    
    public int getCritChance() {
        return critChance;
    }
    
    public void setCritChance(int critChance) {
        this.critChance =  critChance;
    }
    
    public double getCritMultiplier() {
        return critMultiplier;
    }
    
    public void setCritMultiplier(double critMultiplier) {
        this.critMultiplier = critMultiplier;
    }
    
    public int getBuyCost() {
        return buyCost;
    }
    
    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }
    
    public int getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public double getUpgradeCostMultiplier() {
        return upgradeCostMultiplier;
    }
    
    public void setUpgradeCostMultiplier(double upgradeCostMultiplier) {
        this.upgradeCostMultiplier = upgradeCostMultiplier;
    }
    
    public double getUpgradeDamageMultiplier() {
        return upgradeDamageMultiplier;
    }
    
    public void setUpgradeDamageMultiplier(double upgradeDamageMultiplier) {
        this.upgradeDamageMultiplier = upgradeDamageMultiplier;
    }
    
    public TowerType getTowerType() {
        return towerType;
    }
    
    public abstract String getName();

    @Override
    public void create() {}

    @Override
    public EntityType getType() {
        return EntityType.TOWER;
    }
    
    @Override
    public void remove() {
        block.setTowerEntity(null);
    }
} 
