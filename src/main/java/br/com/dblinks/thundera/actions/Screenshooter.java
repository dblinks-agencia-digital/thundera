package br.com.dblinks.thundera.actions;

import br.com.dblinks.thundera.actions.config.ScreenshotConfig;
import br.com.dblinks.thundera.config.ApplicationConfig;
import br.com.dblinks.thundera.navigator.page.Page;
import br.com.dblinks.thundera.navigator.page.PageReadyObserver;
import br.com.dblinks.thundera.navigator.page.PageReadyStrategy;
import com.github.slugify.Slugify;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import org.apache.commons.io.FilenameUtils;
import org.jboss.weld.experimental.Priority;
import org.openqa.selenium.Dimension;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Screenshooter implements PageReadyStrategy, ScreenshotStrategy {

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private ScreenshotConfig screenshotConfig;

    @Override
    public void onReady(@Observes @Priority(Interceptor.Priority.APPLICATION + 1) PageReadyObserver pageReadyObserver) {
        if (screenshotConfig.isEnabled()) {
            takeScreenshot(pageReadyObserver.getPage());
        }
    }

    @Override
    public void takeScreenshot(Page page) {
        for (Dimension dimension : screenshotConfig.getDimensions()) {
            try {
                Screenshot screenshot = getScreenshot(page, dimension);
                File file;
                file = getScreenshotFile(page, dimension);

                saveScreenshot(screenshot, file);
            } catch (IOException ex) {
                Logger.getLogger(Screenshooter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Screenshot getScreenshot(Page page, Dimension dimension) {
        page.getDriver().manage().window().setSize(dimension);
        return new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(page.getDriver());
    }

    private File getScreenshotFile(Page page, Dimension dimension) throws IOException {
        Calendar today = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Slugify slugify = new Slugify();

        String path = "";
        path += "./reports/";
        path += dateFormat.format(today.getTime());
        path += "-" + slugify.slugify(applicationConfig.getHost()) + "/";
        path += "screenshots/";
        path += dimension.getWidth() + "x" + dimension.getHeight() + "/";

        String fileName = page.getDriver().getTitle() + ".png";

        File file = new File(path + fileName);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    private void saveScreenshot(Screenshot screenshot, File file) throws IOException {
        ImageIO.write(screenshot.getImage(), FilenameUtils.getExtension(file.getPath()), file);
    }

}
