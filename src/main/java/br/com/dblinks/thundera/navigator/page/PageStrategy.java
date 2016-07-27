package br.com.dblinks.thundera.navigator.page;

import java.util.List;

public interface PageStrategy {
 
    public void close();

    public <T> List<T> getElements(Class<T> elementClass);
    
}
