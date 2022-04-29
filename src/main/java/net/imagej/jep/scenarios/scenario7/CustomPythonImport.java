package net.imagej.jep.scenarios.scenario6;

import net.imagej.jep.scenarios.IScenario;

/**
 * Class scenario to test if it is possible to easily import class dynamically.
 * Test to be inserted in a new version of the jimport function for SciJava
 *
 * @author Amandine Tournay
 */
public class CustomPythonImport extends IScenario {
    @Override
    public void runScenario(boolean firstRun) {
        openJep(firstRun);

        getJepInter().exec("from importlib import import_module");

        getJepInter().exec("def import_from(class_name):\n" +
                "     module_path = class_name.rsplit('.', 1)\n" +
                "     module = import_module(module_path[0], module_path[1])\n" +
                "     return getattr(module, module_path[1])");

        getJepInter().exec("User = import_from('net.imagej.jep.scenarios.utils.UserJava')");
        getJepInter().exec("user1 = User('Jean', 'Dupont')");

        getJepInter().exec("print(user1.sayHello())");

        closeJep();
    }
}
