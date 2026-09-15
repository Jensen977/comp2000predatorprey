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
        inDanger = findClosestPredator().isPresent() 
            && distanceTo(findClosestPredator().get()) <= DANGER_DISTANCE;

        if (inDanger) {
            moveAwayFrom(findClosestPredator().get());
            return;
        }

        if (getStarvation() >= GRAZE_THRESHOLD) {
            if (findClosestEdibleGrass().isPresent()) {
                moveTowards(findClosestEdibleGrass().get());
            } 
            return;
        }

        wander();
    } 


    @Override 
    public void eat(){
        List<Grass> edibleGrass = new ArrayList<>();
        for (Grass g : grassList){
            if (g.isEdible()) {
                edibleGrass.add(g);
            }
        }

        Optional<Grass> closest = findClosest(edibleGrass);
        closest.ifPresent(grass -> {
            grass.setEaten(true);
            resetStarvation();
        });
    }

    @Override
    public void reproduce() {
        if (creatures == null){
            throw new IllegalStateException("Cannot reproduce: no simulation list assigned");
        }
        if (getStarvation() <= REPRODUCE_THRESHOLD) {
            Prey offspring = new Prey(getSpeed(), 0, getX(), getY(), creatures, grassList);
            creatures.add(offspring);
        }
    }
}
