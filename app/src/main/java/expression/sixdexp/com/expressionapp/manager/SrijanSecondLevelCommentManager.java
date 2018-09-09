package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 18-Aug-17.
 */

public class SrijanSecondLevelCommentManager extends BaseManager {

    Context context;
    String responseString="";


    public SrijanSecondLevelCommentManager (Context context) {
        this.context=context;

    }

    public String callSecondLevelComment(String parentcommentId,String comment,String policyid,String taguser)
    {

        try {

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            String isrijan=new LoginManager(context).getUserInfo().get(0).getIsrijan();

            Log.i("435435 issrijan","issrijan "+isrijan);

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "secondlevelcomment serviceUrl-->" + Constant.BASEURL + "api/srijan/srijan_secondlevelcomment");

            entitymap.put("userid",userid);
            entitymap.put("parentcommentid",parentcommentId);
            entitymap.put("comment",comment);
            entitymap.put("policyid",policyid);
            entitymap.put("issrijan",isrijan);
            entitymap.put("taguser",taguser);
            entitymap.put("devicetype","android");

            Log.i("userId",userid);
            Log.i("parentcommentid",parentcommentId);
            Log.i("comment",comment);
            Log.i("policyid",policyid);
            Log.i("issrijan",isrijan);
            Log.i("tagged users 24352",""+taguser);
            Log.i("devicetype","android");


            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/srijan/srijan_secondlevelcomment");

            if (responseString!=null)
                Log.i("secondlevelcomment", "responseString=" + responseString);
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
