package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.UserLoginInfo;
import db.UserLoginInfoDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 06-Apr-17.
 */

public class UserInfoUpdate extends BaseManager {

    Context _mContext;
    String responseString="";
    public  UserInfoUpdate(Context context){
        _mContext=context;

    }

    public String UserData(String userID)
    {

        try {

            Log.i("responstring", "myProfile serviceUrl-->" + Constant.BASEURL
                    + "&userid=userID");

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("userid",userID);
            entitymap.put("devicetype","android");

            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/myProfile");

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
       // daoSession.getUserLoginInfoDao().deleteAll();
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
                            String role=jsonObject1.optString("role");
                            String quote=jsonObject1.optString("quote");
                            String hobbies=jsonObject1.optString("hobbies");
                            String imageurl=jsonObject1.optString("imageurl");
                            String rrecognition = "";
                            String grecognition = "";

                            if(jsonObject1.has("rrecognition"))
                                rrecognition=jsonObject1.getString("rrecognition");
                            if(jsonObject1.has("grecognition"))
                                grecognition=jsonObject1.getString("grecognition");

                            updateInfo(role,quote,hobbies,imageurl,grecognition,rrecognition);

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


    public void updateInfo (String role,String quote,String hobbies,String imageurl,String grecognition,String rrecognition){
        DaoSession daoSession = getDBSessoin(_mContext);
        UserLoginInfoDao userDao = daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            if (userLoginInfos != null) {
                for (UserLoginInfo param : userLoginInfos) {

                    param.setRole(role);
                    param.setQuote(quote);
                    param.setHobbies(hobbies);
                    param.setImageurl(imageurl);
                    param.setGrecognition(grecognition);
                    param.setRrecognition(rrecognition);
                    userDao.update(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void updateImageURL (String imageurl){
        DaoSession daoSession = getDBSessoin(_mContext);
        UserLoginInfoDao userDao = daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            if (userLoginInfos != null) {
                for (UserLoginInfo param : userLoginInfos) {
                    param.setImageurl(imageurl);
                    userDao.update(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void updateUserName (String userName){
        DaoSession daoSession = getDBSessoin(_mContext);
        UserLoginInfoDao userDao = daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            if (userLoginInfos != null) {
                for (UserLoginInfo param : userLoginInfos) {
                    param.setUsername(userName);
                    userDao.update(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

