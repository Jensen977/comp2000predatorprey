import java.util.List;
import java.util.ArrayList;
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
    
    public void movement() {

    } 

    @Override
    protected int getStarvationRate(){
        return 1; //Prey conserve energy more effectively 
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


}
