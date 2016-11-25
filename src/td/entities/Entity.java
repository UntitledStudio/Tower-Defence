package td.entities;

import java.awt.Graphics2D;

public interface Entity {
    public void create();
    public void update(double dt);
    public void render(Graphics2D g);
    public void remove();
}
