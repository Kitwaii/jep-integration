package net.imagej.jep.utils;

import net.imagej.jep.ui.UI;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Global Utility class
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
     * @param pythonPath Path to the Python home
     * @return The path to the site-packages directory
     */
    public String setSitePackagesDirectory(String pythonPath, int pythonUserSelection) {
        String sitePackagesPath = "";

        if (pythonUserSelection == 3) {
            sitePackagesPath = UI.getInstance().selectSitePackagePath();

            if (!new File(sitePackagesPath).isDirectory()) {
                System.err.println("The selected path is not a directory. Please retry.");
                System.out.println();
                return null;
            }
        }
        else {
            File pythonLibDir = new File(pythonPath, "lib");

            if (!pythonLibDir.isDirectory()) {
                return null;
            }

            File[] pythonLibContent = pythonLibDir.listFiles();

            if (pythonLibContent != null) {
                for (File content : pythonLibContent) {
                    if (content.getName().startsWith("python") && content.isDirectory()) {
                        File sitePackages = new File(content, "site-packages");

                        if (!sitePackages.isDirectory()) {
                            return null;
                        }
                        else {
                            sitePackagesPath = sitePackages.getAbsolutePath();
                            break;
                        }
                    }
                }
            }
        }

        return sitePackagesPath;
    }
}
