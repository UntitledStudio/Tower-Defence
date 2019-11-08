package td.entities.projectile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import td.assets.ImageCache;
import td.entities.enemies.EnemyUnit;
import td.screens.PlayScreen;
import td.towers.Tower;
import td.util.Hitbox;
import td.util.Util;

public class MachineGunProjectile implements TowerProjectile {
    private final Tower tower;
    private final EnemyUnit target;
    private Hitbox hitbox;
    private double speed;
    
    public MachineGunProjectile(Tower tower, EnemyUnit target, double speed) {
        this.tower = tower;
        this.target = target;
        this.speed = speed;
        this.hitbox = new Hitbox(tower.getBlock().getX(), tower.getBlock().getY(), ImageCache.MACHINE_GUN_PROJECTILE.getWidth(), ImageCache.MACHINE_GUN_PROJECTILE.getHeight());
        hitbox.setX(hitbox.getX() + Util.centerValue(hitbox.getWidth(), tower.getBlock().getTexture().getWidth()));
        hitbox.setY(hitbox.getY() + Util.centerValue(hitbox.getHeight(), tower.getBlock().getTexture().getHeight()));
    }
    
    @Override
    public void tick() {
        TowerProjectile.calculateProjectilePos(this);
        
        if(Util.isWithinArea(getX(), getY(), target.getHitbox())) {
            onHit();
        }
    }

    @Override
    public void render(Graphics2D g) {
        //TowerProjectile.drawProjectile(g, this);
        g.drawImage(getImage(), getX(), getY(), null);
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

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void onHit() {
        target.damage(tower.getDamage()); // todo: crit handling
        remove();
    }

    @Override
    public void remove() {
        PlayScreen.instance.getProjectileManager().getProjectiles().remove(this);
    }

    @Override
    public BufferedImage getImage() {
        return ImageCache.MACHINE_GUN_PROJECTILE;
    }
}
