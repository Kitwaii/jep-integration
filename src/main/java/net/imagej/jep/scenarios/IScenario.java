package net.imagej.jep.scenarios;

import jep.Interpreter;
import jep.JepConfig;
import jep.JepException;
import jep.SharedInterpreter;
import net.imagej.ImageJ;
import net.imagej.jep.utils.JepClassEnquirer;

/**
 * Abstract class to use for all scenarios.
 * A contract is made to implement the main function to run the scenario, runScenario().
 * The other functions are common functions to run smoothly the scenario.
 */
public abstract class IScenario {
    private static Interpreter jepInter;
    private static JepConfig jepConfig;
    private static ImageJ ij;

    /**
     * Main function to run an example scenario
     *
     * @param firstRun Is the first that the scenario is run in the program instance
     */
    public abstract void runScenario(boolean firstRun);

    /**
     * Open new JEP SharedInterpreter
     *
     * @param firstRun Is the first that the scenario is run in the program instance
     */
    protected static void openJep(boolean firstRun) {
        jepConfig = new JepConfig();

        // We can configure JEP only before any SharedInterpreter has been created
        if (firstRun) {
            getJepConfig().setClassEnquirer(new JepClassEnquirer());
            SharedInterpreter.setConfig(getJepConfig());
        }

        jepInter = new SharedInterpreter();
    }

    /**
     * Close JEP SharedInterpreter
     */
    protected static void closeJep() {
        System.out.println();
        System.out.println("---- End of scenario ----");

        getJepInter().close();
    }

    /**
     * Throw a new JEPException in the console
     *
     * @param e JEPException thrown by JEP
     */
    protected static void throwJepException(JepException e) {
        System.out.println();
        System.err.println("---- Scenario Failed ----");

        e.printStackTrace();
    }

    /**
     * Create instance of ImageJ and send it to Python
     *
     * @param launch If ImageJ needs to be opened
     */
    protected static void loadImageJ(boolean launch) {
        ij = new ImageJ();
        getJepInter().set("ImageJ", ij);

        if (launch) {
            //getJepInter().exec("ij.launch()");
            ij.launch();
        }
    }

    protected static Interpreter getJepInter() {
        return jepInter;
    }

    public static JepConfig getJepConfig() {
        return jepConfig;
    }

    public static ImageJ getIj() {
        return ij;
    }
}
