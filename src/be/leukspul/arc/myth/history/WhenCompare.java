package be.leukspul.arc.myth.history;

import be.leukspul.arc.myth.event.Event;

import java.util.Comparator;

public class WhenCompare implements Comparator<Event> {

    public static WhenCompare ref = new WhenCompare();

    @Override
    public int compare(Event o1, Event o2) {
        if (o1.when() == o2.when()) {
            if (o1.index() > o2.index()) {
                return 1;
            }
            else {
                return -1;
            }
            //return 0;
        }
        else if (o1.when() > o2.when()) {
            return 2;
        }
        return -2;
    }
}
