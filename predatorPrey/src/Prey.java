import java.util.List;
import java.util.Optional;

public class Prey extends Creature {

    private static final double DANGER_DISTANCE = 75.0;
    private static final double EATING_DISTANCE = 15.0;
    private static final int GRAZE_THRESHOLD = 20;
    private static final int REPRODUCE_THRESHOLD = 20;    

    private boolean inDanger;
    private List<Creature> creatures;
    private List<Grass> grassList;

    public Prey(int speed, int hunger,  int x, int y, 
            List<Creature> creatures, List<Grass> grassList) {
        super(speed, hunger, false, x, y);
        if (creatures == null || grassList == null) {
            throw new IllegalArgumentException("Creatures and grass lists cannot be null");
        }
        this.creatures = creatures;
        this.grassList = grassList;
    }

    @Override
    protected int getStarvationRate(){
        return 5; //Prey conserve energy less effectively 
    }

    public boolean isInDanger(){
        return inDanger;
    }

    public void setInDanger(boolean inDanger){
        this.inDanger = inDanger;
    }

    private Optional<Grass> findClosestEdibleGrass() {
        return findClosest(
            grassList.stream()
                .filter(Grass::isEdible)
        );
    }

    private Optional<Creature> findClosestPredator() {
        return findClosest(
            creatures.stream()
                .filter(creature -> creature instanceof Predator)
        );
    }

    @Override 
    public void movement() {
        Optional<Creature> closestPredator = findClosestPredator();

        boolean wasInDanger = inDanger;
        inDanger = closestPredator.isPresent() 
            && distanceTo(closestPredator.get()) <= DANGER_DISTANCE;

        // A newly detected predator triggers an escape burst for five ticks
        if (inDanger && !wasInDanger) {
            activateSpeedBoost(5);
        }
            
        if (inDanger) {
            setMoveStrategy(MoveStrategy.moveAwayFrom(closestPredator.get()));
        
        } else if (getStarvation() >= GRAZE_THRESHOLD) {
            Optional<Grass> closestGrass = findClosestEdibleGrass();

            if (closestGrass.isPresent()) {
                setMoveStrategy(MoveStrategy.moveTowards(closestGrass.get()));
            } else {
                setMoveStrategy(MoveStrategy.WANDER);
            }

        } else {
            setMoveStrategy(MoveStrategy.WANDER);
        }

        executeMovement();
    } 

    @Override 
    public void eat(){
        if (getStarvation() < GRAZE_THRESHOLD) {
            return; // Not hungry enough to eat
        }

        findClosestEdibleGrass()
            .filter(grass -> distanceTo(grass) <= EATING_DISTANCE)
            .ifPresent(grass -> {
                grass.consume();
                resetStarvation();
            }); 
    }

    @Override
    public void reproduce() {
        if (getStarvation() <= REPRODUCE_THRESHOLD) {
            Prey offspring = new Prey(
                mutatedSpeed(), 
                0, 
                getX() + (int)(Math.random() * 21) - 10, 
                getY() + (int)(Math.random() * 21) - 10, 
                creatures, 
                grassList);
                
            creatures.add(offspring);
        }
    }
}
