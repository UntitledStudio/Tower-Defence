package td.entities.enemies;

import java.util.Random;
import td.Configuration;
import td.data.Block;
import td.maps.MapManager;
import td.util.Direction;
import td.util.Log;
import td.util.Util;

public class AIHelper {
    private final EnemyUnit unit;
    private boolean isSpawned = false;
    private int moveSpeed = 3;
    private int slow = 0;
    private long lastMoveUpdate = 0;
    private Direction direction;
    private int destination;
    private Block targetPath = null;
    
    public AIHelper(EnemyUnit unit) {
        this.unit = unit;
    }
    
    public void spawn() {
        Block spawnBlock = MapManager.getCurrentMap().getPathData().getSpawnPath();
        
        if(spawnBlock.getX() != spawnBlock.getNextPath().getX()) {
            if(spawnBlock.getX() <= 0) {
                direction = Direction.EAST;
                unit.setX(-Configuration.ENEMY_SPAWN_OFFSET);
            } else {
                direction = Direction.WEST;
                unit.setX(spawnBlock.getTexture().getHitbox().getRightX() + Configuration.ENEMY_SPAWN_OFFSET);
            }
            destination = spawnBlock.getX() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getWidth() / 2);
            unit.setY(spawnBlock.getY() + Util.centerValue(unit.getTexture().getHeight(), Configuration.BLOCK_SIZE));
        } else {
            if(spawnBlock.getY() <= 0) {
                direction = Direction.SOUTH;
                unit.setY(-Configuration.ENEMY_SPAWN_OFFSET);
            } else {
                direction = Direction.NORTH;
                unit.setY(spawnBlock.getTexture().getHitbox().getBottomY() + Configuration.ENEMY_SPAWN_OFFSET);
            }
            destination = spawnBlock.getY() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getHeight() / 2);
            unit.setX(spawnBlock.getX() + Util.centerValue(unit.getTexture().getWidth(), Configuration.BLOCK_SIZE));
        }
        targetPath = spawnBlock;
        lastMoveUpdate = System.currentTimeMillis();
        isSpawned = true;
        Log.info("[AIHelper] Enemy spawned at " + unit.getX() + "," + unit.getY() + " heading " + direction.name() + " to " + destination);
    }
    
    public void move() {
        if(isSpawned) {
            if(slow > 0) {
                if(System.currentTimeMillis()-lastMoveUpdate < slow) {
                    return;
                }
            }
            int x = unit.getX();
            int y = unit.getY();
            
            switch(direction) {
                case EAST: {
                    // Increasing X
                    x += moveSpeed;
                    lastMoveUpdate = System.currentTimeMillis();
                    
                    if(x >= destination) {
                        x = destination;
                        calcDestAndDir();
                    }
                    unit.setX(x);
                    break;
                }
                case WEST: {
                    // Decreasing X
                    x -= moveSpeed;
                    lastMoveUpdate = System.currentTimeMillis();
                    
                    if(x <= destination) {
                        x = destination;
                        calcDestAndDir();
                    }
                    unit.setX(x);
                    break;
                }
                case NORTH: {
                    // Increasing Y
                    y += moveSpeed;
                    lastMoveUpdate = System.currentTimeMillis();
                    
                    if(y >= destination) {
                        y = destination;
                        calcDestAndDir();
                    }
                    unit.setY(y);
                    break;
                }
                case SOUTH: {
                    // Decreasing Y
                    y -= moveSpeed;
                    lastMoveUpdate = System.currentTimeMillis();
                    
                    if(y <= destination) {
                        y = destination;
                        calcDestAndDir();
                    }
                    unit.setY(y);
                    break;
                }
            }
        }
    }
    
    public void calcDestAndDir() {
       Block currentPath = targetPath;
       targetPath = targetPath.getNextPath();

       if(targetPath.getX() != currentPath.getX()) {
           if(targetPath.getX() > currentPath.getX()) {
               direction = Direction.EAST;
           } else {
               direction = Direction.WEST;
           }
           destination = targetPath.getX() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getWidth() / 2);
       } else {
           if(targetPath.getY() > currentPath.getY()) {
               direction = Direction.NORTH;
           } else {
               direction = Direction.SOUTH;
           }
           destination = targetPath.getY() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getHeight() / 2);
       }
    }
    
    public boolean isSpawned() {
        return isSpawned;
    }
    
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    
    public int getMoveSpeed() {
        return moveSpeed;
    }
    
    public void setSlow(int slow) {
        this.slow = slow;
    }
    
    public int getSlow() {
        return slow;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public int getDestination() {
        return destination;
    }
    
    public Block getTargetPath() {
        return targetPath;
    }
}
