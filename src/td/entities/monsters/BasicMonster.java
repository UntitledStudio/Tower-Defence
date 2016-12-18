package td.entities.monsters;

import java.awt.Graphics2D;
import td.entities.EntityManager;
import td.entities.EntityType;
import td.entities.LivingEntity;

public class BasicMonster implements LivingEntity {

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
    }
}
