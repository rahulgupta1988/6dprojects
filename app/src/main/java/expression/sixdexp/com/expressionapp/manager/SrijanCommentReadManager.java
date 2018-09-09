package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 24-Aug-17.
 */

public class SrijanCommentReadManager extends BaseManager {

    Context context;
    String responseString="";


    public SrijanCommentReadManager (Context context) {
        this.context=context;

    }

    public String callCommentRead(String commentid,String commentlevel,String feedbackid)
    {

        try {

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();


            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "adminfeedback serviceUrl-->" + Constant.BASEURL + "api/srijan/srijan_adminfeedback_oncomment");

            entitymap.put("userid",userid);
            entitymap.put("commentid",commentid);
            entitymap.put("commentlevel",commentlevel);
            entitymap.put("feedbackid",feedbackid);




            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/srijan/srijan_adminfeedback_oncomment");

            if (responseString!=null)
                Log.i("adminfeedback", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse)
    {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString="Received data is not compatible.";
                    }
                } else {
                    responseString=responseString;
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

