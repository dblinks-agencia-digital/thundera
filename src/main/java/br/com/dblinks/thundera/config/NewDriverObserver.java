package br.com.dblinks.thundera.config;

public class NewDriverObserver {

    private final Class driver;

    public NewDriverObserver(Class driver) {
        this.driver = driver;
    }

    public Class getDriver() {
        return driver;
    }

}
