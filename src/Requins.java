import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Requins extends Essaim {

    public Requins(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(250, 22, 10, Xmax, Xmin, Ymax, Ymin, Color.RED,"Requins",
                new ArrayList<String>(List.of(new String[]{"Sardines", "Saumons"})),
                new ArrayList<String>());
    }
    // Red eating other
    private void rule6(Boid boid) {
        ArrayList<Essaim> Essaims_voisins = this.getOthers();
        for (Essaim essaim : Essaims_voisins) {
            if (!Objects.equals(essaim.getName(), "Requins")) {
                ArrayList<Boid> cache = new ArrayList<>();
                for (Boid b : essaim.getBoids()) {
                    Vector dx = b.getPosition().soustraction(boid.getPosition());
                    double distance = dx.magnitude();
                    if (distance < 5) {
                        cache.add(b);

                    }
                }
                for (Boid value : cache) {
                    essaim.deleteBoid(value);
                }
            }
        }
    }
    @Override
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = super.rules(boid);
        rule6(boid);
        return vectors;
    }
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,0,this));
    }

}
