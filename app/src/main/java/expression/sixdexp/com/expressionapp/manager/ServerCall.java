package expression.sixdexp.com.expressionapp.manager;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Praveen on 7/7/2016.
 */
public class ServerCall {

    public static String makeConnection(String baseUrl, HashMap<String, String> entity, String method) {
        String response = "";
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(baseUrl + method);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(50000);
            //conn.setReadTimeout(50000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(entity));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.i("responseCode12431",""+responseCode);

            switch (responseCode) {

                case HttpURLConnection.HTTP_OK:
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    sb = new StringBuilder();
                    while ((response = br.readLine()) != null) {
                        sb.append(response);}
                    break;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    response = "Gateway Timeout.";
                    sb.append(response);
                    break;
                case HttpURLConnection.	HTTP_CLIENT_TIMEOUT:
                    response = "Request Time-Out.";
                    sb.append(response);
                    break;

                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    response = "Internal Server Error.";
                    sb.append(response);
                    break;

                case HttpURLConnection.HTTP_UNAVAILABLE:
                    response = "Service Temporarily Unavailable.";
                    sb.append(response);
                    break;

                default:
                    response = "Some error occurred, please try again.";
                    sb.append(response);
                    break;
            }


        }

        catch (Exception e) {
            response = "Some error occurred, please try again.";
            sb.append(response);
            e.printStackTrace();
            return sb.toString();

        }

        return sb.toString();
    }


    private static String getPostDataString(HashMap<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            try {
                Log.i(""+entry.getKey(),""+entry.getValue());
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }




}
