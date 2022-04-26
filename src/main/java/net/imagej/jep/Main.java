package net.imagej.jep;

import net.imagej.jep.scenarios.scenario1.JEPTest;
import net.imagej.jep.scenarios.scenario2.RetrieveSimpleData;
import net.imagej.jep.scenarios.scenario2.SendSimpleData;
import net.imagej.jep.scenarios.scenario3.ImportWithoutSetClass;
import net.imagej.jep.scenarios.scenario4.ImageJInfo;
import net.imagej.jep.scenarios.scenario5.OpenImage;
import net.imagej.jep.scenarios.scenario6.CustomPythonImport;
import net.imagej.jep.ui.UI;
import net.imagej.jep.utils.JepUtils;
import net.imagej.jep.utils.Utils;

import java.io.File;

/**
 * Main class to run test JEP scenario
 *
 * @author Amandine Tournay
 */
public class Main {

    public static void main(String[] args) {
        String pythonPath, pythonExecutionPath, sitePackagesPath, jepPath;
        boolean isPythonPathOk = false;
        boolean isJepPathOk = false;

        System.out.println("---- JEP TESTS ----");

        // Loop to set JEP from the console and user input
        do {

            pythonPath = UI.getInstance().selectPythonInterpreter();

            if (new File(pythonPath).isDirectory()) {
                pythonExecutionPath = Utils.getInstance().findPythonExecutable(pythonPath, true);

                if (pythonExecutionPath == null) {
                    pythonExecutionPath = "";
                    System.err.println("The Python execution file could not be found. Please select another directory.");
                    System.out.println();
                    continue;
                }
            }
            else {
                pythonExecutionPath = Utils.getInstance().findPythonExecutable(pythonPath, false);
            }

            isPythonPathOk = Utils.getInstance().isPath(pythonExecutionPath) && Utils.getInstance().findPythonExecutable(pythonExecutionPath, false) != null;
            if (!isPythonPathOk) {
                continue;
            }

            // Set the site-packages directory path
            sitePackagesPath = Utils.getInstance().setSitePackagesDirectory(pythonExecutionPath);

            if (sitePackagesPath == null) {
                System.err.println("The path to the site-packages directory is not correct. Please retry.");
                continue;
            }

            // Verify if JEP is in the directory
            jepPath = JepUtils.getInstance().findJepLib(sitePackagesPath);

            if (jepPath == null) {
                continue;
            }

            // Set JEP
            try {
                JepUtils.getInstance().setJepPath(jepPath);

                System.out.println();
                System.out.println(" ---- JEP initialization done ! ----");
                System.out.println();

                isJepPathOk = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (!isPythonPathOk && !isJepPathOk);

        if (isJepPathOk) {
            boolean firstRun = true;
            boolean isScenarioSelected;
            boolean isFinishedToRunScenarios = false;

            // Loop to run as many scenario as we want
            do {
                isScenarioSelected = false;

                // List of scenarios
                switch (UI.getInstance().selectScenario()) {
                    case 1:
                        new JEPTest().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;
                    case 2:
                        new RetrieveSimpleData().runScenario(firstRun);
                        new SendSimpleData().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    case 3:
                        new ImportWithoutSetClass().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    case 4:
                        new ImageJInfo().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    case 5:
                        new OpenImage().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    case 6:
                        new CustomPythonImport().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    default:
                        System.err.println("Please select only in one of the number above. Please retry");
                        System.out.println();
                        continue;
                }

                // Finish program or loop to run a new scenario
                firstRun = false;
                isFinishedToRunScenarios = !UI.getInstance().tryNewScenario();
            }
            while (isScenarioSelected && isFinishedToRunScenarios);
        }
    }
}
