package br.com.dblinks.thundera.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApplicationConfig {

    @Inject
    private Event<NewDriverObserver> newDriverObserver;

    private String host = "localhost";

    private final Set<Class> drivers = new HashSet<>();

    private Boolean recursive = Boolean.TRUE;

    private Integer maxJobs = 1;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Set<Class> getDrivers() {
        return Collections.unmodifiableSet(drivers);
    }

    public void addDriver(Class driver) {
        this.drivers.add(driver);
        newDriverObserver.fire(new NewDriverObserver(driver));
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public Integer getMaxJobs() {
        return maxJobs;
    }

    public void setMaxJobs(Integer maxJobs) {
        this.maxJobs = maxJobs;
    }

}
