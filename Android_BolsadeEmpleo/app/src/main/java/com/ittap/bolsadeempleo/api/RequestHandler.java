package com.ittap.bolsadeempleo.api;

import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RequestHandler {

    public int response_code = 0;

    public HashMap<String,String> sendPostRequest(String request_url, HashMap<String, String> post_data) {
        URL url;
        HashMap<String,String> response = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();

        try {

            url = new URL(request_url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream, "UTF-8" ) );
            bufferedWriter.write(getPostDataString(post_data));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            response_code = connection.getResponseCode();
            response.put("response_code", String.valueOf(response_code) );
            response.put("response_message", connection.getResponseMessage());
            Log.d("Http conection", "response_code::" + response_code );
            Log.d("Http conection", "response_message::" + connection.getResponseMessage() );


            InputStream inputStream;



            if( response_code < 400 )
                inputStream = connection.getInputStream();
            else
                inputStream = connection.getErrorStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));




            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            Log.d("Http conection", "response_data::" + stringBuilder.toString() );
            response.put("response_data", stringBuilder.toString());



        } catch ( MalformedURLException e )
        {
            Log.d("Http conection", "Mal formed url exception" );
            e.printStackTrace();
            return response;
        }
        catch ( IOException e )
        {
            Log.d("Http conection", "IO exception" );
            response.put("exception", e.getMessage() );
            return response;
        }
        catch ( NullPointerException e)
        {
            Log.d("Http conection", "Null Pointer exception" );
            response.put("exception", e.getMessage() );
            return response;
        }



    return response;
    }

    public HashMap<String,String> sendGetRequest(String request_url, HashMap<String, String> post_data) {
        URL url;
        HashMap<String,String> response = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();

        try {

            url = new URL(request_url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            //connection.setDoOutput(true);

            //OutputStream outputStream = connection.getOutputStream();

            /*
            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream, "UTF-8" ) );
            bufferedWriter.write(getPostDataString(post_data));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            */

            response_code = connection.getResponseCode();
            response.put("response_code", String.valueOf(response_code) );
            response.put("response_message", connection.getResponseMessage());
            Log.d("GET REQUEST", "response_code::" + response_code );
            Log.d("GET REQUEST", "response_message::" + connection.getResponseMessage() );

            InputStream inputStream;



            if( response_code < 400 )
                inputStream = connection.getInputStream();
            else
                inputStream = connection.getErrorStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            Log.d("GET REQUEST", "response_data: " + stringBuilder.toString() );
            response.put("response_data", stringBuilder.toString());



        } catch ( MalformedURLException e )
        {
            Log.d("Http conection", "Mal formed url exception" );
            e.printStackTrace();
            return response;
        }
        catch ( IOException e )
        {
            Log.d("Http conection", "IO exception" );
            response.put("exception", e.getMessage() );
            return response;
        }
        catch ( NullPointerException e)
        {
            Log.d("Http conection", "Null Pointer exception" );
            response.put("exception", e.getMessage() );
            return response;
        }



        return response;
    }

    private String getPostDataString(HashMap<String,String> post_data) throws UnsupportedEncodingException
    {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String,String> entry : post_data.entrySet() )
        {
            if(first)
                first = false;
            else
                stringBuilder.append("&");

            stringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));

        }

        return  stringBuilder.toString();
    }
}
