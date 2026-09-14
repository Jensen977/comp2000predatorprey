import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Prey extends Creature {
    private boolean inDanger;
    private List<Creature> sim;
    private List<Grass> grassList;

    public Prey(int speed, int hunger,  int x, int y, List<Creature> sim, List<Grass> grassList) {
        super(speed, hunger, false, x, y);
        this.inDanger = false;
        this.sim = sim;
        this.grassList = grassList;
    }

    public boolean isInDanger(){
        return inDanger;
    }

    public void setInDanger(boolean inDanger){
        this.inDanger = inDanger;
    }

    private static final int REPRODUCE_THRESHOLD = 20;
    
    @Override
    public void reproduce() {
        if (sim == null){
            throw new IllegalStateException("Cannot reproduce: no simulation list assigned");
        }
        if (getStarvation() <= REPRODUCE_THRESHOLD) {
            Prey offspring = new Prey(getSpeed(), 0, getX(), getY(), sim, grassList);
            sim.add(offspring);
        }
    }



    private static final int GRAZE_THRESHOLD = 60;

    @Override 
    public void movement() {

        if (inDanger){
            return;
        }

        if (getStarvation() < GRAZE_THRESHOLD) {
            return;
        }

        List<Grass> edibleGrass = new ArrayList<>();
        for (Grass g : grassList){
            if (g.isEdible()) {
                edibleGrass.add(g);
            }
        }

        Optional<Grass> target = findClosest(edibleGrass);
        target.ifPresent(grass -> {
            int dx = Integer.compare(grass.getX(), getX());
            int dy = Integer.compare(grass.getY(), getY());
            setX(getX() + dx * getSpeed());
            setY(getY() + dy * getSpeed());
        });

    } 

    @Override
    protected int getStarvationRate(){
        return 1; //Prey conserve energy more effectively 

    }

    private static final int EAT_RADIUS = 10;

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
            double dist = Math.hypot(getX() - grass.getX(), getY() - grass.getY() );
            if (dist <= EAT_RADIUS){
                grass.setEaten(true);
                resetStarvation();
            }
        });
    }


}
