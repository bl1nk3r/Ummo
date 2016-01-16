package com.example.barnes.ummo.ummoAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by barnes on 11/1/15.
 */
public class FormPoster
{
    //Always Override For QUserListner Interface



    private URL url;
    // from Chapter 5, Example 5-8
    private QueryString query = new QueryString();

    public FormPoster(URL url) {
        if (!url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException(
                    "Posting only works for http URLs");
        }
        this.url = url;
    }

    public void add(String name, String value) {
        query.add(name, value);
    }

    public URL getURL() {
        return this.url;
    }

    public InputStream post() throws IOException {
// open the connection and prepare it to POST

        HttpURLConnection uc = (HttpURLConnection)url.openConnection();
        uc.setDoOutput(true);
        try {
            OutputStreamWriter out
                    = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
// The POST line, the Content-type header,
// and the Content-length headers are sent by the URLConnection.
// We just need to send the data
            out.write(query.toString());
            out.write("\r\n");
            out.flush();
        }

        catch (UnknownHostException e){}
// Return the response
        return uc.getInputStream();
    }
}