package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 9/14/2016.
 */

public class UserInfoManager {

    String responseString = "";
    public static UserLoginInfo userInfos;
    Context context;
    public UserInfoManager(Context context) {
        this.context=context;
    }

    public String userInfo(String userid) {

        try {

            Log.i("responstring", "getprofiledetailbyuserid serviceUrl-->" + Constant.BASEURL
                    + "&param=getprofiledetailbyuserid"
                    + "&userid=" + userid);

            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid", userid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/getprofiledetailbyuserid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseUserData(responseString);
    }

    public String parseUserData(String jsonResponse) {

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
                            JSONObject jsonObject1 = jsonObject.getJSONObject("responseData");
                            String userid = jsonObject1.optString("userid");
                            String empno = jsonObject1.optString("empno");
                            String name = jsonObject1.optString("name");
                            String username = jsonObject1.optString("username");
                            String dob = jsonObject1.optString("dob");
                            String location = jsonObject1.optString("location");
                            String role = jsonObject1.optString("role");
                            String department = jsonObject1.optString("department");
                            String designation = jsonObject1.optString("designation");
                            String quote = jsonObject1.optString("quote");
                            String hobbies = jsonObject1.optString("hobbies");
                            String imageurl = jsonObject1.optString("imageurl");
                            String company = jsonObject1.optString("company");
                            String experience = jsonObject1.optString("experience");
                            String is_admin = "";
                            String emailId = jsonObject1.optString("emailId");

                            String isdonor = "";
                            if(jsonObject1.has("isdonor"))
                                isdonor = jsonObject1.optString("isdonor");



                            String reg_mngr = "";
                            String post_xpress = "";
                            String rrecognition = "";
                            String grecognition = "";
                            String isrijan = "";

                            if(jsonObject1.has("rrecognition"))
                                rrecognition=jsonObject1.getString("rrecognition");
                            if(jsonObject1.has("grecognition"))
                                grecognition=jsonObject1.getString("grecognition");
                            if(jsonObject1.has("issrijan"))
                                isrijan=jsonObject1.getString("issrijan");



                            userInfos = new UserLoginInfo(userid, empno, name, username, dob, location, role,
                                    department, designation, quote, hobbies, imageurl, company, experience, is_admin, emailId,
                                    reg_mngr, post_xpress,rrecognition,grecognition,isdonor,isrijan);


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
