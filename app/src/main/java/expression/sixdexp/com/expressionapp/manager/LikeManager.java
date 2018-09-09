package expression.sixdexp.com.expressionapp.manager;

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
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 16-Jan-17.
 */

public class LikeManager extends BaseManager {

    Context context;
    String responseString = "";


    public LikeManager(Context context) {
        this.context = context;

    }

    public String callLikedUser(String recognitionid) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getLikes serviceUrl-->" + Constant.BASEURL + "api/userApi/getLikes");

            entitymap.put("recognitionid", recognitionid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/getLikes");

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