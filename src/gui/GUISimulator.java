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
import java.util.Objects;

public class GUISimulator extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int INIT_SPEED = 100;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10000;
    private static final int STEP_SPEED = 10;
    private final JScrollPane sp;
    private final SimulationPanel simuPanel;
    private final JPanel controlPanel;
    private final JButton restartButton;
    private final JButton playPauseButton;
    private final JButton nextButton;
    private final JLabel speedLabel;
    private final JSpinner speedSpinner;
    private final JLabel stepLabel;
    private final JSpinner stepSpinner;
    private final JButton exitButton;
    private final JComboBox<String> selectBox;
    private int panelWidth;
    private int panelHeight;
    private int numberTickInStep;
    private final Timer timer;
    private Simulable simulator;
    private boolean warning;

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
        } catch (UnsupportedLookAndFeelException var8) {
        } catch (ClassNotFoundException var9) {
        } catch (InstantiationException var10) {
        } catch (IllegalAccessException var11) {
        }

        this.simuPanel = new SimulationPanel(var1, var2, var3);
        this.sp = new JScrollPane(this.simuPanel);
        this.sp.setPreferredSize(new Dimension(Math.min(800, var1), Math.min(600, var2)));
        this.panelWidth = var1;
        this.panelHeight = var2;
        this.simuPanel.setBackground(var3);
        JPanel var5 = new JPanel(new GridLayout(2, 2));
        this.speedLabel = new JLabel("Tps entre 2 affichages (ms) :");
        this.speedSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        var5.add(this.speedLabel);
        var5.add(this.speedSpinner);
        this.stepLabel = new JLabel("Nb de pas simulés entre 2 affichages :");
        this.stepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 10));
        var5.add(this.stepLabel);
        var5.add(this.stepSpinner);
        this.selectBox = new JComboBox();
        this.selectBox.addItemListener((var1x) -> {
            var4.selectedItem((String) var1x.getItem());
        });
        this.restartButton = new JButton("Début");
        this.playPauseButton = new JButton("Lecture");
        this.nextButton = new JButton("Suivant");
        this.exitButton = new JButton("Quitter");
        JPanel var6 = new JPanel();
        var6.add(this.playPauseButton);
        var6.add(this.nextButton);
        var6.add(this.restartButton);
        var6.add(this.exitButton);
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new BorderLayout());
        this.controlPanel.add(var5, "West");
        this.controlPanel.add(var6, "East");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(this.sp, "Center");
        this.getContentPane().add(this.controlPanel, "South");
        DisplayControler var7 = new DisplayControler(this);
        this.restartButton.setActionCommand("restart");
        this.restartButton.addActionListener(var7);
        this.playPauseButton.setActionCommand("playPause");
        this.playPauseButton.addActionListener(var7);
        this.nextButton.setActionCommand("next");
        this.nextButton.addActionListener(var7);
        this.speedSpinner.addChangeListener(var7);
        this.stepSpinner.addChangeListener(var7);
        this.exitButton.setActionCommand("exit");
        this.exitButton.addActionListener(var7);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setVisible(true);
    }

    public void setSimulable(Simulable var1) {
        this.simulator = var1;
        if (this.selectBox != null) {
            this.selectBox.addItemListener((var1x) -> {
                var1.selectedItem((String) var1x.getItem());
            });
        }

    }

    public int getPanelWidth() {
        return this.panelWidth;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    private JPanel getSimulationPanel() {
        return this.simuPanel;
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

    public void resizePanel(int var1, int var2) {
        this.panelHeight = var2;
        this.panelWidth = var1;
        this.simuPanel.setPreferredSize(new Dimension(var1, var2));
        this.sp.revalidate();
        this.simuPanel.revalidate();
    }

    public void addItemToList(String var1) {
        this.selectBox.addItem(var1);
        if (this.selectBox.getItemCount() == 1) {
            this.controlPanel.add(this.selectBox);
            this.pack();
        }

    }

    public void setWarning(boolean var1) {
        this.warning = var1;
    }

    public SimulationPanel getSimuPanel() {
        return simuPanel;
    }

    private class DisplayControler implements ActionListener, ChangeListener {
        private boolean play;

        public DisplayControler(GUISimulator var2) {
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
