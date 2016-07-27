package br.com.dblinks.thundera.navigator;

import br.com.dblinks.thundera.navigator.page.URL;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
public class Queue {

    private final Set<URL> urls;

    public Queue() {
        this.urls = new HashSet<>();
    }

    public void add(URL url) {
        urls.add(url);
    }

    public URL take() {
        if (!urls.iterator().hasNext()) {
            return null;
        }

        URL url = urls.iterator().next();
        urls.remove(url);

        return url;
    }

    public Boolean contains(URL url) {
        return urls.contains(url);
    }

    public Boolean isEmpty() {
        return urls.isEmpty();
    }

    @Override
    public String toString() {
        return urls.toString();
    }

}
