package net.imagej.jep.scenarios.test;

import jep.JepException;
import net.imagej.jep.scenarios.IScenario;

public class Test extends IScenario {
    @Override
    public void runScenario() {
        try {
            openJep();

            getJepInter().exec("import jep");
            getJepInter().exec("import os");
            getJepInter().exec("print(f'PYTHONPATH : ', os.environ.get('PYTHONPATH'))");
            getJepInter().exec("print(f'PATH : ', os.environ.get('PATH'))");
            getJepInter().exec("print(f'Numpy support in JEP: {jep.JEP_NUMPY_ENABLED}')");

            getJepInter().exec("import numpy");

            closeJep();
        }
        catch (JepException e) {
            throwJepException(e);
        }
    }
}
