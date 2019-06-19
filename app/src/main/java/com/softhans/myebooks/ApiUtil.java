package com.softhans.myebooks;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {

    private ApiUtil(){}

    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";

    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY ="key";
    public static final String API_KEY ="AIzaSyAcNfCvwJyrCYjYQPUu6MEuEYBALF0tuZE";

    public  static  URL buildUrl(String title) {

  //Create a URI
        URL url =null;

        // trying to convert the string into a URI
        Uri uri =Uri.parse(BASE_API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                    .appendQueryParameter(KEY, API_KEY)
                    .build();

          try{
                url = new URL(uri.toString());
          }
          catch (Exception e) {
                       e.printStackTrace();
          }
          return url;
          }
          public static String getJason (URL url)  throws IOException {

             HttpURLConnection connection = (HttpURLConnection) url.openConnection();



              try {

                  InputStream stream = connection.getInputStream(); // this allows to read any data

                  //Convert our stream to a string
                  Scanner scanner = new Scanner(stream);

                  //the scanner can be used to delimit large peices of stream into smaller ones.
                  scanner.useDelimiter("\\A"); // This code means we want to read everything.

                    //checking whether we have data
                  boolean hasData = scanner.hasNext();

                  if (hasData) {
                      return scanner.next();
                  } else {
                      return null;
                  }
              }
              catch ( Exception e ){
                  Log.d("Error", e.toString());
                  return  null;
              }
                // Close the connection
              finally {
                  connection.disconnect();
              }
    }


}
