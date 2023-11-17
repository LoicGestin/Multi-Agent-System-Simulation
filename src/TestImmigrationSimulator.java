import gui.GUISimulator;

import java.awt.*;

public class TestImmigrationSimulator {
    public static void main(String[] args) {
        Immigration immigration = new Immigration(80, 80, 5);
        immigration.fillRandomStates();

        /* Ou vous pouvez rentrez à la main chaque cases / ou vous pouvez les rentrer depuis l'interface avec le click
        gameOfLife.makeAlive(3,1);
        gameOfLife.makeAlive(2,2);
        gameOfLife.makeAlive(2,3);
        gameOfLife.makeAlive(1,2);
        */



        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(immigration, gui));
    }
}
