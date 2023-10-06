import gui.GUISimulator;

import java.util.ArrayList;

public class GroupeEssaim implements Game{

    private ArrayList<Essaim> essaims;

    public GroupeEssaim(){
        this.essaims = new ArrayList<>();
    }

    public void addEsaim(Essaim essaim){
        this.essaims.add(essaim);
    }
    @Override
    public void reInit() {
        for (Essaim essaim: this.essaims) {
            essaim.reInit();
        }
    }

    @Override
    public void update() {
        for (Essaim essaim: this.essaims) {
            essaim.update();
        }
    }
    @Override
    public void draw(GUISimulator gui) {
        gui.reset();
        for (Essaim essaim: this.essaims) {
            essaim.draw(gui);
        }
    }

    @Override
    public void setUpEvent(EventManager eventManager) {
        for(Essaim essaim: this.essaims){
            essaim.setUpEvent(eventManager);
        }
    }
}
