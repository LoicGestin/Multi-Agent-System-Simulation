import java.awt.*;

public class CustomFish extends Essaim{

    private final int pas;
    public CustomFish(int distance, int size, int vlim, int Xmax, int Xmin, int Ymax, int Ymin,Color color, int pas) {
        super(distance, size, vlim, Xmax, Xmin, Ymax, Ymin, Color.RED, "CUSTOM");
        this.pas = pas;
    }


    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,this.pas,this));
    }
}
