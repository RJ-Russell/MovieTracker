package com.company;

/**
 * Created by chupacabra on 6/14/16.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

class OmdbScraper {
  Map<String, String> getStuff(String movie, String year) throws IOException {

    movie = movie.replace(' ', '+');

    String yearUrl = "&y=" + (year.equals("") ? "" : year);
    String titleUrl = "t=" + movie;
    String plotAndFormat = "&plot=short&r=json";
    String url = "http://omdbapi.com/?" + titleUrl + yearUrl + plotAndFormat;

    String charset = "UTF-8";

    InputStream input = new URL(url).openStream();
    InputStreamReader stream = new InputStreamReader(input, charset);

    TypeToken token = new TypeToken<Map<String, String>>(){};
    Map<String, String> map = new Gson().fromJson(stream, token.getType());

    System.out.println(map.get("imdbID"));
    System.out.println(map.get("Title"));
    System.out.println(map.get("Year"));
    System.out.println(map.get("Genre"));
    System.out.println(map.get("Actors"));
    System.out.println(map.get("Rated"));
    System.out.println(map.get("Runtime"));
    System.out.println(map.get("Plot"));
    return map;
  }
}
