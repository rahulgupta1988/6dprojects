package expression.sixdexp.com.expressionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InitCall;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import utils.AppData;

/**
 * Created by Praveen on 6/30/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    EditText login_id, login_password;
    ImageButton btn_login;
    ScrollView loginscrollview;
    String login_id_val, password_val;
    ProgressDialog progressDialog = null;
    Context mContext;
    int heightDifference;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Constant.today = 0;
        Constant.lastposition=0;
        Constant.lastposition_home=0;
        setContentView(R.layout.loginview);
        mContext = this;
        getMarketVersion();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Constant.sc_height=height;

       // new GetGCMID(LoginActivity.this).getGCMId();

        // new InternetConnectionDetector(mContext).new NetworkSniffTask(mContext).execute();

        initview();

    }


    public void initview() {
        login_id = (EditText) findViewById(R.id.login_id);
        login_password = (EditText) findViewById(R.id.login_password);
        btn_login = (ImageButton) findViewById(R.id.btn_login);
//        lost_password_txt = (TextView) findViewById(R.id.lost_password_txt);
        linear = (LinearLayout) findViewById(R.id.linear);

        String mystring = new String("Forgot Password ?");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        //  lost_password_txt.setText(content);

        //set listener
        btn_login.setOnClickListener(this);

        if (!new InternetConnectionDetector(mContext).isConnectingToInternet()) {
            showsnack(getResources().getString(R.string.nework_connect_error));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                loginValidation();
                break;
        }
    }


    public void loginValidation() {

        login_id_val = login_id.getText().toString().trim();
        password_val = login_password.getText().toString().trim();

        if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            String appVersion="0.0";
            try {
                appVersion = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if(appVersion.equals(Constant.version)) {
                if (login_id_val.length() == 0) {
                    showsnack("Please enter login id");
                } else if (password_val.length() == 0) {
                    showsnack("Please enter password");

                }
                else
                {
                    if (new InternetConnectionDetector(mContext).isConnectingToInternet())
                    {
                        Constant.notificationId = FirebaseInstanceId.getInstance().getToken();//new GetGCMID(LoginActivity.this).getGCMId();
                        //Toast.makeText(LoginActivity.this,"Id="+Constant.notificationId,Toast.LENGTH_LONG).show();



                        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_USER_NAME, login_id_val,mContext);
                        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_USER_PASSWORD, password_val,mContext);
                        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_TOKEN_DATA, Constant.notificationId,mContext);

                        new GetLatestVersionCodeOnLogin().execute();



                    }
                    else {
                        showsnack(getResources().getString(R.string.nework_connect_error));

                    }
                }
            }


            else{
                String msg = "New Android version is now available.\nPlease update your app through Google Play Store.";
                versionDailogBox(mContext, msg);
            }

        }

        else {
            showsnack(getResources().getString(R.string.nework_connect_error));

        }

    }


    public class TaskGetData extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new LoginManager(mContext).loginUser(login_id_val, password_val, Constant.notificationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    SharedPrefrenceManager.putLoginID(mContext, login_id_val);
                    SharedPrefrenceManager.putPassword(mContext,password_val);

                    if (new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                        new InitCall(mContext, progressDialog); //HOST Activity
                    }
                    else {
                        showsnack(getResources().getString(R.string.nework_connect_error));

                    }
                }
                else
                {
                    showsnack(responseString);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }





    public void showsnack(String msg)
    {
        Snackbar snackbar = Snackbar
                .make(linear,msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }



    public void getMarketVersion() {
        //new GetVersionCode().execute();
        new GetLatestVersionCode().execute();
    }


    class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                   /* if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                        //show dialog
                    }*/
                Log.d("update", "laystore version " + onlineVersion);

            }
            //Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }


    class GetLatestVersionCode extends AsyncTask<Void, Void, Void> {

        String responseString="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("devicetype","android");
            responseString= ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/GetAppVersion");
            return null;
        }

        @Override
        protected void onPostExecute(Void onlineVersion) {
            super.onPostExecute(onlineVersion);

            String responseCode="";


            progressDialog.dismiss();
            if (responseString != null && !responseString.isEmpty()) {

                try {
                    Log.i("responseString 343",""+responseString);
                    JSONObject jsonObject=new JSONObject(responseString);

                    responseCode=jsonObject.getString("responseCode");
                    JSONArray jsonArray=jsonObject.getJSONArray("responseData");

                    if(responseCode.equals("100")){
                        if(jsonArray!=null && jsonArray.length()>0){
                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            Constant.version=jsonObject1.getString("version");
                            Log.i("version",""+Constant.version);
                            String appVersion=getPackageManager().getPackageInfo(getPackageName(),0).versionName;

                            if(!appVersion.equals(Constant.version)) {
                                String msg = "New Android version is now available.\nPlease update your app through Google Play Store.";
                                versionDailogBox(mContext, msg);
                                //Toast.makeText(mContext,""+version,Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Log.i("asdhj 890",""+responseString);

            }

        }
    }



    class GetLatestVersionCodeOnLogin extends AsyncTask<Void, Void, Void> {

        String responseString="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("devicetype","android");
            responseString= ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/GetAppVersion");
            return null;
        }

        @Override
        protected void onPostExecute(Void onlineVersion) {
            super.onPostExecute(onlineVersion);

            String responseCode="";


            progressDialog.dismiss();
            if (responseString != null && !responseString.isEmpty()) {

                try {
                    Log.i("responseString 343",""+responseString);
                    JSONObject jsonObject=new JSONObject(responseString);

                    responseCode=jsonObject.getString("responseCode");
                    JSONArray jsonArray=jsonObject.getJSONArray("responseData");

                    if(responseCode.equals("100")){
                        if(jsonArray!=null && jsonArray.length()>0){
                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            Constant.version=jsonObject1.getString("version");
                            Log.i("version",""+Constant.version);
                            String appVersion=getPackageManager().getPackageInfo(getPackageName(),0).versionName;

                            if(!appVersion.equals(Constant.version)) {
                                String msg = "New Android version is now available.\nPlease update your app through Google Play Store.";
                                versionDailogBox(mContext, msg);
                                //Toast.makeText(mContext,""+version,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                new TaskGetData().execute();
                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Log.i("asdhj 890",""+responseString);

            }

        }
    }

    Dialog dialog=null;
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

            public void onClick(View v)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
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

}
