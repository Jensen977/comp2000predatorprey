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



    public void reset() {
        creatures.clear();
        grassList.clear();

        tick = 0;
        day = 0;

        for (int i = 0; i < INITIAL_PREDATORS; i++) {
            creatures.add(new Predator(
                random.nextInt(5) + 1, // speed
                0,
                random.nextInt(WORLD_WIDTH), // x
                random.nextInt(WORLD_HEIGHT), // y
                creatures));
        }

        for (int i = 0; i < INITIAL_PREY; i++) {
            creatures.add(new Prey(
                random.nextInt(5) + 1, // speed
                0,
                random.nextInt(WORLD_WIDTH), // x
                random.nextInt(WORLD_HEIGHT), // y
                creatures,
                grassList));
        }

        for (int i = 0; i < INITIAL_GRASS; i++) {
            grassList.add(new Grass(
                random.nextInt(WORLD_WIDTH), // x
                random.nextInt(WORLD_HEIGHT), 0)); // y
        }
    }
}