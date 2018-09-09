package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.GPWALLPOST;
import db.GPWALLPOSTDao;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 30-Jan-18.
 */

public class GPWallLikeManager extends BaseManager {


    Context _mContext;
    String responseString = "";
    String gpid = "";

    public GPWallLikeManager(Context context) {
        _mContext = context;

    }

    public String postLike(String gid,String gpid) {

        try {

            this.gpid=gpid;

            Log.i("responstring", " Loke serviceUrl-->" + Constant.BASEURL
                    + "&param=api/coi/coi_addgrouppostlikes"
                    + "&gid=" + gid
                    + "&gpid=" + gpid);

            UserLoginInfo userLoginInfo = new LoginManager(_mContext).getUserInfo().get(0);
            String user_id = userLoginInfo.getUserid();

            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("gid", gid);
            entitymap.put("gpid", gpid);
            entitymap.put("userid", user_id);
            entitymap.put("devicetype", "android");

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_addgrouppostlikes");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeData(responseString);
    }


    public String parseLikeData(String jsonResponse) {

        String responseCode = "";
        DaoSession daoSession = getDBSessoin(_mContext);

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonArray= jsonObject.getJSONArray("responseData");
                            JSONObject responseData_json = jsonArray.getJSONObject(0);
                            String tlike = responseData_json.getString("tlike");
                            String ulike = responseData_json.getString("ulike");
                           // String tcomment = responseData_json.getString("tcomment");
                           // String ucomment = responseData_json.getString("ucomment");
                            //updateLikes(tlike, ulike);
                            updateLikesInPost(tlike, ulike);
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

    public void updateLikes(String tlike, String ulike) {

        try {

            Constant.singlePostList_gp.get(0).setLikes(tlike);
            //Constant.singlePostList_gp.get(0).setComments(tcomment);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void updateLikesInPost(String tlike, String ulike) {
        DaoSession daoSession = getDBSessoin(_mContext);
        GPWALLPOSTDao gpwallpostDao = daoSession.getGPWALLPOSTDao();
        try {
            List<GPWALLPOST> gpwallposts = new ArrayList<GPWALLPOST>();
            gpwallposts = gpwallpostDao.queryBuilder().where(GPWALLPOSTDao.Properties.Gpostid.eq(gpid)).list();

            if (gpwallposts != null) {
                for (GPWALLPOST post : gpwallposts) {
                    post.setLikes(tlike);
                    gpwallpostDao.update(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
