import gui.GUISimulator;
import gui.Triangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


// Classe abstraite représentant un essaim de boids
public abstract class Essaim implements Game {
    // Liste des autres essaims
    private final ArrayList<Essaim> others = new ArrayList<>();
    // Liste des boids initiaux (pour la réinitialisation)
    private final ArrayList<Boid> initBoids = new ArrayList<>();
    // Liste des proies (noms d'autres essaims considérés comme proies)
    private final ArrayList<String> proies;
    // Liste des prédateurs (noms d'autres essaims considérés comme prédateurs)
    private final ArrayList<String> predateurs;
    // Paramètres de l'essaim
    private final double distance;
    private final double vlim;
    private final int Xmax;
    private final int Ymax;
    private final int size;
    private final Color color;
    private final String name;
    // Liste des boids dans l'essaim
    ArrayList<Boid> boids = new ArrayList<>();
    private boolean start = false;

    // Constructeur de la classe Essaim
    public Essaim(double distance, int size, double vlim, int Xmax, int Xmin, int Ymax, int Ymin, Color color, String name, ArrayList<String> proies, ArrayList<String> predateurs) {
        this.distance = distance;
        this.vlim = vlim;

        this.Xmax = Xmax;
        this.Ymax = Ymax;
        this.size = size;

        this.color = color;
        this.name = name;
        this.predateurs = predateurs;
        this.proies = proies;
    }

    // Méthode pour ajouter un boid à l'essaim
    public void addBoid(Vector poistion, Vector vitesse) {
        this.boids.add(new Boid(poistion, vitesse));
        if (!start) {
            this.initBoids.add(new Boid(poistion, vitesse));
        }
    }

    // Méthode pour supprimer un boid de l'essaim
    public void deleteBoid(Boid boid) {
        this.boids.remove(boid);
    }

    // Méthode pour ajouter un autre essaim à la liste des essaims voisins
    public void addEsseim(Essaim essaim) {
        this.others.add(essaim);
    }

    // Méthode pour générer aléatoirement un certain nombre de boids
    public void genereEsseim(int number) {
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            Vector v1 = new Vector(random.nextInt(Xmax), random.nextInt(Ymax));
            int b1 = random.nextInt(2) == 1 ? -1 : 1;
            int b2 = random.nextInt(2) == 1 ? -1 : 1;
            Vector v2 = new Vector(b1 * random.nextInt((int) this.vlim) + b1, b2 * random.nextInt((int) this.vlim) + b2);

            this.boids.add(new Boid(v1, v2));
            this.initBoids.add(new Boid(v1, v2));
        }
    }

    // Méthode pour obtenir les autres essaims avec lesquels celui-ci interagit
    public ArrayList<Essaim> getOthers() {
        return others;
    }

    // Méthode pour appliquer la règle de cohésion (cohesion) à un boid
    public Vector rule1(Boid boid) {
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, this.distance);

        Vector pc = new Vector(0, 0);
        int count = 0;
        for (Boid b : voisins) {
            pc = pc.addition(b.getPosition());
            count++;
        }
        if (count >= 1) {

            pc = pc.division(count);
            pc = pc.soustraction(boid.getPosition());
            pc = pc.division(700);
        }
        return pc;


    }
    public abstract int distanceRepulsion();
    // Méthode pour appliquer la règle d'éloignement (separation) à un boid
    public Vector rule2(Boid boid) {
        Vector c = new Vector(0, 0);

        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, distanceRepulsion());

        for (Boid b : voisins) {
            c = c.soustraction(b.getPosition().soustraction(boid.getPosition()));
        }
        return c.division(10);
    }

    // Méthode pour appliquer la règle d'alignement à un boid
    public Vector rule3(Boid boid) {
        Vector pv = new Vector(0, 0);
        ArrayList<Boid> voisins = getVoisinsAvecAngle(boid, this.distance);
        int count = 0;
        for (Boid b : voisins) {
            pv = pv.addition(b.getVitesse());
            count++;

        }
        if (count >= 1) {
            pv = pv.division(count);
            return pv.soustraction(boid.getVitesse()).division(8);
        }
        return new Vector(0, 0);

    }

    // Méthode pour appliquer la règle d'attaquer la proie la plus proche (pas d'angle de vision)
    public Vector rule4(Boid boid) {
        Vector pv = new Vector(0, 0);
        double min = this.getDistance();
        ArrayList<Essaim> essaims = this.getOthers();
        for (Essaim essaim : essaims) {

            if (proies.contains(essaim.getName())) {

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

    // Méthode pour appliquer la règle de fuir les prédateurs à un boid (pas d'angle de vision)
    public Vector rule5(Boid boid) {

        Vector bestDirection = new Vector(0, 0);
        ArrayList<Essaim> essaims = this.getOthers();
        for (Essaim essaim : essaims) {
            if (predateurs.contains(essaim.getName())) {

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

    // Méthode pour obtenir les voisins d'un boid avec un angle spécifié et une distance maximale
    public ArrayList<Boid> getVoisinsAvecAngle(Boid boid, double distanceMax) {
        double angleDeVue = 300;
        ArrayList<Boid> voisins = new ArrayList<>();

        Vector directionBoid = boid.copy().getVitesse();
        directionBoid.normalize();

        for (Boid autreBoid : this.boids) {
            if (autreBoid != boid) {
                Vector vecteurVersAutreBoid = autreBoid.copy().getPosition().soustraction(boid.getPosition());
                double angleEntreBoids = Math.acos(directionBoid.dotProduct(vecteurVersAutreBoid) / (directionBoid.magnitude() * vecteurVersAutreBoid.magnitude()));
                angleEntreBoids = Math.toDegrees(angleEntreBoids); // Conversion en degrés
                if (angleEntreBoids <= angleDeVue / 2) {
                    if (vecteurVersAutreBoid.magnitude() <= distanceMax) {
                        voisins.add(autreBoid);
                    }
                }
            }
        }

        return voisins;
    }

    // Méthode pour ajuster la position du boid lorsque celle-ci dépasse les limites de l'environnement
    public void circlePosition(Boid b) {
        if (b.getPosition().getX() > this.Xmax + 100) {
            b.getPosition().setX(0);
        } else if (b.getPosition().getX() < 0) {
            b.getPosition().setX(this.Xmax + 100);
        }
        if (b.getPosition().getY() > this.Ymax + 100) {
            b.getPosition().setY(0);
        } else if (b.getPosition().getY() < 0) {
            b.getPosition().setY(this.Ymax + 100);
        }

    }

    // Méthode pour limiter la vitesse d'un boid
    public void limitVelocity(Boid b) {
        if (b.getVitesse().magnitude() > vlim) {
            b.setVitesse(b.getVitesse().division(b.getVitesse().magnitude()).multiplication(vlim));
        }
    }

    // Méthode pour déplacer tous les boids vers de nouvelles positions
    public void moveAllBoidsToNewPosition() {
        ArrayList<Boid> cacheBoid = new ArrayList<>(this.boids);

        for (int i = 0; i < this.boids.size(); i++) {
            Boid b = this.boids.get(i).copy();

            ArrayList<Vector> vectors = rules(b);
            ArrayList<Vector> priorityRules = priorityRules(b);
            boolean priority = false;
            for (Vector v : priorityRules) {
                if (v.getX() != 0 || v.getY() != 0) {
                    cacheBoid.get(i).setVitesse(v);
                    priority = true;
                    break;
                }
            }
            if (!priority) {
                Vector vector = new Vector(0, 0);
                for (Vector v : vectors) {
                    vector = vector.addition(v);
                }
                cacheBoid.get(i).setVitesse(b.getVitesse().addition(vector));
            }
            // la répulsion s'applique toujours même si règle prioritaire
            cacheBoid.get(i).setVitesse(cacheBoid.get(i).getVitesse().addition(rule2(b)));
            //cacheBoid.get(i).setVitesse(cacheBoid.get(i).getVitesse().addition(this.boundPosition(cacheBoid.get(i))));
            this.limitVelocity(cacheBoid.get(i));


            cacheBoid.get(i).setPosition(b.getPosition().addition(cacheBoid.get(i).getVitesse()));
            circlePosition(cacheBoid.get(i));

        }

        this.boids = cacheBoid;
    }

    // Méthode pour appliquer les règles de déplacement non prioritaire à un boid (cohésion / séparation / alignement)
    public ArrayList<Vector> rules(Boid boid) {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(rule1(boid));
        //vectors.add(rule2(boid));
        vectors.add(rule3(boid));

        return vectors;
    }

    // Méthode pour appliquer les règles de priorité à un boid (Chasser et fuire passent devant toute les autres rgèles)
    public ArrayList<Vector> priorityRules(Boid boid) {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(rule5(boid));
        vectors.add(rule4(boid));
        return vectors;
    }

    // Renvoie la vitesse maximale (vlim) pour les boids dans cet essaim.
    public double getVlim() {
        return vlim;
    }

    // Réinitialise la position des boids en les remplaçant par leurs positions initiales.

    public void reInit() {
        this.boids.clear();
        for (Boid initBoid : this.initBoids) {
            this.boids.add(initBoid.copy());
        }
    }

    // Met à jour la position des boids en appelant la méthode interne moveAllBoidsToNewPosition().
    public void update() {
        this.moveAllBoidsToNewPosition();
        this.start = true;
    }

    // Dessine les boids sur l'interface graphique donnée (GUI).
    public void draw(GUISimulator gui) {

        for (Boid boid : this.boids) {
            // Obtient les coordonnées et la vitesse du boid.
            double x = boid.getPosition().getX();
            double y = boid.getPosition().getY();
            double vx = boid.getVitesse().getX();
            double vy = boid.getVitesse().getY();


            // Calcule l'angle de déplacement du boid.
            double angle = Math.atan2(vy, vx);

            // Calcule les points pour dessiner un triangle représentant le boid.
            int[] xPoints = {
                    (int) x,
                    (int) (x - this.size * Math.cos(angle + Math.PI / 7)),
                    (int) (x - this.size * Math.cos(angle - Math.PI / 7))
            };
            int[] yPoints = {
                    (int) y,
                    (int) (y - this.size * Math.sin(angle + Math.PI / 7)),
                    (int) (y - this.size * Math.sin(angle - Math.PI / 7))
            };
            // Ajoute le triangle à l'interface graphique.
            gui.addGraphicalElement(new Triangle(xPoints, yPoints, Color.GRAY, this.color));
        }
    }

    // Définit une méthode abstraite pour configurer les événements de l'essaim.
    public abstract void setUpEvent(EventManager eventManager);

    // Renvoie la liste des boids dans cet essaim.
    public ArrayList<Boid> getBoids() {
        return boids;
    }

    // Renvoie le nom de l'essaim.
    public String getName() {
        return name;
    }

    // Renvoie la distance utilisée dans les règles de mouvement des boids.
    public double getDistance() {
        return distance;
    }

    // Gestionnaire d'événement : clic de souris (non implémenté dans cette classe abstraite).
    @Override
    public void handleClick(int x, int y, int panelX, int panelY) {
        // Ne fait rien dans cette classe abstraite.
    }

    // Gestionnaire d'événement : mouvement de souris (non implémenté dans cette classe abstraite).
    @Override
    public void handleMouseMotion(int x, int y, GUISimulator gui) {
        // Ne fait rien dans cette classe abstraite.
    }

    // Renvoie une représentation sous forme de chaîne de caractères de l'essaim.
    @Override
    public String toString() {
        return "Essaim{" +
                "boids=" + boids +
                ", initBoids=" + initBoids +
                ", distance=" + distance +
                ", vlim=" + vlim +
                ", size=" + size +
                ", color=" + color +
                ", name='" + name + '\'' +
                '}';
    }
}
