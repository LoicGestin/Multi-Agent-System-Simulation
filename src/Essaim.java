import gui.GUISimulator;
import gui.Rectangle;
import gui.Triangle;

import java.awt.*;
import java.util.ArrayList;

public abstract class Essaim extends GroupeEssaim{
    private ArrayList<Boid> boids;
    private ArrayList<Boid> init_boids;
    private final double distance;

    private double vlim;
    private int Xmax;
    private int Xmin;
    private int Ymax;
    private int Ymin;

    private int size;

    private Color color;

    public Essaim(double distance, int size, double vlim, int Xmax, int Xmin, int Ymax, int Ymin, Color color){
        this.boids = new ArrayList<>();
        this.init_boids = new ArrayList<>();
        this.distance = distance;
        this.vlim = vlim;

        this.Xmax = Xmax;
        this.Xmin = Xmin;
        this.Ymax = Ymax;
        this.Ymin = Ymin;
        this.size = size;

        this.color = color;


        /*for (int i = 0; i < nombreBoids; i++) {
            Vector position = new Vector(Math.random(), Math.random());
            Vector vitesse = new Vector(Math.random() - 0.5, Math.random() - 0.5);
            this.boids.add(new Boid(position, vitesse));
        }*/
    }

    public void addBoid(Vector poistion, Vector vitesse){
        this.boids.add(new Boid(poistion,vitesse));
        this.init_boids.add(new Boid(poistion,vitesse));
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
            Vector v1 = rule1(b);
            Vector v2 = rule2(b);
            Vector v3 = rule3(b);

            cache_boid.get(i).setVitesse(b.getVitesse().addition(v1).addition(v2).addition(v3));
            cache_boid.get(i).setPosition(b.getPosition().addition(b.getVitesse()));
            this.limit_velocity(cache_boid.get(i));
            cache_boid.get(i).setVitesse(cache_boid.get(i).getVitesse().addition(this.bound_position(cache_boid.get(i))));

        }

        this.boids = cache_boid;


    }

    @Override
    public void reInit() {
        for (int i = 0; i < this.boids.size(); i++) {
            this.boids.get(i).replace(this.init_boids.get(i));
        }
    }

    @Override
    public void update() {
        this.move_all_boids_to_new_position();
    }

    @Override
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
        /*
        for (int i = 0; i < this.boids.size(); i++) {


                gui.addGraphicalElement(new gui.Rectangle(
                        (int) this.boids.get(i).getPosition().getX(),
                        (int) this.boids.get(i).getPosition().getY(),
                        Color.GRAY,
                        Color.RED,
                        40));
        }

         */
    }

    @Override
    public abstract void setUpEvent(EventManager eventManager);



    @Override
    public String toString() {
        return "Essaim{" +
                "boids=" + boids +
                ", init_boids=" + init_boids +
                ", distance=" + distance +
                '}';
    }
}
