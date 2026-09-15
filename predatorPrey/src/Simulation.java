import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Simulation {
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 600;

    private static final int INITIAL_PREDATORS = 5;
    private static final int INITIAL_PREY = 20;
    private static final int INITIAL_GRASS = 100;
    private static final int TICKS_PER_DAY = 25;

    private int tick;
    private int day;

    private final List<Creature> creatures;
    private final List<Grass> grassList;
    private final Random random; 

    public Simulation() {
        random = new Random();
        reset();
    }

    private int randomX() {
        return random.nextInt(WORLD_WIDTH);
    }

    private int randomY() {
        return random.nextInt(WORLD_HEIGHT);
    }

    public void reset() {
        creatures.clear();
        grassList.clear();

        tick = 0;
        day = 0;

        for (int i = 0; i < INITIAL_PREDATORS; i++) {
            creatures.add(new Predator(
                random.nextInt(5) + 1, // speed
                0,
                randomX(),
                randomY(),
                creatures));
        }

        for (int i = 0; i < INITIAL_PREY; i++) {
            creatures.add(new Prey(
                random.nextInt(3) + 1, // speed
                0,
                randomX(),
                randomY(),
                creatures, 
                grassList));
        }

        for (int i = 0; i < INITIAL_GRASS; i++) {
            grassList.add(new Grass(randomX(), randomY(), 0));
        }
    }

    public int getTick() {
        return tick;
    }   

    public int getDay() {
        return day;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public List<Grass> getGrassList() {
        return grassList;
    }

    public int getPredatorCount() {
        int count = 0;
        for (Creature creature : creatures) {
            if (creature instanceof Predator) {
                count++;
            }
        }
        return count;
    }

    public int getPreyCount() {
        int count = 0;
        for (Creature creature : creatures) {
            if (creature instanceof Prey) {
                count++;
            }
        }
        return count;
    }

    public int getEdibleGrassCount() {
        int count = 0;
        for (Grass grass : grassList) {
            if (grass.isEdible()) {
                count++;
            }
        }
        return count;
    }

    
}