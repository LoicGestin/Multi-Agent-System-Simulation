import gui.GUISimulator;

import java.awt.*;

public class TestImmigrationSimulator {
    public static void main(String[] args) {
        Immigration immigration = new Immigration(30, 30, 5);
        /*
        gameOfLife.makeAlive(3,1);
        gameOfLife.makeAlive(2,2);
        gameOfLife.makeAlive(2,3);
        gameOfLife.makeAlive(1,2);
        */
        immigration.fillRandomStates();
        immigration.makeAlive(4, 5, 1);
        immigration.makeAlive(4, 1 + 5, 1);

        immigration.makeAlive(5, 5, 1);
        immigration.makeAlive(5, 1 + 5, 1);
        immigration.makeAlive(5, 2 + 5, 3);
        immigration.makeAlive(5, 3 + 5, 1);

        immigration.makeAlive(1 + 5, 5, 3);
        immigration.makeAlive(1 + 5, 1 + 5, 1);
        immigration.makeAlive(1 + 5, 2 + 5, 3);
        immigration.makeAlive(1 + 5, 3 + 5, 3);
        immigration.makeAlive(1 + 5, 4 + 5, 2);

        immigration.makeAlive(2 + 5, 5, 1);
        immigration.makeAlive(2 + 5, 1 + 5, 1);
        immigration.makeAlive(2 + 5, 2 + 5, 3);
        immigration.makeAlive(2 + 5, 3 + 5, 3);
        immigration.makeAlive(2 + 5, 4 + 5, 2);

        immigration.makeAlive(3 + 5, 1 + 5, 1);
        immigration.makeAlive(3 + 5, 2 + 5, 2);
        immigration.makeAlive(3 + 5, 3 + 5, 2);
        immigration.makeAlive(3 + 5, 4 + 5, 2);

        immigration.makeAlive(4 + 5, 1 + 5, 2);
        immigration.makeAlive(4 + 5, 2 + 5, 2);
        immigration.makeAlive(4 + 5, 3 + 5, 2);
        immigration.makeAlive(4 + 5, 4 + 5, 1);

        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(immigration, gui));
    }
}
