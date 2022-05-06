package net.imagej.jep.utils;

import jep.*;

import java.io.File;

/**
 * Utility class that groups all functions for JEP
 *
 * @author Amandine Tournay
 */
public class JepUtils {
    private static JepUtils instance;
    private static JepConfig jepConfig;

    private JepUtils() {
    }

    public static JepUtils getInstance() {
        if (instance == null) {
            instance = new JepUtils();
        }

        return instance;
    }

    /**
     * @param sitePackagesPath Path to the site-packages directory
     * @return Path to JEP
     */
    public String findJepLib(String sitePackagesPath) {
        String jetPath = "";
        File jepDir = new File(sitePackagesPath, "jep");

        if (!jepDir.isDirectory()) {
            System.err.println("JEP could not be found. Please check if you have installed JEP before.");
            System.out.println();
            return null;
        }
        else {
            String[] libFiles = {"libjep.so", "libjep.jnilib", "jep.ddl"};

            for (String libFile : libFiles) {
                File lib = new File(jepDir, libFile);

                if (lib.isFile()) jetPath = lib.getAbsolutePath();
            }
        }

        return jetPath;
    }

    /**
     * Set the path of the JEP library either the OS
     */
    public void setJepPath(String jepPath, String pythonHome) throws JepException {
        setJepConfig(new JepConfig());

        getJepConfig().setClassEnquirer(new JepClassEnquirer());

        if (jepPath.matches(".*conda.*|.*venv")) {
            PyConfig pyConfig = new PyConfig();
            pyConfig.setPythonHome(pythonHome);

            MainInterpreter.setInitParams(pyConfig);
        }

        SharedInterpreter.setConfig(getJepConfig());
        MainInterpreter.setJepLibraryPath(jepPath);
    }

    public static JepConfig getJepConfig() {
        return jepConfig;
    }

    private void setJepConfig(JepConfig newJepConfig) {
        jepConfig = newJepConfig;
    }
}
