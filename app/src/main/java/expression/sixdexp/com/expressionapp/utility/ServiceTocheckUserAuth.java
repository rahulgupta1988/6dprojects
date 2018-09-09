package expression.sixdexp.com.expressionapp.utility;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.manager.ServerCall;

/**
 * Created by Praveen on 8/25/2016.
 */
public class ServiceTocheckUserAuth extends IntentService {

    public static final String ACTION_UserAuthService = "expression.sixdexp.com.expressionapp.USERAUTH";

    public ServiceTocheckUserAuth() {

        super(ServiceTocheckUserAuth.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("ServiceTocheckUserAuth", "ServiceTocheckUserAuth");

        HashMap<String, String> entitymap = new HashMap<String, String>();
        String userID = intent.getStringExtra("userid");
        String passw = intent.getStringExtra("passw");


        entitymap.put("username", userID);
        entitymap.put("password", passw);
        Log.i("auth username", "" + userID);
        Log.i("auth password", "" + passw);

        String responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/Islogin");
        parseResponce(responseString);
        Log.i("auth responce111", "" + responseString.toString());
    }

    public void parseResponce(String jsonResponse){
        String responseCode = "";
        String responseString = "";
        try {
            if (jsonResponse != null && !jsonResponse.equals(null)) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            Log.i("auth responce", "" + responseString.toString());

                        }
                        else
                        {
                            Intent intentUpdate = new Intent();
                            intentUpdate.setAction(ACTION_UserAuthService);
                            intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
                            sendBroadcast(intentUpdate);
                            Log.i("auth responce", "" + responseString.toString());
                        }
                    }else {
                        responseString="Received data is not compatible.";
                    }
                } else {
                    responseString="Received data is not compatible.";
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {

        }
    }
}
