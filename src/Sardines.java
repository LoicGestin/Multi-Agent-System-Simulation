import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sardines extends Essaim{

    public Sardines(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(100, 20, 10, Xmax, Xmin, Ymax, Ymin, Color.BLUE,"Sardines",
                new ArrayList<String>(),
                new ArrayList<>(List.of(new String[]{"Requins"})));
    }


    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,1,this));
    }

}
