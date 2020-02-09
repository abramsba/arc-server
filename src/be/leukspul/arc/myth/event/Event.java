package be.leukspul.arc.myth.event;

import be.leukspul.arc.myth.Colors;
import be.leukspul.arc.myth.figure.Figure;
import be.leukspul.arc.myth.location.Location;

import java.util.List;

public class Event {

    public Event(long when, long index, Location location, List<Figure> figures, String description) {
        this.when = when;
        this.figures = figures;
        this.location = location;
        this.index = index;
        this.description = description;
    }

    public long when() { return when; }
    public long index() { return index; }
    public List<Figure> figures() { return figures; }
    public Location location() { return location; }
    public String description() { return description; }

    public String toString() {
        String output = "[%d, %d] %s\n";
        output = String.format(output, when, index, description);
        output += Colors.ANSI_YELLOW+"\tLocation"+Colors.ANSI_RESET+": "+location.name()+"\n";
        if (figures.size() > 0) {
            output += Colors.ANSI_YELLOW+"\tConnected"+Colors.ANSI_RESET+":\n";
        }
        for(Figure f : figures) {
            output += String.format("\t\t%s\n", f);
        }
        return output;
    }
    private String description;
    private long when;
    private long index;
    private List<Figure> figures;
    private Location location;
}
