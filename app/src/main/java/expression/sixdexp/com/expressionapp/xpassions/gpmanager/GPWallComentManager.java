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
import db.GPWALLPOST;
import db.GPWALLPOSTDao;
import db.UserLoginInfo;
import db.XPGPWALLPOST;
import db.XPGPWALLPOSTDao;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 30-Jan-18.
 */

public class GPWallComentManager extends BaseManager {


    Context _mContext;
    String responseString = "";
    String gpid="";
    boolean isXPpost=false;

    public GPWallComentManager(Context context,boolean isXPpost) {
        _mContext = context;
         this.isXPpost=isXPpost;
    }

    public String postComment(String comment,String taguser,String gid,String gpid) {

        try {

            this.gpid = gpid;
            UserLoginInfo userLoginInfo = new LoginManager(_mContext).getUserInfo().get(0);
            String user_id = userLoginInfo.getUserid();
            String designation = userLoginInfo.getDesignation();
            String department = userLoginInfo.getDepartment();


            Log.i("responstring", "comment serviceUrl-->" + Constant.BASEURL
                    + "&param=api/coi/coi_addgrouppostcommentwithtag"
                    + "&gpid=" + gpid
                    + "&userid" + user_id
                    + "&comment=" + comment);
            Log.i("tagged users 24352",""+taguser);

            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid", user_id);
            entitymap.put("commenttext", comment);
            entitymap.put("taguserids", taguser);
            entitymap.put("gid",gid);
            entitymap.put("gpid",gpid);
            entitymap.put("devicetype", "android");


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_addgrouppostcommentwithtag");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsecommentData(responseString);
    }


    public String parsecommentData(String jsonResponse) {

        String responseCode = "";
        DaoSession daoSession = getDBSessoin(_mContext);

        CommentsDao commentsDao = daoSession.getCommentsDao();
        commentsDao.deleteAll();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray responseData_json = jsonObject.getJSONArray("responseData");

                            for (int i = 0; i < responseData_json.length(); i++) {

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
                                String coi_gpid=jObj.getString("coi_gpid");
                                String coi_gid=jObj.getString("coi_gid");

                                String tagcount="";
                                if(jObj.has("tagcount"))
                                    tagcount= jObj.getString("tagcount");

                                String commentid="";
                                if(jObj.has("commentid"))
                                    commentid= jObj.getString("commentid");

                                Comments commentsonj = new Comments(recognition_id,commentid, userid, userName, date,
                                        designation, department, location, imageurl, comments, count,tagcount,coi_gid,coi_gpid);



                                daoSession.insertOrReplace(commentsonj);
                            }

                            updateComments("" + responseData_json.length());
                            updateCommentsInPostList("" + responseData_json.length());
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

        try {
            if(isXPpost)
                Constant.singlePostList_xp_gp.get(0).setComments(tcomment);
            else
            Constant.singlePostList_gp.get(0).setComments(tcomment);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void updateCommentsInPostList(String tcomment) {
        if(isXPpost){
            DaoSession daoSession = getDBSessoin(_mContext);
            XPGPWALLPOSTDao xpgpwallpostDao = daoSession.getXPGPWALLPOSTDao();
            try {
                List<XPGPWALLPOST> xpgpwallposts = new ArrayList<XPGPWALLPOST>();
                xpgpwallposts = xpgpwallpostDao.queryBuilder().where(XPGPWALLPOSTDao.Properties.Gpostid.eq(gpid)).list();

                if (xpgpwallposts != null) {
                    for (XPGPWALLPOST post : xpgpwallposts) {

                        post.setComments(tcomment);
                        xpgpwallpostDao.update(post);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            }


        }
        else {
            DaoSession daoSession = getDBSessoin(_mContext);
            GPWALLPOSTDao gpwallpostDao = daoSession.getGPWALLPOSTDao();
            try {
                List<GPWALLPOST> gpwallposts = new ArrayList<GPWALLPOST>();
                gpwallposts = gpwallpostDao.queryBuilder().where(GPWALLPOSTDao.Properties.Gpostid.eq(gpid)).list();

                if (gpwallposts != null) {
                    for (GPWALLPOST post : gpwallposts) {

                        post.setComments(tcomment);
                        gpwallpostDao.update(post);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


    }






}
