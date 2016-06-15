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
  void getStuff(String toFind) throws IOException {

    String movie = toFind.replace(' ', '+');
    String url = "http://omdbapi.com/?t=" + movie;
    String charset = "UTF-8";

    InputStream input = new URL(url).openStream();
    InputStreamReader stream = new InputStreamReader(input, charset);

    TypeToken token = new TypeToken<Map<String, String>>(){};
    Map<String, String> map = new Gson().fromJson(stream, token.getType());

    String imdbID = map.get("imdbID");
    String title = map.get("Title");
    String year = map.get("Year");
    String genre = map.get("Genre");
    String actors = map.get("Actors");
    String rated = map.get("Rated");
    String runtime = map.get("Runtime");
    String plot = map.get("Plot");

    System.out.println(imdbID);
    System.out.println(title);
    System.out.println(year);
    System.out.println(genre);
    System.out.println(actors);
    System.out.println(rated);
    System.out.println(runtime);
    System.out.println(plot);
  }
}
