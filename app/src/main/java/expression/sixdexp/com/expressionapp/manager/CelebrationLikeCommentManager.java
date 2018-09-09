package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 07-Jan-17.
 */

public class CelebrationLikeCommentManager extends BaseManager {

    Context context;
    String responseString = "";


    public CelebrationLikeCommentManager(Context context) {
        this.context = context;

    }

    public String callCelebrationLikeSkip(String like_skip,String eventid) {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "LikeCommentCelebration serviceUrl-->" + Constant.BASEURL + "api/userApi/likeORSkip_event");

            String userid = new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("like_skip", like_skip);
            entitymap.put("userid", userid);
            entitymap.put("eventid", eventid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/likeORSkip_event");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseCelebrationLikeCommentData(responseString);
    }


    public String callCelebrationComment(String eventid,String rc_userid,String eventmid,String eventcomment) {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "api/userApi/comment_event serviceUrl-->" + Constant.BASEURL + "api/userApi/comment_event");

            String userid = new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("eventid", eventid);
            entitymap.put("rc_userid", rc_userid);
            entitymap.put("eventmid", eventmid);
            entitymap.put("eventcomment", eventcomment);
            entitymap.put("userid", userid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/comment_event");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseCelebrationLikeCommentData(responseString);
    }


    public String parseCelebrationLikeCommentData(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
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

