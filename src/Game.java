import gui.GUISimulator;

import java.awt.*;

public interface Game {
    void reInit();
    void update();
    void draw(GUISimulator gui);

}
