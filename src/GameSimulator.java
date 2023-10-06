import gui.GUISimulator;
import gui.Simulable;

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
