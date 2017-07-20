package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

/**
 * @author RJ Russell
 *
 * The OMDBApi has become a paid service. Leaving here in the event an API key is obtained at
 * a later date.
 */

class OmdbApi {
    Map<String, String> getMovieData(String imdbId, String title, String year) throws IOException {

        title = title.replace(' ', '+');

        String imdbIdUrl = "i=" + imdbId;
        String titleUrl = "t=" + title;
        String yearUrl = "&y=" + year;
        String plotAndFormat = "&plot=short&r=json";

        String url = "http://omdbapi.com/?";// + titleUrl + yearUrl + plotAndFormat;
        if(!imdbId.equals("")) {
            url += imdbIdUrl;
        }

        url += titleUrl;

        if(!year.equals("")) {
            url += yearUrl;
        }

        url += plotAndFormat;

        String charset = "UTF-8";

        InputStream input = new URL(url).openStream();
        InputStreamReader stream = new InputStreamReader(input, charset);

        return new Gson().fromJson(stream, new TypeToken<Map<String, String>>(){}.getType());
    }
}
