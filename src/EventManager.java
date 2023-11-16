import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventManager {
    private long currentDate = 0;

    private ArrayList<Event> events = new ArrayList<>();

    public void addEvent(Event event){
        this.events.add(event);
        events.sort(Comparator.comparingLong(Event::getDate));

    }
    public void next(){

        ArrayList<Event> eventsToExecute = new ArrayList<>();

        for (Event e: this.events) {
            if(e.getDate() <= currentDate){
                eventsToExecute.add(e);
            }
            else{
                break;
            }
        }
        for(Event e: eventsToExecute){
            e.execute();
            this.events.remove(e);
        }
        currentDate ++;
    }
    public boolean isFinished(){
       return this.events.isEmpty();
    }
    public void restart(){
        currentDate = 0;
        events.clear();
    }
}
