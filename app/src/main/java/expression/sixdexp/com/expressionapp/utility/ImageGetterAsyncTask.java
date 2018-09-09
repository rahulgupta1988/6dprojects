package expression.sixdexp.com.expressionapp.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Praveen on 05-Apr-17.
 */

public class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {


    private LevelListDrawable levelListDrawable;
    private Context context;
    private String source;
    private TextView t;

    public ImageGetterAsyncTask(Context context, String source, LevelListDrawable levelListDrawable) {
        this.context = context;
        this.source = source;
        this.levelListDrawable = levelListDrawable;
    }

    @Override
    protected Bitmap doInBackground(TextView... params) {
        t = params[0];
        try {
            //  Log.d(LOG_CAT, "Downloading the image from: " + source);
            return Picasso.with(context).load(source).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
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


            Drawable result = new BitmapDrawable(context.getResources(), bitmap);
               /* Point size = new Point();
                ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
                // Lets calculate the ratio according to the screen width in px
                int multiplier = size.x / bitmap.getWidth();*/
            // Log.d(LOG_CAT, "multiplier: " + multiplier);
            levelListDrawable.addLevel(1, 1, result);
            // Set bounds width  and height according to the bitmap resized size
            //levelListDrawable.setBounds(0, 0, bitmap.getWidth() * multiplier, bitmap.getHeight() * multiplier);



            levelListDrawable.setBounds(0, 5, temp_w + result.getIntrinsicWidth(), temp_h
                    + result.getIntrinsicHeight());


            levelListDrawable.setLevel(1);
            t.setText(t.getText()); // invalidate() doesn't work correctly...


        } catch (Exception e) { /* Like a null bitmap, etc. */ }
    }

    public double getdeviceInch(){
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double wi=(double)width/(double)displaymetrics.xdpi;
        double hi=(double)height/(double)displaymetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        Log.i("height133",""+height);



        return screenInches;
    }
}