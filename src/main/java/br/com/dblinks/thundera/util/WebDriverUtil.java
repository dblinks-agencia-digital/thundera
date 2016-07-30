package br.com.dblinks.thundera.util;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverUtil {

    private static final HashMap<Class, String> NAMES = new HashMap<>();

    private static void populateNames() {
        NAMES.put(EdgeDriver.class, "Edge");
        NAMES.put(InternetExplorerDriver.class, "Internet Explorer");
        NAMES.put(OperaDriver.class, "Opera");
        NAMES.put(MarionetteDriver.class, "Firefox");
        NAMES.put(ChromeDriver.class, "Chrome");
    }

    public static String getName(WebDriver driver) {
        if (NAMES.isEmpty()) {
            populateNames();
        }

        return NAMES.get(driver.getClass());
    }
}
