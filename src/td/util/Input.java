package td.util;

import javax.swing.JPanel;

public class Input {
    private final JPanel panel;
    private int last_x = -1;
    private int last_y = -1;
    
    public Input(JPanel panel) {
        this.panel = panel;
    }
    
    public int getMouseX() {
        try {
            last_x = panel.getMousePosition().x;
        } catch(Exception ex) {}
        return last_x;
    }
    
    public int getMouseY() {
        try {
            last_y = panel.getMousePosition().y;
        } catch(Exception ex) {}
        return last_y;
    }
    
    public boolean isMouseInWindow() {
        return panel.getMousePosition() != null;
    }
    
    @Override
    public String toString() {
        return "x: " + getMouseX() + ", y: " + getMouseY() + ", mouseInFrame: " + isMouseInWindow();
    }
}
