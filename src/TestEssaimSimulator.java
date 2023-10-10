import gui.GUISimulator;

import java.awt.*;

public class TestEssaimSimulator {
    public static void main(String[] args) {
        GroupeEssaim groupeEssaim = new GroupeEssaim();

        Essaim essaim = new Sardines(1400,100,900,100);


        Essaim essaim2 = new Saumons(1400,100,900,100);



        Essaim essaim3 = new Requins(1400,100,900,100);


        essaim2.genereEsseim(150);
        essaim.genereEsseim(150);
        essaim3.genereEsseim(5);
        groupeEssaim.addEsaim(essaim);
        groupeEssaim.addEsaim(essaim2);
        groupeEssaim.addEsaim(essaim3);


        GUISimulator gui = new GUISimulator (1500 , 1000 , Color.BLACK ) ;
        gui.setSimulable ( new GameSimulator(groupeEssaim, gui)) ;
    }


}
