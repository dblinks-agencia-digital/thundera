package br.com.dblinks.thundera.navigator.page;

import org.openqa.selenium.WebDriver;

public class PageFactory {
    
    public Page create(WebDriver driver, URL url) {
        return new Page(driver, url);
    }
    
}
