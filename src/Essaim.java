import gui.GUISimulator;
import gui.Rectangle;
import gui.Triangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;



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

    public void genereEsseim(int number){
        Random random = new Random();
        for (int i = 0; i < number ; i++) {
            Vector v1 = new Vector( random.nextInt(Xmax),random.nextInt(Ymax));
            int b1 = random.nextInt(2) == 1 ? -1 : 1;
            int b2 = random.nextInt(2) == 1 ? -1 : 1;
            Vector v2 = new Vector( b1 * random.nextInt((int) this.vlim) + b1,b2 * random.nextInt((int) this.vlim) + b2);

            this.boids.add(new Boid(v1,v2));
            this.init_boids.add(new Boid(v1,v2));
        }
    }

    public ArrayList<Essaim> getOthers() {
        return others;
    }

    public Vector rule1(Boid boid){
        double angleDeVue = Math.PI ;
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, angleDeVue, this.distance);

        Vector pc = new Vector(0,0);
        int count = 0;
        for (Boid b: voisins) {
            pc = pc.addition(b.getPosition());
            count ++;
        }
        if(count >= 1){

            pc = pc.division(count);
            pc = pc.soustraction(boid.getPosition());
            pc = pc.division(700);
        }
        return pc;


    }
    public Vector rule2(Boid boid){
        Vector c = new Vector(0,0);

        double angleDeVue = Math.PI ;
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, angleDeVue, 30);

        for (Boid b: voisins) {
            c = c.soustraction( b.getPosition().soustraction(boid.getPosition()));
        }
        return c.division(10);
    }
    public Vector rule3(Boid boid){
        Vector pv = new Vector(0,0);
        double angleDeVue = Math.PI ;
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, angleDeVue, this.distance);
        int count = 0;
        for (Boid b: voisins) {
            pv = pv.addition(b.getVitesse());
            count ++;

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
    public ArrayList<Boid> getVoisinsAvecAngle(Boid boid, double angleDeVue, double distanceMax) {
        angleDeVue = 300;
        ArrayList<Boid> voisins = new ArrayList<>();

        Vector directionBoid = boid.copy().getVitesse();
        directionBoid.normalize();

        for (Boid autreBoid : this.boids) {
            if (autreBoid != boid) {
                Vector vecteurVersAutreBoid = autreBoid.copy().getPosition().soustraction(boid.getPosition());
                double angleEntreBoids = Math.acos(directionBoid.dotProduct(vecteurVersAutreBoid) / (directionBoid.magnitude() * vecteurVersAutreBoid.magnitude()));
                angleEntreBoids = Math.toDegrees(angleEntreBoids); // Conversion en degrés
                if (angleEntreBoids <= angleDeVue / 2 ) {
                    if (vecteurVersAutreBoid.magnitude() <= distanceMax) {
                        voisins.add(autreBoid);
                    }
                }
            }
        }

        return voisins;
    }

    public void circle_position(Boid b){
        if(b.getPosition().getX() > this.Xmax + 100){
            b.getPosition().setX(0);
        }
        else if(b.getPosition().getX() < 0){
            b.getPosition().setX(this.Xmax + 100);
        }
        if(b.getPosition().getY() > this.Ymax + 100){
            b.getPosition().setY(0);
        }
        else if(b.getPosition().getY() < 0){
            b.getPosition().setY(this.Ymax + 100);
        }

    }

    public void limit_velocity(Boid b) {
        if (b.getVitesse().magnitude() > vlim) {
            b.setVitesse(b.getVitesse().division(b.getVitesse().magnitude()).multiplication(vlim));
        }
    }
    public Vector bound_position(Boid b){
        Vector v = new Vector(0,0);
        if(b.getPosition().getX() < Xmin){
            v.setX(1);
        }
        else if(b.getPosition().getX() > Xmax){
            v.setX(-1);
        }

        if(b.getPosition().getY() < Ymin){
            v.setY(1);
        }
        else if(b.getPosition().getY() > Ymax){

            v.setY(-1);
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
            //cache_boid.get(i).setVitesse(cache_boid.get(i).getVitesse().addition(this.bound_position(cache_boid.get(i))));
            this.limit_velocity(cache_boid.get(i));


            cache_boid.get(i).setPosition(b.getPosition().addition(cache_boid.get(i).getVitesse()));
            circle_position(cache_boid.get(i));

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
                    (int) (x - this.size * Math.cos(angle + Math.PI / 7) ),
                    (int) (x - this.size * Math.cos(angle - Math.PI / 7) )
            };
            int[] yPoints = {
                    (int) y,
                    (int) (y - this.size * Math.sin(angle + Math.PI / 7) ),
                    (int) (y - this.size * Math.sin(angle - Math.PI / 7) )
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
    public void handleClick(int x, int y, int panelX, int panelY) {

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
