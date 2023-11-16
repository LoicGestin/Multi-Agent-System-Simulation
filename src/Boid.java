// La classe Boid représente un agent (entité) dans le modèle de simulation.
public class Boid {
    private Vector position;// Position actuelle du boid.
    private Vector vitesse;// Vecteur de vitesse du boid.
    // Constructeur prenant la position et la vitesse initiales du boid.
    public Boid(Vector position, Vector vitesse){
        this.position = position;
        this.vitesse = vitesse;
    }
    // Méthode pour remplacer les attributs d'un boid par ceux d'un autre boid.
    public void replace(Boid b){
        this.position.setX(b.position.getX());
        this.position.setY(b.position.getY());

        this.vitesse.setX(b.vitesse.getX());
        this.vitesse.setY(b.vitesse.getY());
    }
    // Méthode pour créer une copie du boid avec la même position et vitesse.
    public Boid copy(){
        return new Boid(new Vector(this.position.getX(),this.position.getY()),new Vector(this.vitesse.getX(),this.vitesse.getY()));
    }
    // Getters et setters pour la position et la vitesse du boid.
    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVitesse() {
        return vitesse;
    }

    public void setVitesse(Vector vitesse) {
        this.vitesse = vitesse;
    }
    // Méthode pour afficher les informations du boid sous forme de chaîne de caractères.
    @Override
    public String toString() {
        return "Boid{" +
                "position=" + position +
                ", vitesse=" + vitesse +
                '}';
    }
}
