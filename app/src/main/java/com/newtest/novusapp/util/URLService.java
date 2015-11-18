package com.newtest.novusapp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ROOT-PC on 10.09.2015.
 */
public class URLService {
    private static String url;

    public URLService(String url) {
        this.url = url;
    }

    public static String getJsonURL(String uri){
        try {
            //установить соединение и выполнить запрос к базе

            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;
                while((json = bufferedReader.readLine())!= null){
                    sb.append(json+"\n");
                }
                String str = sb.toString();
                Log.v("tagg", str);
                str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
                return str;

            }catch(Exception e){
                return null;
            }

        }  finally {
//            try {
//                if (inputStream != null) inputStream.close();
//            } catch (Exception squish) {
//            }
        }


    }

}
