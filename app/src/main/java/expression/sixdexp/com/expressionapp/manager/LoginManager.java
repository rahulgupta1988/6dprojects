package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.AllUsers;
import db.AllUsersDao;
import db.DaoSession;
import db.UserLoginInfo;
import db.UserLoginInfoDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 7/4/2016.
 */
public class LoginManager extends BaseManager {

    Context _mContext;
    String responseString="";
    public  LoginManager(Context context){
        _mContext=context;

    }

    public String loginUser(String username,String password,String apnstoken)
    {

        try {

            Log.i("responstring", "LoginUser serviceUrl-->" + Constant.BASEURL
                    + "&param=login"
                    + "&username=" + username
                    + "&password=" + password
                    + "&apnstoken=" +apnstoken);

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("username",username);
            entitymap.put("password",password);
            if(apnstoken==null || apnstoken.equalsIgnoreCase(""))
                entitymap.put("devicetoken","13243235767");
            else
                entitymap.put("devicetoken",apnstoken);

            entitymap.put("devicetype","android");

            //responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/isauthenticate/Islogin");
              responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/Islogin");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLoginData(responseString);
    }

    public String parseLoginData(String jsonResponse)
    {

        String responseCode = "";
        DaoSession daoSession=getDBSessoin(_mContext);
        daoSession.getUserLoginInfoDao().deleteAll();
        UserLoginInfoDao userLoginInfoDao=daoSession.getUserLoginInfoDao();
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
                            String post_xpress=jsonObject1.optString("post_xpress");
                            String isrijan=jsonObject1.optString("issrijan");

                            String isdonor = "";
                            if(jsonObject1.has("isdonor"))
                                isdonor = jsonObject1.optString("isdonor");


                            String rrecognition = "";
                            String grecognition = "";

                            if(jsonObject1.has("rrecognition"))
                                rrecognition=jsonObject1.getString("rrecognition");
                            if(jsonObject1.has("grecognition"))
                                grecognition=jsonObject1.getString("grecognition");

                            SharedPrefrenceManager.putUsername(_mContext, username);


                          UserLoginInfo userLoginInfo=new UserLoginInfo(userid,empno,name,username,dob,location,role,
                                    department,designation,quote,hobbies,imageurl,company,experience,is_admin,emailId,reg_mngr,
                                  post_xpress,rrecognition,grecognition,isdonor,isrijan);
                            userLoginInfoDao.insertOrReplace(userLoginInfo);

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


    public List<UserLoginInfo> getUserInfo()
    {
        DaoSession daoSession=getDBSessoin(_mContext);
        UserLoginInfoDao userDao=daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            return userLoginInfos;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<AllUsers> getUserInfoByID(String Userid)
    {
        DaoSession daoSession = BaseManager.getDBSessoin(_mContext);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        try {
            List<AllUsers> userLoginInfos =allUsersDao.queryBuilder().where(AllUsersDao.Properties.User_id.eq(Userid)).list();

            return userLoginInfos;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
