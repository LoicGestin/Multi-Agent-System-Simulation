import java.awt.*;
import java.util.ArrayList;

/**
 * La classe CustomFish étend la classe Essaim et représente un type personnalisé d'essaim.
 * Elle est utilisée pour créer une simulation avec des poissons personnalisés.
 */
public class CustomFish extends Essaim {

    private final int pas;

    public CustomFish(int distance, int size, int vlim, int Xmax, int Xmin, int Ymax, int Ymin, Color color, ArrayList<String> proies, ArrayList<String> predateurs, int pas) {
        super(distance, size, vlim, Xmax, Xmin, Ymax, Ymin, color, "CUSTOM",
                proies,
                predateurs);
        this.pas = pas;
    }


    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0, eventManager, this.pas, this));
    }
}
