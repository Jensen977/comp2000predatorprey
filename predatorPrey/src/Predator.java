import java.util.List;



public class Predator extends Creature {
    private List<Creature> sim;

    public Predator(int speed, int hunger, int x, int y, List<Creature> sim) {
        super(speed, hunger, false, x, y);
        this.sim = sim;
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