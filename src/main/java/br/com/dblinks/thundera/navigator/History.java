package br.com.dblinks.thundera.navigator;

import br.com.dblinks.thundera.navigator.page.PageReadyObserver;
import br.com.dblinks.thundera.navigator.page.PageReadyStrategy;
import br.com.dblinks.thundera.navigator.page.URL;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import org.jboss.weld.experimental.Priority;

@Singleton
public class History implements PageReadyStrategy {

    private final Set<URL> urls;

    public History() {
        this.urls = new HashSet<>();
    }

    public Boolean contains(URL url) {
        return urls.contains(url);
    }

    @Override
    public void onReady(@Observes @Priority(Interceptor.Priority.APPLICATION) PageReadyObserver pageReadyObserver) {
        urls.add(pageReadyObserver.getPage().getURL());
    }

    @Override
    public String toString() {
        return urls.toString();
    }

}
