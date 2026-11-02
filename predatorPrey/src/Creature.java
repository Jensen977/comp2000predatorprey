import java.util.List;
import java.util.Optional;
import java.util.Comparator;

public abstract class Creature extends Entity {

    private static final int STARVATION_THRESHOLD = 100;

    private int speed; 
    private int starvation;
    private MoveStrategy moveStrategy; 

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
        return targets.stream()
            .filter(target -> target != this)
            .min(Comparator.comparingDouble(this::distanceTo));
    }

    protected void moveByDirection(int dx, int dy){
        setX(getX() + dx * speed);
        setY(getY() + dy * speed);
    }

    protected void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy; 
    }

    protected void executeMovement() {
        moveStrategy.move(this); 
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

    protected int mutatedSpeed(){
        int change = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        return Math.max(1, speed + change); // Ensure speed is at least 1
    }
    
}