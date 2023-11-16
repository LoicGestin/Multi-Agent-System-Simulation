import gui.GUISimulator;

import java.util.ArrayList;
// Classe représentant un groupe d'essaims
public class GroupeEssaim implements Game{
    // Liste des essaims dans le groupe
    private ArrayList<Essaim> essaims;
    // Constructeur de la classe
    public GroupeEssaim(){
        this.essaims = new ArrayList<>();
    }
    // Méthode pour ajouter un essaim au groupe
    public void addEsaim(Essaim essaim){
        // Ajoute l'essaim aux essaims existants et vice versa
        for (Essaim e: this.essaims) {
            e.addEsseim(essaim);
            essaim.addEsseim(e);
        }
        // Ajoute l'essaim à la liste du groupe
        this.essaims.add(essaim);

    }
    // Méthode d'interface pour réinitialiser le groupe d'essaims
    @Override
    public void reInit() {
        for (Essaim essaim: this.essaims) {
            essaim.reInit();
        }
    }
    // Méthode d'interface pour mettre à jour le groupe d'essaims
    @Override
    public void update() {
        for (Essaim essaim: this.essaims) {
            essaim.update();
        }
    }
    // Méthode d'interface pour dessiner le groupe d'essaims sur l'interface graphique
    @Override
    public void draw(GUISimulator gui) {
        gui.reset();
        for (Essaim essaim: this.essaims) {
            essaim.draw(gui);
        }
    }
    // Méthode d'interface pour configurer les événements du gestionnaire d'événements
    @Override
    public void setUpEvent(EventManager eventManager) {
        for(Essaim essaim: this.essaims){
            essaim.setUpEvent(eventManager);
        }
    }
    // Méthode d'interface pour gérer un clic de souris
    @Override
    public void handleClick(int x, int y, int panelX, int panelY) {
        this.essaims.get(0).addBoid(new Vector(x,y), new Vector(5,5));
    }
    // Méthode d'interface pour gérer le mouvement de la souris, ici inutile.
    @Override
    public void handleMouseMotion(int x, int y, GUISimulator gui) {
        // Ne fait rien dans cette classe abstraite.
    }
    // Méthode pour obtenir une représentation sous forme de chaîne de caractères du groupe d'essaims
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Essaim essaim: this.essaims
             ) {
                result.append(essaim.toString());
        }
        return result.toString();
    }


}
