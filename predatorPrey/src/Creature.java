import java.util.List;
import java.util.Optional;

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

        public int getStarvation(){
        return starvation;
    }

    public abstract void movement();

    public abstract void eat();

    public abstract void reproduce();

    public <E extends Entity> Optional<E> findClosest(List<E> targets){
        E closest = null;
        double minDist = Double.MAX_VALUE;

        for (E target : targets){
            if (target == this){ // Skip self
                continue; 
            }
            double dist = distanceTo(target);
            if (dist < minDist) {
                minDist = dist;
                closest = target;
            }
        }

        return Optional.ofNullable(closest);
    }

    private void moveByDirection(int dx, int dy){
        setX(getX() + dx * speed);
        setY(getY() + dy * speed);
    }

    protected void moveTowards(Entity target){
        moveByDirection(
            Integer.compare(target.getX(), getX()), 
            Integer.compare(target.getY(), getY()));
    }

    protected void moveAwayFrom(Entity target){
        moveByDirection(
            Integer.compare(getX(), target.getX()), 
            Integer.compare(getY(), target.getY()));
    }

    protected void wander(){
        int dx = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        int dy = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        moveByDirection(dx, dy);
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        if (speed < 1) {
            throw new IllegalArgumentException("Speed must be at least 1");
        }
        this.speed = speed;
    }
    
}