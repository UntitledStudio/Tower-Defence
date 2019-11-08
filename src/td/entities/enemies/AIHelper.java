package td.entities.enemies;

import td.Configuration;
import td.data.Block;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.towers.Tower;
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
    private boolean canDamagePlayer = false;
    private boolean lastPathCalculated = false;
    
    public AIHelper(EnemyUnit unit) {
        this.unit = unit;
    }
    
    public void spawn() {
        Block spawnBlock = MapManager.getCurrentMap().getPathData().getSpawnPath();
        
        if(spawnBlock.getX() != spawnBlock.getNextPath().getX()) {
            if(spawnBlock.getX() <= 0) {
                direction = Direction.RIGHT;
                unit.setX(-Configuration.ENEMY_SPAWN_OFFSET);
            } else {
                direction = Direction.LEFT;
                unit.setX(spawnBlock.getTexture().getHitbox().getRightX() + Configuration.ENEMY_SPAWN_OFFSET);
            }
            destination = spawnBlock.getX() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getWidth() / 2);
            unit.setY(spawnBlock.getY() + Util.centerValue(unit.getTexture().getHeight(), Configuration.BLOCK_SIZE));
        } else {
            if(spawnBlock.getY() <= 0) {
                direction = Direction.UP;
                unit.setY(-Configuration.ENEMY_SPAWN_OFFSET);
            } else {
                direction = Direction.DOWN;
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
                case RIGHT: {
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
                case LEFT: {
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
                case DOWN: {
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
                case UP: {
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
            
            // Once the enemy unit has reached it's spawn block, we can begin to check if the unit is outside the map. Because if it is, then we need to damage the player.
            if(!canDamagePlayer && Util.isWithinArea(unit.getX(), unit.getY(), MapManager.getCurrentMap().getPathData().getSpawnPath().getTexture())) {
                canDamagePlayer = true;
            }
            
            if(canDamagePlayer && !Util.isWithinArea(unit.getX(), unit.getY(), 0, 0, Configuration.GAME_WIDTH, Configuration.GAME_HEIGHT)) {
                if(PlayScreen.instance != null) {
                    unit.despawn();
                    PlayScreen.instance.getPlayer().damage(1);
                }
            }
        }
    }
    
    public void calcDestAndDir() {
       if(lastPathCalculated) {
           switch(direction) {
               case UP: destination = -Configuration.BLOCK_SIZE; break;
               case DOWN: destination = PlayScreen.instance.getGameWindow().getHeight() + Configuration.BLOCK_SIZE; break;
               case LEFT: destination = -Configuration.BLOCK_SIZE; break;
               case RIGHT: destination = PlayScreen.instance.getGameWindow().getWidth() + Configuration.BLOCK_SIZE; break;
           }
           return;
       }
       Block currentPath = targetPath;
       targetPath = targetPath.getNextPath();
       
       if(targetPath.getX() != currentPath.getX()) {
           if(targetPath.getX() > currentPath.getX()) {
               direction = Direction.RIGHT;
           } else {
               direction = Direction.LEFT;
           }
           destination = targetPath.getX() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getWidth() / 2);
       } else {
           if(targetPath.getY() > currentPath.getY()) {
               direction = Direction.DOWN;
           } else {
               direction = Direction.UP;
           }
           destination = targetPath.getY() + ((Configuration.BLOCK_SIZE / 2) - unit.getHitbox().getHeight() / 2);
       }
       
       // Check to see if the next path after the freshly calculated path is the last path ID. Flag it if it is.
       if(targetPath.getPathID() >= MapManager.getCurrentMap().getPathData().getDestinationPath().getPathID()) {
           lastPathCalculated = true;
           Log.info("[AIHelper] Last path calculated!");
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
