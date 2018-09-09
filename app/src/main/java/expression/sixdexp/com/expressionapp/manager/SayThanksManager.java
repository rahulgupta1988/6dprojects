package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 29-Dec-17.
 */

public class SayThanksManager  extends  BaseManager {

    Context _mContext;
    String responseString = "";

    public  SayThanksManager(Context _mContext){
        this._mContext = _mContext;
    }
    public String sayThaksCall( String messagetext , String useridforthankinguserid, String bookid) {

        try {


            Log.i("responstring", "saythanksafterreceivedgift serviceUrl-->" + Constant.BASEURL);
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid", userid);
            entitymap.put("messagetext", messagetext);
            entitymap.put("useridforthankinguserid", useridforthankinguserid);
            entitymap.put("bookid", bookid);


            Log.i("userid", userid);
            Log.i("messagetext", messagetext);
            Log.i("useridforthankinguserid", useridforthankinguserid);
            Log.i("bookid", bookid);


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/toread/saythanksafterreceivedgift");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseData(responseString);
    }

    public String parseData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");
                            String userid=jsonObject1.optString("userid");

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
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
