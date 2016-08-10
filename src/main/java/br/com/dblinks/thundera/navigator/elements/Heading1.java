package br.com.dblinks.thundera.navigator.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Heading1 implements ElementStrategy {

    private WebElement element;

    public Heading1() {
    }

    public Heading1(WebElement element) {
        this.element = element;
    }

    @Override
    public By getSelector() {
        return By.tagName("h1");
    }

    @Override
    public String toString() {
        return "<h1>" + element.getText() + "</h1>";
    }
}
