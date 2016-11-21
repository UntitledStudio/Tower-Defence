package td.util;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

public class Hitbox {
    private int x;
    private int y;
    private int width;
    private int height;
    
    private static final Set<Hitbox> data = new HashSet<>();
    private boolean pressed = false;
    
    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Hitbox(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getX() {
        return x;
    }
    
    public int getRightX() {
        return x + width;
    }
    
    public int getY() {
        return y;
    }
    
    public int getBottomY() {
        return y + height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Rectangle getBox() {
        return new Rectangle(x, y, width, height);
    }
    
    public boolean isPressed() {
        return pressed;
    }
    
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        
        if(pressed) {
            data.add(this);
        } else {
            data.remove(this);
        }
    }
    
    public static Set<Hitbox> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y + ", width: " + width + ", height: " + height + ", pressed: " + pressed;
    }
}
