package expression.sixdexp.com.expressionapp.utility;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.expression.ui.FlipComplexLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.manager.ServerCall;

/**
 * Created by Praveen on 22-Mar-17.
 */

public class NotificationCountService extends IntentService {



    public NotificationCountService() {
        super(NotificationCountService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String userid = intent.getStringExtra("userid");

        HashMap<String, String> entitymap = new HashMap<String, String>();
        entitymap.put("userid",userid);
        String responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/getweb_notificationcount");
        Log.i("count responce",""+responseString);
        String notifyC="0";
        try {
            Object object = new JSONTokener(responseString.toString()).nextValue();
            if(object instanceof JSONObject){
                JSONObject jsonObject=new JSONObject(responseString.toString());
                String responseCode = jsonObject.getString("responseCode");
                if (responseCode.equalsIgnoreCase("100"))
                {
                    JSONObject jsonObject1=jsonObject.getJSONObject("responseData");
                    notifyC=jsonObject1.getString("count");
                    Log.i("notiount09102",""+notifyC);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


            Intent receiverIntent=new Intent(FlipComplexLayoutActivity.receviserFilter);
            receiverIntent.putExtra("notifyCount",notifyC);
            this.sendBroadcast(receiverIntent);




        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
