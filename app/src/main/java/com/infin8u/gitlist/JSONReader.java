package com.infin8u.gitlist;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONReader {

    /**
     * This method requires a valid URL and a DataReceivedBehavior Object
     * that will act like a bridge for callbacks
     * drb callbacks will contain either a Json Object
     * or an Integer describing the error
     * case Integer : (from ErrorCodes static class)
     * ErrorCodes.UNKNOWN_ERROR
     * ErrorCodes.MALFORMED_URL
     * ErrorCodes.JSON_PARSE_ERROR
     *
     * case JSON Object : obvious
     * */
    public static void fromUrl(final String url,final DataReceivedBehavior drb)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jobj = null;
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                URL objUrl = null;
                try
                {
                    objUrl = new URL(url);
                    inputStream = objUrl.openStream();

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream,
                            Charset.forName("UTF-8")));
                    StringBuilder stringBuilder = new StringBuilder();
                    int rb = 0;
                    while((rb = bufferedReader.read()) != -1)
                    {
                        stringBuilder.append((char)rb);
                    }
                    jobj = new JSONObject(stringBuilder.toString());
                    inputStream.close();
                    drb.SetData(jobj);
                    drb.Execute();
                }catch(MalformedURLException mue)
                {
                    drb.SetData(ErrorCodes.MALFORMED_ERROR);
                    drb.Execute();
                }
                catch(JSONException jex)
                {

                    drb.SetData(ErrorCodes.JSON_PARSE_ERROR);
                    drb.Execute();
                }
                catch(Exception ex)
                {
                    drb.SetData(ErrorCodes.UNKNOWN_ERROR);
                    drb.Execute();
                }
            }
        }).start();


    }
}
