import java.awt.*;

public class Sardines extends Essaim{
    public Sardines(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(200, 20, 20, Xmax, Xmin, Ymax, Ymin, Color.BLUE);
    }
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,1,this));
    }

}
