package td.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import td.Configuration;
import td.GameWindow;
import td.data.Block;
import td.data.BlockType;
import td.data.Fonts;
import td.entities.enemies.EnemyUnit;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.screens.ScreenManager;
import td.screens.buildmenu.BuildMenu;
import td.towers.Tower;
import td.waves.Wave;
import td.waves.WaveManager;

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
            Wave wave = WaveManager.getWave();
            String waveInfo = "";
            
            if(wave != null) {
                waveInfo = "W: " + (wave.isActive() ? "Active" : "Not active") + ", pause: " + wave.isPaused() + ", launched: " + wave.isLaunched() + ", id: " + wave.getId() + ", enemies: " + wave.getEnemyIndex().size();
            } else {
                waveInfo = "Wave: null";
            }
            RenderUtil.drawString(waveInfo, 0, window.getPanel().getHeight()-22, Color.white, bgClr, g);
            
            for(Block b : MapManager.getCurrentMap().getBlocks()) {
                if(b.getType() == BlockType.PATH) {
                    g.setColor(Color.BLACK);
                    g.drawLine(b.getX(), b.getY(), b.getTexture().getHitbox().getRightX(), b.getTexture().getHitbox().getBottomY());
                    g.drawLine(b.getTexture().getHitbox().getRightX(), b.getY(), b.getX(), b.getTexture().getHitbox().getBottomY());
                }
                
                if(WaveManager.isWaveActive()) {
                    if(b.hasTowerEntity()) {
                        Tower t = b.getTowerEntity();
                        
                        if(t != null) {
                            if(t.hasTarget()) {
                                EnemyUnit unit = t.getTarget();
                                
                                if(unit != null) {
                                    // Laser line
                                    g.setColor(Color.cyan);
                                    int tx = b.getX() + b.getTexture().getWidth()/2;
                                    int ty = b.getY() + b.getTexture().getHeight()/2;
                                    int ux = unit.getX() + unit.getWidth()/2;
                                    int uy = unit.getY() + unit.getHeight()/2;
                                    g.drawLine(tx, ty, ux, uy);
                                    
                                    // Blue dot in the middle.
                                    g.setColor(Color.blue);
                                    Point center = Util.getPointBetween(tx, ty, ux, uy);
                                    g.fillOval(center.x - 3, center.y - 3, 6, 6);
                                    
                                    // Debug text
                                    g.setFont(Fonts.DEFAULT_11);
                                    RenderUtil.drawString("T.dis: " + Util.cleanDouble(Util.getDistanceBetween(tx, ty, ux, uy), 1), b.getX(), b.getY(), Color.cyan, Color.black, g);
                                }
                            }
                            
                            g.setFont(Fonts.DEFAULT_11);
                            // Target index debug text
                            RenderUtil.drawString("TI: " + t.getTargetIndex().size(), b.getX(), b.getY()+19, Color.cyan, Color.black, g);
                        }
                    }
                }
            }
        }
    }
    
    public static void feedInstance(BuildMenu instance) {
        buildMenu = instance;
    }
}
