import gui.GUISimulator;
import gui.Simulable;
import gui.SimulationPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
// La classe GameSimulator implémente l'interface Simulable pour être utilisée avec le GUISimulator.

public class GameSimulator implements Simulable {
    private final Game game;
    private final GUISimulator gui;
    private final EventManager eventManager;

    // Constructeur prenant en paramètre le jeu à simuler (implementant l'interface Game) et le GUISimulator.
    public GameSimulator(Game game, GUISimulator gui) {
        this.game = game;
        this.gui = gui;
        // Création et initialisation de l'EventManager.
        this.eventManager = new EventManager();
        this.game.setUpEvent(eventManager);
        // Initialisation du dessin du jeu dans le GUI.
        this.game.draw(gui);

        // Configuration des événements liés à la souris.
        SimulationPanel simulationPanel = this.gui.getSimuPanel();

        // Ajout d'un écouteur pour gérer les clics de souris.
        simulationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                handleMouseClick(x, y);
            }
        });
        // Ajout d'un écouteur pour gérer le mouvement de la souris.
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

    // Gestion des clics de souris.
    public void handleMouseClick(int x, int y) {
        this.game.handleClick(x, y, this.gui.getPanelWidth(), this.gui.getPanelHeight());
        this.game.draw(gui);
    }

    // Méthode appelée à chaque itération de la simulation.
    @Override
    public void next() {
        //this.game.update();
        this.eventManager.next();
        this.game.draw(gui);
    }

    // Méthode appelée pour réinitialiser la simulation.
    @Override
    public void restart() {
        this.game.reInit();

        this.eventManager.restart();
        this.game.setUpEvent(eventManager);

        this.game.draw(gui);
    }


}
