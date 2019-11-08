package td.entities.enemies;

import java.awt.Graphics2D;
import td.Configuration;
import td.assets.ImageCache;
import td.assets.Texture;
import td.entities.EntityRemoveReason;
import td.entities.EntityType;
import td.towers.Tower;
import td.util.Hitbox;
import td.util.Log;
import td.waves.Wave;

public class BasicEnemy implements EnemyUnit {
    private final Texture texture;
    private final EnemyType type;
    private final Wave associatedWave;
    private final AIHelper ai;
    private int health = 100;
    private int maxHealth = 100;
    private int worth = 100;
    
    public BasicEnemy(Wave wave, int maxHealth, int moveSpeed) {
        this.texture = new Texture(ImageCache.ENEMY_BASIC);
        texture.createHitbox(-100, -100);
        this.type = EnemyType.BASIC;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.associatedWave = wave;
        this.ai = new AIHelper(this);
        ai.setMoveSpeed(moveSpeed);
    }
    
    @Override
    public void tick() {
        ai.move();
    }

    @Override
    public void render(Graphics2D g) {
        if(ai.isSpawned()) {
            // Enemy
            texture.draw(g);
            
            // Health bar
            EnemyUtil.paintHealthBar(g, this);
        }
    }

    @Override
    public Wave getAssociatedWave() {
        return associatedWave;
    }

    @Override
    public void create() {}

    @Override
    public EntityType getType() {
        return EntityType.MONSTER;
    }

    @Override
    public void remove(EntityRemoveReason reason) {
        getAssociatedWave().onEnemyRemoval(this, reason);
    }
    
    @Override
    public void spawn() {
        ai.spawn();
    }

    @Override
    public void kill() {
        // do some animation or some shiz
        Log.info("[BasicEnemy] " + getEnemyType().name() + " enemy killed. (Health: " + getHealth() + ", MaxHealth: " + getMaxHealth() + ", WaveID: " + getAssociatedWave().getId() + ")");
        remove(EntityRemoveReason.KILLED);
    }

    @Override
    public void despawn() {
        Log.info("[BasicEnemy] " + getEnemyType().name() + " enemy despawned. (Health: " + getHealth() + ", MaxHealth: " + getMaxHealth() + ", WaveID: " + getAssociatedWave().getId() + ")");
        remove(EntityRemoveReason.ESCAPED);
    }

    @Override
    public int getX() {
        return getHitbox().getX();
    }

    @Override
    public void setX(int x) {
        getHitbox().setX(x);
    }

    @Override
    public int getY() {
        return getHitbox().getY();
    }

    @Override
    public void setY(int y) {
        getHitbox().setY(y);
    }

    @Override
    public int getWidth() {
        return getTexture().getWidth();
    }

    @Override
    public int getHeight() {
        return getTexture().getHeight();
    }

    @Override
    public int getWorth() {
        return worth;
    }
    
    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Hitbox getHitbox() {
        return texture.getHitbox();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }
    
    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    @Override
    public void damage(int amount) {
        setHealth(health - amount);
        
        if(health <= 0) {
            kill();
        }
    }

    @Override
    public void heal(int amount) {
        setHealth(health + amount);
        
        if(health > maxHealth) {
            health = maxHealth;
        }
    }
    
    @Override
    public int getMoveSpeed() {
        return ai.getMoveSpeed();
    }
    
    @Override
    public void setMoveSpeed(int moveSpeed) {
        ai.setMoveSpeed(moveSpeed);
    }
    
    @Override
    public EnemyType getEnemyType() {
        return type;
    }

    @Override
    public AIHelper getAI() {
        return ai;
    }

    @Override
    public boolean isWithinTowerRange(Tower tower) {
        if(getX() < -getWidth() || getX() > Configuration.GAME_WIDTH || getY() < -getHeight() || getY() > Configuration.GAME_HEIGHT) {
            return false;
        }
        return tower.getRangeIndicator().intersects(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
