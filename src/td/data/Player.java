package td.data;

public class Player {
    private int maxHealth = 0;
    private final int startCash;
    
    public Player(int maxHealth, int startCash) {
        this.maxHealth = maxHealth;
        this.startCash = startCash;
        
        currentCash = startCash;
        currentHealth = maxHealth;
        currentWave = 0;
    }
    private int currentCash = 0;
    private int currentWave = 0;
    private int currentHealth = 0;
    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getStartingCash() {
        return startCash;
    }
    
    public int getHealth() {
        return currentHealth;
    }
    
    public void setHealth(int health) {
        this.currentHealth = health;
    }
    
    public void damage(int amount) {
        setHealth(currentHealth -= amount);
    }
    
    public void heal(int amount) {
        setHealth(currentHealth += amount);
    }
    
    public void setWave(int wave) {
        this.currentCash = wave;
    }
    
    public void increaseWave() {
        setWave(currentWave++);
    }
    
    public int getCash() {
        return currentCash;
    }
    
    public void setCash(int cash) {
        this.currentCash = cash;
    }
    
    public void addCash(int amount) {
        setCash(currentCash += amount);
    }
    
    public void takeCash(int amount) {
        setCash(currentCash -= amount);
    }
    
    public int getWave() {
        return currentWave;
    }
}
