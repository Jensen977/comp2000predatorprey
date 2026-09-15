public class Grass extends Entity{

    private static final int DEFAULT_REGROWTH_TICKS = 50;

    private int growthTimer;
    private boolean isEaten;

    public Grass (int x, int y, int growthTimer) {
        super(x, y, true);
        this.growthTimer = Math.max(0, growthTimer);
        this.isEaten = growthTimer > 0; // If growthTimer is greater than 0, it means the grass has been eaten and is regrowing
    }

    public int getGrowthTimer(){
        return growthTimer;
    }

    public void setGrowthTimer(int growthTimer){
        this.growthTimer = Math.max(0, growthTimer);
        this.isEaten = growthTimer > 0; // If growthTimer is greater than 0, it means the grass has been eaten and is regrowing
    }

    public boolean isEaten(){
        return isEaten;
    }

    public void setEaten(boolean isEaten){
        this.isEaten = isEaten;
        growthTimer = isEaten ? DEFAULT_REGROWTH_TICKS : 0; // Reset growth timer if eaten
    }

    public boolean isEdible(){
        return !isEaten;
    }

    public void tick(){
        if (!isEaten){
            return;
        }

        growthTimer--;
        if (growthTimer <= 0){
            isEaten = false; // Grass has regrown
        }
    
    }
}