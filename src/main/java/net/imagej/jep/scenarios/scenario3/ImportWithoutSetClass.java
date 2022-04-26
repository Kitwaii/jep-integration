package net.imagej.jep.scenarios.scenario3;

import net.imagej.jep.scenarios.IScenario;

/**
 * Test to import a Java class without doing anything
 *
 * @author Amandine Tournay
 */
public class ImportWithoutSetClass extends IScenario {
    @Override
    public void runScenario(boolean firstRun) {
        openJep(firstRun);

        getJepInter().exec("from net.imagej.jep.scenarios.utils import UserJava");
        getJepInter().exec("user1 = UserJava('Jean', 'Dupont')");
        getJepInter().exec("print(user1.sayHello())");

        closeJep();
    }
}
