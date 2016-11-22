package td.screens;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import td.GameWindow;
import td.util.Log;

public class ScreenManager {
    /**
     * The current active screen that is being managed.
     */
    protected static Screen currentScreen = null;
    
    /**
     * An instance of the GameWindow class.
     */
    protected static GameWindow gameWindow = null;
    
    /**
     * Used to determine whether the current screen has been properly loaded or not.
     * Render & update calls will be blocked if this is false.
     */
    protected static boolean loaded = false;
    
    /**
     * The latest FPS report.
     * Should be updated once a second.
     */
    private static int fps = -1;
    
    public static void setGameWindow(GameWindow window) {
        gameWindow = window;
    }
    
    public static void setScreen(Screen screen) {
        Log.info("Switching screens .. (" + (currentScreen != null ? currentScreen.toString() + " -> " + screen.toString().concat(")") : "null -> " + screen.toString().concat(")")));
        long start = System.currentTimeMillis();
        loaded = false;
        
        if(currentScreen != null) {
            Log.info("Disposing old screen ..");
            currentScreen.dispose();
        }
        currentScreen = screen;
        currentScreen.create(gameWindow);
        gameWindow.registerInputHandlers(screen.getKeyAdapter(), screen.getMouseAdapter(), screen.getMouseWheelListener());
        loaded = true;
        
        Log.info("Screens switched! Took " + (System.currentTimeMillis()-start) + "ms");
    }
    
    public static Screen getCurrentScreen() {
        return currentScreen;
    }
    
    public static void update(double dt) {
        if(currentScreen != null) {
            if(isLoaded()) {
                getCurrentScreen().update(dt);
            }
        } else {
            Log.error("[ScreenManager] Attempted to update an undefined screen. (null)");
        }
    }
    
    public static void render(Graphics2D g) {
        if(currentScreen != null) {
            if(isLoaded()) {
                getCurrentScreen().render(g);
            }
        } else {
            Log.error("[ScreenManager] Attempted to render an undefined screen. (null)");
        }
    }
    
    public static int getFPS() {
        return fps;
    }
    
    public static void reportFPS(int fps) {
        ScreenManager.fps = fps;
    }
    
    public static boolean isLoaded() {
        return loaded;
    }
}
