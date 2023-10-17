import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sardines extends Essaim{

    public Sardines(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 10, 3, Xmax, Xmin, Ymax, Ymin, Color.BLUE,"Sardines",
                new ArrayList<String>(),
                new ArrayList<>(List.of(new String[]{"Requins"})));
    }

    private Vector rule6(Boid boid){
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, 300, getDistance());
        if (voisins.size() == 0){
            return new Vector(boid.getVitesse().getX() * 1.2, boid.getVitesse().getY() * 1.2);
        }
        return new Vector(0,0);
    }
    @Override
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = super.rules(boid);
        vectors.add(rule6(boid));
        return vectors;
    }
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,1,this));
    }

}
