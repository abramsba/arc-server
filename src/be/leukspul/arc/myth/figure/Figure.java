package be.leukspul.arc.myth.figure;

import be.leukspul.arc.myth.Colors;
import be.leukspul.arc.myth.event.Event;
import be.leukspul.arc.myth.history.WhenCompare;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Figure {

    public Figure(String name, String gender, Figure mother, Figure father) {
        this.name = name;
        this.gender = gender;
        this.events = new TreeSet<>(WhenCompare.ref);
        this.children = new ArrayList<>();
        this.mother = mother;
        this.father = father;
    }

    public Figure mother() { return mother; }
    public Figure father() { return father; }
    public List<Figure> children() { return children; }

    public String toString() {
        if (gender.equals("male")) {
            return Colors.ANSI_CYAN+"♂ "+Colors.ANSI_RESET + name();
        }
        return Colors.ANSI_PURPLE+"♀ "+Colors.ANSI_RESET + name();
    }

    public String name() { return name; }
    public String gender() { return gender; }
    public String baby() {
        if (gender.equals("male")) {
            return "boy";
        }
        return "girl";
    }

    public Set<Event> events() { return events; }

    private String name;
    private String gender;
    private Set<Event> events;
    private Figure mother;
    private Figure father;
    private List<Figure> children;

}
