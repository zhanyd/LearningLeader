package p201608;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Created by Administrator on 2017/3/4 0004.
 */
public class L003 {
    public static void main(String[] args) throws Exception{
        ScriptEngineManager scriptEngineManager =
                new ScriptEngineManager();
        ScriptEngine nashorn =
                scriptEngineManager.getEngineByName("nashorn");
        String name = "Olli";
        //nashorn.eval("print('" + name + "')");
        nashorn.eval(new FileReader("F:\\IdeaProjects\\LearningLeader\\src\\p201608\\script.js"));

        Invocable invocable = (Invocable) nashorn;

        Object result = invocable.invokeFunction("getName", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());
    }


    public static String javaFun(String name) {
        System.out.format("Hi there from Java, %s", name);
        return " greetings from java";
    }
}
