import java.util.List;


public class Predator extends Creature {

    private static final double CATCH_DISTANCE = 12.0;
    private static final int HUNT_THRESHOLD = 10;
    private static final int REPRODUCE_THRESHOLD = 25;

    private List<Creature> creatures;

    public Predator(int speed, int hunger, int x, int y, List<Creature> creatures) {
        super(speed, hunger, false, x, y);
        if (creatures == null) {
            throw new IllegalArgumentException("Creatures list cannot be null");
        }
        this.creatures = creatures;
    }

    @Override 
    protected int getStarvationRate(){
        return 3; //Predators require more energy to hunt so starvation rate is higher
    }

    @Override 
    public void eat() {
        resetStarvation();

        // TODO: Needs further work
    }

    @Override 
    public void movement() {
        // TODO
    }

    @Override 
    public void reproduce() {
        //TODO
    }
}