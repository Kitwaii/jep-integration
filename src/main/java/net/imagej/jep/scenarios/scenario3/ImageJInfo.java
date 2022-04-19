package net.imagej.jep.scenarios.scenario3;

import jep.JepException;
import net.imagej.jep.scenarios.IScenario;

/**
 * Class scenario to test if ImageJ is imported and launching well and retrieve its information from Python
 */
public class ImageJInfo extends IScenario {
    @Override
    public void runScenario(boolean firstRun) {
        System.out.println();
        System.out.println("---- Get ImageJ version from Python ----");

        try {
            openJep(firstRun);
            loadImageJ(false);

            getJepInter().exec("ijInfo = ImageJ.getVersion()");
            System.out.println("ImageJ Version: " + getJepInter().getValue("ijInfo"));

            closeJep();
        }
        catch (JepException e) {
            throwJepException(e);
        }
    }
}
