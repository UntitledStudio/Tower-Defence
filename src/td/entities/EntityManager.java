package td.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import td.util.Log;

public class EntityManager {
    /*private static final List<Entity> ENTITIES = new ArrayList<>();
    private static final List<LivingEntity> LIVING_ENTITIES = new ArrayList<>();
    //private static long lastTick = 0;
    
    public static List<Entity> getAllEntities() {
        return ENTITIES;
    }
    
    public static List<LivingEntity> getLivingEntities() {
        return LIVING_ENTITIES;
    }
    
    public static void removeAll() {
        Log.info("[EntityManager] Removing all entities ..");
        int i = 0;
        
        for(Entity e : ENTITIES) {
            e.remove();
            i++;
        }
        ENTITIES.clear();
        Log.info("[EntityManager] Removed " + i + " entitie(s)");
    }
    
    /*public static void tickLivingEntities() {
        if(LIVING_ENTITIES.isEmpty()) {
            return;
        }
        
        if(System.currentTimeMillis()-lastTick >= Configuration.ENTITY_TICK_INTERVAL) {
            for(LivingEntity le : LIVING_ENTITIES) {
                le.tick();
            }
            lastTick = System.currentTimeMillis();
        }
    }
    
    public static void renderLivingEntities(Graphics2D g) {
        for(LivingEntity le : LIVING_ENTITIES) {
            le.render(g);
        }
    }*/
}
