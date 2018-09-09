package expression.sixdexp.com.expressionapp.manager;

import android.graphics.Bitmap;

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
public class MultipartServerCall {

    public static String makeConnection(String baseUrl, HashMap<String, String> entity, String method,Bitmap bitmap) {
        String response = "";
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(baseUrl + method);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            writer.write(getPostDataString(entity));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }

            else{
                response = "Can't connect to server.";
                sb.append(response);
                return sb.toString();
            }

        } catch (Exception e) {
            response = "Can't connect to server.";
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
