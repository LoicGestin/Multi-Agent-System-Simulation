public class Boid {
    private Vector position;
    private Vector vitesse;

    public Boid(Vector position, Vector vitesse){
        this.position = position;
        this.vitesse = vitesse;
    }

    public void replace(Boid b){
        this.position.setX(b.position.getX());
        this.position.setY(b.position.getY());

        this.vitesse.setX(b.vitesse.getX());
        this.vitesse.setY(b.vitesse.getY());
    }

    public Boid copy(){
        return new Boid(new Vector(this.position.getX(),this.position.getY()),new Vector(this.vitesse.getX(),this.vitesse.getY()));
    }
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

    @Override
    public String toString() {
        return "Boid{" +
                "position=" + position +
                ", vitesse=" + vitesse +
                '}';
    }
}
