import gui.GUISimulator;
import gui.Simulable;
import gui.SimulationPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameSimulator implements Simulable {
    private final Game game;
    private final GUISimulator gui;

    private final EventManager eventManager;

    public GameSimulator(Game game, GUISimulator gui){
        this.game = game;
        this.gui = gui;

        this.eventManager = new EventManager();
        this.game.setUpEvent(eventManager);

        this.game.draw(gui);
        SimulationPanel simulationPanel = this.gui.getSimuPanel();
        simulationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                handleMouseClick(x, y);
            }
        });

        simulationPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                game.handleMouseMotion(x, y, gui);
            }
        });
    }
    public void handleMouseClick(int x, int y) {
        this.game.handleClick(x,y,this.gui.getPanelWidth(), this.gui.getPanelHeight());
        this.game.draw(gui);
    }
    @Override
    public void next() {
        //this.game.update();
        this.eventManager.next();
        this.game.draw(gui);
    }

    @Override
    public void restart() {
        this.game.reInit();

        this.eventManager.restart();
        this.game.setUpEvent(eventManager);

        this.game.draw(gui);
    }



}
