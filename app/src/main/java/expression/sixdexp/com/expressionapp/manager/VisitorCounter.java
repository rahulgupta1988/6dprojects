package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 9/5/2016.
 */
public class VisitorCounter {

    public static String vistor_counter = "";
    public static String web_user_count = "";
    String responseString = "";


   public VisitorCounter(){
   }
    public String callVisitorCounter() {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getawards serviceUrl-->" + Constant.BASEURL + "api/userapi/getvisitors");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getvisitors");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseVisitorCounter(responseString);
    }

    public String parseVisitorCounter(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null)) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

                            if(jsonObject.has("responseData"))
                            {

                                 JSONObject  res_json=jsonObject.getJSONObject("responseData");
                                 vistor_counter=res_json.getString("visitor_count");
                                 web_user_count=res_json.getString("web_user_count");

                            }


                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = "Received data is not compatible.";
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }


}
