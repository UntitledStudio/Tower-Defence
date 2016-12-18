package td.entities;

import java.awt.Graphics2D;

public interface LivingEntity extends Entity {
    public void tick();
    public void render(Graphics2D g);
}
