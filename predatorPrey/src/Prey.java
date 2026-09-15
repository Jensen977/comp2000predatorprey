import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Prey extends Creature {

    private static final double DANGER_DISTANCE = 75.0;
    private static final double EATING_DISTANCE = 15.0;
    private static final int GRAZE_THRESHOLD = 60;
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
        return 1; //Prey conserve energy more effectively 
    }

    public boolean isInDanger(){
        return inDanger;
    }

    public void setInDanger(boolean inDanger){
        this.inDanger = inDanger;
    }

    private Optional<Grass> findClosestEdibleGrass() {
        List<Grass> edibleGrass = new ArrayList<>();

        for (Grass g : grassList){
            if (g.isEdible()) {
                edibleGrass.add(g);
            }
        }
        return findClosest(edibleGrass);
    }

    private Optional<Creature> findClosestPredator() {
        List<Creature> predators = new ArrayList<>();

        for (Creature creature : creatures) {
            if (creature instanceof Predator) {
                predators.add(creature);
            }
        }
        return findClosest(predators);
    }

    @Override 
    public void movement() {
        Optional<Creature> closestPredator = findClosestPredator();

        inDanger = closestPredator.isPresent() 
            && distanceTo(closestPredator.get()) <= DANGER_DISTANCE;

        if (inDanger) {
            moveAwayFrom(closestPredator.get());
            return;
        }

        Optional<Grass> closestGrass = findClosestEdibleGrass();

        if (getStarvation() >= GRAZE_THRESHOLD) {
            if (closestGrass.isPresent()) {
                moveTowards(closestGrass.get());
            } 
            return;
        }

        wander();
    } 


    @Override 
    public void eat(){
        if (getStarvation() < GRAZE_THRESHOLD) {
            return; // Not hungry enough to eat
        }

        Optional<Grass> closestGrass = findClosestEdibleGrass();
        if (closestGrass.isPresent() 
            && distanceTo(closestGrass.get()) <= EATING_DISTANCE) {
            closestGrass.get().consume();
            resetStarvation();
        }
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
