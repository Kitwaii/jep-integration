package net.imagej.jep.ui;

import java.util.Scanner;

/**
 * Class containing all user interactions
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
     * Menu selection to choose the Python executable
     *
     * @return User selection of the type of Python
     */
    public int mainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What Python do you want to use ?");
        System.out.println("\033[3mSelect option 1 or 2\033[0m");
        System.out.println("1. Conda environment (\033[3mMust be currently active\033[0m)");
        System.out.println("2. Venv environment (\033[3mMust be currently active\033[0m)");
        System.out.println("3. Python interpreter");

        return scanner.nextInt();
    }

    /**
     * Set the path to the Python execution directory for Venv environment and classic Python installation
     *
     * @return The path to the Python interpreter (for non-Conda environment)
     */
    public String selectPythonInterpreter() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the path to the directory of your Python: ");

        return scanner.nextLine();
    }

    /**
     * Set the site-packages directory for the classic Python interpreter (/usr/lib/python3.x for Linux and AppData for Windows)
     *
     * @return The path to the Python site-packages directory
     */
    public String selectSitePackagePath() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the path to the site-packages directory :");
        System.out.println("\033[3mThis directory contains all modules installed via pip\033[0m");
        System.out.println("\033[3mTo easily find where they are installed, execute the command pip list -v or pip show <package_name>\033[0m");

        return scanner.nextLine();
    }
}
