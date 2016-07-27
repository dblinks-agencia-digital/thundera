package br.com.dblinks.thundera.navigator.page;

import br.com.dblinks.thundera.navigator.elements.ElementStrategy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page implements PageStrategy {

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
    public void close() {
        driver.close();
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
