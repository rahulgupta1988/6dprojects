package expression.sixdexp.com.expressionapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.manager.VisitorCounter;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InitCall;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import utils.AppData;


public class LoggedActivity extends Activity implements View.OnClickListener {
    ProgressDialog progressDialog = null;
    Context mContext;
    LinearLayout linear;
    TextView txt_remove_user, txt_tap_login, txt_username;
    String Userprofileimage = "";
    CircularImageView user_profile_img;
    Intent intent;
    String saved_login_id_val = "", saved_password_val = "", saved_token = "";
    String counter_val = "";

    LinearLayout animimglay;
    CircularImageView user_profile_img1;
    ImageView log;
    LinearLayout loggedin_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Constant.today = 0;
        Constant.lastposition = 0;
        Constant.lastposition_home = 0;
        setContentView(R.layout.logged_view_screen);

        animimglay = (LinearLayout) findViewById(R.id.animimglay);
        user_profile_img1 = (CircularImageView) findViewById(R.id.user_profile_img1);
        log = (ImageView) findViewById(R.id.log);

        mContext = this;
        deleteCache(mContext);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Constant.sc_height = height;


        float density = getResources().getDisplayMetrics().density;

        //l Toast.makeText(mContext,""+density,Toast.LENGTH_SHORT).show();

// return 0.75 if it's LDPI
// return 1.0 if it's MDPI
// return 1.5 if it's HDPI
// return 2.0 if it's XHDPI
// return 3.0 if it's XXHDPI
// return 4.0 if it's XXXHDPI


        loggedin_lay = (LinearLayout) findViewById(R.id.loggedin_lay);
        txt_remove_user = (TextView) findViewById(R.id.txt_remove_user);
        txt_tap_login = (TextView) findViewById(R.id.txt_tap_login);
        linear = (LinearLayout) findViewById(R.id.linear);
        //txt_tap_login.setOnClickListener(this);
        txt_remove_user.setOnClickListener(this);
        user_profile_img = (CircularImageView) findViewById(R.id.user_profile_img);
        //user_profile_img.setOnClickListener(this);
        loggedin_lay.setOnClickListener(this);
        txt_username = (TextView) findViewById(R.id.txt_username);

        setUserInfo();


        SharedPrefrenceManager sharedPrefrenceManager = new SharedPrefrenceManager();
        String logged_color_no = sharedPrefrenceManager.getLoggedColorNo(mContext);
        if (logged_color_no.equals("1")) {
            linear.setBackgroundColor(getResources().getColor(R.color.firstcolor));
            sharedPrefrenceManager.putLoggedColorNo(mContext, "2");
        } else if (logged_color_no.equals("2")) {
            linear.setBackgroundColor(getResources().getColor(R.color.secondcolor));
            sharedPrefrenceManager.putLoggedColorNo(mContext, "3");
        } else if (logged_color_no.equals("3")) {
            linear.setBackgroundColor(getResources().getColor(R.color.thirdcolor));
            sharedPrefrenceManager.putLoggedColorNo(mContext, "1");
        }


    /* ValueAnimator colorAnim = ObjectAnimator.ofInt(linear, "backgroundColor", R.color.firstcolor,R.color.secondcolor,R.color.thirdcolor);
                colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();*/




      /*  BackgroundPainter backgroundPainter = new BackgroundPainter();

        int color1 = ContextCompat.getColor(this, R.color.colorAccent);
        int color2 = ContextCompat.getColor(this, R.color.colorPrimary);

        backgroundPainter.animate(linear, color1, color2);
*/


    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loggedin_lay:
                if (new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                    //Toast.makeText(LoginActivity.this,"lOGGED",Toast.LENGTH_LONG).show();
//                    SharedPrefrenceManager.putLoginID(mContext, login_id_val);
//                    SharedPrefrenceManager.putPassword(mContext,password_val);
                    //new InitCall(mContext,progressDialog);
                    // new InitCall(mContext, progressDialog); //HOST Activity
                    getMarketVersion();
                    //new GetVisitorCounter().execute();
                   /* new GetVisitorCounter().execute();*/
                   /* intent = new Intent(LoggedActivity.this, FlipComplexLayoutActivity.class);
                    startActivity(intent);
                    finish();*/


                } else {
                    showsnack(getResources().getString(R.string.nework_connect_error));

                }
                break;

            case R.id.txt_remove_user:
                if (new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                    logoutDailogBox(mContext, "Are you sure,  You want to Logout? ");
                } else {
                    showsnack(getResources().getString(R.string.nework_connect_error));

                }
                break;

            /*case R.id.user_profile_img:
                if (new InternetConnectionDetector(mContext).isConnectingToInternet())
                {
                    //Toast.makeText(LoginActivity.this,"lOGGED",Toast.LENGTH_LONG).show();
//                    SharedPrefrenceManager.putLoginID(mContext, login_id_val);
//                    SharedPrefrenceManager.putPassword(mContext,password_val);
                    //new InitCall(mContext,progressDialog);
*//*
                    String appVersion= "0.0";
                    try {
                        appVersion =getPackageManager().getPackageInfo(getPackageName(),0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(!appVersion.equals(Constant.version)) {
                       // Toast.makeText(mContext,""+appVersion+" "+version,Toast.LENGTH_SHORT).show();
                        String msg = "New Android version is now available.\nPlease update your app through Google Play Store.";
                        versionDailogBox(mContext, msg);

                    }

                    else{*//*
                        getMarketVersion();

                   // }


                   *//* new GetMorePostTask().execute();
                    new InitCall(mContext, progressDialog); //HOST Activity
                    new GetVisitorCounter().execute();
                    new GetExpresswayPostTask().execute();
                    new GetBulletenCategory().execute();



                    intent = new Intent(LoggedActivity.this, FlipComplexLayoutActivity.class);
                    startActivity(intent);
                    finish();*//*
                }
                else {
                    showsnack(getResources().getString(R.string.nework_connect_error));

                }
                break;*/
        }
    }


    public void logoutDailogBox(Context mCtx, final String msg) {
        dialog = new Dialog(mCtx);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirmdialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvMsg = (TextView) window.findViewById(R.id.msg_yesno);
        TextView tvNO = (TextView) window.findViewById(R.id.no_yesno);
        TextView tvYes = (TextView) window.findViewById(R.id.yes_yesno);
        tvMsg.setText(msg);

        tvNO.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }

                DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                daoSession.getUserLoginInfoDao().deleteAll();
                SharedPrefrenceManager.putSelectedTab(mContext, "");
                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                SharedPrefrenceManager.putUserID(mContext, null);
                SharedPrefrenceManager.putPassword(mContext, null);
                SharedPrefrenceManager.putUsername(mContext, null);
            }
        });
        dialog.show();
    }

    public class GetVisitorCounter extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

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
                responseString = new VisitorCounter().callVisitorCounter();
                //insetUserdata();
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
                    String vistor_counter = VisitorCounter.vistor_counter;
                    Log.i("counter", "" + vistor_counter);
                    counter_val = vistor_counter;
                    AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Visitors_Counter, counter_val, mContext);
                } else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class GetMorePostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
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
            try {
                progressDialog.dismiss();
                if (responseString.equals("100")) {

                } else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class GetExpresswayPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetXpresswayPost();
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

                } else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class GetBulletenCategory extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetBulletinCategory();
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
                    Toast.makeText(mContext, "You have logged successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class TaskGetData extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(LoggedActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new LoginManager(mContext).loginUser(saved_login_id_val, saved_password_val, saved_token);
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
                    // Toast.makeText(LoggedActivity.this,"lOGin Data Successfully!",Toast.LENGTH_LONG).show();

                    //getMarketVersion();
                } else {
                    showsnack(responseString);
            /*    // Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void showsnack(String msg) {
        Snackbar snackbar = Snackbar
                .make(linear, msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }


    public String getDayWish() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("WrongConstant") int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            return "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            return "Good Night";
        }
        return "";
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (children[i].equals("picasso-cache")) {
                    Log.i("dir1010", children[i]);
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }


            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void getMarketVersion() {
        //new GetVersionCode().execute();
        new GetLatestVersionCode().execute();
    }

    class GetLatestVersionCode extends AsyncTask<Void, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(LoggedActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("devicetype", "android");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/GetAppVersion");
            return null;
        }

        @Override
        protected void onPostExecute(Void onlineVersion) {
            super.onPostExecute(onlineVersion);

            String responseCode = "";


            progressDialog.dismiss();
            if (responseString != null && !responseString.isEmpty()) {

                try {
                    JSONObject jsonObject = new JSONObject(responseString);

                    responseCode = jsonObject.getString("responseCode");
                    JSONArray jsonArray = jsonObject.getJSONArray("responseData");

                    if (responseCode.equals("100")) {
                        if (jsonArray != null && jsonArray.length() > 0) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            Constant.version = jsonObject1.getString("version");
                            Log.i("version", "" + Constant.version);
                            String appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

                            if (!appVersion.equals(Constant.version)) {
                                String msg = "New Android version is now available.\nPlease update your app through Google Play Store.";
                                versionDailogBox(mContext, msg);
                                //Toast.makeText(mContext,""+version,Toast.LENGTH_SHORT).show();
                            } else {
                                new InitCall(mContext, progressDialog);
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "New version available Plese Update app from google play.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("asdhj 890", "" + responseString);

            }

        }
    }

    Dialog dialog = null;

    public void versionDailogBox(final Context mCtx, final String msg) {
        dialog = new Dialog(mCtx);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.versiondialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvMsg = (TextView) window.findViewById(R.id.msg_yesno);
        TextView tvNO = (TextView) window.findViewById(R.id.no_yesno);
        TextView tvYes = (TextView) window.findViewById(R.id.yes_yesno);
        tvMsg.setText(msg);

        tvNO.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        dialog.show();
    }

    TextToSpeech textToSpeech;

    public void textTOspeech(final String username) {
        textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speakOut(username);
            }
        });
    }

    private void speakOut(String username) {

        textToSpeech.speak(username, TextToSpeech.QUEUE_FLUSH, null);
    }


    public void setUserInfo() {

        List<UserLoginInfo> userDatas = null;
        userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        if (userDatas != null) {
            Userprofileimage = userDatas.get(0).getImageurl();

            String profile_img_url = userDatas.get(0).getImageurl();
            String user_name = userDatas.get(0).getName();

            if (user_name != null && !user_name.equalsIgnoreCase("")) {

                txt_username.setText(getDayWish() + " " + user_name);
                //textTOspeech(getDayWish()+" "+user_name);
            }
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
                        .resize(800, 800)
                        .centerInside()
                        .placeholder(R.drawable.default_profile_picture)
                        .error(R.drawable.default_profile_picture)
                        .into(user_profile_img1);
            }


            Picasso.with(mContext)
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img);
        }


        Animation myAnim1 = AnimationUtils.loadAnimation(mContext, R.anim.userimageanim);
        myAnim1.setFillAfter(true);
        myAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                user_profile_img1.setVisibility(View.GONE);
                animimglay.setVisibility(View.GONE);
                expressionLogoAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        animimglay.startAnimation(myAnim1);
    }

    public void expressionLogoAnim() {
        final Animation myAnim2 = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        //myAnim2.setFillAfter(true);
        myAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    if (new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                                        saved_token = Constant.notificationId = FirebaseInstanceId.getInstance().getToken();

                                        saved_login_id_val = AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_USER_NAME, mContext);
                                        saved_password_val = AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_USER_PASSWORD, mContext);
                                        saved_token = AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_TOKEN_DATA, mContext);
                                        new TaskGetData().execute();
                                    } else {
                                        showsnack(getResources().getString(R.string.nework_connect_error));

                                    }
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        log.startAnimation(myAnim2);


    }

}
