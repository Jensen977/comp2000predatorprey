import java.util.stream.Stream;
import java.util.Optional;
import java.util.Comparator;

public abstract class Creature extends Entity {

    private static final int STARVATION_THRESHOLD = 100;

    private int speed; 
    private int starvation;
    private MoveStrategy moveStrategy; 

    private int consecutiveMovingTicks;
    private int speedBoostTicks;

    private final SpeedEffect speedEffect = new SpeedBoost(
        new FatigueSlowDown(
            new StarvationSlowDown((creature, baseSpeed) -> baseSpeed)
        )
    );

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

    public <E extends Entity> Optional<E> findClosest(Stream<E> targets){
        return targets
            .filter(target -> target != this)
            .min(Comparator.comparingDouble(this::distanceTo));
    }

    public int getEffectiveSpeed() {
        return speedEffect.apply(this, speed);
    }

    public int getConsecutiveMovingTicks() {
        return consecutiveMovingTicks;
    }

    public boolean hasSpeedBoost() {
        return speedBoostTicks > 0;
    }

    public void activateSpeedBoost(int durationTicks) {
        if (durationTicks > 0) {
            speedBoostTicks = Math.max(speedBoostTicks, durationTicks);
        }
    }

    protected void moveByDirection(int dx, int dy){
        int effectiveSpeed = getEffectiveSpeed();

        setX(getX() + dx * effectiveSpeed);
        setY(getY() + dy * effectiveSpeed);

        //Standing still resets the consecutive moving ticks counter
        if (dx == 0 && dy == 0) {
            consecutiveMovingTicks = 0;
        } else {
            consecutiveMovingTicks++;
        }
    }

    protected void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy; 
    }

    protected void executeMovement() {
        moveStrategy.move(this); 
        //Count down after movement so the tick receives the boost
        if (speedBoostTicks > 0) {
            speedBoostTicks--;
        }
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