import java.awt.*;

public class Requins extends Essaim {
    public Requins(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(300, 45, 30, Xmax, Xmin, Ymax, Ymin, Color.RED);
    }

    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,3,this));
    }
}
