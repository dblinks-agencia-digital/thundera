package br.com.dblinks.thundera.navigator.elements;

import br.com.dblinks.thundera.util.URLUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Link implements ElementStrategy {

    private WebElement element;

    private String href;

    public Link() {
    }

    public Link(WebElement element) {
        this.element = element;

        String elementHref = element.getAttribute("href");
        if (elementHref == null) {
            elementHref = "";
        }
        this.href = elementHref;
    }

    public String getHref() {
        return href;
    }

    @Override
    public By getSelector() {
        return By.tagName("a");
    }

    @Override
    public Boolean isValid() {
        Boolean isLongEnough = getHref().length() > 1;
        Boolean isNotJavascript = !getHref().contains("javascript:");
        Boolean isNotHashTag = !getHref().contains("#");
        Boolean isNotFile = !URLUtil.isFile(href);

        return isLongEnough && isNotJavascript && isNotHashTag && isNotFile;
    }

    @Override
    public String toString() {
        return "<a href=\"" + getHref() + "\">" + element.getText() + "</a>";
    }

}
