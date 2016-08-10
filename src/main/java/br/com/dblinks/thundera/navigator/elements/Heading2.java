package br.com.dblinks.thundera.navigator.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Heading2 implements ElementStrategy {

    private WebElement element;

    public Heading2() {
    }

    public Heading2(WebElement element) {
        this.element = element;
    }

    @Override
    public By getSelector() {
        return By.tagName("h2");
    }

    @Override
    public String toString() {
        return "<h2>" + element.getText() + "</h2>";
    }
}
