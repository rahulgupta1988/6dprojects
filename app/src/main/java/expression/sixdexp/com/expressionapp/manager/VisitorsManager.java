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
        import db.GetMorePost;
        import db.GetMorePostDao;
        import expression.sixdexp.com.expressionapp.utility.Constant;
        import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;


public class VisitorsManager extends BaseManager
{

    Context context;
    String responseString="";
    public VisitorsManager (Context context) {
        this.context=context;

    }

    public String callVisitorCounter() {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getawards serviceUrl-->" + Constant.BASEURL + "api/userapi/getvisitors");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getvisitors");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseAppVisitorCounter(responseString);
    }


    public String parseAppVisitorCounter(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getGetMorePostDao().deleteAll();
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

                                GetMorePost getMorePost = new GetMorePost(userid,nominator_name, nominator_designation,
                                        nominator_location, nominator_imageurl, nominee_name, nominee_designation,
                                        nominee_location, nominee_imageurl, title, description, details, challengesfaced,
                                        is_story, recognise_date, event_date, type, url, icon, count, likes, comments, ulikes,
                                        ucomments, recognition_id, nominee, subject, isxpress, /*totalrows,*/awardid,date,isNomineeDonor,isNominatorDonar,
                                        taguserid,tagcount);
                                getMorePostDao.insertOrReplace(getMorePost);


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

    public List<GetMorePost> getMorePosts()
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
    }

    public List<GetMorePost> getPostsByPostID(String postID)
    {
        DaoSession daoSession=getDBSessoin(context);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts =  getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(postID)).list();
            return getMorePosts;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
