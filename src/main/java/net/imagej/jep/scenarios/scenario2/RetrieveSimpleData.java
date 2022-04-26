package net.imagej.jep.scenarios.scenario2;

import jep.JepException;
import jep.NDArray;
import net.imagej.jep.scenarios.IScenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class scenario to test the exchange of data between Java and Python
 * -> First test, create data in Python and retrieve it with Java
 *
 * @author Amandine Tournay
 */
public class RetrieveSimpleData extends IScenario {
    @Override
    public void runScenario(boolean firstRun) {
        System.out.println();
        System.out.println("---- Retrieve data from Python ----");

        try {
            openJep(firstRun);

            getJepInter().exec("import numpy");

            // Number types and calculations
            getJepInter().exec("x = 476");
            getJepInter().exec("y = 1437");
            getJepInter().exec("z = 125.96");
            getJepInter().exec("addition = x + y");
            getJepInter().exec("multiplication = z * y");

            // Booleans
            getJepInter().exec("boolTrue = True");
            getJepInter().exec("boolFalse = False");
            getJepInter().exec("boolCondition = addition == 1913");

            // Lists
            getJepInter().exec("testList = [3, 6, 9, 12, 15]");
            getJepInter().exec("testTuple = (0, 2, 4, 6, 8)");
            getJepInter().exec("dict = {'fruit' : 'apple', 'vegetable' : 'carrot', 'nut' : 'hazelnut'}");

            getJepInter().exec("npndarray = numpy.ndarray(shape=(2, 2), dtype=float, buffer=numpy.array([[28.4, 459.12], [9., 4427.5]]))");

            // String
            getJepInter().exec("string = 'Hello World !'");

            // Object
            getJepInter().exec("class User:\n" +
                    "    def __init__(self, firstname, lastname):\n" +
                    "        self.firstname = firstname\n" +
                    "        self.lastname = lastname\n" +
                    "        \n" +
                    "    def getFirstname(self):\n" +
                    "        return self.firstname\n" +
                    "    \n" +
                    "    def getLastname(self):\n" +
                    "        return self.lastname\n" +
                    "    \n" +
                    "    def sayHello(self):\n" +
                    "        return 'Hello ' + self.getFirstname() + ' ' + self.getLastname() + '!'");
            getJepInter().exec("user = User('Jean', 'Dupont')");
            getJepInter().exec("user.sayHello()");

            /* Get the values from Python
             * JEP can automatically convert primitive types and numpy ndarrays, but if we want to save as a variable, we must specify
             * the type of object we want to get.
             */
            int getX = getJepInter().getValue("x", Integer.class);
            int getY = getJepInter().getValue("y", Integer.class);
            float getZ = getJepInter().getValue("z", Float.class);
            int getAddition = getJepInter().getValue("addition", Integer.class);
            float getMultiplication = getJepInter().getValue("multiplication", Float.class);
            boolean getBoolTrue = getJepInter().getValue("boolTrue", Boolean.class);
            boolean getBoolFalse = getJepInter().getValue("boolFalse", Boolean.class);
            boolean getBoolCondition = getJepInter().getValue("boolCondition", Boolean.class);
            ArrayList<Integer> getTestList = getJepInter().getValue("testList", ArrayList.class);
            List<Integer> testTuple = getJepInter().getValue("testTuple", List.class);
            Map<String, String> dict = getJepInter().getValue("dict", Map.class);
            NDArray<Float> ndArray = getJepInter().getValue("npndarray", NDArray.class);
            String string = getJepInter().getValue("string", String.class);
            String hello = getJepInter().getValue("user.sayHello()", String.class);

            // Print the results
            System.out.println("_______________________");
            System.out.println("x = " + getX);
            System.out.println("y = " + getY);
            System.out.println("z = " + getZ);
            System.out.println("addition = " + getAddition);
            System.out.println("multiplication = " + getMultiplication);
            System.out.println();
            System.out.println("boolTrue = " + getBoolTrue);
            System.out.println("boolFalse = " + getBoolFalse);
            System.out.println("boolCondition = " + getBoolCondition);
            System.out.println();
            System.out.println("testList = " + getTestList);
            System.out.println("testTuple = " + testTuple);
            System.out.println("dict = " + dict);
            System.out.println("npndarray dimension = " + Arrays.toString(ndArray.getDimensions()));
            System.out.println();
            System.out.println("string = " + string);
            System.out.println();
            System.out.println("Say hello through User class: " + hello);
            System.out.println("_______________________");
            System.out.println();

            closeJep();
        }
        catch (JepException e) {
            throwJepException(e);
        }
    }
}
