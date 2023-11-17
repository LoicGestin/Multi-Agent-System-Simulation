import java.awt.*;

public class TestBalls {
    public static void main(String[] args) {
        Balls balls = new Balls();
        balls.add_ball(new Point(1, 0));
        balls.add_ball(new Point(4, 5));
        balls.add_ball(new Point(2, 6));
        System.out.println(balls);
        balls.translate(5, 2);
        System.out.println(balls);
        balls.reInit();
        System.out.println(balls);
    }
}