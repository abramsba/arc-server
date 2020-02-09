package be.leukspul.arc.library;

import be.leukspul.arc.html.HtmTemplate;
import be.leukspul.arc.library.repository.*;

import javax.script.ScriptException;
import java.io.FileNotFoundException;

public class Library {

    public Library() {
        PcTemplates();
        pcTemplates = new PcTemplates(PcTemplateFile);
        npcTemplates = new NpcTemplates(NpcTemplateFile);
        doorTemplates = new DoorTemplates(DoorTemplateFile);
        itemTemplates = new ItemTemplates(ItemTemplateFile);
        skillTemplates = new SkillTemplates(SkillTemplateFile);

        wordRepository = new WordRepository();
        wordRepository.addLanguage("us", "./data/lang/names/us.txt");
        wordRepository.addLanguage("de", "./data/lang/names/de.txt");
        wordRepository.addLanguage("us", "./data/lang/names/es.txt");
        wordRepository.addLanguage("de", "./data/lang/names/ru.txt");
        wordRepository.addLanguage("fr", "./data/lang/names/us.txt");
        wordRepository.addLanguage("uk", "./data/lang/names/de.txt");
        wordRepository.addLanguage("pl", "./data/lang/names/us.txt");

        try {
            htmTemplates = new HtmTemplate("./lib/mustache.js", "./data/html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public PcTemplates PcTemplates() {
        return pcTemplates;
    }

    public NpcTemplates NpcTemplates() {
        return npcTemplates;
    }

    public DoorTemplates DoorTemplates() { return doorTemplates; }

    public ItemTemplates ItemTemplates() { return itemTemplates; }

    public SkillTemplates SkillTemplates() { return skillTemplates; }

    public HtmTemplate htmTemplates() { return htmTemplates; }

    public WordRepository WordRepository() { return wordRepository; }

    public static String PcTemplateFile = "./data/pc.json";
    public static String NpcTemplateFile = "./data/npc.json";
    public static String DoorTemplateFile = "./data/doors.json";
    public static String ItemTemplateFile = "./data/items.json";
    public static String SkillTemplateFile = "./data/skills.json";

    private PcTemplates pcTemplates;
    private NpcTemplates npcTemplates;
    private DoorTemplates doorTemplates;
    private ItemTemplates itemTemplates;
    private SkillTemplates skillTemplates;
    private HtmTemplate htmTemplates;
    private WordRepository wordRepository;
}
