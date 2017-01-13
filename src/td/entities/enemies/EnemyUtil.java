package td.entities.enemies;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyUtil {
    public static void paintHealthBar(Graphics2D g, EnemyUnit unit) {
        int x = unit.getX();
        int y = unit.getY() - 8;
        int height = 5;
        
        // Red bar, missing health
        g.setColor(Color.red);
        g.fillRect(x, y, unit.getWidth(), height);
        
        // Green bar, health
        if(unit.getHealth() > 0) {
            double healthPercentage = ((double)unit.getHealth() / (double)unit.getMaxHealth()) * 100.0;
            double width = (healthPercentage / 100.0) * (double)unit.getWidth();
            g.setColor(Color.green);
            g.fillRect(x, y, (int)width, height);
        }
    }
}
