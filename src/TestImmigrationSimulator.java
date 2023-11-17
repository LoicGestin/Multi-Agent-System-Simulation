import gui.GUISimulator;

import java.awt.*;

public class TestImmigrationSimulator {
    public static void main(String[] args) {
        Immigration immigration = new Immigration(50, 50, 5);


        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(immigration, gui));
    }
}
