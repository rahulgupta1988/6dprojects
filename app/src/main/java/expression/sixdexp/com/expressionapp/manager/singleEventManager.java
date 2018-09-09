package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import db.CelebrationMoments;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 25-Apr-17.
 */

public class singleEventManager extends BaseManager{

    Context context;
    String responseString="";


    public singleEventManager (Context context) {
        this.context=context;

    }

    public String callCelebration(String postid)
    {
        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getCelebration serviceUrl-->" + Constant.BASEURL + "api/userApi/getcelebrateMomentdetailbyid");

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("postid",postid);
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/getcelebrateMomentdetailbyid");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseeCelebrationData(responseString);
    }


    public String parseeCelebrationData(String jsonResponse)
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
                            JSONArray jsonarray=jsonObject.getJSONArray("responseData");

                            for(int i=0;i<jsonarray.length();i++) {
                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
                                String userid = jsonObject1.optString("userid");
                                String name = jsonObject1.optString("name");
                                String eventmaster_id = jsonObject1.optString("eventmaster_id");
                                String useevent_idrid = jsonObject1.optString("event_id");
                                String department = jsonObject1.optString("department");
                                String event_desc = jsonObject1.optString("event_desc");
                                String work_year = jsonObject1.optString("work_year");
                                String imageurl = jsonObject1.optString("imageurl");
                                String emailId = jsonObject1.optString("emailId");
                                String title = jsonObject1.optString("title");

                                CelebrationMoments celebrationMoments = new CelebrationMoments(userid,name,eventmaster_id,useevent_idrid,
                                        department,event_desc,work_year,imageurl,emailId,title);

                                Constant.singleEvent.add(celebrationMoments);

                            }

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
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

        return responseString;
    }

}
