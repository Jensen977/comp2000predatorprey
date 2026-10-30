public interface MoveStrategy {
    void move(Creature creature);
}

// Random movement
class WanderStrategy implements MoveStrategy {
    @Override
    public void move(Creature creature) {
        int dx = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        int dy = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        creature.moveByDirection(dx, dy);
    }
}

// Move towards a target
class MoveTowardsStrategy implements MoveStrategy {
    private final Entity target;

    public MoveTowardsStrategy(Entity target) {
        this.target = target;
    }

    @Override
    public void move(Creature creature) {
        creature.moveByDirection(
            Integer.compare(target.getX(), creature.getX()), 
            Integer.compare(target.getY(), creature.getY()));
    }
}

// Move away from a target
class MoveAwayFromStrategy implements MoveStrategy {
    private final Entity target;

    public MoveAwayFromStrategy(Entity target) {
        this.target = target;
    }

    @Override
    public void move(Creature creature) {
        creature.moveByDirection(
            Integer.compare(creature.getX(), target.getX()), 
            Integer.compare(creature.getY(), target.getY()));
    }
}