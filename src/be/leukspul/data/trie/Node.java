package be.leukspul.data.trie;

import be.leukspul.math.Rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    public Node(Character character, boolean word) {
        this.children = new HashMap<>();
        this.character = character;
        this.words = new ArrayList<>();
        this.childList = new ArrayList<>();
        this.word = word;
    }

    public void addChild(Node sym) {
        children.put(sym.character, sym);
        childList.add(sym);
        if (sym.word) {
            this.words.add(sym);
        }
    }

    public Node randomWord() {
        /* Search List for a word at this level */
        return words.get(Rand.getInt(words.size()));
    }

    public Node randomChild() {
        if (childList.size() == 0) {
            return null;
        }
        return childList.get(Rand.getInt(childList.size()));
    }

    public boolean hasWords() {
        return words.size() > 0;
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    public Node getChild(Character c) {
        return children.get(c);
    }

    public List<Node> words() { return words; }

    public void word(boolean w) {
        word = w;
    }

    public boolean word() {
        return word;
    }

    public Node atIndex(int i) {
        return childList.get(i);
    }

    public List<Node> children() {
        return childList;
    }

    private boolean word;
    private Character character;
    private Map<Character, Node> children;
    private List<Node> childList;
    private List<Node> words;

    @Override
    public String toString() {
        return ""+this.character;
    }
}
