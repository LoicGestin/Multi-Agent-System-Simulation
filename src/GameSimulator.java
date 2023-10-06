import gui.GUISimulator;
import gui.Simulable;

public class GameSimulator implements Simulable {
    private final Game game;
    private final GUISimulator gui;

    public GameSimulator(Game game, GUISimulator gui){
        this.game = game;
        this.gui = gui;
        this.game.draw(gui);
    }

    @Override
    public void next() {
        this.game.update();
        this.game.draw(gui);
    }

    @Override
    public void restart() {
        this.game.reInit();
        this.game.draw(gui);
    }



}
