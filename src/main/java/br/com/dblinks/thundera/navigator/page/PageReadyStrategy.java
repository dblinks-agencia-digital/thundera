package br.com.dblinks.thundera.navigator.page;

import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;
import org.jboss.weld.experimental.Priority;

public interface PageReadyStrategy {
    
    public void onReady(@Observes @Priority(Interceptor.Priority.APPLICATION) PageReadyObserver pageReadyObserver);
    
}
