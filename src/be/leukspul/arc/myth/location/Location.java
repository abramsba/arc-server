package be.leukspul.arc.myth.location;

import be.leukspul.arc.myth.event.Event;
import be.leukspul.arc.myth.history.WhenCompare;

import java.util.Set;
import java.util.TreeSet;

public class Location {

    public Location(String name) {
        this.name = name;
        this.events = new TreeSet<>(WhenCompare.ref);
    }

    public String toString() {
        return name();
    }

    public String name() { return name; }
    public Set<Event> events() { return events; }

    private String name;
    private Set<Event> events;
}
