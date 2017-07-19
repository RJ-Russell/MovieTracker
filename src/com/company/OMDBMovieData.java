package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

class OMDBMovieData implements DataSource {
  @Override
  public Map<String, String> getMovieData(String imdbId, String title, String year) throws IOException {

    title = title.replace(' ', '+');

    String imdbIdUrl = "i=" + imdbId;
    String titleUrl = "t=" + title;
    String yearUrl = "&y=" + (year.equals("") ? "" : year);
    String plotAndFormat = "&plot=short&r=json";
    String url = "http://omdbapi.com/?";// + titleUrl + yearUrl + plotAndFormat;
    if(!imdbId.equals("")) {
      url += imdbIdUrl + plotAndFormat;
    } else {
      url += titleUrl + yearUrl + plotAndFormat;
    }

    String charset = "UTF-8";

    InputStream input = new URL(url).openStream();
    InputStreamReader stream = new InputStreamReader(input, charset);

    TypeToken token = new TypeToken<Map<String, String>>() {
    };

    return new Gson().fromJson(stream, token.getType());
  }
}
