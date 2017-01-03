package td.util;

import java.awt.Color;
import java.awt.Graphics2D;
import td.GameWindow;
import td.data.Block;
import td.data.BlockType;
import td.data.Fonts;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.screens.ScreenManager;
import td.screens.buildmenu.BuildMenu;

public class Debug {
    public static boolean ENABLED = false;
    public static Color bgClr = new Color(0f, 0f, 0f, 0.4f);
    protected static final int baseY = 8;
    private static BuildMenu buildMenu = null;
    
    public static void render(Graphics2D g, GameWindow window) {
        g.setFont(Fonts.DEFAULT);
        
        /**
         * General Debug
         */
        RenderUtil.drawString("FPS: " + window.getFPS(), 8, baseY, Color.WHITE, bgClr, g);
        RenderUtil.drawString(window.getPanel().getWidth() + "x" + window.getPanel().getHeight(), 8, baseY * 4, Color.WHITE, bgClr, g);
        RenderUtil.drawString(window.getInput().getMouseX() + "," + window.getInput().getMouseY(), 8, baseY * 7, Color.WHITE, bgClr, g);
        RenderUtil.drawString("S: " + ScreenManager.getCurrentScreen().toString(), 8, baseY * 10, Color.WHITE, bgClr, g);
        
        /**
         * PlayScreen Debug
         */
        if(ScreenManager.getCurrentScreen() instanceof PlayScreen) {
            if(buildMenu != null) {
                RenderUtil.drawString("BuildMenu: " + buildMenu.getState().name() + ", " + (buildMenu.isOpen() ? "open" : "closed"), 8, baseY * 13, Color.WHITE, bgClr, g);
            } else {
                RenderUtil.drawString("BuildMenu: INSTANCE IS NULL", 8, baseY * 13, Color.WHITE, bgClr, g);
            }
            
            for(Block b : MapManager.getCurrentMap().getBlocks()) {
                if(b.getType() == BlockType.PATH) {
                    g.setColor(Color.BLACK);
                    g.drawLine(b.getX(), b.getY(), b.getTexture().getHitbox().getRightX(), b.getTexture().getHitbox().getBottomY());
                    g.drawLine(b.getTexture().getHitbox().getRightX(), b.getY(), b.getX(), b.getTexture().getHitbox().getBottomY());
                }
            }
        }
    }
    
    public static void feedInstance(BuildMenu instance) {
        buildMenu = instance;
    }
}
