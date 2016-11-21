package td.maps;

import java.awt.Graphics2D;
import java.util.List;
import td.data.Block;
import td.screens.PlayScreen;

public interface Map {
    public void buildMap();
    public void update(double dt);
    public void render(Graphics2D g, PlayScreen screen);
    public List<Block> getBlocks();
    public Block getBlockAt(int x, int y);
}
