package br.com.dblinks.thundera.navigator;

import br.com.dblinks.thundera.config.ApplicationConfig;
import br.com.dblinks.thundera.navigator.elements.Link;
import br.com.dblinks.thundera.navigator.page.Page;
import br.com.dblinks.thundera.navigator.page.PageReadyObserver;
import br.com.dblinks.thundera.navigator.page.URL;
import br.com.dblinks.thundera.util.WebDriverUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;

@Singleton
public class Navigator implements NavigatorStrategy {

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private Queue queue;

    @Inject
    private History history;

    @Inject
    private Event<PageReadyObserver> pageReadyObserver;

    private Integer runningJobs = 0;

    public void navigate(URL url) {
        queue.add(url);
        navigate();
    }

    @Override
    public void navigate() {
        URL url = queue.take();
        while (url != null) {
            try {
                synchronized (queue) {
                    if (runningJobs < applicationConfig.getMaxJobs()) {
                        runJob(url);
                    } else {
                        queue.wait();
                    }

                    url = queue.take();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void runJob(URL url) {
        runningJobs++;

        for (Class driverClass : applicationConfig.getDrivers()) {
            WebDriver driver = WebDriverUtil.newInstance(driverClass);
            Page page = new Page(driver, url);
            pageReadyObserver.fire(new PageReadyObserver(page));

            if (applicationConfig.getRecursive()) {
                page.getElements(Link.class)
                        .stream()
                        .filter((link) -> link.isValid())
                        .filter((link) -> link.getHref().contains(applicationConfig.getHost()))
                        .filter((link) -> !queue.contains(new URL(link.getHref())))
                        .filter((link) -> !history.contains(new URL(link.getHref())))
                        .forEach((link) -> {
                            queue.add(new URL(link.getHref()));
                        });
            }

            page.close();
        }

        runningJobs--;
    }

}
