import gui.GUISimulator;

import java.awt.*;

public interface Game {
    void reInit();
    void update();
    void draw(GUISimulator gui);

    void setUpEvent(EventManager eventManager);

    void handleClick(int x, int y, int panelX, int panelY);

}
