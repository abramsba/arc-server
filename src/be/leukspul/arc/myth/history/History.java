package be.leukspul.arc.myth.history;

import be.leukspul.arc.library.Library;
import be.leukspul.arc.library.repository.WordRepository;
import be.leukspul.arc.myth.event.Event;
import be.leukspul.arc.myth.figure.Figure;
import be.leukspul.arc.myth.location.Location;

import java.util.*;

public class History {

    public History() {
        figures = new ArrayList<>();
        locations = new ArrayList<>();
        events = new TreeSet<>(WhenCompare.ref);
        words = (new Library()).WordRepository();
        epoch();
    }

    private String keyEin;
    private String keyGran;

    private void epoch() {

        Location universe = addLocation("Universe");
        increaseTime(1);
        Figure einhasad = addFigure(universe, "Einhasad", "female", null, null);
        Figure gran = addFigure(universe, "Gran Kain", "male", null, null);
        Location east = addLocation("Eastverse", einhasad);
        Location west = addLocation("Westverse", gran);
        creation(einhasad, gran, east, west);
    }

    private void creation(Figure einhasad, Figure gran, Location east, Location west) {
        // Einhasad creates three females in her likeness that share elements water, earth, and wind

        keyEin = words.randomLang();
        System.out.println("Ein: " + keyEin);

        String waterLN = words.lang(keyEin).random();
        String windLN = words.lang(keyEin).random();
        String earthLN = words.lang(keyEin).random();
        String waterName = waterLN + " of Water";
        String windName = windLN + " of Wind" ;
        String earthName = earthLN + " of Earth";
        Figure ein1 = addFigure(east, waterName, "female", einhasad, null);
        Figure ein2 = addFigure(east, windName, "female", einhasad, null);
        Figure ein3 = addFigure(east, earthName, "female", einhasad, null);
        Location ein1home = addLocation(waterLN + ", dominion of Water", einhasad, ein1);
        Location ein2home = addLocation(windLN + ", dominion of Wind", einhasad, ein2);
        Location ein3home = addLocation(earthLN + ", dominion of Earth", einhasad, ein3);


        keyGran = keyEin;
        while (keyGran == keyEin) {
            keyGran = words.randomLang();
        }
        System.out.println("Kran: " + keyGran);

        // Two gods of fire
        String fireName1 = words.lang(keyGran).random() + " of the Northern Fire";
        String fireName2 = words.lang(keyGran).random() + " of the Southern Fire";
        Figure human1 = addFigure(west, fireName1, "male", null, gran);
        Figure dwarf1 = addFigure(west, fireName2, "male", null, gran);




    }

    public void increaseTime(long time) {
        timeIndex = 0;
        currentTime += time;
    }

    public Figure addFigure(Location birthplace, String name, String gender, Figure mother, Figure father) {
        Figure figure = new Figure(name, gender, mother, father);
        if (mother == null && father == null) {
            addEvent(birthplace, String.format("`%s` exists.", name), figure);
        }
        else if (father != null && mother != null) {
            addEvent(birthplace, String.format("The %s `%s` was given life by mother `%s` and father `%s`.", figure.baby(), name, mother.name(), father.name()), figure, mother, father);
            mother.children().add(figure);
            father.children().add(figure);
        }
        else if (mother != null) {
            addEvent(birthplace, String.format("`%s` has given birth to `%s`, a %s.", mother.name(), name, figure.baby()), figure, mother);
        }
        else if (father != null) {
            addEvent(birthplace, String.format("`%s` has created `%s`, a %s.", father.name(), name, figure.baby()), figure, father);
        }
        figures.add(figure);
        return figure;
    }

    public Location addLocation(String name, Figure ...figureIn) {
        Location location = new Location(name);
        if (figureIn.length == 0) {
            addEvent(location, String.format("`%s` has emerged.", name));
        }
        else {
            addEvent(location, String.format("`%s` has been established.", name), figureIn);
        }
        locations.add(location);
        return location;
    }

    public Event addEvent(Location location, String description, Figure ...figureIn) {
        Event event = new Event(currentTime, timeIndex, location, figureList(figureIn), description);
        location.events().add(event);
        for(Figure f : figureIn) {
            f.events().add(event);
        }
        events.add(event);
        timeIndex++;
        return event;
    }

    private static List<Figure> figureList(Figure ...figuresIn) {
        List<Figure> output = new ArrayList<>();
        for(Figure f : figuresIn) {
            output.add(f);
        }
        return output;
    }

    public void print() {
        for(Event e : events) {
            System.out.println(e.toString());
        }
    }

    private long currentTime; // Numbers before 10k are considered years, after that days
    private long timeIndex;

    private List<Figure> figures;
    private List<Location> locations;
    private Set<Event> events;

    private WordRepository words;

    private static Random random = new Random();

    public static void main(String [] args) {
        History history = new History();
        history.print();
    }



}
