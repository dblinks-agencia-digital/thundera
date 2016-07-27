package br.com.dblinks.thundera.util;

import java.util.ArrayList;
import java.util.List;

public class URLUtil {

    public static Boolean isFile(String url) {
        List<String> fileExtensions = new ArrayList<>();
        fileExtensions.add("jpg");
        fileExtensions.add("png");
        fileExtensions.add("jpeg");

        return fileExtensions
                .stream()
                .anyMatch((extension) -> (url.toLowerCase().contains("." + extension)));
    }

}
