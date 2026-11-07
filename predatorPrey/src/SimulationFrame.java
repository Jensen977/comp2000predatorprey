import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SimulationFrame extends JFrame{
    
    private final Simulation simulation;
    private final SimulationPanel simulationPanel; 
    private final Timer timer;

    private final JLabel statisticsLabel; 
    private final JLabel averageStarvationLabel; 
    private final JLabel mostStarvingLabel;

    private static final int UPDATE_INTERVAL_MS = 40;

    public SimulationFrame() {
        super("Predator-Prey Simulation");

        simulation = new Simulation(); 
        simulationPanel = new SimulationPanel(simulation); 
        timer = new Timer(UPDATE_INTERVAL_MS, event -> updateSimulation());
        
        statisticsLabel = new JLabel(); 
        averageStarvationLabel = new JLabel(); 
        mostStarvingLabel = new JLabel();

        setLayout(new BorderLayout()); 

        // Group the labels in a small floating tatistics panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        averageStarvationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mostStarvingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(statisticsLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(averageStarvationLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(mostStarvingLabel);

        JPanel overlay = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        overlay.setOpaque(false);
        overlay.add(statsPanel);

        simulationPanel.setLayout(new BorderLayout());
        simulationPanel.add(overlay, BorderLayout.NORTH);

        add(simulationPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateLabels();
        pack();
        setLocationRelativeTo(null);
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

        return controls;
    }

    public void updateSimulation() {
        simulation.update();
        simulationPanel.repaint();
        updateLabels(); 
    }

    public void updateLabels() {
        Map<String, IntSummaryStatistics> statistics = simulation.getParallelCreatureStatistics(); 

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
            "<html>Day: %d<br><br><b>Population</b><br>Predators: %d<br>Prey: %d<br>Grass: %d</html>", 
            simulation.getDay(),
            predatorCount, 
            preyCount,
            simulation.getEdibleGrassCount())); 

        averageStarvationLabel.setText(String.format(
            "<html><b>Average Starvation</b><br>Predators: %s<br>Preys: %s</html>", 
            predatorAverage, 
            preyAverage
        ));

        List<String> mostStarving = simulation.getMostStarvingCreatures();
        mostStarvingLabel.setText(
            "<html><b>Highest Starvation:</b><br>" + 
            (mostStarving.isEmpty() 
                ? "N/A" 
                : String.join("<br>", mostStarving)) +
                "</html>"
        );
    }
}
