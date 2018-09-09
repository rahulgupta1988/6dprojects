package expression.sixdexp.com.expressionapp;

/**
 * Created by Praveen on 8/25/2016.
 */
public class GCMIntentService /*extends GCMBaseIntentService */ {

   /* public static int notificationcount = 0;
    final static String MY_ACTION = "MY_ACTION";
    //Intent notificationIntent;
    Intent dintent;
    String title = "", dataReceived = "", type = "", data = "", msg = "", invId = "";

    public GCMIntentService() {
        super(Constant.GCMSenderId);
    }

    @Override
    protected void onError(Context context, String regId) {
        // TODO Auto-generated method stub

        Logger.i(context, "error registration id : " + regId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Logger.i(context, "messages in onMessage()" + "msg      ");
        notificationcount++;
        reactToNotification(context, intent);
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        // TODO Auto-generated method stub
        handleRegistration(context, regId);
        Logger.i(context, "Registration ID = " + regId);
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        // TODO Auto-generated method stub
    }

    private void reactToNotification(Context context, Intent intent) {
        //AnimationSound aSound=new AnimationSound(context, R.raw.phonering);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
        wakeLock.acquire();

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
        *//*kl.disableKeyguard();*//*

        dataReceived = "";
        dataReceived = intent.getStringExtra("xpression");
      *//*  String dataReceived1 = intent.getStringExtra("data");
        Logger.i(context, " dataReceivede202: " + dataReceived);*//*
        Logger.i(context, " dataReceived101: " + dataReceived);


        if (!dataReceived.equalsIgnoreCase("") && dataReceived != null) {

            String postid = "";
            String recoid = "";
            String notificationmasterid = "";
            String new_title="";
            String new_message="";

            String is_story_status="";

            try {
                JSONObject jsonObject = new JSONObject(dataReceived);
                postid = jsonObject.getString("id");
                recoid = jsonObject.getString("recognition_id");
                notificationmasterid = jsonObject.getString("NotificationTypeId");
                is_story_status = jsonObject.getString("is_story_status");


                new_title = jsonObject.getString("title");
                new_message = jsonObject.getString("message");


                //checkVolume(new_message,context);


                Logger.i(context, " postid: " + postid);
                Logger.i(context, " recoid: " + recoid);
                Logger.i(context, " notificationmasterid: " + notificationmasterid);
                Logger.i(context, " new_title: " + new_title);
                Logger.i(context, " new_message: " + new_message);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            int icon = R.mipmap.applogo;
            CharSequence tickerText = "Meeting coming up soon!";
            long when = System.currentTimeMillis();
            CharSequence contentTitle = "Meeting coming up soon!";
            CharSequence contentText = dataReceived;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Notification notification = new Notification(icon, tickerText, when);
            //notificationIntent=null;
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                            .setContentTitle(new_title)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(new_message))
                            .setContentText(new_message)
                            .setWhen(when)
                            .setAutoCancel(true)
                            .setVibrate(new long[]{100L, 100L, 200L, 500L})
                            .setSound(alarmSound)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            if (Build.VERSION.SDK_INT >= 21) {
                notificationBuilder.setSmallIcon(R.mipmap.applogo);
            } else {
                notificationBuilder.setSmallIcon(R.mipmap.applogo);
            }

            Intent notificationIntent=null;
            if(SharedPrefrenceManager.getUserID(context)==null || SharedPrefrenceManager.getUserID(context).equalsIgnoreCase("")){
                notificationIntent = new Intent(context, LoginActivity.class);

            }

            else{

               *//* if (notificationmasterid.equals("4") && is_story_status.equalsIgnoreCase("4")){
                    notificationIntent = new Intent(context, UserToReadActivity_notification.class);
                    intent.putExtra("policyid", recoid);
                    notificationIntent.putExtra("fromnotification","yes");

                }



                else*//* if (notificationmasterid.equalsIgnoreCase("1")
                        || notificationmasterid.equalsIgnoreCase("2")
                        ||notificationmasterid.equalsIgnoreCase("3")
                         || notificationmasterid.equalsIgnoreCase("4")
                         ||notificationmasterid.equals("12")){
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

                  *//*  notificationIntent = new Intent(context, FlipComplexLayoutActivity.class);
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    notificationIntent.putExtra("ispollORpolicy", "yes");*//*
                    notificationIntent = new Intent(context, PolicyCommentActivity.class);
                    intent.putExtra("policyid", recoid);
                    notificationIntent.putExtra("fromnotification","yes");

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


    private void handleRegistration(Context context, String regId) {
        // TODO Auto-generated method stub
        Constant.notificationId = regId;
        Logger.i(context, "Registration ID = " + regId);
    }

    TextToSpeech textToSpeech;
    public void textTOspeech(final String msg,Context context){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.speak("In xpressions "+msg, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }


    public void checkVolume(String new_message,Context context){

        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                textTOspeech(new_message,context);
                break;


        }

    }*/

}
