package td.entities.projectile;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;
import td.util.Log;

public class ProjectileManager {
    private final CopyOnWriteArrayList<TowerProjectile> projectiles = new CopyOnWriteArrayList<>();
    
    public void processAll(Graphics2D g) {
        for(TowerProjectile projectile : projectiles) {
            projectile.tick();
            projectile.render(g);
        }
    }
    
    public void removeAll() {
        int i = projectiles.size();
        projectiles.clear();
        Log.info("[ProjectileManager] Removed all " + i + " projectiles.");
    }
    
    public CopyOnWriteArrayList getProjectiles() {
        return projectiles;
    }
}
