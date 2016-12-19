package td.entities.monsters;

import java.awt.Graphics2D;
import td.assets.ImageCache;
import td.assets.Texture;
import td.entities.EntityManager;
import td.entities.EntityType;
import td.entities.LivingEntity;
import td.util.Hitbox;

public class BasicEnemy implements LivingEntity {
    private final Texture texture;
    private int health = 1;
    private int maxHealth = 1;
    /**
     * Pixels per tick.
     */
    private int moveSpeed = 5;
    
    public BasicEnemy(int maxHealth, int moveSpeed) {
        this.texture = new Texture(ImageCache.ENEMY_BASIC);
        texture.createHitbox(0, 0);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.moveSpeed = moveSpeed;
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics2D g) {
        
    }

    @Override
    public void create() {
        EntityManager.getAllEntities().add(this);
        EntityManager.getLivingEntities().add(this);
    }

    @Override
    public EntityType getType() {
        return EntityType.MONSTER;
    }

    @Override
    public void remove() {
        EntityManager.getAllEntities().remove(this);
        EntityManager.getLivingEntities().remove(this);
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
    
    public int getMoveSpeed() {
        return moveSpeed;
    }
    
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
