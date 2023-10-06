import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Saumons extends Essaim{
    ArrayList<String> predateurs = new ArrayList<>(List.of(new String[]{"Requins"}));
    public Saumons(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 30, 25, Xmax, Xmin, Ymax, Ymin, Color.YELLOW,"Saumons");
    }
    public Vector rule5(Boid boid){
        Vector pv = new Vector(0,0);
        Vector bestDirection = new Vector(0,0);
        ArrayList<Essaim> essaims = this.getOthers();
        for(Essaim essaim : essaims) {
            if(predateurs.contains(essaim.getName())){

                for (Boid b : essaim.getBoids()) {
                    if (b != boid) {
                        double distance = boid.getPosition().soustraction(b.getPosition()).magnitude();
                        if (Math.abs(distance) < this.getDistance()) {
                            pv = boid.getPosition().soustraction(b.getPosition());

                            pv.normalize();
                            bestDirection = bestDirection.addition(pv);
                        }
                    }
                }
            }
        }

        bestDirection.normalize();
        return bestDirection.multiplication(this.getVlim());
    }
    @Override
    public void move_all_boids_to_new_position(){
        ArrayList<Boid> cache_boid = new ArrayList<>(this.getBoids());

        for (int i = 0; i < this.getBoids().size() ; i++) {
            Boid b = this.getBoids().get(i).copy();
            Vector v1 = rule1(b);
            Vector v2 = rule2(b);
            Vector v3 = rule3(b);
            Vector v4 = rule5(b);

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
        eventManager.addEvent(new ActionEvent(0,eventManager,2,this));
    }
}
