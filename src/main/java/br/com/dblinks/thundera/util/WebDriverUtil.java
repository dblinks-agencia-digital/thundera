package br.com.dblinks.thundera.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverUtil {

    public static WebDriver newInstance(Class driverClass) {
        if (driverClass == EdgeDriver.class) {
            return new EdgeDriver();
        }
        if (driverClass == InternetExplorerDriver.class) {
            return new InternetExplorerDriver();
        }
        if (driverClass == OperaDriver.class) {
            return new OperaDriver();
        }
        if (driverClass == MarionetteDriver.class) {
            return new MarionetteDriver();
        }

        return new ChromeDriver();
    }
}
