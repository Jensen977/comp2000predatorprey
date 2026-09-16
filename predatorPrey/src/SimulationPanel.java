import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.Dimension;
import java.awt.Color;

public class SimulationPanel extends JPanel {

    private static final Color PREDATOR_COLOR = Color.ORANGE;
    private static final Color PREY_COLOR = Color.WHITE;
    private static final Color DANGER_COLOR = Color.LIGHT_GRAY;
    private static final Color GRASS_COLOR = Color.GREEN;

    private static final Color FIELD_COLOR = new Color(210, 180, 140);

    private final Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        this.simulation = simulation;
        setPreferredSize(new Dimension(900, 600));
        setBackground(FIELD_COLOR);
    }

    @Override 
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        Graphics2D g2d = (Graphics2D) graphics.create(); 
        
        double scaleX = getWidth() / (double) Simulation.WORLD_WIDTH;
        double scaleY = getHeight() / (double) Simulation.WORLD_HEIGHT; 

        drawCreature(g2d, scaleX, scaleY);
        drawGrass(g2d, scaleX, scaleY); 
        drawLegend(g2d); 
    }
    
    private int toScreenX(int worldX, double scaleX) {
        return (int) Math.round(worldX * scaleX); // Implement scaling if needed
    }

    private int toScreenY(int worldY, double scaleY) {
        return (int) Math.round(worldY * scaleY); // Implement scaling if needed
    }

    private void drawPredator(Graphics2D g2d, int x, int y) {
        Path2D Fox = new Path2D.Double();

        Fox.moveTo(x - 12, y - 10);
        Fox.lineTo(x - 6, y - 22);
        Fox.lineTo(x, y - 10); //Left ear

        Fox.lineTo(x + 6, y - 22);
        Fox.lineTo(x + 12, y - 10); //Right ear

        Fox.lineTo(x + 10, y + 8); //Right cheek

        Fox.lineTo(x, y + 14); //Chin

        Fox.lineTo(x - 10, y + 8); //Left cheek

        Fox.closePath();

        g2d.setColor(PREDATOR_COLOR);
        g2d.fill(Fox);
    }

    private void drawPrey(Graphics2D g2d, Prey prey, int x, int y) {
        Path2D Rabbit = new Path2D.Double();

        Rabbit.moveTo(x - 10, y - 10);
        Rabbit.lineTo(x - 10, y - 26);
        Rabbit.lineTo(x - 4, y - 26);
        Rabbit.lineTo(x - 4, y - 10); //Left ear

        Rabbit.lineTo(x + 4, y - 10); //Top of head

        Rabbit.lineTo(x + 4, y - 26);
        Rabbit.lineTo(x + 10, y - 26);
        Rabbit.lineTo(x + 10, y - 10); //Right ear

        Rabbit.lineTo(x + 10, y + 8); //Right cheek

        Rabbit.lineTo(x, y + 10); //Chin

        Rabbit.lineTo(x - 10, y + 8); //Left cheek

        Rabbit.closePath();

        g2d.setColor(prey.isInDanger() ? DANGER_COLOR : PREY_COLOR);
        g2d.fill(Rabbit);
    }

    private void drawCreature(Graphics2D g2d, double scaleX, double scaleY) {
        for (Creature creature : simulation.getCreatures()) {
            int screenX = toScreenX(creature.getX(), scaleX);
            int screenY = toScreenY(creature.getY(), scaleY);

            if (creature instanceof Predator) {
                drawPredator(g2d, screenX, screenY);
            } else if (creature instanceof Prey) {
                drawPrey(g2d, (Prey) creature, screenX, screenY);
            }
        }
    }

    private void drawGrass(Graphics2D g2d, double scaleX, double scaleY) {
        g2d.setColor(GRASS_COLOR);
        for (Grass grass : simulation.getGrassList()) {
            if (grass.isEdible()) { // Only draw edible grass
                int screenX = toScreenX(grass.getX(), scaleX);
                int screenY = toScreenY(grass.getY(), scaleY);
                g2d.fillOval(screenX - 3, screenY - 3, 6, 6);
            }
        }
    }

    private void drawLegend(Graphics2D g2d) {
        g2d.setColor(new Color(250, 240, 210)); 
        g2d.fillRoundRect(10, 10, 380, 40, 10, 10); 

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(10, 10, 380, 40, 10, 10);
        
        g2d.setColor(PREDATOR_COLOR);
        g2d.fillOval(20, 20, 10, 10);

        g2d.setColor(PREY_COLOR); 
        g2d.fillOval(110, 20, 10, 10); 

        g2d.setColor(DANGER_COLOR);
        g2d.fillOval(180, 20, 10, 10); 

        g2d.setColor(GRASS_COLOR); 
        g2d.fillOval(300, 20, 10, 10); 

        g2d.setColor(Color.DARK_GRAY); 
        g2d.drawString("Predator", 35, 30); 
        g2d.drawString("Prey", 125, 30); 
        g2d.drawString("Fleeing Prey", 195, 30); 
        g2d.drawString("Grass", 315, 30); 
    }
}
