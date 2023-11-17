import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// La classe Sardines étend la classe Essaim et représente un essaim de sardines.
public class Sardines extends Essaim {
    // Constructeur de la classe Sardines qui initialise les propriétés de l'essaim de sardines.
    public Sardines(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 10, 12, Xmax, Xmin, Ymax, Ymin, Color.BLUE, "Sardines",
                // Les sardines n'ont pas de proies.
                new ArrayList<String>(),
                // Les sardines fuient les requins.
                new ArrayList<>(List.of(new String[]{"Requins"})));
    }

    // Règle spécifique aux sardines : Accélère si aucune sardine n'est autours de lui (Don't let me alone ... uwu).
    private Vector rule6(Boid boid) {
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, getDistance());
        if (voisins.isEmpty()) {
            return new Vector(boid.getVitesse().getX() * 1.2, boid.getVitesse().getY() * 1.2);
        }
        return new Vector(0, 0);
    }

    @Override
    public int distanceRepulsion() {
        return 15;
    }

    // Override la méthode rules de la classe Essaim pour inclure la règle spécifique aux sardines.
    @Override
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = super.rules(boid);
        vectors.add(rule6(boid));
        return vectors;
    }

    // Override la méthode setUpEvent de la classe Essaim pour configurer un événement spécifique aux sardines.
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0, eventManager, 1, this));
    }

}
