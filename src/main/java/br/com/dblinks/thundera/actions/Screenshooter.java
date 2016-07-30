package br.com.dblinks.thundera.actions;

import br.com.dblinks.thundera.actions.config.ScreenshotConfig;
import br.com.dblinks.thundera.config.ApplicationConfig;
import br.com.dblinks.thundera.navigator.page.Page;
import br.com.dblinks.thundera.navigator.page.PageReadyObserver;
import br.com.dblinks.thundera.navigator.page.PageReadyStrategy;
import br.com.dblinks.thundera.util.StringUtil;
import br.com.dblinks.thundera.util.WebDriverUtil;
import java.awt.image.BufferedImage;
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
        screenshotConfig.getDimensions().stream().forEach((dimension) -> {
            takeScreenshot(page, dimension);
        });
    }

    private void takeScreenshot(Page page, Dimension dimension) {
        page.getDriver().manage().window().setSize(dimension);

        Double numberOfScrolls = Math.floor(page.getFullHeight() / dimension.getHeight());
        for (Integer i = 0; i <= numberOfScrolls; i++) {
            page.scrollTo(dimension.getHeight() * i);
            waitALittleBit();

            BufferedImage screenshot = page.takeScreenshot();

            String fileName = page.getDriver().getTitle() + "-" + i.toString() + ".png";
            File output = getOutputPath(page, dimension, fileName);

            saveToFile(screenshot, output);
            waitALittleBit();
        }
    }

    private void waitALittleBit() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Screenshooter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File getOutputPath(Page page, Dimension dimension, String fileName) {
        Calendar today = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String path = "";
        path += "./reports/";
        path += dateFormat.format(today.getTime());
        path += "-" + StringUtil.slugify(applicationConfig.getHost()) + "/";
        path += "screenshots/";
        path += StringUtil.slugify(WebDriverUtil.getName(page.getDriver())) + "/";
        path += dimension.getWidth() + "x" + dimension.getHeight() + "/";

        File file = new File(path + fileName);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    private void saveToFile(BufferedImage screenshot, File output) {
        try {
            ImageIO.write(screenshot, FilenameUtils.getExtension(output.getPath()), output);
        } catch (IOException ex) {
            Logger.getLogger(Screenshooter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
