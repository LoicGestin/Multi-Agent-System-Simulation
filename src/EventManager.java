import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

// La classe EventManager gère une liste d'événements et les exécute dans l'ordre chronologique.
public class EventManager {
    // Utilisation d'une file de priorité pour maintenir les événements triés par date.
    private final PriorityQueue<Event> events = new PriorityQueue<>(Comparator.comparingLong(Event::getDate));
    // Date actuelle de la simulation.
    private long currentDate = 0;

    // Ajoute un événement à la file de priorité.
    public void addEvent(Event event) {
        this.events.add(event);
    }

    // Exécute tous les événements dont la date est égale à la date actuelle.
    public void next() {

        ArrayList<Event> eventsToExecute = new ArrayList<>();

        for (Event e : this.events) {
            if (e.getDate() <= currentDate) {
                eventsToExecute.add(e);
            } else {
                break;
            }
        }
        for (Event e : eventsToExecute) {
            e.execute();
            this.events.remove(e);
        }
        currentDate++;
    }
    // Vérifie si la file d'événements est vide, indiquant que la simulation est terminée.

    public boolean isFinished() {
        return this.events.isEmpty();
    }
    // Réinitialise le gestionnaire d'événements en remettant la date à zéro et en vidant la file.

    public void restart() {
        currentDate = 0;
        events.clear();
    }
}
