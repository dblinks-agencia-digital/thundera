package br.com.dblinks.thundera;

import java.io.IOException;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {

    private static Weld weld;
    private static WeldContainer container;
    
    public static void main(String[] args) throws IOException {
        weld = new Weld();
        container = weld.initialize();
        
        container.instance().select(FrmInicial.class).get();
    }
}
