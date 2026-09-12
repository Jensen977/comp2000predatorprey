public class Predator extends Creature {
    
    public Predator(int speed, int hunger, int x, int y) {
        super(speed, hunger, false, x, y);
    }

    @Override 
    protected int getStarvationRate(){
        return 3; //Predators require more energy to hunt so starvation rate is higher
    }

    @Override 
    public void eat() {
        resetStarvation();
    }
}