package td.entities.projectile;

import java.awt.Graphics2D;
import td.entities.enemies.EnemyUnit;
import td.towers.Tower;
import td.util.Hitbox;

public class MachineGunProjectile implements TowerProjectile {
    private final Tower tower;
    private final EnemyUnit target;
    private Hitbox hitbox;
    
    public MachineGunProjectile(Tower tower, EnemyUnit target) {
        this.tower = tower;
        this.target = target;
        this.hitbox = new Hitbox(tower.getBlock().getX()-19, 0, 0, 0); // !
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics2D g) {
        
    }

    @Override
    public Tower getTower() {
        return tower;
    }

    @Override
    public EnemyUnit getTarget() {
        return target;
    }

    @Override
    public void setX(int x) {
        hitbox.setX(x);
    }

    @Override
    public int getX() {
        return hitbox.getX();
    }

    @Override
    public void setY(int y) {
        hitbox.setY(y);
    }

    @Override
    public int getY() {
        return hitbox.getY();
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
