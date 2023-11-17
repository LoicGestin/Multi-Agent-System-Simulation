import gui.GUISimulator;

import java.awt.*;

public class TestImmigrationSimulator {
    public static void main(String[] args) {
        Immigration immigration = new Immigration(30, 30, 5);
        /*
        gameOfLife.make_alive(3,1);
        gameOfLife.make_alive(2,2);
        gameOfLife.make_alive(2,3);
        gameOfLife.make_alive(1,2);
        */
        immigration.fillRandomStates();
        immigration.make_alive(4, 5, 1);
        immigration.make_alive(4, 1 + 5, 1);

        immigration.make_alive(5, 5, 1);
        immigration.make_alive(5, 1 + 5, 1);
        immigration.make_alive(5, 2 + 5, 3);
        immigration.make_alive(5, 3 + 5, 1);

        immigration.make_alive(1 + 5, 5, 3);
        immigration.make_alive(1 + 5, 1 + 5, 1);
        immigration.make_alive(1 + 5, 2 + 5, 3);
        immigration.make_alive(1 + 5, 3 + 5, 3);
        immigration.make_alive(1 + 5, 4 + 5, 2);

        immigration.make_alive(2 + 5, 5, 1);
        immigration.make_alive(2 + 5, 1 + 5, 1);
        immigration.make_alive(2 + 5, 2 + 5, 3);
        immigration.make_alive(2 + 5, 3 + 5, 3);
        immigration.make_alive(2 + 5, 4 + 5, 2);

        immigration.make_alive(3 + 5, 1 + 5, 1);
        immigration.make_alive(3 + 5, 2 + 5, 2);
        immigration.make_alive(3 + 5, 3 + 5, 2);
        immigration.make_alive(3 + 5, 4 + 5, 2);

        immigration.make_alive(4 + 5, 1 + 5, 2);
        immigration.make_alive(4 + 5, 2 + 5, 2);
        immigration.make_alive(4 + 5, 3 + 5, 2);
        immigration.make_alive(4 + 5, 4 + 5, 1);

        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(immigration, gui));
    }
}
