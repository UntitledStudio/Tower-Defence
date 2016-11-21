package td.util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class RenderUtil {
    public static int centerStringX(String s, int comparison_width, Graphics g, int x) {
        return x + Util.centerValue(Util.getStringWidth(s, g), comparison_width);
    }
    
    public static int centerStringY(String s, int comparison_height, Graphics g, int y) {
        /*FontRenderContext frc = g.getFontRenderContext();
        GlyphVector gv = g.getFont().createGlyphVector(frc, s);
        Rectangle2D box = gv.getVisualBounds();
        return y + (int)(((comparison_height - box.getHeight()) / 2d) + (-box.getY()));*/
        FontMetrics fm = g.getFontMetrics();
        int yy = ((comparison_height - fm.getHeight()) / 2) + fm.getAscent();
        return y + yy;
    }
    
    public static void fillOval(Graphics2D g, int x, int y, int radius, boolean centered) {
        if(centered) {
            int diameter = radius * 2;
            g.fillOval(x - radius, y - radius, diameter, diameter);
        } else {
            g.fillOval(x, y, radius, radius);
        }
    }
    
    public static void drawString(String string, int x, int y, Color foreground, Color background, Graphics2D g) {
        g.setColor(background);
        int width = (int) (Util.getStringWidth(string, g) + 10);
        int height = (int) (g.getFontMetrics().getAscent() * 1.5);
        g.fillRect(x, y, width, height);
        g.setColor(foreground);
        g.drawString(string, x + 5, centerStringY(string, height, g, y));
    }
}
