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
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/7/2016.
 */
public class CommentManager extends BaseManager{

    Context _mContext;
    String responseString="";
    String recognizationId="";
    public static ArrayList<Comments> commentses=new ArrayList<Comments>();
    public  CommentManager(Context context){
        _mContext=context;

    }

    public String commentCall(String postID)
    {

        try {
            Log.i("09122 call server", "responseString=" + responseString);
            Log.i("responstring", "getComments serviceUrl-->" + Constant.BASEURL
                    + "&api/userapi/getComments"
                    + "&recognitionid=" + postID
                    + "&gettype=" + "All");

            recognizationId=postID;
            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("recognitionid",postID);
            entitymap.put("gettype","All");
            entitymap.put("devicetype", "android");



            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/getComments");

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
