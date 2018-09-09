package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import db.AllUsersDao;
import db.DaoSession;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.TestIntentService;
import utils.AppData;

public class SplashActivity extends Activity
{
    TextView tv_version;
    String terms_id;
    String Userid="0";
    // MediaPlayer sound;
    Context mContext;
    public static SplashActivity smCurrentActivity=null;
    public static SplashActivity getCurrentActivity()
    {
        return smCurrentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splashview);
        mContext = this;

       /* NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        smCurrentActivity=this;*/

        String val = "1";
        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_Home, val,mContext);
        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_ExpresssWay, val,mContext);


        try
        {
            DaoSession daoSession = BaseManager.getDBSessoin(this);
            Constant.daoSession=daoSession;
            AllUsersDao allUsersDao = daoSession.getAllUsersDao();
            long totalusers = allUsersDao.count();
            Log.i("totalusers", "" + totalusers);
            Intent intent = new Intent(this, TestIntentService.class);
            intent.putExtra("totalusers",totalusers);


            if (new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                startService(intent);
            }
            else{
                Toast.makeText(mContext,"Please check your internet connection.",Toast.LENGTH_SHORT).show();
            }

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(getApplicationContext()).getUserInfo();
            Userid = userDatas.get(0).getUserid();

          //  Toast.makeText(getApplicationContext(), "Userid=" + Userid, Toast.LENGTH_LONG).show();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        tv_version=(TextView)findViewById(R.id.tv_version);
        try {
            String appVersion=getPackageManager().getPackageInfo(getPackageName(),0).versionName;
             tv_version.setText("Version: " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Thread timer = new Thread() {

            public void run() {

                try {
                    sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                finally
                {
                    {
                        {
                            if (Userid.equalsIgnoreCase("0"))
                            {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class); //SignUPActivity
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
//                                Intent intent = new Intent(SplashActivity.this, HostActivty.class);
//                                startActivity(intent);
//                                finish();

                                Intent intent = new Intent(SplashActivity.this, LoggedActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }

            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        // sound.release();
        finish();
    }


}