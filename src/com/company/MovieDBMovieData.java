package com.company;

import java.io.IOException;
import java.util.Map;

/**
 * Created on 7/19/17
 *
 * @author RJ Russell
 */
public class MovieDBMovieData implements DataSource {
    @Override
    public Map<String, String> getMovieData(String imdbId, String title, String year) throws IOException {
        title = title.replace(' ', '+');


    }
}
