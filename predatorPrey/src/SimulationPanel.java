import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.Color;

public class SimulationPanel extends JPanel {

    private static final Color PREDATOR_COLOR = Color.ORANGE;
    private static final Color PREY_COLOR = Color.WHITE;
    private static final Color GRASS_COLOR = Color.GREEN;

    private final Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override 
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        // Here you would add code to draw the simulation state
        // For example, you could iterate over the creatures and grass and draw them
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

    private void drawPrey(Graphics2D g2d, int x, int y) {
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

        g2d.setColor(PREY_COLOR);
        g2d.fill(Rabbit);
    }

    private void drawCreature() {
        // Implement drawing logic for generic creatures
    }

    private void drawGrass() {
        // Implement drawing logic for grass
    }


}
