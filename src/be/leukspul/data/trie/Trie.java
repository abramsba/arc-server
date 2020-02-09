package be.leukspul.data.trie;

import be.leukspul.math.Rand;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    public Trie() {
        this.root = new Node(' ', false);
        this.symbols = new ArrayList<>();
    }

    public List<String> getWords(String startsWith) {
        char [] letters = startsWith.toCharArray();
        String prefix = "";
        Node from = this.root;
        for(char letter : letters) {
            from = from.getChild(letter);
            if (from == null) {
                return null;
            }
            prefix += letter;
        }
        List<String> wordpool = new ArrayList<>();
        loop(from, wordpool, prefix);
        return wordpool;
    }

    private void loop (Node current, List<String> wordpool, String prefix) {
        // If this node is a word,
        if (current.word()) {
            wordpool.add(prefix);
        }
        for (int i = 0; i < current.children().size(); i++) {
            loop(current.children().get(i), wordpool, prefix+current.children().get(i).toString());
        }
    }

    public void addWord(String word) {
        char [] characters = word.toCharArray();
        Node current = null;
        char first = characters[0];
        Character firstSym = addSymbol(first);
        Node firstNode = root.getChild(firstSym); // todo find ref or create it
        if (firstNode == null) {
            if (characters.length == 1) {
                wordCount++;
                firstNode = new Node(firstSym, true);
                this.root.addChild(firstNode);
                return;
            }
            else {
                firstNode = new Node(firstSym, false);
                this.root.addChild(firstNode);
            }
        } else {
            if (characters.length == 1) {
                wordCount++;
                firstNode.word(true);
                return;
            }
        }
        current = firstNode;

        for(int i = 1; i < characters.length; i++) {
            char next = characters[i];
            Character symbol = addSymbol(next);
            Node child =  current.getChild(symbol);
            if (child == null) {
                Node nextNode = null;
                if (i == (characters.length) - 1) {
                    wordCount++;
                    nextNode = new Node(symbol, true);
                }
                else {
                    nextNode = new Node(symbol, false);
                }
                current.addChild(nextNode);
                current = nextNode;
            }
            else {
                if (i == (characters.length) - 1) {
                    current.words().add(child);
                    wordCount++;
                    child.word(true);
                }
                current = child;
            }
        }
    }

    public Character addSymbol(char c) {
        Character found = null;
        for(Character sym : symbols) {
            if (sym.equals(c)) {
                found = sym;
                break;
            }
        }
        if (found == null) {
            found = new Character(c);
            symbols.add(found);
        }
        return found;
    }

    public Character getSymbol(char c) {
        for(Character sym : symbols) {
            if (sym.equals(c)) {
                return sym;
            }
        }
        return null;
    }

    public String random() {
        Node current = this.root;
        String output = "";
        boolean loop = true;
        while (loop) {
            boolean getWord = Rand.getBool(0.35);
            if (getWord && current.hasWords()) {
                Node end = current.randomWord();
                output += end.toString();
                loop = false;
            }
            else {
                Node next = current.randomChild();
                if (next == null) {
                    output += current.toString();
                    loop = false;
                }
                else {
                    output += next.toString();
                    current = next;
                }
            }
        }
        return output;
    }

    public int count() { return wordCount; }

    private int wordCount;
    private Node root;
    private List<Character> symbols;
}
