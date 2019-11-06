package td.util;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import td.assets.Texture;

public class Util {
    public static void applyDesign() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setMainIcon(JFrame frame) {
        //frame.setIconImage(Images.ICON_MAIN.getImage());
    }
    
    public static String cleanNumber(int number) {
        return NumberFormat.getIntegerInstance(Locale.US).format(number);
    }
    
    public static String cleanNumber(long number) {
        return NumberFormat.getIntegerInstance(Locale.US).format(number);
    }
    
    public static String cleanNumber(double number) {
        return NumberFormat.getIntegerInstance(Locale.US).format(number);
    }
    
    public static Process runFile(File directory, File file) throws IOException {
        Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + directory + File.separator + file.getName());
        return p;
    }
    
    public static Process runFile(File directory, String file) throws IOException {
        Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + directory + File.separator + file);
        return p;
    }
    
    public static List<String> readFile(File file) {
        try {
            return Files.readAllLines(Paths.get(file.toURI()), Charset.forName("UTF-8"));
        } catch (IOException ex) {
            System.err.println("Error reading file: " + file);
            ex.printStackTrace();
            return Arrays.asList(new String[]{"Error reading file: " + file});
        }
    }
    
    public static void writeFile(File file, List<String> lines) {
        if(!file.exists()) {
            System.out.println("Error while trying to write to a file. (File does not exist)");
            return;
        }
        
        try {
            Files.write(Paths.get(file.toURI()), lines, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> getLines(String text) {
        return new ArrayList<>(Arrays.asList(text.split("\n")));
    }
    
    public static String buildString(List<String> list) {
        String result = "";
        result = list.stream().map((s) -> s + "\n").reduce(result, String::concat);
        return result;
    }
    
    public static void doNoEffectClick(JButton button) {
        for(ActionListener a : button.getActionListeners()) {
            a.actionPerformed(null);
        }
    }
    
    public static void exitWindow(JFrame window) {
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
    
    public static int centerValue(int shape_value, int comparison) {
        return (comparison / 2) - (shape_value / 2);
    }
    
    public static int centerStringX(String s, int comparison_width, Graphics g, int x) {
        return x + Util.centerValue(Util.getStringWidth(s, g), comparison_width);
    }
    
    public static int centerStringY(String s, int comparison_height, Graphics2D g, int y) {
        FontRenderContext frc = g.getFontRenderContext();
        GlyphVector gv = g.getFont().createGlyphVector(frc, s);
        Rectangle2D box = gv.getVisualBounds();
        return y + (int)(((comparison_height - box.getHeight()) / 2d) + (-box.getY()));
    }
    
    public static void centerWindow(JFrame window) {
        int x = centerValue(window.getWidth(), window.getToolkit().getScreenSize().width);
        int y = centerValue(window.getHeight(), window.getToolkit().getScreenSize().height);
        window.setLocation(x, y);
    }
    
    public static boolean isWithinArea(int x, int y, int areaX, int areaY, int width, int height) {
        if(x >= areaX && x <= (areaX + width)) {
            if(y >= areaY && y <= (areaY + height)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isWithinArea(int x, int y, Rectangle rect) {
        return isWithinArea(x, y, rect.x, rect.y, rect.width, rect.height);
    }
    
    public static boolean isWithinArea(int x, int y, Hitbox box) {
        return isWithinArea(x, y, box.getX(), box.getY(), box.getWidth(), box.getHeight());
    }
    
    public static boolean isWithinArea(int x, int y, Texture texture) {
        return isWithinArea(x, y, texture.getHitbox());
    }
    
    public static boolean isWithinArea(Input input, Hitbox box) {
        return isWithinArea(input.getMouseX(), input.getMouseY(), box);
    }
    
    public static int getStringWidth(String s, Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        return fm.stringWidth(s);
    }
    
    public static int getStringWidth(String s, Graphics g) {
        return g.getFontMetrics().stringWidth(s);
    }
    
    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    
    public static int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
    
    public static int getBottomY(int object_y,  int object_height) {
        return object_y + object_height;
    }
    
    public static boolean isBrowsingSupported() {
        if(!Desktop.isDesktopSupported()) {
            return false;
        }
        return Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
    }
    
    public static void playSound(InputStream resource) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean hasConnection() {
        try {
            URL google = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection)google.openConnection();
            Object data = connection.getContent();
        } catch(IOException e) {
            return false;
        }
        return true;
    }
    
    public static String cleanDouble(double d, int decimals) {
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(decimals);
        nf.setMinimumFractionDigits(decimals);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        
        return nf.format(d).replace(",", ".");
    }
    
    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    
    public static BufferedImage resizeImageHQ(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    
    public static void setCursorVisible(boolean visible, JFrame window) {
        if(visible) {
            window.setCursor(Cursor.getDefaultCursor());
        } else {
            // Cache?
            BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "blank cursor");
            window.setCursor(cursor);
        }
    }
    
    public static Ellipse2D getEllipseFromCenter(double x, double y, double width, double height) {
        double newX = x - width / 2.0;
        double newY = y - height / 2.0;
        Ellipse2D ellipse = new Ellipse2D.Double(newX, newY, width, height);
        return ellipse;
    }
    
    public static double getDistanceBetween(int fromX, int fromY, int toX, int toY) {
        return Math.sqrt(Math.pow((fromX-toX), 2) + Math.pow((fromY-toY), 2));
    }
    
    public static Point getPointBetween(int x1, int y1, int x2, int y2) {
        return getPointBetween(x1, y1, x2, y2, 2);
    }
    
    public static Point getPointBetween(int x1, int y1, int x2, int y2, double divisor) {
        double midX = (x1 + x2) / divisor;
        double midY = (y1 + y2) / divisor;
        return new Point((int)midX, (int)midY);
    }
}
