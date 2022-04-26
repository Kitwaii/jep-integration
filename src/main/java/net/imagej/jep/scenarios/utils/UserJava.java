package net.imagej.jep.scenarios.utils;

/**
 * Class to test Object comportment with JEP
 *
 * @author Amandine Tournay
 */
public class UserJava {
    private final String firstname, lastname;

    public UserJava(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String sayHello() {
        return "Hello " + getFirstname() + " " + getLastname() + " !";
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }
}
