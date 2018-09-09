package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import db.GPWALLPOST;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Rahul Gupta on 08-05-2018.
 */

public class GPSinglePostManager extends BaseManager {

    Context context;
    String responseString = "";

    public GPSinglePostManager(Context context) {
        this.context = context;

    }


    public String callGPWallPost(String coi_gid, String gpid) {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();

            //entitymap.put("userid", SharedPrefrenceManager.getUserID(context));
            entitymap.put("userid", UserId);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            /*Date date1 = new Date();
            String today_date=dateFormat.format(date1);
            entitymap.put("date", today_date);*/

            String appVersion = "0.0";
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, Constant.today);
            String today_date = dateFormat.format(cal.getTime());
            entitymap.put("gid", coi_gid);
            entitymap.put("gpid", gpid);
            entitymap.put("postlastcount", "0");
            entitymap.put("devicetype", "android");
            entitymap.put("appversion", appVersion);
            entitymap.put("isxpressway", "0");

            Log.i("isxpressway", "0");
            Log.i("devicetype", "android");
            Log.i("appversion", appVersion);


            Log.i("responstring", "coi_getgrouppostbygid serviceUrl-->" + Constant.BASEURL + "api/coi/coi_getgrouppostbygid" + "&userid=" + UserId + "&date=" + today_date);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getgrouppostbygid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGPWallPostData(responseString);
    }


    public String parseGPWallPostData(String jsonResponse) {
        Constant.web_app_version = "0";
        String responseCode = "";

        try {

            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");
                            Log.i("responseStringlength", "responseStringlength=" + jsonarray.length());
                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                                String gpostid = jsonObject1.optString("coi_gpid");
                                String gid = jsonObject1.optString("coi_gid");
                                String details = jsonObject1.optString("coi_gppostdescription");
                                String type = jsonObject1.optString("type");
                                String userid = jsonObject1.optString("coi_gpsharebyuserid");
                                String event_date = jsonObject1.optString("coi_gpaddeddate");
                                String isxpress = jsonObject1.optString("coi_gpisxpress");
                                String taguserid = jsonObject1.optString("taguserid");
                                String tabusername = jsonObject1.optString("tabusername");
                                String nominee_imageurl = jsonObject1.optString("imageurl");
                                String nominee_designation = jsonObject1.optString("department");
                                String nominator_name = jsonObject1.optString("sharebyusername");
                                String likes = jsonObject1.optString("likes");
                                String comments = jsonObject1.optString("comments");
                                String tagcount = jsonObject1.optString("tagcount");
                                String subject = jsonObject1.optString("coi_xpresswaysubject");
                                String url = jsonObject1.optString("url");
                                String devicetype = jsonObject1.optString("devicetype");
                                String vcount = jsonObject1.optString("vcount");
                                String themeid = jsonObject1.optString("themeid");

                                String isstorystatus = jsonObject1.optString("isstorystatus");

                                GPWALLPOST gpwallpost = new GPWALLPOST(gpostid, gid, details, type, userid, event_date, isxpress, taguserid,
                                        tabusername, nominee_imageurl, nominee_designation, nominator_name, likes, comments, tagcount, subject,
                                        url, devicetype, vcount, themeid, isstorystatus);
                                Constant.singlePostList_gp.add(gpwallpost);


                            }

                        } else {
                            if (responseCode.equals("300")) {
                                Constant.web_app_version = responseCode;
                            }
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else if (responseString.equals("Too much load on Server. Please try Again.")) {
                    responseString = "Too much load on Server. Please try Again.";
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
