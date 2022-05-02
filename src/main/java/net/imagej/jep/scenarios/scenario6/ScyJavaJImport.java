package net.imagej.jep.scenarios.scenario6;

import net.imagej.jep.scenarios.IScenario;
import net.imagej.jep.scenarios.utils.UserJava;

public class ScyJavaJImport extends IScenario {
    @Override
    public void runScenario() {
        openJep();

        getJepInter().exec("from scyjava import jimport");
        getJepInter().exec("User = jimport('net.imagej.jep.scenarios.utils.UserJava')");
        getJepInter().exec("user1 = User('Jean', 'Dupont')");

        UserJava user1 = getJepInter().getValue("user1", UserJava.class);
        System.out.println(user1.sayHello());

        closeJep();
    }
}
