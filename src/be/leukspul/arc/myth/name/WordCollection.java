package be.leukspul.arc.myth.name;

import java.util.ArrayList;
import java.util.List;

public class WordCollection {

    public WordCollection(String type, String language) {
        this.type = type;
        this.language = language;
        this.words = new ArrayList<>();
    }

    public String type() { return type; }
    public String language() { return language; }

    public void add(String word) {
        words.add(word);
    }

    public String random(String regex) {
        return "";
    }

    private String type;
    private String language;
    private List<String> words;

}
