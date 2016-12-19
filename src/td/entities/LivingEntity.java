package td.entities;

import java.awt.Graphics2D;
import td.assets.Texture;
import td.util.Hitbox;

public interface LivingEntity extends Entity {
    public void tick();
    public void render(Graphics2D g);
    public void kill();
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
    public Texture getTexture();
    public Hitbox getHitbox();
}
