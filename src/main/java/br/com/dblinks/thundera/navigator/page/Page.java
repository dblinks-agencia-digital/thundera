package br.com.dblinks.thundera.navigator.page;

import br.com.dblinks.thundera.actions.Screenshooter;
import br.com.dblinks.thundera.navigator.elements.ElementStrategy;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

public class Page implements PageStrategy, PageScreenshotStrategy {

    private final WebDriver driver;
    private final URL url;

    public Page(WebDriver driver, URL url) {
        this.driver = driver;
        this.url = url;

        driver.get(url.getUrl());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public URL getURL() {
        return url;
    }

    @Override
    public <T> T executeScript(String script, Object... args) {
        return (T) ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public <T> T executeScript(File file, Object... args) {
        try {
            String script = IOUtils.toString(new FileInputStream(file));
            return executeScript(script, args);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getFullHeight() {
        File file = new File(getClass().getClassLoader().getResource("js/page_height.js").getFile());
        return ((Number) executeScript(file)).intValue();
    }

    @Override
    public void scrollTo(Integer top) {
        executeScript("scrollTo(0, " + top + ");");
    }

    @Override
    public <T> List<T> getElements(Class<T> elementClass) {
        if (elementClass.isInstance(ElementStrategy.class)) {
            throw new IllegalArgumentException();
        }

        List<T> elements = new ArrayList<>();
        try {
            Constructor elementConstructor = elementClass.getConstructor();
            Object elementInstance = elementConstructor.newInstance(elementConstructor.getParameters());

            Method getSelectorMethod = elementClass.getMethod("getSelector");
            By selector = (By) getSelectorMethod.invoke(elementInstance, new Object[]{});

            List<WebElement> webElements = driver.findElements(selector);
            for (WebElement webElement : webElements) {
                elements.add(elementClass.getConstructor(WebElement.class).newInstance(webElement));
            }
        } catch (Exception ex) {

        }

        return elements;
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public BufferedImage takeScreenshot() {
        TakesScreenshot takesScreenshot = (TakesScreenshot) new Augmenter().augment(driver);

        ByteArrayInputStream imageArrayStream = null;
        try {
            imageArrayStream = new ByteArrayInputStream(takesScreenshot.getScreenshotAs(OutputType.BYTES));
            return ImageIO.read(imageArrayStream);
        } catch (IOException ex) {
            Logger.getLogger(Screenshooter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(imageArrayStream);
        }

        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.driver);
        hash = 79 * hash + Objects.hashCode(this.url);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Page other = (Page) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.driver, other.driver)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + url + ": " + driver.getTitle() + "]";
    }

}
