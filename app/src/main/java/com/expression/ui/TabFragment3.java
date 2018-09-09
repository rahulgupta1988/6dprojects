package com.expression.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import db.CelebrationMoments;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecogniseActivity;
import expression.sixdexp.com.expressionapp.ShareActivity;
import expression.sixdexp.com.expressionapp.manager.CelebrationLikeCommentManager;
import expression.sixdexp.com.expressionapp.manager.CelebrationMomentsManger;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SearchActivity;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

public class TabFragment3 extends Fragment {
    View view;
    Context mContext;
    ImageView event_icon,event_banner_icon;
    TextView event_txt,user_name_txt,work_anw_txt;
    ProgressDialog progressDialog;
    CircularImageView user_profile_img;
    LinearLayout nodata_lay,main_lay;
    int likeskpiORcomment=0;
    public TabFragment3(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        view= inflater.inflate(R.layout.tab_fragment_3, container, false);

        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setExpandView();

    }

    public void initView(){
        nodata_lay=(LinearLayout)view.findViewById(R.id.nodata_lay);
        main_lay=(LinearLayout)view.findViewById(R.id.main_lay);

        event_icon=(ImageView)view.findViewById(R.id.event_icon);
        event_banner_icon=(ImageView)view.findViewById(R.id.event_banner_icon);
        user_profile_img=(CircularImageView)view.findViewById(R.id.user_profile_img);
        event_txt=(TextView) view.findViewById(R.id.event_txt);
        work_anw_txt=(TextView) view.findViewById(R.id.work_anw_txt);
        user_name_txt=(TextView) view.findViewById(R.id.user_name_txt);
        new GetCelebration().execute();
        //setCelebration();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class GetCelebration extends AsyncTask<String, Void, Void> {

        String responseString = "";
        CelebrationMomentsManger celebrationMomentsManger=null;
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
                celebrationMomentsManger= new CelebrationMomentsManger(mContext);
                responseString=celebrationMomentsManger.callCelebration();
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
                if (responseString.equals("100")){
                    setCelebration();
                    if(changeSortPopUp!=null)
                        changeSortPopUp.dismiss();
                }
                /*{
                    List<CelebrationMoments> celebrationMomentsList=celebrationMomentsManger.getCelebrationList();
                    if(celebrationMomentsList.size()>0)
                    {
                        nodata_lay.setVisibility(View.GONE);
                        main_lay.setVisibility(View.VISIBLE);
                        CelebrationMoments celebrationMoments=celebrationMomentsList.get(0);
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
                            *//*String[] strArray = user_name.split(" ");
                            StringBuilder builder = new StringBuilder();
                            for (String s : strArray) {
                                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                                builder.append(cap + " ");
                            }*//*
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
                                    .resize(60, 60)
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

                }*/

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


    public void setFloatingButton(){
        final FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu)view.findViewById(R.id.fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded()
            {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        final FloatingActionButton actionA = (FloatingActionButton)view.findViewById(R.id.fab_search);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fabMenu.collapse();
                SharedPrefrenceManager.putSelectedTab(mContext, "searchtab");
                //actionA.setTitle("Search clicked");
                Intent searchactiivity = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(searchactiivity);
            }
        });

        final FloatingActionButton actionB = (FloatingActionButton)view.findViewById(R.id.fab_recognize);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionB.setTitle("Recognize clicked");
                fabMenu.collapse();
                Intent recognizeActivityIntent = new Intent(mContext, RecogniseActivity.class);
                mContext.startActivity(recognizeActivityIntent);

            }
        });

        final FloatingActionButton actionC = (FloatingActionButton)view.findViewById(R.id.fab_shareupdate);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionC.setTitle("Share and Update clicked");
                fabMenu.collapse();
                Intent shareactvityintent = new Intent(mContext, ShareActivity.class);
                mContext.startActivity(shareactvityintent);

            }
        });
    }

    LinearLayout unlikeit,likeit,skip_lay;
    ImageView commentit;
    public void setExpandView(){
        skip_lay= (LinearLayout) view.findViewById(R.id.skip_lay);
        likeit = (LinearLayout) view.findViewById(R.id.likeit);
        unlikeit = (LinearLayout) view.findViewById(R.id.unlikeit);
        final LinearLayout notification_ll=(LinearLayout) view.findViewById(R.id.notification_ll);
        commentit = (ImageView) view.findViewById(R.id.commentit);
        final ImageView notification_img=(ImageView)view.findViewById(R.id.notification_img);
        final ImageView share_img=(ImageView)view.findViewById(R.id.share_img);
        final ImageView email_img=(ImageView)view.findViewById(R.id.email_img);
        share_img.setVisibility(View.GONE);
        email_img.setVisibility(View.GONE);
        notification_img.setBackgroundResource(R.drawable.more_disable);
        notification_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (share_img.getVisibility() == View.VISIBLE) {
                    // first visible
                    share_img.setVisibility(View.GONE);
                    email_img.setVisibility(View.GONE);
                    notification_img.setBackgroundResource(R.drawable.more_disable);
                } else {
                    // first gone
                    notification_img.setBackgroundResource(R.drawable.more_enable);
                    share_img.setVisibility(View.VISIBLE);
                    email_img.setVisibility(View.VISIBLE);

                }
            }
        });


        likeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeskpiORcomment=0;
                likeit.setVisibility(View.GONE);
                unlikeit.setVisibility(View.VISIBLE);
                like_skip="like";
                new LikeORSkipCelebration().execute();
            }
        });

       /* unlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unlikeit.setVisibility(View.GONE);
                likeit.setVisibility(View.VISIBLE);

                //new TabFragmentFirstAapterPage.PostLike().execute(postid);
            }
        });*/

        skip_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeskpiORcomment=0;
                like_skip="skip";
                new LikeORSkipCelebration().execute();
            }
        });


        commentit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeskpiORcomment=1;
                commentWondow();
            }
        });

    }

    String like_skip="";
    String eventid="";
    String eventmaster_id="";
    String comment_user_id="";
    String comment_txt="";
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
                        likeit.setVisibility(View.VISIBLE);
                    }

                    new GetCelebration().execute();
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

    public void setCelebration(){
        CelebrationMomentsManger celebrationMomentsManger= new CelebrationMomentsManger(mContext);
        List<CelebrationMoments> celebrationMomentsList=celebrationMomentsManger.getCelebrationList();
        if(celebrationMomentsList.size()>0)
        {
            nodata_lay.setVisibility(View.GONE);
            main_lay.setVisibility(View.VISIBLE);
            CelebrationMoments celebrationMoments=celebrationMomentsList.get(0);
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

}