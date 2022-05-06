package net.imagej.jep.ui;

import java.util.Scanner;

/**
 * Class containing all user interactions
 *
 * @author Amandine Tournay
 */
public class UI {
    private static UI instance;

    private UI() {
    }

    public static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }

        return instance;
    }

    /**
     * Set the path to the Python execution directory for Venv environment and classic Python installation
     *
     * @return The path to the Python interpreter (for non-Conda environment)
     */
    public String selectPythonInterpreter() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the Python Home Directory: ");

        return scanner.nextLine();
    }

    /**
     * Menu selection to choose which scenario to run
     *
     * @return User selection about its choice of scenario
     */
    public int selectScenario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which scenario do you want to execute ?");
        System.out.println();

        System.out.println("---- Basic examples of JEP ----");
        System.out.println("1. Basic running test");
        System.out.println("2. Send and retrieve data");
        System.out.println("3. Get Java class without set() method");
        System.out.println("4. Test availability of Numpy in JEP");
        System.out.println();

        System.out.println("---- Simple ImageJ examples by sending instance to Python ----");
        System.out.println("5. Get ImageJ version through Python");
        System.out.println("6. Open Image");
        System.out.println();

        System.out.println("---- ScyJava examples ----");
        System.out.println("7. Use jimport function to call any Java class");
        System.out.println("\\033[3mAvailable only if you have a development version of ScyJava containing the JEP implementation\\033[0m");
        System.out.println();

        System.out.println("---- Misc ----");
        System.out.println("8. Custom Python function to import Java classes");

        System.out.println();

        return scanner.nextInt();
    }

    /**
     * Ask the user the choice to run a new scenario
     *
     * @return User's answer as a boolean
     */
    public boolean tryNewScenario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to run an other scenario ? (y/yes/n/no)");
        String answer = scanner.nextLine();

        switch (answer) {
            case "y":
            case "yes":
                return false;

            case "n":
            case "no":
                return true;

            default:
                System.err.println("Please answer only by y or yes or n or no. Please retry.");
                System.out.println();

                tryNewScenario();
                break;
        }

        return true;
    }
}
