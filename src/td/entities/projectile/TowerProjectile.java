package td.entities.projectile;

import java.awt.Graphics2D;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;
import td.util.Hitbox;

public interface TowerProjectile {
    public void tick();
    public void render(Graphics2D g);
    public Tower getTower();
    public EnemyUnit getTarget();
    public void setX(int x);
    public int getX();
    public void setY(int y);
    public int getY();
    public Hitbox getHitbox();
}
