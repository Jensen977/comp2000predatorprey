import java.util.List;

public class Prey extends Creature {
    private boolean inDanger;
    private List<Creature> sim;

    public Prey(int speed, int hunger,  int x, int y, List<Creature> sim) {
        super(speed, hunger, false, x, y);
        this.inDanger = false;
        this.sim = sim;
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
        resetStarvation();
    }


}
