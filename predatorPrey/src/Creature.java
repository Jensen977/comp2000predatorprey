import java.util.List;

public abstract class Creature extends Entity {

    private int speed; 
    private int starvation;

    private static final int STARVATION_THRESHOLD = 100;

    public Creature(int speed, int starvation, boolean isFood, int x, int y) {
        super(x, y, isFood);
        this.speed = speed;
        this.starvation = starvation;
    }

    protected abstract int getStarvationRate();

    public boolean starve(){
        starvation += getStarvationRate();
        return starvation >= STARVATION_THRESHOLD;
    }

    public void resetStarvation(){
        starvation = 0;
    }

    public abstract void movement();
    public abstract void eat();
    public abstract void reproduce();

    public <E extends Entity> E findClosest(List<E> targets){
        E closest = null;
        double minDist = Double.MAX_VALUE;

        for (E target : targets){
            double dist = Math.hypot(getX() - target.getX(), getY() - target.getY());
            if (dist < minDist) {
                minDist = dist;
                closest = target;
            }
        }
        return closest;
    } 

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getStarvation(){
        return starvation;
    }

    
    
}