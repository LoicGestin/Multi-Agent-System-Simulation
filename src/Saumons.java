import java.awt.*;
import java.util.ArrayList;
import java.util.List;


// La classe Saumons étend la classe Essaim et représente un essaim de saumons.
public class Saumons extends Essaim {
    // Constructeur de la classe Saumons qui initialise les propriétés de l'essaim de saumons.
    public Saumons(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 15, 5, Xmax, Xmin, Ymax, Ymin, Color.YELLOW, "Saumons",
                // Les saumons n'ont pas de proies.
                new ArrayList<String>(),
                // Les saumons fuit les requins.
                new ArrayList<>(List.of(new String[]{"Requins"})));
    }

    // Règle spécifique aux saumons : exemple d'une règle "OPEN/CLOSE", qui ne fait rien.
    private Vector rule6(Boid boid) {
        return new Vector(0.0, 0.0);
    }

    // Override la méthode rules de la classe Essaim pour inclure la règle spécifique aux saumons.
    @Override
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = super.rules(boid);
        vectors.add(rule6(boid));
        return vectors;
    }

    // Override la méthode setUpEvent de la classe Essaim pour configurer un événement spécifique aux saumons.
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0, eventManager, 2, this));
    }
}
