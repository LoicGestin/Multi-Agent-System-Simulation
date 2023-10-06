import java.awt.*;

public class Saumons extends Essaim{
    public Saumons(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(250, 30, 30, Xmax, Xmin, Ymax, Ymin, Color.YELLOW);
    }
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,2,this));
    }
}
