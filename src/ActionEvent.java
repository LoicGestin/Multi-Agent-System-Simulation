public class ActionEvent extends Event{
    private EventManager eventManager;
    private int pas;

    private Game game;
    public ActionEvent(long date, EventManager eventManager, int pas, Game game) {
        super(date);
        this.pas = pas;
        this.eventManager = eventManager;
        this.game = game;
    }


    // Méthode exécutée lorsqu'un événement d'action est déclenché
    @Override
    public void execute() {
        this.game.update();
        this.eventManager.addEvent(new ActionEvent(getDate() + this.pas, eventManager, pas,game));
    }
}
