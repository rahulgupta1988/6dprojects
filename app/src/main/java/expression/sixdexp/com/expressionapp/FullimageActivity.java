package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
public class FullimageActivity extends Activity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        BigImageViewer.initialize(GlideImageLoader.with(context));
        setContentView(R.layout.imagefull_activity);
        BigImageView imagefull =(BigImageView)findViewById(R.id.imagefull);
        Intent intent = getIntent();
        String Url = intent.getStringExtra("imageval");

        if (!Url.equalsIgnoreCase("null") && !Url.equalsIgnoreCase("")
                && !Url.equalsIgnoreCase(" ")) {
           imagefull.showImage(Uri.parse(Url));


        }
    }


}
