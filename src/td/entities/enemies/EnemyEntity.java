package td.entities.enemies;

import java.awt.Graphics2D;
import td.assets.Texture;
import td.data.Block;
import td.entities.Entity;
import td.towers.Tower;
import td.util.Direction;
import td.util.Hitbox;
import td.waves.Wave;

public interface EnemyEntity extends Entity {
    public void tick();
    public void render(Graphics2D g);
    public Wave getAssociatedWave();
    public void kill();
    public void spawn();
    public int getHealth();
    public void setHealth(int health);
    public int getMaxHealth();
    public void setMaxHealth(int maxHealth);
    public void damage(int amount);
    public void heal(int amount);
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public int getWidth();
    public int getHeight();
    public Texture getTexture();
    public Hitbox getHitbox();
    public void setMoveSpeed(int speed);
    public int getMoveSpeed();
    public EnemyType getEnemyType();
    public AIHelper getAI();
    public boolean isWithinTowerRange(Tower tower);
}
