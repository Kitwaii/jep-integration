package net.imagej.jep;

import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
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
                        System.out.println("Please activate a conda environment before using this.");
                        System.out.println("You can activate a conda environment with the command \033[3mconda activate <env_name>\033[0m");
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
                            System.out.println("The Python execution file could not be found. Please select another directory.");
                            System.out.println();
                            continue;
                        }
                    }
                    else {
                        pythonPath = "";
                        System.out.println("The selected Python path is not a directory. Please retry.");
                        System.out.println();
                        continue;
                    }
                    break;

                default:
                    System.out.println("Please select only option 1 or 2.");
                    System.out.println();
                    continue;
            }

            isPythonPathOk = Utils.getInstance().isPath(pythonExecutionPath) && Utils.getInstance().findPythonExecutable(pythonExecutionPath, false) != null;
            if (!isPythonPathOk) {
                continue;
            }

            sitePackagesPath = Utils.getInstance().setSitePackagesDirectory(pythonPath, pythonUserSelection);

            if (sitePackagesPath == null) {
                System.out.println("The path to the site-packages directory is not correct. Please retry.");
                continue;
            }

            jepPath = JepUtils.getInstance().findJepLib(sitePackagesPath);

            if (jepPath == null) {
                continue;
            }

            try {
                JepUtils.getInstance().setJepPath(jepPath);

                System.out.println(" ---- JEP initialization done ! ----");
                isJepPathOk = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (!isPythonPathOk && !isJepPathOk);

        if (isJepPathOk) {
            try {
                Interpreter interp = new SharedInterpreter();

                interp.exec("from java.lang import System");
                interp.exec("s = 'Hello World'");
                interp.exec("System.out.println(s)");
                interp.exec("print(s)");
                interp.exec("print(s[1:-1])");

            }
            catch (JepException e) {
                e.printStackTrace();
            }
        }
    }
}
