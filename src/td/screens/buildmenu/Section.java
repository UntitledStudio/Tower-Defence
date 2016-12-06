package td.screens.buildmenu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import td.util.Hitbox;

public interface Section {
    public void update(double dt);
    public void render(Graphics2D g);
    public void mousePressed(MouseEvent e);
    public Hitbox getHitbox();
    public int getY();
    public int getX();
    public int getWidth();
    public int getHeight();
}
