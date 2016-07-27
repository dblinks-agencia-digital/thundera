package br.com.dblinks.thundera.actions.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import org.openqa.selenium.Dimension;

@Singleton
public class ScreenshotConfig implements ConfigStrategy {

    private Boolean enabled = Boolean.TRUE;

    private final List<Dimension> dimensions = new ArrayList();

    @Override
    public Boolean isEnabled() {
        return enabled.equals(Boolean.TRUE);
    }

    @Override
    public void enable() {
        enabled = Boolean.TRUE;
    }

    @Override
    public void disable() {
        enabled = Boolean.FALSE;
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public List<Dimension> getDimensions() {
        return Collections.unmodifiableList(dimensions);
    }

}
