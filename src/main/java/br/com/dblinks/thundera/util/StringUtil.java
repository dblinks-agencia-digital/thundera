package br.com.dblinks.thundera.util;

import com.github.slugify.Slugify;
import java.io.IOException;

public class StringUtil {

    public static String slugify(String original) {
        try {
            Slugify slugify = new Slugify();
            return slugify.slugify(original);
        } catch (IOException ex) {
            return "";
        }
    }

}
