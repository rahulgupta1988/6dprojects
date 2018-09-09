package expression.sixdexp.com.expressionapp.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Praveen on 17-Jan-17.l
 */

public class URLImageParser implements Html.ImageGetter {
    Context c;
    View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     *
     * @param t
     * @param c
     */
    public URLImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }


    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            try {

                int temp_h=0;
                int temp_w=0;
                if(getdeviceInch()>4.5 && getdeviceInch()<=5.0){
                    temp_h=30;
                    temp_w=30;
                }

                else if(getdeviceInch()>5.0){
                    temp_h=40;
                    temp_w=40;
                }
                else{
                    temp_h=20;
                    temp_w=20;
                }

                urlDrawable.setBounds(0, 0, temp_w + result.getIntrinsicWidth(), temp_h
                        + result.getIntrinsicHeight());

                // change the reference of the current drawable to the result
                // from the HTTP call
                urlDrawable.drawable = result;

                // redraw the image by invalidating the container
                URLImageParser.this.container.invalidate();
                TextView textView=(TextView)container;
                textView.setText(textView.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Drawable fetchDrawable(String urlString) {
            try {
               InputStream is = fetch(urlString);
                //InputStream is = testFetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");

                int temp_h=0;
                int temp_w=0;
                if(getdeviceInch()>4.5 && getdeviceInch()<=5.0){
                    temp_h=30;
                    temp_w=30;
                }

                else if(getdeviceInch()>5.0){
                    temp_h=42;
                    temp_w=42;
                }
                else{
                    temp_h=20;
                    temp_w=20;
                }

                drawable.setBounds(0, 0, temp_w + drawable.getIntrinsicWidth(), temp_h
                        + drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

      private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            //httpClient.clearRequestInterceptors();
            //httpClient.clearResponseInterceptors();
            return response.getEntity().getContent();

         /* URL url = new URL(urlString);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          return conn.getInputStream();*/

        }
    }

    public double getdeviceInch(){
        DisplayMetrics displaymetrics = c.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double wi=(double)width/(double)displaymetrics.xdpi;
        double hi=(double)height/(double)displaymetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        return screenInches;
    }


}

