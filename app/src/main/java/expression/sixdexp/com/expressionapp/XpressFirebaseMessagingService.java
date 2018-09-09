package expression.sixdexp.com.expressionapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.expression.ui.FlipComplexLayoutActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import expression.sixdexp.com.expressionapp.utility.Logger;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.xpassions.GPInfoActivity;
import expression.sixdexp.com.expressionapp.xpassions.GPNotificationdetailsView;
import expression.sixdexp.com.expressionapp.xpassions.GPOtherPollDetails_notify_Activty;

/**
 * Created by Praveen on 25-Apr-18.
 */

public class XpressFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    public static int notificationcount = 0;
    Context context=null;

    private static final int NOTIFICATION_ID = 237;
    private static int value = 0;
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Log.d(TAG, "From: " + remoteMessage.getData());
       /* Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Title: " + remoteMessage.getNotification().getTitle());
        reactToNotification( remoteMessage.getNotification().getBody());*/
        notificationcount++;
        context=getApplicationContext();
        reactToNotification(remoteMessage.getData().toString());
    }

    private void reactToNotification(String msg) {
        //AnimationSound aSound=new AnimationSound(context, R.raw.phonering);
      /*  PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
        wakeLock.acquire();







            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //Notification notification = new Notification(icon, tickerText, when);
            //notificationIntent=null;
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setContentTitle("Test")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                            .setContentText(msg)
                            .setAutoCancel(true)
                            .setVibrate(new long[]{100L, 100L, 200L, 500L})
                            .setSound(alarmSound)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        notificationManager.notify(100, notificationBuilder.build());

            PowerManager pm1 = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm1.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            wl.acquire();*/


    /*    value++;

        Log.d("value3245325", "From: " + value);

        // NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
        Notification.Builder builder = new Notification.Builder(this);
        // builder.setContentTitle("Xpression");
        builder.setContentText(""+msg);
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        //builder.setLargeIcon(bitmap);
        builder.setAutoCancel(true);
        inboxStyle.setBigContentTitle("Xpression");
        inboxStyle.addLine(""+msg);
        inboxStyle.setBuilder(builder);

        inboxStyle.setSummaryText("Notification from Xpression " + value);
        builder.setStyle(inboxStyle);
        nManager.notify("App Name", 1, builder.build());*/

        String postid = "";
        String recoid = "";
        String notificationmasterid = "";
        String new_title="";
        String new_message="";
        String is_story_status="";
        String wishlistuserid="";
        String gpID="";

        try {
            JSONObject jsonObject= new JSONObject(msg);
            JSONObject xpressions= jsonObject.getJSONObject("notification");
            postid = xpressions.getString("id");
            recoid = xpressions.getString("recognition_id");
            notificationmasterid = xpressions.getString("NotificationTypeId");
            is_story_status = xpressions.getString("is_story_status");
            wishlistuserid = xpressions.getString("wishlistuserid");


            new_title = xpressions.getString("title");
            new_message = xpressions.getString("body");
            gpID = xpressions.getString("parentid");

            //checkVolume(new_message,context);


            Log.i("postid"," postid: " + postid);
            Log.i(" recoid: ", " recoid: " + recoid);
            Log.i(" notificationmasterid: ", " notificationmasterid: " + notificationmasterid);
            Log.i(" new_title: ", " new_title: " + new_title);
            Log.i(" new_message: ", " new_message: " + new_message);


        } catch (JSONException e) {
            e.printStackTrace();
        }



        int icon = R.mipmap.applogo;
        CharSequence tickerText = "Meeting coming up soon!";
        long when = System.currentTimeMillis();
       // CharSequence contentTitle = "Meeting coming up soon!";
       // CharSequence contentText = dataReceived;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Notification notification = new Notification(icon, tickerText, when);
        //notificationIntent=null;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

       /* NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                        .setContentTitle(new_title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(new_message))
                        .setContentText(new_message)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{100L, 100L, 200L, 500L})
                        .setSound(alarmSound)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/



        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setContentTitle(new_title);
        notificationBuilder.setContentText(""+new_message);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), icon));
        notificationBuilder.setAutoCancel(true);
        inboxStyle.setBigContentTitle(new_title);
        inboxStyle.addLine(""+new_message);
        inboxStyle.setBuilder(notificationBuilder);





        if (Build.VERSION.SDK_INT >= 21) {
            notificationBuilder.setSmallIcon(R.mipmap.applogo);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.applogo);
        }

        Intent notificationIntent=null;
        if(SharedPrefrenceManager.getUserID(context)==null || SharedPrefrenceManager.getUserID(context).equalsIgnoreCase("")){
            notificationIntent = new Intent(context, LoginActivity.class);

        }

        else
            {

               /* if (notificationmasterid.equals("4") && is_story_status.equalsIgnoreCase("4")){
                    notificationIntent = new Intent(context, UserToReadActivity_notification.class);
                    intent.putExtra("policyid", recoid);
                    notificationIntent.putExtra("fromnotification","yes");

                }



                else*/ if (notificationmasterid.equalsIgnoreCase("1")
                    || notificationmasterid.equalsIgnoreCase("2")
                    ||notificationmasterid.equalsIgnoreCase("3")
                    || notificationmasterid.equalsIgnoreCase("4")
                    ||notificationmasterid.equals("13")){
                notificationIntent = new Intent(context, NotificationdetailsView.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId", postid);
                notificationIntent.putExtra("recoid", recoid);
                notificationIntent.putExtra("notificationmasterid", notificationmasterid);
                notificationIntent.putExtra("fromnotification","yes");

            }

            else if (notificationmasterid.equalsIgnoreCase("6") || notificationmasterid.equalsIgnoreCase("7")
                    || notificationmasterid.equalsIgnoreCase("8")){

                notificationIntent = new Intent(context, EventdetailView.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId", postid);
                notificationIntent.putExtra("recoid", recoid);
                notificationIntent.putExtra("notificationmasterid", notificationmasterid);
                notificationIntent.putExtra("fromnotification","yes");
            }



            else if (notificationmasterid.equals("5")){

                notificationIntent = new Intent(context,OtherPollDetails_notify_Activty.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId",postid);
                notificationIntent.putExtra("notificationmasterid",notificationmasterid);
                notificationIntent.putExtra("pollid",recoid);


            }

            else if (notificationmasterid.equals("11")){

                  /*  notificationIntent = new Intent(context, FlipComplexLayoutActivity.class);
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    notificationIntent.putExtra("ispollORpolicy", "yes");*/
                notificationIntent = new Intent(context, PolicyCommentActivity.class);
                notificationIntent.putExtra("policyid", recoid);
                notificationIntent.putExtra("fromnotification","yes");

            }


            // ToRead notifications
            else if (notificationmasterid.equals("12")){

                Log.i(" from 12 00000000: ", " new_message: " + notificationmasterid);
                notificationIntent = new Intent(context, UserToReadActivity.class);
                notificationIntent.putExtra("userid", wishlistuserid);
                notificationIntent.putExtra("fromNotification", "yes");
            }

           /* else if (notificationmasterid.equals("13")){
                notificationIntent = new Intent(context, ToReadActivity.class);
                notificationIntent.putExtra("fromNotification", "yes");
            }*/


            // group notifications

            // group memeber add, remove
            else if(notificationmasterid.equals("14") || notificationmasterid.equals("15")){

                notificationIntent = new Intent(context, GPInfoActivity.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId", postid);
                notificationIntent.putExtra("recoid", recoid);
                notificationIntent.putExtra("gpID", gpID);
                notificationIntent.putExtra("notificationmasterid", notificationmasterid);
                notificationIntent.putExtra("fromnotification","yes");
            }

            // group post, like,comment
            else if(notificationmasterid.equals("16") || notificationmasterid.equals("18") || notificationmasterid.equals("19")){
                notificationIntent = new Intent(context, GPNotificationdetailsView.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId", postid);
                notificationIntent.putExtra("recoid", recoid);
                notificationIntent.putExtra("gpID", gpID);
                notificationIntent.putExtra("notificationmasterid", notificationmasterid);
                notificationIntent.putExtra("fromnotification","yes");
            }

            // group Poll
            else if(notificationmasterid.equals("17")){
                notificationIntent = new Intent(context,GPOtherPollDetails_notify_Activty.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("postId",postid);
                notificationIntent.putExtra("gpID", gpID);
                notificationIntent.putExtra("notificationmasterid",notificationmasterid);
                notificationIntent.putExtra("pollid",recoid);


            }


            // for testing
            else if (notificationmasterid.equals("-1")){

                notificationIntent = new Intent(context, FlipComplexLayoutActivity.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.putExtra("ispollORpolicy", "yes");

            }



        }


        PendingIntent pendingIntent = null;

        pendingIntent = PendingIntent.getActivity(context, notificationcount, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT); //PendingIntent.FLAG_UPDATE_CURRENT

        // set intent
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(notificationcount, notificationBuilder.build());

        PowerManager pm1 = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm1.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wl.acquire();


    }


}

