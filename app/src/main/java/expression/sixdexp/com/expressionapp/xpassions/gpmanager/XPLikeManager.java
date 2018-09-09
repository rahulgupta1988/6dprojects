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
import db.GivenLike;
import db.GivenLikeDao;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 15-Feb-18.
 */

public class XPLikeManager extends BaseManager {

    Context context;
    String responseString = "";


    public XPLikeManager(Context context) {
        this.context = context;

    }

    public String callLikedUser(String gid,String gpid) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "coi_getLikesUseronpost serviceUrl-->" + Constant.BASEURL + "api/coi/coi_getLikesUseronpost");

            String userID=new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("userid", userID);
            entitymap.put("gid", gid);
            entitymap.put("gpid", gpid);


            Log.i("userid", userID);
            Log.i("gid", gid);
            Log.i("gpid", gpid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getLikesUseronpost");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeGivenData(responseString);
    }


    public String parseLikeGivenData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getGivenLikeDao().deleteAll();
        GivenLikeDao givenLikeDao = daoSession.getGivenLikeDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");

                            for (int i = 0; i < jsonarray.length(); i++) {


                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String userid = jsonObject1.optString("userid");
                                String userName = jsonObject1.optString("userName");
                                String designation = jsonObject1.optString("designation");
                                String department = jsonObject1.optString("department");
                                String location = jsonObject1.optString("location");
                                String imageurl = jsonObject1.optString("imageurl");

                                GivenLike givenLike=new GivenLike(recognition_id,userid,userName,
                                        designation,department,location,imageurl);

                                givenLikeDao.insertOrReplace(givenLike);

                            }

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


    public List<GivenLike> getGivenLikeList() {
        DaoSession daoSession = getDBSessoin(context);
        GivenLikeDao givenLikeDao = daoSession.getGivenLikeDao();
        try {
            List<GivenLike> givenLikes = new ArrayList<GivenLike>();
            givenLikes = givenLikeDao.loadAll();
            return givenLikes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}