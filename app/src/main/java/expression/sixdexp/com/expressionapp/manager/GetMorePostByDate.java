package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import db.DaoSession;
import db.GetMorePost;
import db.GetMorePostDao;
import db.GetXpresswayPost;
import db.GetXpresswayPostDao;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/11/2016.
 */
public class GetMorePostByDate extends BaseManager{

    Context context;
    String responseString="";
    String postDate="";

    public GetMorePostByDate (Context context, String postDate) {
        this.context=context;
        this.postDate=postDate;
    }

    public String callGetMorePost()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
           // entitymap.put("userid", SharedPrefrenceManager.getUserID(context));

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String  UserId = userDatas.get(0).getUserid();

            String appVersion="0.0";
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;

            //entitymap.put("userid", SharedPrefrenceManager.getUserID(context));
            entitymap.put("userid", UserId);
            entitymap.put("devicetype","android");
            entitymap.put("appversion",appVersion);

            Log.i("devicetype","android");
            Log.i("appversion",appVersion);
        /*    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            *//*Date date1 = new Date();
            String today_date=dateFormat.format(date1);
            entitymap.put("date", today_date);*//*



            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, Constant.today);
            String today_date=dateFormat.format(cal.getTime());*/



            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar cal=null;
            try {
                Date date = format.parse(postDate);
                cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE,-1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String today_date=format.format(cal.getTime());
            entitymap.put("date", today_date);

            String postCount=getPostCountByDate(postDate);
            Log.i("0912postcount",postCount);
            entitymap.put("postlastcount",postCount);


            Log.i("date hello", "" + today_date);

            Log.i("responstring", "getmorepost serviceUrl-->" + Constant.BASEURL+"/api/userapi/getmorepost" + "&userid=" + UserId + "&date=" + today_date);
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"/api/userapi/getmorepost");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetMorePostData(responseString);
    }

    public String parseGetMorePostData(String jsonResponse)
    {
        Constant.web_app_version="0";
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);

        //daoSession.getGetMorePostDao().deleteAll();
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
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
                            Log.i("responseStringlength", "responseStringlength=" + jsonarray.length());
                            for(int i=0;i<jsonarray.length();i++) {

                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
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
                               /* String totalrows = jsonObject1.optString("totalrows");*/
                                String awardid = jsonObject1.optString("awardid");
                                String date = jsonObject1.optString("date");

                                String isNomineeDonor = jsonObject1.optString("isNomineeDonor");
                                String isNominatorDonar = jsonObject1.optString("isNominatorDonar");

                                String taguserid = jsonObject1.optString("taguserid");
                                String tagcount = jsonObject1.optString("tagcount");

                                GetMorePost getMorePost = new GetMorePost(userid,nominator_name, nominator_designation,
                                        nominator_location, nominator_imageurl, nominee_name, nominee_designation,
                                        nominee_location, nominee_imageurl, title, description, details, challengesfaced,
                                        is_story, recognise_date, event_date, type, url, icon, count, likes, comments,
                                        ulikes, ucomments, recognition_id, nominee, subject,isxpress, /*totalrows,*/awardid,date,isNomineeDonor,isNominatorDonar,
                                        taguserid,tagcount);
                                getMorePostDao.insertOrReplace(getMorePost);


                            }

                        }
                        else
                        {

                            if(responseCode.equals("300"))
                            {
                                Constant.web_app_version=responseCode;
                            }
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


    public String getPostCountByDate(String lastpostDate)
    {
        DaoSession daoSession=getDBSessoin(context);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();

      //  List<GetMorePost>  getMorePosts=getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Date.eq(lastpostDate)).list();

        List<GetMorePost>  getMorePosts=getMorePostDao.loadAll();
        String postCount="0";
        if(getMorePosts!=null){
            postCount=String.valueOf(getMorePosts.size());
        }

        return postCount;

    }


    public String callGetMoreXPPost()
    {

        try {


            HashMap<String,String> entitymap=new HashMap<String,String>();
            // entitymap.put("userid", SharedPrefrenceManager.getUserID(context));

            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String  UserId = userDatas.get(0).getUserid();

            //entitymap.put("userid", SharedPrefrenceManager.getUserID(context));
            entitymap.put("userid", UserId);

        /*    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            *//*Date date1 = new Date();
            String today_date=dateFormat.format(date1);
            entitymap.put("date", today_date);*//*



            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, Constant.today);
            String today_date=dateFormat.format(cal.getTime());*/



            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar cal=null;
            try {
                Date date = format.parse(postDate);
                cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE,-1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String today_date=format.format(cal.getTime());
            entitymap.put("date", today_date);

            Log.i("date hello", "" + today_date);

            Log.i("responstring", "xpresswaypost serviceUrl-->" + Constant.BASEURL+"/api/userapi/xpressway" + "&userid=" + UserId + "&date=" + today_date);
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"/api/userapi/xpressway");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseGetXPMorePostData(responseString);
    }

    public String parseGetXPMorePostData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        //daoSession.getGetXpresswayPostDao().deleteAll();
        GetXpresswayPostDao getXpresswayPostDao=daoSession.getGetXpresswayPostDao();

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
                            Log.i("responseStringlength", "responseStringlength=" + jsonarray.length());
                            for(int i=0;i<jsonarray.length();i++) {

                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
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

                                if(jsonObject1.has("isNomineeDonor"))
                                    isNomineeDonor = jsonObject1.optString("isNomineeDonor");

                                if(jsonObject1.has("isNominatorDonor"))
                                    isNominatorDonar = jsonObject1.optString("isNominatorDonor");
                                String taguserid = jsonObject1.optString("taguserid");
                                String tagcount = jsonObject1.optString("tagcount");

                                GetXpresswayPost getXpresswayPost1 = new GetXpresswayPost(userid,nominator_name, nominator_designation,
                                        nominator_location, nominator_imageurl, nominee_name, nominee_designation,
                                        nominee_location, nominee_imageurl, title, description, details, challengesfaced,
                                        is_story, recognise_date, event_date, type, url, icon, count, likes, comments, ulikes,
                                        ucomments, recognition_id, nominee, subject, isxpress, /*totalrows,*/awardid,date,isNomineeDonor,isNominatorDonar,
                                        taguserid,tagcount);
                                getXpresswayPostDao.insertOrReplace(getXpresswayPost1);

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
