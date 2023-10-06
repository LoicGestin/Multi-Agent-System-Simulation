public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector addition(Vector vector){
        return new Vector(this.x + vector.x, this.y + vector.y);
    }
    public Vector soustraction(Vector vector){
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    public Vector division(double divide){
        return new Vector(this.x / divide, this.y / divide);
    }

    public Vector multiplication(double multiply){
        return new Vector(this.x * multiply, this.y * multiply);
    }
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

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

    @Override
    public String toString() {
        return "[ " + this.x + ", " + this.y + " ]";
    }
}
