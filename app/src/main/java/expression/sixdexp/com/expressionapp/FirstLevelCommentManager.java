package expression.sixdexp.com.expressionapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 21-Aug-17.
 */

public class FirstLevelCommentManager  extends BaseManager {

    Context context;
    String responseString="";


    public FirstLevelCommentManager (Context context) {
        this.context=context;

    }

    public String callSecondLevelComment(String comment,String policyid,String base64_string,
                                         String filename,String file_extension,String content_type,String taguser)
    {

        try {

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            String isrijan=new LoginManager(context).getUserInfo().get(0).getIsrijan();


            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "srijanfirstlevelcomment serviceUrl-->" + Constant.BASEURL + "api/srijan/srijanfirstlevelcomment");

            entitymap.put("userid",userid);
            entitymap.put("comment",comment);
            entitymap.put("policyid",policyid);
            entitymap.put("issrijan",isrijan);
            entitymap.put("base64_string",base64_string);
            entitymap.put("filename",filename);
            entitymap.put("file_extension",file_extension);
            entitymap.put("content_type",content_type);
            entitymap.put("devicetype","android");
            entitymap.put("taguser",taguser);

            Log.i("userid",userid);
            Log.i("comment",comment);
            Log.i("policyid",policyid);
            Log.i("issrijan",isrijan);
            Log.i("base64_string",base64_string);
            Log.i("filename",filename);
            Log.i("file_extension",file_extension);
            Log.i("content_type",content_type);
            Log.i("devicetype","android");
            Log.i("taguser",taguser);


            responseString= ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/srijan/srijanfirstlevelcomment");

            if (responseString!=null)
                Log.i("srijanfirstlevelcomment", "responseString=" + responseString);
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