package td.entities.enemies;

import java.awt.Graphics2D;
import td.Configuration;
import td.assets.ImageCache;
import td.assets.Texture;
import td.data.Block;
import td.entities.EntityType;
import td.maps.MapManager;
import td.util.Hitbox;
import td.util.Log;
import td.util.Util;
import td.waves.Wave;

public class BasicEnemy implements EnemyEntity {
    private final Texture texture;
    private final EnemyType type;
    private final Wave associatedWave;
    private int health = 1;
    private int maxHealth = 1;
    // Pixels per tick
    private int moveSpeed = 5;
    private boolean isSpawned = false;
    
    public BasicEnemy(Wave wave, int maxHealth, int moveSpeed) {
        this.texture = new Texture(ImageCache.ENEMY_BASIC);
        texture.createHitbox(-100, -100);
        this.type = EnemyType.BASIC;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.moveSpeed = moveSpeed;
        this.associatedWave = wave;
    }
    
    @Override
    public void tick() {
        if(isSpawned) {
            setX(getX() + 5);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(isSpawned) {
            texture.draw(g);
        }
    }

    @Override
    public Wave getAssociatedWave() {
        return associatedWave;
    }

    @Override
    public void create() {
        //EntityManager.getAllEntities().add(this);
        //EntityManager.getLivingEntities().add(this);
    }

    @Override
    public EntityType getType() {
        return EntityType.MONSTER;
    }

    @Override
    public void remove() {
        
    }
    
    @Override
    public void spawn() {
        Block spawn = MapManager.getCurrentMap().getSpawnBlock();
        //Block destination = MapManager.getCurrentMap().getDestinationBlock();
        
        if(spawn.getX() <= 0) {
            setX(-100);
            setY(spawn.getY() + Util.centerValue(texture.getHeight(), Configuration.BLOCK_SIZE));
        } else {
            System.out.println("todo");
        }
        isSpawned = true;
    }

    @Override
    public void kill() {
        // do some animation or some shiz
        remove();
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
        return moveSpeed;
    }
    
    @Override
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    
    @Override
    public EnemyType getEnemyType() {
        return type;
    }
}
