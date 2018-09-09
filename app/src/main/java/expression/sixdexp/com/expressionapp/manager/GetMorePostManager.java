package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import db.AllUserEvent;
import db.AllUserEventDao;
import db.AllWebNotification;
import db.AllWebNotificationDao;
import db.BulletinCategory;
import db.BulletinCategoryDao;
import db.DaoSession;
import db.GetMorePost;
import db.GetMorePostDao;
import db.GetXpresswayPost;
import db.GetXpresswayPostDao;
import db.UserLoginInfo;
import db.UserWedding;
import db.UserWeddingDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;


public class GetMorePostManager extends BaseManager {

    Context context;
    String responseString = "";

    public GetMorePostManager(Context context) {
        this.context = context;

    }


    public String GetBulletinCategory() {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstringrt", "getbulletincategorymaster serviceUrl-->" + Constant.BASEURL + "/api/userapi/getbulletincategorymaster");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/getbulletincategorymaster");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetBulletinCategoryData(responseString);
    }


    public String parseGetBulletinCategoryData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getBulletinCategoryDao().deleteAll();
        BulletinCategoryDao bulletinCategoryDao = daoSession.getBulletinCategoryDao();
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
                                String id = jsonObject1.optString("id");
                                String name = jsonObject1.optString("name");
                                String isactive = jsonObject1.optString("isactive");

                                BulletinCategory bulletinCategory = new BulletinCategory(id, name, isactive);
                                bulletinCategoryDao.insertOrReplace(bulletinCategory);
                            }

                        } else {
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


    public String UserFeedback(String txtfeedback) {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);
            entitymap.put("txtfeedback", txtfeedback);

            Log.i("responstringrt", "userfeedback serviceUrl-->" + Constant.BASEURL + "/api/userapi/userfeedback" + "&userid=" + UserId + "&txtfeedback=" + txtfeedback);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/userfeedback");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseUserFeedbackData(responseString);
    }

    public String parseUserFeedbackData(String jsonResponse) {
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
                        } else {
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


    public String getUserEventsPOPUP(String fromdate, String todate, String eventid) {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);


            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, Constant.today);
            String today_date = dateFormat.format(cal.getTime());

           /* String first_dt=today_date+"/01"+"/01";
            String last_dt=today_date+"/12"+"/31";*/
            entitymap.put("fdate", fromdate);
            entitymap.put("todate", todate);
         /*   String last_dt=today_date+"/12"+"/31";
            entitymap.put("todate", last_dt);*/
            entitymap.put("eventmid", eventid);


            Log.i("responstringrt", "getCelebrate_myspace serviceUrl-->" + Constant.BASEURL + "/api/userapi/getCelebrate_myspace"
                    + "&userid=" + UserId + "&fdate=" + fromdate + "&todate=" + todate + "&eventmid=" + eventid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/getCelebrate_myspace");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsegetUserEventsData(responseString);
    }


    public String getUserEvents() {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);


            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, Constant.today);
            String today_date = dateFormat.format(cal.getTime());

            String first_dt = today_date + "/01" + "/01";
            String last_dt = today_date + "/12" + "/31";
            entitymap.put("fdate", first_dt);
            entitymap.put("todate", last_dt);
            entitymap.put("eventmid", "0");


            Log.i("responstringrt", "getCelebrate_myspace serviceUrl-->" + Constant.BASEURL + "/api/userapi/getCelebrate_myspace"
                    + "&userid=" + UserId + "&fdate=" + first_dt + "&todate=" + last_dt + "&eventmid=" + "0");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/getCelebrate_myspace");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsegetUserEventsData(responseString);
    }

    public String parsegetUserEventsData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllUserEventDao().deleteAll();
        AllUserEventDao allUserEventDao = daoSession.getAllUserEventDao();
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
                                String name = jsonObject1.optString("name");
                                String event_date = jsonObject1.optString("event_date");
                                String image_url = jsonObject1.optString("image_url");
                                String event_desc = jsonObject1.optString("event_desc");
                                String event_id = jsonObject1.optString("event_id");
                                String likecount = jsonObject1.optString("likecount");
                                String commentcount = jsonObject1.optString("commentcount");
                                String event_title = jsonObject1.optString("event_title");
                                String eventmaster_id = jsonObject1.optString("eventmaster_id");
                                String eventicon = jsonObject1.optString("eventicon");

                                AllUserEvent allUserEvent = new AllUserEvent(name, event_date, image_url,
                                        event_desc, event_id, likecount, commentcount, event_title, eventmaster_id, eventicon);
                                allUserEventDao.insertOrReplace(allUserEvent);

                            }

                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else if (responseString.equals("Too much load on Server. Please try Again.")) {
                        responseString = "Too much load on Server. Please try Again.";
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

    public String ChangeWeddingDate(String change_dowd) {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);
            entitymap.put("change_dowd", change_dowd);

            Log.i("responstringrt", "change_wedding_date serviceUrl-->" + Constant.BASEURL + "/api/userapi/change_wedding_date"
                    + "&userid=" + UserId + "&change_dowd=" + change_dowd);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/change_wedding_date");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseChangeWeddingDateData(responseString);
    }

    public String parseChangeWeddingDateData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
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


    public String issubscribe(String eventtype, String isactive) {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);
            entitymap.put("eventtype", eventtype);
            entitymap.put("isactive", isactive);

            Log.i("responstringrt", "sub_unsubcribe_event serviceUrl-->" + Constant.BASEURL + "/api/userapi/sub_unsubcribe_event"
                    + "&userid=" + UserId + "&eventtype=" + "300" + "&isactive=" + isactive);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/sub_unsubcribe_event");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseissubscribeData(responseString);
    }

    public String parseissubscribeData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
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


    public String getWedding() {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);

            Log.i("responstringrt", "user_bod_doj_wed serviceUrl-->" + Constant.BASEURL + "/api/userapi/user_bod_doj_wed"
                    + "&userid=" + UserId);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/user_bod_doj_wed");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsegetWeddingData(responseString);
    }


    public String parsegetWeddingData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getUserWeddingDao().deleteAll();
        UserWeddingDao userWeddingDao = daoSession.getUserWeddingDao();
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
                                String userid = jsonObject1.optString("userid");
                                String name = jsonObject1.optString("name");
                                String dob = jsonObject1.optString("dob");
                                String doj = jsonObject1.optString("doj");
                                String dow = jsonObject1.optString("dow");
                                String image_url = jsonObject1.optString("image_url");

                                String issubcribe="";
                                if(jsonObject1.has("issubcribe")){
                                     issubcribe = jsonObject1.optString("issubcribe");
                                }


                                String isbirthday_subscribe = jsonObject1.optString("isbirthday_subscribe");
                                String iswork_subscribe = jsonObject1.optString("iswork_subscribe");
                                String iswedding_subscribe = jsonObject1.optString("iswedding_subscribe");


                                UserWedding userWedding = new UserWedding(userid, name, dob, doj,
                                        dow, image_url, issubcribe, isbirthday_subscribe, iswork_subscribe, iswedding_subscribe);
                                userWeddingDao.insertOrReplace(userWedding);

                            }

                        } else {
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


    public String GetWebNotificationList() {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);
            entitymap.put("top", "1");


            Log.i("responstringrt", "getweb_notification serviceUrl-->" + Constant.BASEURL + "api/userapi/getweb_notification" + "&userid=" + UserId);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getweb_notification");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetWebNotificationListPost(responseString);
    }

    public String parseGetWebNotificationListPost(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllWebNotificationDao().deleteAll();
        AllWebNotificationDao allWebNotificationDao = daoSession.getAllWebNotificationDao();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            Gson gson = new Gson();
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");
                            Log.i("responseStringlength", "responseStringlength=" + jsonarray.length());
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);

                                AllWebNotification allWebNotification = gson.fromJson(jsonObject1.toString(), AllWebNotification.class);

                              /*  String recognition_id = jsonObject1.optString("recognition_id");
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String image_url = jsonObject1.optString("image_url");
                                String total_count = jsonObject1.optString("total_count");
                                String is_story_status = jsonObject1.optString("is_story_status");
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String recognize_date = jsonObject1.optString("recognize_date");
                                AllWebNotification allWebNotification=new AllWebNotification(recognition_id,nominator_name,
                                        image_url,total_count,is_story_status,nominee_name,recognize_date);*/
                                allWebNotificationDao.insertOrReplace(allWebNotification);

                            }

                        } else {
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


    public String GetXpresswayPost() {
        try {
            HashMap<String, String> entitymap = new HashMap<String, String>();

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String UserId = userDatas.get(0).getUserid();
            entitymap.put("userid", UserId);
            Log.i("responstringrt", "GetXpresswayPost serviceUrl-->" + Constant.BASEURL + "api/userapi/xpressway" + "&userid=" + UserId);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/xpressway");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetXpresswayPost(responseString);
    }

    public String parseGetXpresswayPost(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getGetXpresswayPostDao().deleteAll();
        GetXpresswayPostDao getXpresswayPostDao = daoSession.getGetXpresswayPostDao();

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
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String userid = jsonObject1.optString("userid");
                                String nominator_designation = jsonObject1.optString("nominator_designation");
                                String nominator_location = jsonObject1.optString("nominator_location");
                                String nominator_imageurl = jsonObject1.optString("nominator_imageurl");
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String nominee_designation = jsonObject1.optString("nominee_designation");
                                String nominee_location = jsonObject1.optString("nominee_location");
                                String nominee_imageurl = jsonObject1.optString("nominee_imageurl");
                                String title = jsonObject1.optString("title");
                                String description = jsonObject1.optString("description");
                                String details = jsonObject1.optString("details");
                                String challengesfaced = jsonObject1.optString("challengesfaced");
                                String is_story = jsonObject1.optString("is_story");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String event_date = jsonObject1.optString("event_date");
                                String type = jsonObject1.optString("type");
                                String url = jsonObject1.optString("url");
                                String icon = jsonObject1.optString("icon");
                                String count = jsonObject1.optString("count");
                                String likes = jsonObject1.optString("likes");
                                String comments = jsonObject1.optString("comments");
                                String ulikes = jsonObject1.optString("ulikes");
                                String ucomments = jsonObject1.optString("ucomments");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String nominee = jsonObject1.optString("nominee");
                                String subject = jsonObject1.optString("subject");
                                String isxpress = jsonObject1.optString("isxpress");
                              /*  String totalrows = jsonObject1.optString("totalrows");*/
                                String awardid = jsonObject1.optString("awardid");
                                String date = jsonObject1.optString("date");

                                String isNomineeDonor = "";
                                String isNominatorDonar = "";

                                if (jsonObject1.has("isNomineeDonor"))
                                    isNomineeDonor = jsonObject1.optString("isNomineeDonor");

                                if (jsonObject1.has("isNominatorDonor"))
                                    isNominatorDonar = jsonObject1.optString("isNominatorDonor");

                                String taguserid = jsonObject1.optString("taguserid");
                                String tagcount = jsonObject1.optString("tagcount");


                                GetXpresswayPost getXpresswayPost1 = new GetXpresswayPost(userid, nominator_name, nominator_designation,
                                        nominator_location, nominator_imageurl, nominee_name, nominee_designation,
                                        nominee_location, nominee_imageurl, title, description, details, challengesfaced,
                                        is_story, recognise_date, event_date, type, url, icon, count, likes, comments, ulikes,
                                        ucomments, recognition_id, nominee, subject, isxpress, /*totalrows,*/awardid, date, isNomineeDonor, isNominatorDonar,
                                        taguserid, tagcount);
                                getXpresswayPostDao.insertOrReplace(getXpresswayPost1);

                            }

                        } else {
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


    public String callGetMorePost() {
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
            entitymap.put("date", today_date);
            entitymap.put("postlastcount", "0");
            entitymap.put("devicetype", "android");
            entitymap.put("appversion", appVersion);

            Log.i("devicetype", "android");
            Log.i("appversion", appVersion);

            Log.i("Hello", "Hello");


            Log.i("date jhakhfdjf", "" + today_date);

            Log.i("responstring", "getmorepost serviceUrl-->" + Constant.BASEURL + "/api/userapi/getmorepost" + "&userid=" + UserId + "&date=" + today_date);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "/api/userapi/getmorepost");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetMorePostData(responseString);
    }

    public String parseGetMorePostData(String jsonResponse) {
        Constant.web_app_version = "0";
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getGetMorePostDao().deleteAll();
        GetMorePostDao getMorePostDao = daoSession.getGetMorePostDao();
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
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String userid = jsonObject1.optString("userid");
                                String nominator_designation = jsonObject1.optString("nominator_designation");
                                String nominator_location = jsonObject1.optString("nominator_location");
                                String nominator_imageurl = jsonObject1.optString("nominator_imageurl");
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String nominee_designation = jsonObject1.optString("nominee_designation");
                                String nominee_location = jsonObject1.optString("nominee_location");
                                String nominee_imageurl = jsonObject1.optString("nominee_imageurl");
                                String title = jsonObject1.optString("title");
                                String description = jsonObject1.optString("description");
                                String details = jsonObject1.optString("details");
                                String challengesfaced = jsonObject1.optString("challengesfaced");
                                String is_story = jsonObject1.optString("is_story");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String event_date = jsonObject1.optString("event_date");
                                String type = jsonObject1.optString("type");
                                String url = jsonObject1.optString("url");
                                String icon = jsonObject1.optString("icon");
                                String count = jsonObject1.optString("count");
                                String likes = jsonObject1.optString("likes");
                                String comments = jsonObject1.optString("comments");
                                String ulikes = jsonObject1.optString("ulikes");
                                String ucomments = jsonObject1.optString("ucomments");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String nominee = jsonObject1.optString("nominee");
                                String subject = jsonObject1.optString("subject");
                                String isxpress = jsonObject1.optString("isxpress");
                              /*  String totalrows = jsonObject1.optString("totalrows");*/
                                String awardid = jsonObject1.optString("awardid");
                                String date = jsonObject1.optString("date");
                                String isNomineeDonor = jsonObject1.optString("isNomineeDonor");
                                String isNominatorDonar = jsonObject1.optString("isNominatorDonar");
                                String taguserid = jsonObject1.optString("taguserid");
                                String tagcount = jsonObject1.optString("tagcount");


                                GetMorePost getMorePost = new GetMorePost(userid, nominator_name, nominator_designation,
                                        nominator_location, nominator_imageurl, nominee_name, nominee_designation,
                                        nominee_location, nominee_imageurl, title, description, details, challengesfaced,
                                        is_story, recognise_date, event_date, type, url, icon, count, likes, comments, ulikes,
                                        ucomments, recognition_id, nominee, subject, isxpress, /*totalrows,*/awardid, date, isNomineeDonor, isNominatorDonar,
                                        taguserid, tagcount);
                                getMorePostDao.insertOrReplace(getMorePost);


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

   /* public List<GetMorePost> getMorePosts()
    {
        DaoSession daoSession=getDBSessoin(context);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.loadAll();
            return getMorePosts;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            getMorePostDao.getDatabase().close();
        }
    }*/


    /*public List<GetMorePost> getMorePosts()
    {
        DaoSession daoSession=getDBSessoin(context);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        List<GetMorePost> getMorePosts=new ArrayList<>();
        getMorePosts=getMorePostDao.loadAll();
        return getMorePosts;

    }*/

/*
    public List<GetXpresswayPost> getXpresswayPosts()
    {
        DaoSession daoSession=getDBSessoin(context);
        GetXpresswayPostDao getXpresswayPostDao=daoSession.getGetXpresswayPostDao();
        List<GetXpresswayPost> getXpresswayPosts=new ArrayList<>();
        getXpresswayPosts=getXpresswayPostDao.loadAll();
        return  getXpresswayPosts;

    }
*/

    public List<GetXpresswayPost> getXpresswayPosts() {
        DaoSession daoSession = getDBSessoin(context);
        GetXpresswayPostDao getXpresswayPostDao = daoSession.getGetXpresswayPostDao();
        try {
            List<GetXpresswayPost> getXpresswayPosts = new ArrayList<>();
            getXpresswayPosts = getXpresswayPostDao.loadAll();
            return getXpresswayPosts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
       /* finally {
            getXpresswayPostDao.getDatabase().close();
        }*/

    }

    public List<GetMorePost> getMorePosts() {
        DaoSession daoSession = getDBSessoin(context);
        GetMorePostDao getMorePostDao = daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<>();
            getMorePosts = getMorePostDao.loadAll();
            return getMorePosts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
       /* finally {
            getMorePostDao.getDatabase().close();
        }*/
    }


    public List<AllWebNotification> getAllWebNotifications() {
        DaoSession daoSession = getDBSessoin(context);
        AllWebNotificationDao allWebNotificationDao = daoSession.getAllWebNotificationDao();
        try {
            List<AllWebNotification> allWebNotifications = new ArrayList<>();
            allWebNotifications = allWebNotificationDao.loadAll();
            return allWebNotifications;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<UserWedding> getUserWeddings() {
        DaoSession daoSession = getDBSessoin(context);
        UserWeddingDao userWeddingDao = daoSession.getUserWeddingDao();
        try {
            List<UserWedding> userWeddings = new ArrayList<>();
            userWeddings = userWeddingDao.loadAll();
            return userWeddings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<AllUserEvent> getAllUserEvents() {
        DaoSession daoSession = getDBSessoin(context);
        AllUserEventDao allUserEventDao = daoSession.getAllUserEventDao();
        try {
            List<AllUserEvent> allUserEvents = new ArrayList<>();
            allUserEvents = allUserEventDao.loadAll();
            return allUserEvents;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BulletinCategory> getBulletinCategories() {
        DaoSession daoSession = getDBSessoin(context);
        BulletinCategoryDao bulletinCategoryDao = daoSession.getBulletinCategoryDao();
        try {
            List<BulletinCategory> bulletinCategories = new ArrayList<>();
            bulletinCategories = bulletinCategoryDao.loadAll();
            return bulletinCategories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<GetMorePost> getPostsByPostID(String postID) {
        DaoSession daoSession = getDBSessoin(context);
        GetMorePostDao getMorePostDao = daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(postID)).list();
            return getMorePosts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            getMorePostDao.getDatabase().close();
        }
    }


    public List<GetXpresswayPost> getXPPostsByPostID(String postID) {
        DaoSession daoSession = getDBSessoin(context);
        GetXpresswayPostDao getXpresswayPostDao = daoSession.getGetXpresswayPostDao();
        try {
            List<GetXpresswayPost> getMorePosts = new ArrayList<GetXpresswayPost>();
            getMorePosts = getXpresswayPostDao.queryBuilder().where(GetXpresswayPostDao.Properties.Recognition_id.eq(postID)).list();
            return getMorePosts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            getXpresswayPostDao.getDatabase().close();
        }
    }


}
