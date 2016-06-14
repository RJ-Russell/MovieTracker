package com.company;

/**
 * Created by chupacabra on 6/14/16.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class omdbScraper {
    void getStuff(String toFind) throws IOException {
        String url = "http://omdbapi.com/?t=" + toFind;
        String charset = "UTF-8";

        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("Accept-Charset", charset);
        InputStream response = conn.getInputStream();


    }
}
