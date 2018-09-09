package expression.sixdexp.com.expressionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import db.CelebrationMoments;
import expression.sixdexp.com.expressionapp.manager.CelebrationLikeCommentManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.manager.singleEventManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 25-Apr-17.
 */

public class EventdetailView extends AppCompatActivity {

    String postId;
    String notificationmasterid;
    String recoid;
    String fromnotification;
    Context mContext;
    ImageView event_icon,event_banner_icon;
    TextView event_txt,user_name_txt,work_anw_txt;
    ProgressDialog progressDialog;
    CircularImageView user_profile_img;
    LinearLayout nodata_lay,main_lay;
    ImageView commentit;
    LinearLayout likeit,unlikeit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdetailview);
        mContext = this;
        Constant.singleEvent.clear();
        postId =getIntent().getStringExtra("postId");
        notificationmasterid=getIntent().getStringExtra("notificationmasterid");
        recoid=getIntent().getStringExtra("recoid");
        fromnotification=getIntent().getStringExtra("fromnotification");
        initView();
    }

    int likeskpiORcomment=0;
    public void initView() {


        if (postId != null && !postId.equalsIgnoreCase("")
                && notificationmasterid!=null && !notificationmasterid.equalsIgnoreCase("")
                && recoid != null && !recoid.equalsIgnoreCase("") ) {
            nodata_lay=(LinearLayout)findViewById(R.id.nodata_lay);
            main_lay=(LinearLayout)findViewById(R.id.main_lay);
            commentit = (ImageView) findViewById(R.id.commentit);
            likeit = (LinearLayout) findViewById(R.id.likeit);
            unlikeit= (LinearLayout) findViewById(R.id.unlikeit);



            event_icon=(ImageView)findViewById(R.id.event_icon);
            event_banner_icon=(ImageView)findViewById(R.id.event_banner_icon);
            user_profile_img=(CircularImageView)findViewById(R.id.user_profile_img);
            event_txt=(TextView) findViewById(R.id.event_txt);
            work_anw_txt=(TextView)findViewById(R.id.work_anw_txt);
            user_name_txt=(TextView)findViewById(R.id.user_name_txt);


            commentit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeskpiORcomment=1;
                    commentWondow();
                }
            });

            likeit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeskpiORcomment=0;
                    commentit.setVisibility(View.GONE);
                    likeit.setVisibility(View.GONE);
                    unlikeit.setVisibility(View.GONE);
                    new LikeORSkipCelebration().execute();
                }
            });

            new GetCelebration().execute();
        } else {
            Toast.makeText(mContext, "PostID Should not be null", Toast.LENGTH_LONG).show();
        }
    }

    public class GetCelebration extends AsyncTask<String, Void, Void> {

        String responseString = "";
        singleEventManager celebrationMomentsManger=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if(progressDialog==null){
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                celebrationMomentsManger= new singleEventManager(mContext);
                responseString=celebrationMomentsManger.callCelebration(recoid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                progressDialog.dismiss();
                if (responseString.equals("100")) {
                    setCelebration();
                    new GetReadNotification().execute();

                }

                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();


                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    String eventid="";
    String eventmaster_id="";
    String comment_user_id="";
    String comment_txt="";

    public void setCelebration(){

        if(Constant.singleEvent.size()>0)
        {
            nodata_lay.setVisibility(View.GONE);
            main_lay.setVisibility(View.VISIBLE);
            CelebrationMoments celebrationMoments=Constant.singleEvent.get(0);
            String title=celebrationMoments.getTitle();
            eventmaster_id=celebrationMoments.getEventmaster_id();
            eventid=celebrationMoments.getEvent_id();
            comment_user_id=celebrationMoments.getUserid();

            if(title!=null){
                event_txt.setText(""+title);
            }
            if(eventmaster_id.equals("2")){
                work_anw_txt.setText("Celebrating "+celebrationMoments.getWork_year()+" years at Tata Power.");
            }

            else{
                work_anw_txt.setText("");
            }


            if(eventmaster_id!=null && !eventmaster_id.equals("") && eventmaster_id.equals("1")){
                event_icon.setImageDrawable(null);
                event_icon.setImageResource(R.drawable.birthday_event_icon);
                event_banner_icon.setImageDrawable(null);
                event_banner_icon.setImageResource(R.drawable.birthday_event_banner);
            }

            else  if(eventmaster_id!=null && !eventmaster_id.equals("") && eventmaster_id.equals("2")){
                event_icon.setImageDrawable(null);
                event_icon.setImageResource(R.drawable.work_event_icon);
                event_banner_icon.setImageDrawable(null);
                event_banner_icon.setImageResource(R.drawable.work_anniversary_banner);


            }

            else  if(eventmaster_id!=null && !eventmaster_id.equals("") && eventmaster_id.equals("3")){
                event_icon.setImageDrawable(null);
                event_icon.setImageResource(R.drawable.wedding_event_icon);
                event_banner_icon.setImageDrawable(null);
                event_banner_icon.setImageResource(R.drawable.wedding_anniversary_banner);
            }



            String user_name=celebrationMoments.getName();

            if (user_name != null && !user_name.equalsIgnoreCase(""))
            {
                /*String[] strArray = user_name.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String s : strArray) {
                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                    builder.append(cap + " ");
                }*/
                user_name_txt.setText(user_name);
            }




            String profile_img_url = celebrationMoments.getImageurl();
            URI uri = null;
            try {
                uri = new URI(profile_img_url.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            String profile_img_url1 = uri.toString();
            if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                    && !profile_img_url.equalsIgnoreCase(" "))
            {

                Log.i("4326543675",""+profile_img_url1);
                Picasso.with(mContext)
                        .load(profile_img_url1)
                        .resize(150, 150)
                        .centerCrop()
                        .placeholder(R.drawable.icon_profile_drawer_bg)
                        .error(R.drawable.icon_profile_drawer_bg)
                        .into(user_profile_img);
            }




        }

        else{
            main_lay.setVisibility(View.GONE);
            nodata_lay.setVisibility(View.VISIBLE);
        }

    }

    View window_layout=null;
    PopupWindow changeSortPopUp=null;

    public void commentWondow() {
        if(changeSortPopUp==null){
            changeSortPopUp = new PopupWindow(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            window_layout = layoutInflater.inflate(R.layout.celebration_comment_window, null);

            changeSortPopUp.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }




        ImageView subcombtn=(ImageView)window_layout.findViewById(R.id.subcombtn);
        ImageView cancelshare=(ImageView) window_layout.findViewById(R.id.cancelshare);
        final EditText commenttxt=(EditText)window_layout.findViewById(R.id.commenttxt);


        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_txt=commenttxt.getText().toString().trim();
                if(!comment_txt.equals("")){
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)(mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }

                    likeit.setVisibility(View.GONE);
                    unlikeit.setVisibility(View.GONE);
                    commentit.setVisibility(View.GONE);
                    changeSortPopUp.dismiss();
                    new LikeORSkipCelebration().execute();
                }
                else{
                    Toast.makeText(mContext,"Please Enter Comment",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });

        Rect rc = new Rect();
        commenttxt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        commenttxt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(window_layout);

        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        int height=displayMetrics.heightPixels;

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/3));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/3));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(window_layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }

    String like_skip="like";
    public class LikeORSkipCelebration extends AsyncTask<String, Void, Void> {

        String responseString = "";
        CelebrationLikeCommentManager celebrationLikeCommentManager=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //String eventid,String rc_userid,String eventmid,String eventcomment
                celebrationLikeCommentManager= new CelebrationLikeCommentManager(mContext);
                if(likeskpiORcomment==0)
                    responseString=celebrationLikeCommentManager.callCelebrationLikeSkip(like_skip,eventid);
                else if(likeskpiORcomment==1)
                    responseString=celebrationLikeCommentManager.callCelebrationComment(eventid,comment_user_id,
                            eventmaster_id,comment_txt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    comment_txt="";
                    if(likeskpiORcomment==0){
                        unlikeit.setVisibility(View.GONE);
                        likeit.setVisibility(View.GONE);
                    }

                }
                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
            if(progressDialog==null){
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }

        }
        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(postId,notificationmasterid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
           // progressDialog.dismiss();
            if (responseString.equals("100")) {
                if(fromnotification!=null) {
                    new GetMorePostRefresh().execute();
                }

                else{
                    progressDialog.dismiss();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(fromnotification!=null){
            finish();
            //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else{
            super.onBackPressed();
        }

    }


    public class GetMorePostRefresh extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            //progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).callGetMorePost();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

            } else {

                // progressDialog.dismiss();
            }

        }


    }


}
