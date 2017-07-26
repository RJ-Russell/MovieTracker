/*
    Copyright 2017 RJ Russell

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:

    The above copyright notice and this permission notice shall be included
    in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
    OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
    CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
    TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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
