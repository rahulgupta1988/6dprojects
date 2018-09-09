package expression.sixdexp.com.expressionapp.manager;

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
import db.GetMorePost;
import db.GetMorePostDao;
import db.GetXpresswayPost;
import db.GetXpresswayPostDao;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/6/2016.
 */
public class LikeAndCommentManager extends BaseManager {



    Context _mContext;
    String responseString="";
    String recognizationId="";
    public  LikeAndCommentManager(Context context){
        _mContext=context;

    }


    public String postLikeexpressway(String postID)
    {

        try {

            Log.i("responstring", "LoginUser serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userApi/PostLike"
                    + "&postID=" + postID);

            recognizationId=postID;
            UserLoginInfo userLoginInfo=new LoginManager(_mContext).getUserInfo().get(0);
            String user_id= userLoginInfo.getUserid();
            String designation=userLoginInfo.getDesignation();
            String department=userLoginInfo.getDepartment();

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("id",postID);
            entitymap.put("userid",user_id);
            entitymap.put("designation",designation);
            entitymap.put("department",department);
            entitymap.put("devicetype", "android");


            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/PostLike");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeDataexpress(responseString);
    }

    public String parseLikeDataexpress(String jsonResponse)
    {

        String responseCode = "";
        DaoSession daoSession=getDBSessoin(_mContext);

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
                            JSONObject responseData_json=jsonObject.getJSONObject("responseData");
                            String tlike=responseData_json.getString("tlike");
                            String ulike=responseData_json.getString("ulike");
                            String tcomment=responseData_json.getString("tcomment");
                            String ucomment=responseData_json.getString("ucomment");
                            updateLikesexpress(tlike,ulike,tcomment,ucomment);
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


    public void updateLikesexpress(String tlike, String ulike, String tcomment, String ucomment) {
        DaoSession daoSession = getDBSessoin(_mContext);
        GetXpresswayPostDao getXpresswayPostDao=daoSession.getGetXpresswayPostDao();
        try {
            List<GetXpresswayPost> getXpresswayPosts=new ArrayList<>();
            getXpresswayPosts=getXpresswayPostDao.queryBuilder().where(GetXpresswayPostDao.Properties.Recognition_id.eq(recognizationId)).list();

            if (getXpresswayPosts!=null)
            {
                for (GetXpresswayPost post:getXpresswayPosts)
                {
                    post.setLikes(tlike);
                    post.setUlikes(ulike);
                    post.setComments(tcomment);
                    post.setUcomments(ucomment);
                    getXpresswayPostDao.update(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


  /*  public void updateLikesexpress(String tlike,String ulike,String tcomment,String ucomment) {
        DaoSession daoSession=getDBSessoin(_mContext);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(recognizationId)).list();

            if (getMorePosts != null) {
                for (GetMorePost post : getMorePosts) {
                    post.setLikes(tlike);
                    post.setUlikes(ulike);
                    post.setComments(tcomment);
                    post.setUcomments(ucomment);
                    getMorePostDao.update(post);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }*/


  /*  ==========================================================================*/


    public String postLike(String postID)
    {

        try {

            Log.i("responstring", "LoginUser serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userApi/PostLike"
                    + "&postID=" + postID);

            recognizationId=postID;
            UserLoginInfo userLoginInfo=new LoginManager(_mContext).getUserInfo().get(0);
            String user_id= userLoginInfo.getUserid();
            String designation=userLoginInfo.getDesignation();
            String department=userLoginInfo.getDepartment();

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("id",postID);
            entitymap.put("userid",user_id);
            entitymap.put("designation",designation);
            entitymap.put("department",department);
            entitymap.put("devicetype", "android");


            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/PostLike");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeData(responseString);
    }



    public String parseLikeData(String jsonResponse)
    {

        String responseCode = "";
        DaoSession daoSession=getDBSessoin(_mContext);

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
                            JSONObject responseData_json=jsonObject.getJSONObject("responseData");
                            String tlike=responseData_json.getString("tlike");
                            String ulike=responseData_json.getString("ulike");
                            String tcomment=responseData_json.getString("tcomment");
                            String ucomment=responseData_json.getString("ucomment");
                            updateLikes(tlike,ulike,tcomment,ucomment);
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



    public void updateLikes(String tlike,String ulike,String tcomment,String ucomment) {
        DaoSession daoSession=getDBSessoin(_mContext);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(recognizationId)).list();

            if (getMorePosts != null) {
                for (GetMorePost post : getMorePosts) {
                    post.setLikes(tlike);
                    post.setUlikes(ulike);
                    post.setComments(tcomment);
                    post.setUcomments(ucomment);
                    getMorePostDao.update(post);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }


    public List<GetMorePost> getPostByRecognizationID(String recogID){

        DaoSession daoSession=getDBSessoin(_mContext);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Recognition_id.eq(recogID)).list();
            return  getMorePosts;
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }


    public List<GetXpresswayPost> getPostByRecognizationIDXpressway(String recogizationId)
    {
        DaoSession daoSession=getDBSessoin(_mContext);
        GetXpresswayPostDao getXpresswayPostDao=daoSession.getGetXpresswayPostDao();
        try
        {
            List<GetXpresswayPost> getXpresswayPosts=new ArrayList<>();
            getXpresswayPosts=getXpresswayPostDao.queryBuilder().where(GetXpresswayPostDao.Properties.Recognition_id.eq(recogizationId)).list();
            return getXpresswayPosts;
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

/*    public List<GetXpresswayPost> getPostByRecognizationID1(String recogID){

        DaoSession daoSession=getDBSessoin(_mContext);
        GetXpresswayPostDao getXpresswayPostDao=daoSession.getGetXpresswayPostDao();
        try {
            List<GetXpresswayPost> getXpresswayPosts = new ArrayList<GetXpresswayPost>();
            getXpresswayPosts = getXpresswayPostDao.queryBuilder().where(GetXpresswayPostDao.Properties.Recognition_id.eq(recogID)).list();
            return  getXpresswayPosts;
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }*/


    public String postComment(String postID,String comment,String taguser)
    {

        try {

            recognizationId=postID;
            UserLoginInfo userLoginInfo=new LoginManager(_mContext).getUserInfo().get(0);
            String user_id= userLoginInfo.getUserid();
            String designation=userLoginInfo.getDesignation();
            String department=userLoginInfo.getDepartment();


            Log.i("responstring", "LoginUser serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userApi/postComments"
                    + "&id="+postID
                    +"&userid"+user_id
                    +"&comment="+comment
                    +"&designation="+designation
                    +"department"+department);
            Log.i("tagged users 24352",""+taguser);

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("id",postID);
            entitymap.put("userid",user_id);
            entitymap.put("comment",comment);
            entitymap.put("designation",designation);
            entitymap.put("taguser", taguser);
            entitymap.put("department",department);
            entitymap.put("devicetype", "android");


            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/postComments");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsecommentData(responseString);
    }


    public String parsecommentData(String jsonResponse)
    {

        String responseCode = "";
        DaoSession daoSession=getDBSessoin(_mContext);

        CommentsDao commentsDao=daoSession.getCommentsDao();
        commentsDao.deleteAll();

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
                            JSONArray responseData_json=jsonObject.getJSONArray("responseData");

                            for (int i=0;i<responseData_json.length();i++){

                                JSONObject jObj=responseData_json.getJSONObject(i);
                                String recognition_id=jObj.getString("recognition_id");
                                String userid=jObj.getString("userid");
                                String userName=jObj.getString("userName");
                                String date=jObj.getString("date");
                                String designation=jObj.getString("designation");
                                String department=jObj.getString("department");
                                String location=jObj.getString("location");
                                String imageurl=jObj.getString("imageurl");
                                String comments=jObj.getString("comments");
                                String count=jObj.getString("count");
                                String tagcount="";
                                if(jObj.has("tagcount"))
                                    tagcount= jObj.getString("tagcount");
                                String commentid="";
                                if(jObj.has("commentid"))
                                    commentid= jObj.getString("commentid");

                                Comments commentsonj=new Comments(recognition_id,commentid,userid,userName,date,
                                        designation,department,location,imageurl,comments,count,tagcount,"","");
                                daoSession.insertOrReplace(commentsonj);
                            }

                            updateComments(""+responseData_json.length());

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
        DaoSession daoSession=getDBSessoin(_mContext);
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

        }
    }

}
