package br.com.dblinks.thundera.navigator.page;

import java.util.List;

public interface PageStrategy {

    public <T> T executeScript(String script, Object... args);

    public int getFullHeight();

    public void scrollTo(Integer top);

    public <T> List<T> getElements(Class<T> elementClass);

    public void close();

}
