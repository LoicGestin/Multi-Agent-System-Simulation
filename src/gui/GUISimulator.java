//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Objects;

public class GUISimulator extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private final SimulationPanel simuPanel;
    private final JButton playPauseButton;
    private final JSpinner speedSpinner;
    private final JSpinner stepSpinner;
    private final JComboBox<String> selectBox;
    private final Timer timer;
    private final int panelWidth;
    private final int panelHeight;
    private int numberTickInStep;
    private Simulable simulator;
    private final boolean warning;

    public GUISimulator(int var1, int var2, Color var3) {
        this(var1, var2, var3, new DefaultSimulator());
    }

    public GUISimulator(int var1, int var2, Color var3, Simulable var4) {
        super("Simulateur de Systèmes Multi-Agents");
        this.numberTickInStep = 1;
        this.warning = false;
        this.timer = new Timer(100, new TimerListener());
        this.timer.stop();
        this.setSimulable(var4);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException |
                 IllegalAccessException ignored) {
        }

        this.simuPanel = new SimulationPanel(var1, var2, var3);
        JScrollPane sp = new JScrollPane(this.simuPanel);
        sp.setPreferredSize(new Dimension(Math.min(800, var1), Math.min(600, var2)));
        this.panelWidth = var1;
        this.panelHeight = var2;
        this.simuPanel.setBackground(var3);
        JPanel var5 = new JPanel(new GridLayout(2, 2));
        JLabel speedLabel = new JLabel("Tps entre 2 affichages (ms) :");
        this.speedSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        var5.add(speedLabel);
        var5.add(this.speedSpinner);
        JLabel stepLabel = new JLabel("Nb de pas simulés entre 2 affichages :");
        this.stepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 10));
        var5.add(stepLabel);
        var5.add(this.stepSpinner);
        this.selectBox = new JComboBox<>();
        this.selectBox.addItemListener((var1x) -> var4.selectedItem((String) var1x.getItem()));
        JButton restartButton = new JButton("Début");
        this.playPauseButton = new JButton("Lecture");
        JButton nextButton = new JButton("Suivant");
        JButton exitButton = new JButton("Quitter");
        JPanel var6 = new JPanel();
        var6.add(this.playPauseButton);
        var6.add(nextButton);
        var6.add(restartButton);
        var6.add(exitButton);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(var5, "West");
        controlPanel.add(var6, "East");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(sp, "Center");
        this.getContentPane().add(controlPanel, "South");
        DisplayControler var7 = new DisplayControler();
        restartButton.setActionCommand("restart");
        restartButton.addActionListener(var7);
        this.playPauseButton.setActionCommand("playPause");
        this.playPauseButton.addActionListener(var7);
        nextButton.setActionCommand("next");
        nextButton.addActionListener(var7);
        this.speedSpinner.addChangeListener(var7);
        this.stepSpinner.addChangeListener(var7);
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(var7);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void setSimulable(Simulable var1) {
        this.simulator = var1;
        if (this.selectBox != null) {
            this.selectBox.addItemListener((var1x) -> var1.selectedItem((String) var1x.getItem()));
        }

    }

    public int getPanelWidth() {
        return this.panelWidth;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    public void addGraphicalElement(GraphicalElement var1) {
        this.simuPanel.addGraphicalElement(var1);
    }

    public void reset() {
        this.simuPanel.reset();
    }

    private void next() {
        this.simulator.next();
        this.simuPanel.repaint();
        this.repaint();
    }

    public SimulationPanel getSimuPanel() {
        return simuPanel;
    }

    private class DisplayControler implements ActionListener, ChangeListener {
        private boolean play;

        public DisplayControler() {
            this.init();
        }

        private void init() {
            this.play = false;
            GUISimulator.this.timer.stop();
            GUISimulator.this.timer.restart();
            GUISimulator.this.timer.stop();
            GUISimulator.this.playPauseButton.setText("Lecture");
        }

        public void actionPerformed(ActionEvent var1) {
            if (Objects.equals(var1.getActionCommand(), "playPause")) {
                if (this.play) {
                    GUISimulator.this.timer.stop();
                    GUISimulator.this.playPauseButton.setText("Lecture");
                } else {
                    GUISimulator.this.timer.restart();
                    GUISimulator.this.playPauseButton.setText("Pause");
                }

                this.play = !this.play;
            } else if (Objects.equals(var1.getActionCommand(), "next")) {
                for (int var2 = 0; var2 < GUISimulator.this.numberTickInStep; ++var2) {
                    GUISimulator.this.next();
                }
            } else if (Objects.equals(var1.getActionCommand(), "restart")) {
                this.init();
                GUISimulator.this.simulator.restart();
                GUISimulator.this.simuPanel.repaint();
                GUISimulator.this.repaint();
            } else if (Objects.equals(var1.getActionCommand(), "exit")) {
                System.exit(0);
            }

        }

        public void stateChanged(ChangeEvent var1) {
            GUISimulator.this.numberTickInStep = ((SpinnerNumberModel) GUISimulator.this.stepSpinner.getModel()).getNumber().intValue();
            GUISimulator.this.timer.setDelay(((SpinnerNumberModel) GUISimulator.this.speedSpinner.getModel()).getNumber().intValue());
        }
    }

    private class TimerListener implements ActionListener {
        private TimerListener() {
        }

        public void actionPerformed(ActionEvent var1) {
            long var2 = System.currentTimeMillis();

            for (int var4 = 0; var4 < GUISimulator.this.numberTickInStep; ++var4) {
                GUISimulator.this.next();
            }

            if (GUISimulator.this.warning && System.currentTimeMillis() - var2 > (long) GUISimulator.this.timer.getDelay()) {
                System.err.println("/!\\ Pas de simulation plus long que la durée attendue ! " + (System.currentTimeMillis() - var2) + "ms au lieu de " + GUISimulator.this.timer.getDelay() + "ms");
            }

        }
    }
}
