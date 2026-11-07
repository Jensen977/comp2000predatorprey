import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;

import java.util.IntSummaryStatistics;
import java.util.Map;

public class SimulationFrame extends JFrame{
    
    private final Simulation simulation;
    private final SimulationPanel simulationPanel; 
    private final Timer timer;
    private final JLabel statisticsLabel; 
    private final JLabel averageStarvationLabel; 

    private static final int UPDATE_INTERVAL_MS = 40;

    public SimulationFrame() {
        super("Predator-Prey Simulation");

        simulation = new Simulation(); 
        simulationPanel = new SimulationPanel(simulation); 
        timer = new Timer(UPDATE_INTERVAL_MS, event -> updateSimulation());
        statisticsLabel = new JLabel(); 
        averageStarvationLabel = new JLabel(); 

        setLayout(new BorderLayout()); 

        add(simulationPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(averageStarvationLabel, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        updateLabels();
    }

    private JPanel createControlPanel() {
        JPanel controls = new JPanel(); 

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause"); 
        JButton resetButton = new JButton("Reset");

        startButton.addActionListener(event -> timer.start());
        pauseButton.addActionListener(event -> timer.stop());
        resetButton.addActionListener(event -> {
            timer.stop(); 
            simulation.reset();
            simulationPanel.repaint();
            updateLabels();
        });

        controls.add(startButton);
        controls.add(pauseButton);
        controls.add(resetButton);
        controls.add(statisticsLabel); 

        return controls;
    }

    public void updateSimulation() {
        simulation.update();
        simulationPanel.repaint();
        updateLabels(); 
    }

    public void updateLabels() {
        

        Map<String, IntSummaryStatistics> statistics = simulation.getCreatureStatistics(); 

        IntSummaryStatistics predatorStat = statistics.get("Predator"); 
        IntSummaryStatistics preyStat = statistics.get("Prey"); 

        long predatorCount = predatorStat == null ? 0 : predatorStat.getCount();
        long preyCount = preyStat == null ? 0 : preyStat.getCount();

        String predatorAverage = predatorStat == null 
            ? "N/A" 
            : String.format("%.1f", predatorStat.getAverage());

        String preyAverage = preyStat == null 
            ? "N/A" 
            : String.format("%.1f", preyStat.getAverage());

        statisticsLabel.setText(String.format(
            "Day %d  |  Predators: %d  Prey %d  Grass: %d", 
            simulation.getDay(),
            predatorCount, 
            preyCount,
            simulation.getEdibleGrassCount())); 

        averageStarvationLabel.setText(String.format(
            "AverageStarvation  |  Predators: %s  Preys: %s", 
            predatorAverage, 
            preyAverage
        ));
    }
}
