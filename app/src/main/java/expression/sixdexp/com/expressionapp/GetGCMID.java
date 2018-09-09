package expression.sixdexp.com.expressionapp;

import android.app.Activity;

/**
 * Created by Praveen on 8/25/2016.
 */
public class GetGCMID extends Activity {


   /* public static IntentFilter gcmFilter;
    private static String broadcastMessage = "No broadcast message";
    private static String regId = "";
    private static String registrationStatus = "Not yet registered";
    public static Context mContext;
    BroadcastReceiver broadcastReceiver=null;

    public GetGCMID(Context context) {
        super();
        // TODO Auto-generated constructor stub
        mContext=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public static BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            broadcastMessage = intent.getExtras().getString("Meeting coming up soon!");
            broadcastMessage = broadcastMessage + "Meeting coming up soon!";
            if (broadcastMessage != null) {
                // display our received message
            }
        }
    };

    public String getGCMId() {
        try {
            gcmFilter = new IntentFilter();
            gcmFilter.addAction("GCM_RECEIVED_ACTION");
            registerClient();
            Constant.notificationId = regId;
            Logger.i(mContext, Constant.notificationId);
            mContext.registerReceiver(gcmReceiver, gcmFilter);
            return regId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (Error r) {
            r.printStackTrace();
            return null;
        }
    }

    public static void registerClient() {
        try {

            // Check that the device supports GCM (should be in a try / catch)
            GCMRegistrar.checkDevice(mContext);
            // Check the manifest to be sure this app has all the required
            // permissions.
            GCMRegistrar.checkManifest(mContext);
            // Get the existing registration id, if it exists.
            regId = GCMRegistrar.getRegistrationId(mContext);
            Logger.i(mContext, "Registration ID = " + regId);
            if (regId.equals("")) {
                registrationStatus = "Registering...";
                Logger.i(mContext, "registrationStatus = " + registrationStatus);

                // register this device for this project
                GCMRegistrar.register(mContext, Constant.GCMSenderId);
                regId = GCMRegistrar.getRegistrationId(mContext);
                Logger.i(mContext, "Registration ID = " + regId);
                registrationStatus = "Registration Acquired";
                // This is actually a dummy function. At this point, one
                // would send the registration id, and other identifying
                // information to your server, which should save the id
                // for use when broadcasting messages.
                sendRegistrationToServer();
            } else {
                registrationStatus = "Already registered";
                Logger.w(mContext, "Already registered");
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrationStatus = e.getMessage();
        }
        Logger.w(mContext, registrationStatus);
    }

    private static void sendRegistrationToServer() {
        // This is an empty placeholder for an asynchronous task to post the
        // registration
        // id and any other identifying information to your server.
        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
        // sets the app name in the intent
        registrationIntent.putExtra("app",
                PendingIntent.getBroadcast(mContext, 0, new Intent(), 0));
        registrationIntent.putExtra("sender", Constant.GCMSenderId);
        mContext.startService(registrationIntent);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("BroadcastMessage", broadcastMessage);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        broadcastMessage = savedInstanceState.getString("BroadcastMessage");
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            unregisterReceiver(gcmReceiver);
            if(broadcastReceiver!=null)
                unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }
*/
}
