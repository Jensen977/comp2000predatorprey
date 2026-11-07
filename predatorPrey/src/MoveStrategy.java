@FunctionalInterface 
public interface MoveStrategy {
    void move(Creature creature);

    static MoveStrategy moveTowards(Entity target) {
        return creature -> {
            creature.moveByDirection(
                Integer.compare(target.getX(), creature.getX()), 
                Integer.compare(target.getY(), creature.getY()));
        };
    }

    static MoveStrategy moveAwayFrom(Entity target) {
        return creature -> {
            creature.moveByDirection(
                Integer.compare(creature.getX(), target.getX()), 
                Integer.compare(creature.getY(), target.getY()));
        };
    }

    MoveStrategy WANDER = creature -> {
        int dx = (int) (Math.random() * 3) - 1; // Random value between -1 and 1
        int dy = (int) (Math.random() * 3) - 1; // Random value between -1 and 1

        creature.moveByDirection(dx, dy);
    }; 
}

