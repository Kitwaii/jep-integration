package net.imagej.jep.scenarios.scenario2;

import jep.JepException;
import jep.NDArray;
import net.imagej.jep.scenarios.IScenario;
import net.imagej.jep.scenarios.utils.UserJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Class scenario to test the exchange of data between Java and Python
 * -> Second test, create data in Java, send it to Python and print the result in Python or in Java
 *
 * @author Amandine Tournay
 */
public class SendSimpleData extends IScenario {
    @Override
    public void runScenario() {
        System.out.println();
        System.out.println("---- Send data to Python ----");

        try {
            openJep();

            getJepInter().exec("import numpy");

            // Numbers
            getJepInter().set("x", 476);
            getJepInter().set("y", 1437);
            getJepInter().set("z", 125.96);
            getJepInter().set("addition", (getJepInter().getValue("x", Integer.class) + getJepInter().getValue("y", Integer.class)));
            getJepInter().set("multiplication", (getJepInter().getValue("z", Float.class) * getJepInter().getValue("y", Integer.class)));

            // Booleans
            getJepInter().set("boolTrue", true);
            getJepInter().set("boolCondition", getJepInter().getValue("addition", Integer.class) == 1913);

            // Lists
            getJepInter().set("testList", new ArrayList<>(Arrays.asList(3, 6, 9, 12, 15)));
            getJepInter().set("testTuple", Collections.unmodifiableCollection(new ArrayList<>(Arrays.asList(0, 2, 4, 6, 8))));
            getJepInter().set("dict", new HashMap<String, String>() {{
                put("fruit", "apple");
                put("vegetable", "carrot");
                put("nut", "hazelnut");
            }});
            getJepInter().set("npndarray", new NDArray<>(new double[]{28.4, 459.12, 9., 4427.5}, 2, 2));

            // String
            getJepInter().set("string", "Hello World !");

            // Objects
            getJepInter().set("User", UserJava.class);

            // Python results
            getJepInter().eval("print(f'x = {x}')");
            getJepInter().eval("print(f'y = {y}')");
            getJepInter().eval("print(f'z = {z}')");
            getJepInter().eval("print(f'addition = {addition}')");
            getJepInter().eval("print(f'multiplication = {multiplication}')");
            getJepInter().eval("print()");
            getJepInter().eval("print(f'boolTrue = {boolTrue}')");
            getJepInter().eval("print(f'boolCondition (addition = 1913) = {boolCondition}')");
            getJepInter().eval("print()");
            getJepInter().eval("print(f'list : {testList}')");
            getJepInter().eval("print(f'tuple: {testTuple}')");
            getJepInter().eval("print(f'dict: {dict}')");
            getJepInter().eval("print('numpy.ndarray:')");
            getJepInter().eval("print(str(npndarray))");
            getJepInter().eval("print()");
            getJepInter().eval("print(f'string: {string}')");
            getJepInter().eval("print()");
            getJepInter().exec("user = User('Jean', 'Dupont')");
            getJepInter().eval("print(f'message from class User: {user.sayHello()}')");
            System.out.println("_______________________");
            System.out.println();
        }
        catch (JepException e) {
            throwJepException(e);
        }

        closeJep();
    }
}
