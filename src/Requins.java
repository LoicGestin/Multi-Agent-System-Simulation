import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Requins extends Essaim {

    public Requins(int Xmax, int Xmin, int Ymax, int Ymin) {
        super(250, 22, 5, Xmax, Xmin, Ymax, Ymin, Color.RED,"Requins",
                new ArrayList<String>(List.of(new String[]{"Sardines", "Saumons"})),
                new ArrayList<String>());
    }

    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,3,this));
    }

}
