package td;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import td.assets.ImageCache;
import td.data.Colors;
import td.maps.MapManager;
import td.screens.MenuScreen;
import td.screens.ScreenManager;
import td.util.Debug;
import td.util.Input;
import td.util.Log;

public class GameWindow {
    protected final JFrame frame;
    protected final GamePanel panel;
    protected final Input input;
    protected KeyAdapter keyAdapter = null;
    protected MouseAdapter mouseAdapter = null;
    protected MouseWheelListener mouseWheelListener = null;
    
    public GameWindow(String title) {
        this.frame = new JFrame(title + " - WIP");
        this.panel = new GamePanel();
        this.input = new Input(panel);
    }
    
    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupPanel();
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        ImageCache.load();
        handleMaps();
        ScreenManager.setGameWindow(this);
        ScreenManager.setScreen(new MenuScreen());
        panel.startGameLoop();
        loadGlobalInputHandlers();
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Log.info("Game exiting ..");
            }
        });
    }
    
    private void handleMaps() {
        MapManager.loadRowsAndColumns();
        MapManager.loadDefaultMaps();
        MapManager.setMap(MapManager.getDefaultMap(), true);
    }
    
    private void setupPanel() {
        panel.setPreferredSize(new Dimension(Configuration.GAME_WIDTH, Configuration.GAME_HEIGHT));
        panel.setFocusable(true);
        panel.setBackground(Colors.BACKGROUND);
    }
    
    private void loadGlobalInputHandlers() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_F3) {
                    Debug.ENABLED = !Debug.ENABLED;
                }
            }
        });
    }
    
    public Input getInput() {
        return input;
    }
    
    public int getFPS() {
        return ScreenManager.getFPS();
    }
    
    public int getWidth() {
        return panel.getWidth();
    }
    
    public int getHeight() {
        return panel.getHeight();
    }
    
    public GamePanel getPanel() {
        return panel;
    }
    
    public void disposeInputHandlers() {
        panel.removeKeyListener(keyAdapter);
        panel.removeMouseListener(mouseAdapter);
        panel.removeMouseWheelListener(mouseWheelListener);
        keyAdapter = null;
        mouseAdapter = null;
        mouseWheelListener = null;
    }
    
    public void registerInputHandlers(KeyAdapter keyAdapter, MouseAdapter mouseAdapter, MouseWheelListener mouseWheelListener) {
        this.keyAdapter = keyAdapter;
        this.mouseAdapter = mouseAdapter;
        this.mouseWheelListener = mouseWheelListener;
        panel.addKeyListener(keyAdapter);
        panel.addMouseListener(mouseAdapter);
        panel.addMouseWheelListener(mouseWheelListener);
    }
}
