package net.imagej.jep;

import net.imagej.jep.scenarios.scenario1.JEPTest;
import net.imagej.jep.scenarios.scenario2.RetrieveSimpleData;
import net.imagej.jep.scenarios.scenario2.SendSimpleData;
import net.imagej.jep.scenarios.scenario3.ImageJInfo;
import net.imagej.jep.scenarios.scenario4.OpenImage;
import net.imagej.jep.ui.UI;
import net.imagej.jep.utils.JepUtils;
import net.imagej.jep.utils.Utils;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String condaHome = System.getenv("CONDA_PREFIX");
        String venvHome = System.getenv("VIRTUAL_ENV");
        String pythonExecSuffix = Utils.getInstance().getOSName().matches("Windows.*") ? "\\bin" : "/bin";
        String pythonPath = "";
        String pythonExecutionPath = "";
        String sitePackagesPath;
        String jepPath;

        int pythonUserSelection;
        boolean isPythonPathOk = false;
        boolean isJepPathOk = false;

        /*
            Loop to set JEP from the console and user input
         */
        do {
            pythonUserSelection = UI.getInstance().mainMenu();

            /*
                Select the type of Python environment (Conda and Venv works in the same way, so easy to locate the site-packages directory)
                Set the path and the execution path
             */
            switch (pythonUserSelection) {
                case 1:
                    if (condaHome == null) {
                        System.err.println("Please activate a conda environment before using this.");
                        System.err.println("You can activate a conda environment with the command \033[3mconda activate <env_name>\033[0m");
                        System.exit(4);
                    }
                    else {
                        pythonPath = condaHome;
                        pythonExecutionPath = Utils.getInstance().findPythonExecutable(pythonPath + pythonExecSuffix, true);
                    }
                    break;
                case 2:
                    pythonPath = venvHome;
                    pythonExecutionPath = Utils.getInstance().findPythonExecutable(pythonPath + pythonExecSuffix, true);
                    break;
                case 3:
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
                        pythonPath = "";
                        System.err.println("The selected Python path is not a directory. Please retry.");
                        System.out.println();
                        continue;
                    }
                    break;

                default:
                    System.err.println("Please select only option 1 or 2.");
                    System.out.println();
                    continue;
            }

            isPythonPathOk = Utils.getInstance().isPath(pythonExecutionPath) && Utils.getInstance().findPythonExecutable(pythonExecutionPath, false) != null;
            if (!isPythonPathOk) {
                continue;
            }

            // Set the site-packages directory path
            sitePackagesPath = Utils.getInstance().setSitePackagesDirectory(pythonPath, pythonUserSelection);

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

            /*
                Loop to run as many scenario as we want
             */
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
                        new ImageJInfo().runScenario(firstRun);
                        isScenarioSelected = true;
                        break;

                    case 4:
                        new OpenImage().runScenario(firstRun);
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
