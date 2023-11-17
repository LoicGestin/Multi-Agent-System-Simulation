import java.awt.*;
import java.util.ArrayList;

public class Balls {
    private final ArrayList<Point> balls;
    private final ArrayList<Integer> posX;
    private final ArrayList<Integer> posY;

    private final ArrayList<Boolean> direct_X;

    private ArrayList<Boolean> direct_Y;

    public Balls() {
        this.balls = new ArrayList<>();
        this.posX = new ArrayList<>();
        this.posY = new ArrayList<>();
        this.direct_X = new ArrayList<>();
        this.direct_Y = new ArrayList<>();
    }

    public Balls(ArrayList<Point> balls) {
        this.balls = new ArrayList<>();
        this.direct_X = new ArrayList<>();
        this.posX = new ArrayList<>();
        this.posY = new ArrayList<>();
        for (Point ball : balls) {
            this.addBall(ball);
        }
    }

    public void addBall(Point balle) {
        this.balls.add(balle);
        this.posY.add(balle.y);
        this.posX.add(balle.x);
        this.direct_X.add(false);
        this.direct_Y.add(false);
    }

    public void translate(int dx, int dy) {
        for (Point ball : this.balls) {
            ball.translate(dx, dy);
        }
    }

    public void translate(int dx, int dy, int limit_x, int limiy_y) {
        for (int i = 0; i < this.balls.size(); i++) {

            int pos_x = this.direct_X.get(i) ? this.balls.get(i).x - dx : dx + this.balls.get(i).x;
            int pos_y = this.direct_Y.get(i) ? this.balls.get(i).y - dy : dy + this.balls.get(i).y;

            if (pos_x > limit_x || pos_x < 0) {
                this.direct_X.set(i, !this.direct_X.get(i));
            }
            if (pos_y > limiy_y || pos_y < 0) {
                this.direct_Y.add(i, !this.direct_Y.get(i));
            }
            this.balls.get(i).translate(this.direct_X.get(i) ? -dx : dx, this.direct_Y.get(i) ? -dy : dy);

        }

    }

    public void reInit() {
        for (int i = 0; i < this.balls.size(); i++) {
            this.balls.get(i).setLocation(this.posX.get(i), this.posY.get(i));
            this.direct_X.add(i, false);
            this.direct_Y.add(i, false);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Point ball : this.balls) {
            result.append("( ").append(ball.x).append(", ").append(ball.y).append(" ) ");
        }
        return result.toString();
    }

    public ArrayList<Point> getBalls() {
        return balls;
    }

    public Point getBall(int i) {
        return this.balls.get(i);
    }
}
