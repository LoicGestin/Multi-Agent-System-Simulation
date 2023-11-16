// La classe ActionEvent hérite de la classe abstraite Event et représente un événement d'action périodique.
public class ActionEvent extends Event{
    // Le gestionnaire d'événements auquel l'événement est associé.
    private EventManager eventManager;
    // L'intervalle entre chaque exécution de l'événement d'action.
    private int pas;
    // Le jeu associé à l'événement d'action.
    private Game game;
    // Constructeur de la classe ActionEvent
    public ActionEvent(long date, EventManager eventManager, int pas, Game game) {
        super(date);
        this.pas = pas;
        this.eventManager = eventManager;
        this.game = game;
    }


    // Méthode exécutée lorsqu'un événement d'action est déclenché.
    @Override
    public void execute() {
        // Met à jour l'état du jeu en appelant la méthode update().
        this.game.update();
        // Ajoute un nouvel événement d'action à l'EventManager avec une date future.
        this.eventManager.addEvent(new ActionEvent(getDate() + this.pas, eventManager, pas,game));
    }
}
