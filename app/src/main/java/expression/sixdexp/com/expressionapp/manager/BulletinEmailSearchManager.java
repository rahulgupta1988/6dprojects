package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 27-Mar-17.
 */

public class BulletinEmailSearchManager {

    Context context;
    String responseString = "";
    public static List<String> autosearchresultList=new ArrayList<String>();

    public BulletinEmailSearchManager(Context context) {
        this.context = context;

    }

    public String callAutoText(String emailtext) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "keyword_search serviceUrl-->" + Constant.BASEURL + "api/userapi/getbulletingroupemail");

            entitymap.put("emailtext",emailtext);
            Log.i("emailtext", "" + emailtext);


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getbulletingroupemail");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseawardData(responseString);
    }


    public String parseawardData(String jsonResponse) {
        String responseCode = "";
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            autosearchresultList.clear();
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");

                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                                String value = jsonObject1.optString("Value");
                                autosearchresultList.add(value);
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
