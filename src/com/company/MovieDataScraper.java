package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

class MovieDataScraper {
  Map<String, String> getMovieData(String imdbId, String movie, String year) throws IOException {

    movie = movie.replace(' ', '+');

    String imdbIdUrl = "i=" + imdbId;
    String titleUrl = "t=" + movie;
    String yearUrl = "&y=" + (year.equals("") ? "" : year);
    String plotAndFormat = "&plot=short&r=json";
    String url = "http://omdbapi.com/?";// + titleUrl + yearUrl + plotAndFormat;
    if(!imdbId.equals("")) {
      url += imdbIdUrl + plotAndFormat;
    } else {
      url += titleUrl + yearUrl + plotAndFormat;
    }

    String charset = "UTF-8";

    InputStream input = new URLConnection(url).openStream();
    InputStreamReader stream = new InputStreamReader(input, charset);

    TypeToken token = new TypeToken<Map<String, String>>(){};

//    System.out.println(map.get("imdbID"));
//    System.out.println(map.get("Title"));
//    System.out.println(map.get("Year"));
//    System.out.println(map.get("Genre"));
//    System.out.println(map.get("Actors"));
//    System.out.println(map.get("Rated"));
//    System.out.println(map.get("Runtime"));
//    System.out.println(map.get("Plot"));
    return new Gson().fromJson(stream, token.getType());
  }
}
