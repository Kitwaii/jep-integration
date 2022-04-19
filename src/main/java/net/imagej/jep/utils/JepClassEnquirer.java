package net.imagej.jep.utils;

import jep.ClassEnquirer;
import jep.ClassList;

/**
 * Class to override the management of the default class loader configuration.
 * Jep scans the Java classpath to be able to import those classes in Python.
 * But, if a library or a class is available on both sides AND if the path is the same, it will create a conflict.
 * To prevent this, we can choose which one to load.
 */
public class JepClassEnquirer implements ClassEnquirer {

    /**
     * Verification of a scanned package and confirm if it is a package from Java.
     * In case of conflict, and we want to load only the Python version of it, it is possible to force to return false.
     *
     * @param name Name of the package
     * @return True if it corresponds to a package from Java
     */
    @Override
    public boolean isJavaPackage(String name) {
        return !(name.equals("tensorflow")) && ClassList.getInstance().isJavaPackage(name);
    }

    @Override
    public String[] getClassNames(String packageName) {
        return ClassList.getInstance().getClassNames(packageName);
    }

    @Override
    public String[] getSubPackages(String packageName) {
        return ClassList.getInstance().getSubPackages(packageName);
    }
}
