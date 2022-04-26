package net.imagej.jep.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Global Utility class
 *
 * @author Amandine Tournay
 */
public class Utils {
    private static Utils instance;

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }

        return instance;
    }

    /**
     * @return The name of the OS where the program is executed
     */
    public String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * @param path The path to test
     * @return The result if the String is really a path (compatible Windows, MacOS & Linux)
     */
    public boolean isPath(String path) {
        if (Pattern.compile("([A-Za-z]:|[/\\\\])+.(\\w)+.*").matcher(path).find()) {
            return true;
        }
        else {
            System.err.println("The input does not correspond to a System path. Please retry.");
            System.out.println();
            return false;
        }
    }

    /**
     * The Python executable file in Windows is python.exe and in Unix systems /python (which can be linked to a specific version file)
     *
     * @param path The path to a directory where the Python executable must be found
     */
    public String findPythonExecutable(String path, boolean isDirectory) {
        if (isDirectory) {
            File[] pythonFiles = new File(path).listFiles(((file, name) -> name.equals(getOSName().matches("Windows.*") ? "python.exe" : "python")));

            if (pythonFiles != null) {
                return Arrays.stream(pythonFiles).findFirst().map(File::getAbsolutePath).orElse(null);
            }
            else {
                return null;
            }
        }
        else {
            if (Files.exists(Paths.get(path))) {
                return path;
            }
            else {
                return null;
            }
        }
    }

    /**
     * @param pythonExecutionPath Path to the Python home
     * @return The path to the site-packages directory
     */
    public String setSitePackagesDirectory(String pythonExecutionPath) {
        String terminalLine;
        String[] command = new String[]{pythonExecutionPath, "-c", "import sys; print([p for p in sys.path if p.endswith(\"site-packages\")])"};
        String sitePackagesPath = "";

        try {
            // Execute terminal command to find the site-packages directory
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader streamInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader streamError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Execution result stream output
            while ((terminalLine = streamInput.readLine()) != null) {
                String[] spList = terminalLine.replaceAll("[\\[\\]' ]", "").split(",");

                /*
                    With classical Python interpreter (non-isolated environment like Venv and Conda with its consorts), there can be multiple site-packages folder.
                    So, we must find which one has JEP inside
                 */
                for (String spPath : spList) {
                    String isThereJEP = JepUtils.getInstance().findJepLib(spPath);

                    if (isThereJEP != null) {
                        sitePackagesPath = spPath;
                    }
                }
            }

            // Error stream output
            while ((terminalLine = streamError.readLine()) != null) {
                System.err.println(terminalLine);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return sitePackagesPath;
    }
}
