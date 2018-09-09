package expression.sixdexp.com.expressionapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 12-May-17.
 */

public class ActivityChooserDailog_new  extends AppCompatActivity{

    ImageView choosedimg,shareanupdate_icon,recognize_icon;
    TextView cancel_txt;
    Context mContext;
    LinearLayout headerlay;
    CircularImageView user_icon;
    TextView name_degig,share_txt,reco_txt;
    TextView choosetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        mContext=this;

        String userID = SharedPrefrenceManager.getUserID(mContext);
        if (userID == null || userID.equals("")) {
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        else{
            setContentView(R.layout.activitychooser_view);
            initViews();
        }




    }

    public void initViews(){

        share_txt=(TextView)findViewById(R.id.share_txt);
        reco_txt=(TextView)findViewById(R.id.reco_txt);

        share_txt.setVisibility(View.INVISIBLE);
        reco_txt.setVisibility(View.INVISIBLE);

        user_icon=(CircularImageView)findViewById(R.id.user_icon);
        name_degig=(TextView)findViewById(R.id.name_degig);
        choosedimg=(ImageView)findViewById(R.id.choosedimg);
        choosetxt=(TextView) findViewById(R.id.choosetxt);

        shareanupdate_icon=(ImageView)findViewById(R.id.shareanupdate_icon);
        recognize_icon=(ImageView)findViewById(R.id.recognize_icon);
        cancel_txt=(TextView) findViewById(R.id.cancel_txt);
        headerlay=(LinearLayout)findViewById(R.id.headerlay);

        final Intent receivedIntent = getIntent();
        final String action = receivedIntent.getAction();
        final String type = receivedIntent.getType();

        // animwithheader();
        animwithbtn();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                choosetxt.setVisibility(View.GONE);
                choosedimg.setVisibility(View.VISIBLE);
                Uri imageUri = (Uri) receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    choosedimg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if(type.startsWith("text/plain")){
                choosedimg.setVisibility(View.GONE);
                choosetxt.setVisibility(View.VISIBLE);

                String sharedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    Log.i("2312432543",""+sharedText);
                    choosetxt.setText(""+sharedText);
                }
            }
        }


        shareanupdate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent1=new Intent(mContext,ShareActivity.class);
                intent1.setAction(action);
                intent1.setType(type);

                if (Intent.ACTION_SEND.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        Uri imageUri = (Uri) receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                        intent1.putExtra(Intent.EXTRA_STREAM,imageUri);

                    }


                    else if(type.startsWith("text/plain")){

                        String sharedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                        if (sharedText != null) {
                            intent1.putExtra(Intent.EXTRA_STREAM,sharedText);

                        }
                    }

                } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        ArrayList<Uri> imageUris = receivedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        intent1.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
                    }
                }





                startActivityForResult(intent1,431);
               /* startActivity(intent1);
                finish();*/
            }
        });



        recognize_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(mContext,RecogniseActivity.class);
                intent1.setAction(action);
                intent1.setType(type);

                if (Intent.ACTION_SEND.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        Uri imageUri = (Uri) receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                        intent1.putExtra(Intent.EXTRA_STREAM,imageUri);
                    }

                    {

                        String sharedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                        if (sharedText != null) {
                            intent1.putExtra(Intent.EXTRA_STREAM,sharedText);

                        }
                    }

                } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        ArrayList<Uri> imageUris = receivedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        intent1.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
                    }
                }


                startActivityForResult(intent1,431);
               /* startActivity(intent1);
                finish();*/
            }
        });


        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setUserInfo();

    }


    public void setUserInfo(){
        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        String profile_img_url = userDatas.get(0).getImageurl();
        String user_name = userDatas.get(0).getName();
        String designtion= userDatas.get(0).getDesignation();


        String name_str="<b>"+user_name+"</b><br/>"+designtion;
        name_degig.setText(Html.fromHtml(name_str));



        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" ")) {
            Picasso.with(mContext)
                    .load(profile_img_url1)
                    .resize(500,500)
                    .centerInside()
                    .placeholder(R.drawable.icon_profile_drawer_bg)
                    .error(R.drawable.icon_profile_drawer_bg)
                    .into(user_icon);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void animwithbtn(){
        final Animation myAnim2 = AnimationUtils.loadAnimation(mContext, R.anim.bounce1);
        final Animation myAnim3 = AnimationUtils.loadAnimation(mContext, R.anim.bounce1);
        //myAnim2.setFillAfter(true);
        shareanupdate_icon.setAnimation(myAnim2);
        recognize_icon.setAnimation(myAnim3);

        myAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                share_txt.setVisibility(View.VISIBLE);
                doBounceAnimation(share_txt);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        myAnim3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                reco_txt.setVisibility(View.VISIBLE);
                doBounceAnimation(reco_txt);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    public void animwithheader(){
        Animation myAnim1 = AnimationUtils.loadAnimation(mContext, R.anim.headeraniminshare);
        // myAnim1.setFillAfter(true);
        myAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animwithbtn();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        headerlay.startAnimation(myAnim1);
    }

    private void doBounceAnimation(View targetView) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(targetView, "translationY", -90f, 0f);
        animY.setDuration(400);//1sec
        animY.setInterpolator(new LinearInterpolator());
        animY.setRepeatCount(0);
        animY.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==431){
            finish();
        }
    }
}
