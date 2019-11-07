package td.entities.projectile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import td.assets.ImageCache;
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
    public double getSpeed();
    public void setSpeed(double speed);
    public void onHit();
    public void remove();
    public BufferedImage getImage();
    
    /**
     * Calculates and sets the correct position of the projectile.
     * Called every tick.
     * @param proj 
     */
    public static void calculateProjectilePos(TowerProjectile proj) {
        int x = proj.getTarget().getX() + (proj.getTarget().getWidth()/2);
        int y = proj.getTarget().getY() + (proj.getTarget().getHeight()/2);
        int dx = x - proj.getX();
        int dy = y - proj.getY();
        float direction = (float)Math.atan2(dy, dx);
        
        proj.setX((int)(proj.getX() + (proj.getSpeed() * Math.cos(direction))));
        proj.setY((int)(proj.getY() + (proj.getSpeed() * Math.sin(direction))));
    }
    
    /**
     * Calculates the angle of the projectile and renders it. 
     * 
     * Needs work.
     * @param g 
     * @param proj 
     */
    public static void drawProjectile(Graphics2D g, TowerProjectile proj) {
        int x = proj.getTarget().getX() + (proj.getTarget().getWidth()/2);
        int y = proj.getTarget().getY() + (proj.getTarget().getHeight()/2);
        int dx = x - proj.getX();
        int dy = y - proj.getY();
        float direction = (float)Math.atan2(dy, dx);
        
        int cx = proj.getImage().getWidth() / 2;
        int cy = proj.getImage().getHeight() / 2;
        AffineTransform backup = g.getTransform();
        g.translate(cx + proj.getX(), cy + proj.getY());
        g.rotate(direction);
        g.translate(-cx, -cy);
        g.drawImage(proj.getImage(), 0, 0, null);
        g.setTransform(backup);
    }
}
