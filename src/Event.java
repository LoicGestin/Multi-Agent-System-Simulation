// La classe abstraite Event définit un événement générique.
public abstract class Event {
    // La date à laquelle l'événement est associé.
    private long date;
    // Constructeur de la classe Event.
    public Event(long date) {
        this.date = date;
    }
    // Getter pour obtenir la date de l'événement.
    public long getDate() {
        return date;
    }
    // Méthode abstraite à implémenter par les sous-classes pour définir le comportement de l'événement.
    public abstract void execute();
}
