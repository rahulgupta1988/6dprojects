package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.GetMorePost;
import db.GetMorePostDao;
import db.UserLoginInfo;
import db.UserLoginInfoDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 7/15/2016.
 */
public class UpdateProfileManager extends BaseManager {

    Context _mContext;
    String responseString = "";

    public UpdateProfileManager(Context context) {
        _mContext = context;

    }

    public String callUpdateProfile(String name, String user_role, String user_hobby, String user_quote) {

        try {

            Log.i("responstring", "editprofile serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userapi/editprofile");

          String username = SharedPrefrenceManager.getUsername(_mContext);
            String userid = SharedPrefrenceManager.getUserID(_mContext);
            /*  Log.i("userid",userid);
            Log.i("name", name);
            Log.i("role", user_role);
            Log.i("quote", user_quote);
            Log.i("hobbies", user_hobby);
            Log.i("username", username);
*/
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("name", name);
            entitymap.put("role", user_role);
            entitymap.put("quote", user_quote);
            entitymap.put("hobbies", user_hobby);
            entitymap.put("username", username);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/editprofile");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsePrfileData(responseString);
    }

    public String parsePrfileData(String jsonResponse) {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");
                            String userid=jsonObject1.optString("userid");
                            String empno=jsonObject1.optString("empno");
                            String name=jsonObject1.optString("name");
                            String username=jsonObject1.optString("username");
                            String dob=jsonObject1.optString("dob");
                            String location=jsonObject1.optString("location");
                            String role=jsonObject1.optString("role");
                            String department=jsonObject1.optString("department");
                            String designation=jsonObject1.optString("designation");
                            String quote=jsonObject1.optString("quote");
                            String hobbies=jsonObject1.optString("hobbies");
                            String imageurl=jsonObject1.optString("imageurl");
                            String company=jsonObject1.optString("company");
                            String experience=jsonObject1.optString("experience");
                            String is_admin=jsonObject1.optString("is_admin");
                            String emailId=jsonObject1.optString("emailId");
                            String reg_mngr=jsonObject1.optString("reg_mngr");


                            SharedPrefrenceManager.putUserID(_mContext, userid);
                            SharedPrefrenceManager.putUsername(_mContext, username);
                            updateProfile(name, role, hobbies, quote);
                            updatePost(name);

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


    public void updateProfile(String name, String user_role, String user_hobby, String user_quote) {
        DaoSession daoSession = getDBSessoin(_mContext);
        UserLoginInfoDao userDao = daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            if (userLoginInfos != null) {
                for (UserLoginInfo param : userLoginInfos) {

                    param.setName(name);
                    param.setRole(user_role);
                    param.setHobbies(user_hobby);
                    param.setQuote(user_quote);
                    userDao.update(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void updatePost(String name) {
        DaoSession daoSession=getDBSessoin(_mContext);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Userid.eq(
                    SharedPrefrenceManager.getUserID(_mContext))).list();

            if (getMorePosts != null) {
                for (GetMorePost post : getMorePosts) {

                    post.setNominator_name(name);
                    getMorePostDao.update(post);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }


}
