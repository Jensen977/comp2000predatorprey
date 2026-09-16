import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimulationFrame extends JFrame{
    
    private final Simulation simulation;
    private final SimulationPanel simulationPanel; 

    public SimulationFrame() {
        super("Predator-Prey Simulation");

        simulation = new Simulation(); 
        simulationPanel = new SimulationPanel(simulation); 


    }

    private JPanel createControlPanel() {

    }

    public void startSimulation() {

    }

    public void pauseSimulation() {

    }
}
