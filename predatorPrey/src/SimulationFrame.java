import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
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
    private final JLabel birthsLabel; 
    private final JLabel deathsLabel;

    private static final int UPDATE_INTERVAL_MS = 40;

    public SimulationFrame() {
        super("Predator-Prey Simulation");

        simulation = new Simulation(); 
        simulationPanel = new SimulationPanel(simulation); 
        timer = new Timer(UPDATE_INTERVAL_MS, event -> updateSimulation());
        
        statisticsLabel = new JLabel(); 
        averageStarvationLabel = new JLabel(); 
        mostStarvingLabel = new JLabel();
        birthsLabel = new JLabel();
        deathsLabel = new JLabel();

        setLayout(new BorderLayout()); 

        // Group the labels in a small floating tatistics panel
        JPanel statsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };

        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        averageStarvationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mostStarvingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        birthsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deathsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(statisticsLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(averageStarvationLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(mostStarvingLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(birthsLabel);
        statsPanel.add(Box.createVerticalStrut(12));
        statsPanel.add(deathsLabel);

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

        Map<String, long[]> daily = simulation.getBirthDeathStatistics(simulation.getDay());
        long[] predatorEvents = daily.getOrDefault("Predator", new long[2]);
        long[] preyEvents = daily.getOrDefault("Prey", new long[2]);
        
        birthsLabel.setText(String.format(
            "<html><b>Births (Day %d)</b><br>Predators: %d<br>Prey: %d</html>", 
            simulation.getDay(), 
            predatorEvents[0], 
            preyEvents[0]
        ));

        deathsLabel.setText(String.format(
            "<html><b>Deaths (Day %d)</b><br>Predators: %d<br>Prey: %d</html>", 
            simulation.getDay(), 
            predatorEvents[1], 
            preyEvents[1]
        ));
    }
}
