package br.com.dblinks.thundera.config;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

@Singleton
public class DriverConfigurer {

    public void init(@Observes NewDriverObserver driverChangeObserver) {
        if (driverChangeObserver.getDriver() == ChromeDriver.class) {
            ChromeDriverManager.getInstance().setup();
        }
        
        if (driverChangeObserver.getDriver() == MarionetteDriver.class) {
            MarionetteDriverManager.getInstance().setup();
        }

        if (driverChangeObserver.getDriver() == InternetExplorerDriver.class) {
            InternetExplorerDriverManager.getInstance().setup();
        }

        if (driverChangeObserver.getDriver() == OperaDriver.class) {
            OperaDriverManager.getInstance().setup();
        }

        if (driverChangeObserver.getDriver() == EdgeDriver.class) {
            EdgeDriverManager.getInstance().setup();
        }
    }

}
