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

public class AddToReadBookManager extends  BaseManager {

    Context _mContext;
    String responseString = "";

    public  AddToReadBookManager(Context _mContext){
       this._mContext = _mContext;
      }
    public String addBook( String title, String description, String base64string, String authername) {

        try {


            Log.i("responstring", "add book serviceUrl-->" + Constant.BASEURL);
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid", userid);
            entitymap.put("title", title);
            entitymap.put("description", description);
            entitymap.put("base64string", base64string);
            entitymap.put("authername", authername);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/toread/addtoreadbooks");

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
                            responseString=responseCode;

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