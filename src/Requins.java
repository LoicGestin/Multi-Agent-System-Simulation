import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Requins extends Essaim {
    ArrayList<String> proies = new ArrayList<>(List.of(new String[]{"Sardines", "Saumons"}));
    public Requins(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(150, 45, 20, Xmax, Xmin, Ymax, Ymin, Color.RED,"Requins");
    }

    public Vector rule4(Boid boid){
        Vector pv = new Vector(0,0);
        double min = this.getDistance();
        ArrayList<Essaim> essaims = this.getOthers();
        for(Essaim essaim : essaims) {

            if(proies.contains(essaim.getName())){

                for (Boid b : essaim.getBoids()) {
                    if (b != boid) {
                        double distance = boid.getPosition().soustraction(b.getPosition()).magnitude();
                        if (Math.abs(distance) < min) {
                            pv = b.getPosition().soustraction(boid.getPosition());
                            pv.normalize();
                            pv = pv.multiplication(this.getVlim() / 2);
                            min = Math.abs(distance);
                        }
                    }
                }
            }
        }
        return pv;
    }
    @Override
    public void move_all_boids_to_new_position(){
        ArrayList<Boid> cache_boid = new ArrayList<>(this.getBoids());

        for (int i = 0; i < this.getBoids().size() ; i++) {
            Boid b = this.getBoids().get(i).copy();
            Vector v1 = rule1(b);
            Vector v2 = rule2(b);
            Vector v3 = rule3(b);
            Vector v4 = rule4(b);

            if(v4.getX() == 0 && v4.getY() == 0){
                cache_boid.get(i).setVitesse(b.getVitesse().addition(v1).addition(v2).addition(v3));
            }
            else{
                cache_boid.get(i).setVitesse(v4);
            }
            cache_boid.get(i).setPosition(b.getPosition().addition(b.getVitesse()));
            this.limit_velocity(cache_boid.get(i));
            cache_boid.get(i).setVitesse(cache_boid.get(i).getVitesse().addition(this.bound_position(cache_boid.get(i))));
        }

        this.boids = cache_boid;


    }
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,3,this));
    }

    @Override
    public String toString() {
        return "REQUIN " + super.toString() + this.proies.toString();
    }
}
