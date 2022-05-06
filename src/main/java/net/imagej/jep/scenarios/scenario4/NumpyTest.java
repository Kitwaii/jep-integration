package net.imagej.jep.scenarios.scenario4;

import jep.JepException;
import net.imagej.jep.scenarios.IScenario;

/**
 * Test case to be sure that Numpy support is enabled in JEP. <br>
 * It can happen that there can be some issue as for example currently the necessity to use LD_PRELOAD in the terminal for Linux users
 * (fixed in Autumn 2022 with JEP 4.1). <br>
 * Or if you choose to build from source, sometimes it can do not detect well Numpy or if you install JEP before Numpy.
 * Install that case, redo the installation process.
 *
 * @author Amandine Tournay
 */
public class NumpyTest extends IScenario {
    @Override
    public void runScenario() {
        try {
            openJep();

            getJepInter().exec("import jep");
            getJepInter().exec("import os");

            getJepInter().exec("print(f'Numpy support in JEP (1 = True, 0 = False): {jep.JEP_NUMPY_ENABLED}')");

            try {
                getJepInter().exec("import numpy");
                System.out.println("Numpy have been imported through JEP !");
            }
            catch (JepException e) {
                System.out.println("Numpy could not been imported through JEP..");
                throwJepException(e);
            }

            closeJep();
        }
        catch (JepException e) {
            throwJepException(e);
        }
    }
}
