package com.company;

/**
 * Created by chupacabra on 6/14/16.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class omdbScraper {
    void getStuff(String toFind) throws IOException {
        String url = "http://omdbapi.com/?t=" + toFind;
        String charset = "UTF-8";

        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("Accept-Charset", charset);
        InputStream response = conn.getInputStream();


    }
}
