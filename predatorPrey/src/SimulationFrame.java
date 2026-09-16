import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class SimulationFrame extends JFrame{
    
    private final Simulation simulation;
    private final SimulationPanel simulationPanel; 

    private final JLabel statisticsLabel; 

    public SimulationFrame() {
        super("Predator-Prey Simulation");

        simulation = new Simulation(); 
        simulationPanel = new SimulationPanel(simulation); 
        statisticsLabel = new JLabel(); 

    }

    private JPanel createControlPanel() {

    }

    public void startSimulation() {

    }

    public void pauseSimulation() {

    }

    public void updateSimulation() {
        simulation.update();
        simulationPanel.repaint();
        updateLabels(); 
    }

    public void updateLabels() {
        statisticsLabel.setText(String.format(
                "Day %d  |  Predators: %d  Prey %d  Grass: %d", 
                simulation.getDay(),
                simulation.getPredatorCount(), 
                simulation.getPreyCount(),
                simulation.getEdibleGrassCount())); 
    }
}
