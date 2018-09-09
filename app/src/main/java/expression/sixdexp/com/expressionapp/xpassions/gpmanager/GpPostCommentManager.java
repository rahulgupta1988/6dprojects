package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.Comments;
import db.CommentsDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 14-Feb-18.
 */

public class GpPostCommentManager extends BaseManager {

    Context _mContext;
    String responseString="";
    public static ArrayList<Comments> commentses=new ArrayList<Comments>();

    public  GpPostCommentManager(Context context){
        _mContext=context;

    }

    public String commentCall(String gid,String gpid)
    {

        try {
            Log.i("responstring", "coi_Getcommentbygidandpostid serviceUrl-->" + Constant.BASEURL
                    + "&api/coi/coi_Getcommentbygidandpostid"
                    + "&gid=" + gid
                    + "&gpid=" + gpid);

            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("userid",userid);
            entitymap.put("gid",gid);
            entitymap.put("gpid",gpid);


            responseString= ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/coi/coi_Getcommentbygidandpostid");

            if (responseString!=null)
                Log.i("responseString cooment", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsecommentData(responseString);
    }

    public String parsecommentData(String jsonResponse)
    {

        String responseCode = "";
      /*  DaoSession daoSession=getDBSessoin(_mContext);
        CommentsDao commentsDao=daoSession.getCommentsDao();
        commentsDao.deleteAll();*/

        try {
            commentses.clear();
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;

                            JSONArray responseData_json=jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "responseStringlength=" + responseData_json.length());
                            updateComments(""+responseData_json.length());
                            for (int i=0;i<responseData_json.length();i++){

                                JSONObject jObj=responseData_json.getJSONObject(i);
                                String recognition_id=jObj.getString("coi_gpid");
                                String userid=jObj.getString("userid");
                                String userName=jObj.getString("userName");
                                String date=jObj.getString("date");
                                String designation="";
                                String department=jObj.getString("department");
                                String location="";
                                String imageurl=jObj.getString("imageurl");
                                String comments=jObj.getString("comments");
                                String count=jObj.getString("count");

                                String coi_gid=jObj.getString("coi_gid");
                                String coi_gpid=jObj.getString("coi_gpid");


                                String tagcount="";
                                if(jObj.has("tagcount"))
                                    tagcount= jObj.getString("tagcount");

                                String commentid="";
                                if(jObj.has("commentid"))
                                    commentid= jObj.getString("commentid");


                                Comments commentsonj=new Comments(recognition_id,commentid,userid,userName,date,
                                        designation,department,location,imageurl,comments,count,tagcount,coi_gid,coi_gpid);


                                commentses.add(commentsonj);
                                // commentsDao.insertOrReplace(commentsonj);
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


    public List<Comments> getCommentsList()
    {
        DaoSession daoSession=getDBSessoin(_mContext);
        CommentsDao commentsDao=daoSession.getCommentsDao();
        try {
            List<Comments> commentses = new ArrayList<Comments>();
            commentses = commentsDao.loadAll();
            return commentses;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public void updateComments(String tcomment) {
     /*   DaoSession daoSession=getDBSessoin(_mContext);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(recognizationId)).list();

            if (getMorePosts != null) {
                for (GetMorePost post : getMorePosts) {

                    post.setComments(tcomment);
                    getMorePostDao.update(post);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }*/
    }
}

