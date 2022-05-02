package net.imagej.jep.scenarios.scenario1;

import jep.JepException;
import net.imagej.jep.scenarios.IScenario;

/**
 * Class scenario to test if JEP is running correctly
 *
 * @author Amandine Tournay
 */
public class JEPTest extends IScenario {
    @Override
    public void runScenario() {
        System.out.println("---- JEP Test ----");
        System.out.println();

        try {
            openJep();

            getJepInter().exec("from java.lang import System");
            getJepInter().exec("s = 'Hello World'");
            getJepInter().exec("System.out.println(s)");
            getJepInter().exec("print(s)");
            getJepInter().exec("print(s[1:-1])");
        }
        catch (JepException e) {
            throwJepException(e);
        }

        closeJep();
    }
}
