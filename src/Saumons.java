import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Saumons extends Essaim{

    public Saumons(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 15, 5, Xmax, Xmin, Ymax, Ymin, Color.YELLOW,"Saumons",
                new ArrayList<String>(),
                new ArrayList<>(List.of(new String[]{"Requins"})));

    }

    // Exemple que le code est OPEN / CLOSE
    private Vector rule6(Boid boid){
        return new Vector(0.0, 0.0);
    }

    @Override
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = super.rules(boid);
        vectors.add(rule6(boid));
        return vectors;
    }

    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,2,this));
    }
}
