package be.leukspul.arc.library.repository;

import be.leukspul.data.trie.Trie;
import be.leukspul.math.Rand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordRepository {

    public WordRepository() {
        languageTrees = new HashMap<>();
    }

    public void addLanguage(String code, String path) {
        try {
            Trie t = new Trie();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = null;
            int lines = 0;
            while ((line = reader.readLine()) != null) {
                t.addWord(line);
                lines++;
            }
            languageTrees.put(code, t);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Trie lang(String lang) {
        return languageTrees.get(lang);
    }

    public String randomLang() {
        List<String> keys = new ArrayList<>(languageTrees.keySet());
        return keys.get(Rand.getInt(keys.size()));
    }

    public Map<String, Trie> languageTrees;

}
