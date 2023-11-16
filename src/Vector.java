// La classe Vector représente un vecteur bidimensionnel.
public class Vector {
    private double x;
    private double y;
    // Constructeur prenant les composantes x et y du vecteur.
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    // Méthode pour effectuer l'addition de deux vecteurs et retourner le résultat.
    public Vector addition(Vector vector){
        return new Vector(this.x + vector.x, this.y + vector.y);
    }
    // Méthode pour effectuer la soustraction de deux vecteurs et retourner le résultat.
    public Vector soustraction(Vector vector){
        return new Vector(this.x - vector.x, this.y - vector.y);
    }
    // Méthode pour calculer le produit scalaire de deux vecteurs.
    public double dotProduct(Vector other) {
        return this.x * other.x + this.y * other.y;
    }
    // Méthode pour diviser chaque composante du vecteur par un nombre donné.
    public Vector division(double divide){
        return new Vector(this.x / divide, this.y / divide);
    }
    // Méthode pour multiplier chaque composante du vecteur par un nombre donné.
    public Vector multiplication(double multiply){
        return new Vector(this.x * multiply, this.y * multiply);
    }
    // Méthode pour calculer la magnitude (norme) du vecteur.
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    // Méthode pour normaliser le vecteur (le rendre unitaire).
    public void normalize(){
        double magnitude = this.magnitude();
        if(magnitude != 0 ){
            this.x /= magnitude;
            this.y /= magnitude;
        }
    }
    // Getters et setters pour les composantes x et y du vecteur.
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    // Méthode pour afficher le vecteur sous forme de chaîne de caractères.
    @Override
    public String toString() {
        return "[ " + this.x + ", " + this.y + " ]";
    }
}
