import gui.GUISimulator;
import gui.Rectangle;
import gui.Triangle;

import java.awt.*;
import java.util.ArrayList;

public abstract class Essaim implements Game{
    ArrayList<Boid> boids = new ArrayList<>();
    private final ArrayList<Essaim> others = new ArrayList<>();
    private final ArrayList<Boid> init_boids = new ArrayList<>();

    private ArrayList<String> proies;
    private ArrayList<String> predateurs;
    private final double distance;

    private double vlim;
    private int Xmax;
    private int Xmin;
    private int Ymax;
    private int Ymin;

    private int size;

    private Color color;

    private String name;

    public Essaim(double distance, int size, double vlim, int Xmax, int Xmin, int Ymax, int Ymin, Color color, String name, ArrayList<String> proies, ArrayList<String> predateurs){
        this.distance = distance;
        this.vlim = vlim;

        this.Xmax = Xmax;
        this.Xmin = Xmin;
        this.Ymax = Ymax;
        this.Ymin = Ymin;
        this.size = size;

        this.color = color;
        this.name = name;
        this.predateurs = predateurs;
        this.proies = proies;
    }

    public void addBoid(Vector poistion, Vector vitesse){
        this.boids.add(new Boid(poistion,vitesse));
        this.init_boids.add(new Boid(poistion,vitesse));
    }
    public void addEsseim(Essaim essaim){
        this.others.add(essaim);
    }

    public ArrayList<Essaim> getOthers() {
        return others;
    }

    public Vector rule1(Boid boid){
        Vector pc = new Vector(0,0);
        int count = 0;
        for (Boid b: this.boids) {
            if(b != boid) {
                double distance = boid.getPosition().soustraction(b.getPosition()).magnitude();
                if (Math.abs(distance)< this.distance) {
                    pc = pc.addition(b.getPosition());
                    count ++;
                }
            }
        }
        if(count >= 1){

            pc = pc.division(count);
            pc = pc.soustraction(boid.getPosition());
            pc = pc.division(100);
        }
        return pc;


    }
    public Vector rule2(Boid boid){
        Vector c = new Vector(0,0);
        for (Boid b: this.boids) {
            if(b != boid){
                double distance = b.getPosition().soustraction(boid.getPosition()).magnitude();
                if (Math.abs(distance) < 7) {

                    c = c.soustraction( b.getPosition().soustraction(boid.getPosition()));
                }
            }
        }
        return c;
    }
    public Vector rule3(Boid boid){
        Vector pv = new Vector(0,0);
        int count = 0;
        for (Boid b: this.boids) {
            if(b != boid) {
                double distance = boid.getPosition().soustraction(b.getPosition()).magnitude();
                if (Math.abs(distance) < this.distance) {
                    pv = pv.addition(b.getVitesse());
                    count ++;
                }
            }
        }
        if(count >= 1){
            pv = pv.division(count);
            return pv.soustraction(boid.getVitesse()).division(8);
        }
        return new Vector(0,0);

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
    public Vector rule5(Boid boid){

        Vector bestDirection = new Vector(0,0);
        ArrayList<Essaim> essaims = this.getOthers();
        for(Essaim essaim : essaims) {
            if(predateurs.contains(essaim.getName())){

                for (Boid b : essaim.getBoids()) {
                    if (b != boid) {
                        double distance = boid.getPosition().soustraction(b.getPosition()).magnitude();
                        if (Math.abs(distance) < this.getDistance()) {
                            Vector tampon = boid.getPosition().soustraction(b.getPosition());

                            tampon.normalize();
                            bestDirection = bestDirection.addition(tampon);
                        }
                    }
                }
            }
        }

        bestDirection.normalize();
        return bestDirection.multiplication(this.getVlim());
    }
    public void limit_velocity(Boid b){
        if(b.getVitesse().magnitude() > vlim){
            b.setVitesse(b.getVitesse().division(b.getVitesse().magnitude()).multiplication(vlim));
        }
    }
    public Vector bound_position(Boid b){
        Vector v = new Vector(0,0);
        if(b.getPosition().getX() < Xmin){
            v.setX(5);
        }
        else if(b.getPosition().getX() > Xmax){
            v.setX(-5);
        }

        if(b.getPosition().getY() < Ymin){
            v.setY(5);
        }
        else if(b.getPosition().getY() > Ymax){

            v.setY(-5);
        }
        return v;
    }
    public void move_all_boids_to_new_position(){
        ArrayList<Boid> cache_boid = new ArrayList<>(this.boids);

        for (int i = 0; i < this.boids.size() ; i++) {
            Boid b = this.boids.get(i).copy();

            ArrayList<Vector> vectors = rules(b);
            ArrayList<Vector> priority_rules = priority_rules(b);
            boolean priority = false;
            for (Vector v: priority_rules) {
                if(v.getX() != 0 || v.getY() != 0){
                    cache_boid.get(i).setVitesse(v);
                    priority = true;
                    break;
                }
            }
            if(!priority){
                Vector vector = new Vector(0,0);
                for (Vector v: vectors) {
                    vector = vector.addition(v);
                }
                cache_boid.get(i).setVitesse(b.getVitesse().addition(vector));
            }
            cache_boid.get(i).setVitesse(cache_boid.get(i).getVitesse().addition(this.bound_position(cache_boid.get(i))));
            this.limit_velocity(cache_boid.get(i));

            cache_boid.get(i).setPosition(b.getPosition().addition(cache_boid.get(i).getVitesse()));

        }

        this.boids = cache_boid;
    }

    public ArrayList<Vector> rules(Boid boid){
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(rule1(boid));
        vectors.add(rule2(boid));
        vectors.add(rule3(boid));

        return vectors;
    }
    public ArrayList<Vector> priority_rules(Boid boid){
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(rule5(boid));
        vectors.add(rule4(boid));
        return vectors;
    }

    public double getVlim() {
        return vlim;
    }

    public void reInit() {
        for (int i = 0; i < this.boids.size(); i++) {
            this.boids.get(i).replace(this.init_boids.get(i));
        }
    }


    public void update() {
        this.move_all_boids_to_new_position();
    }


    public void draw(GUISimulator gui) {

        for (Boid boid : this.boids) {
            double x = boid.getPosition().getX();
            double y = boid.getPosition().getY();
            double vx = boid.getVitesse().getX();
            double vy = boid.getVitesse().getY();



            double angle = Math.atan2(vy, vx);


            int[] xPoints = {
                    (int) x,
                    (int) (x - this.size * Math.cos(angle + Math.PI / 6)),
                    (int) (x - this.size * Math.cos(angle - Math.PI / 6))
            };
            int[] yPoints = {
                    (int) y,
                    (int) (y - this.size * Math.sin(angle + Math.PI / 6)),
                    (int) (y - this.size * Math.sin(angle - Math.PI / 6))
            };

            gui.addGraphicalElement(new Triangle(xPoints, yPoints, Color.GRAY, this.color));
        }
    }


    public abstract void setUpEvent(EventManager eventManager);

    public ArrayList<Boid> getBoids() {
        return boids;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Essaim{" +
                "boids=" + boids +
                ", init_boids=" + init_boids +
                ", distance=" + distance +
                ", vlim=" + vlim +
                ", size=" + size +
                ", color=" + color +
                ", name='" + name + '\'' +
                '}';
    }
}
