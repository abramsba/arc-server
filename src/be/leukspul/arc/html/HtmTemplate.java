package be.leukspul.arc.html;

import org.json.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HtmTemplate {

    public HtmTemplate(String lib, String templateDir) throws FileNotFoundException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        FileReader libReader = new FileReader(lib);
        engine = manager.getEngineByName("nashorn");
        engine.eval(libReader);
        templates = new HashMap<>();
        File dir = new File(templateDir);
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                String templateName = file.getName().replaceFirst("[.][^.]+$", "");
                templates.put(templateName, file.getAbsolutePath());
            }
        }
        invocable = (Invocable)engine;
        json = engine.eval("JSON");
        mustache = engine.eval("Mustache");
    }

    private ScriptEngine engine;
    private Invocable invocable;
    private Object json;
    private Object mustache;
    private Map<String, String> templates;

    public String render(String name, JSONObject context) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        String output = null;
        String templatePath = templates.get(name);
        if (templatePath != null) {
            String templateStr = new Scanner(new File(templatePath)).useDelimiter("\\Z").next();
            String contextStr = context.toString();
            Object data = invocable.invokeMethod(json, "parse", contextStr);
            output = (String)invocable.invokeMethod(mustache, "render", templateStr, data);
        }
        return output;
    }

    public static void main(String [] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        HtmTemplate t = new HtmTemplate("lib/mustache.js", "data/html");
        System.out.println(t.render("test", new JSONObject()));
    }

}
