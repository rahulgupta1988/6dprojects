package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.SrijanTOPComment;
import db.SrijanTOPCommentDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class SrijanTOPCommentsManager extends BaseManager {

    Context context;
    String responseString="";


    public SrijanTOPCommentsManager (Context context) {
        this.context=context;

    }

    public String callSrijanTopComment()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getTop20SrijanComments serviceUrl-->" + Constant.BASEURL + "api/srijan/getTop20SrijanComments");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/srijan/getTop20SrijanComments");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        SrijanTOPCommentDao commentsDao=daoSession.getSrijanTOPCommentDao();
        commentsDao.deleteAll();
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

                            JSONArray responseData_json=jsonObject.getJSONArray("responseData");

                            for (int i=0;i<responseData_json.length();i++){
                                JSONObject jObj=responseData_json.getJSONObject(i);
                                String policyid=jObj.getString("policyid");
                                String policyname=jObj.getString("policyname");
                                String policynamedisplayvalue=jObj.getString("policynamedisplayvalue");
                                String usercommentdisplayvalue=jObj.getString("usercommentdisplayvalue");
                                String commentdatedisplayvalue=jObj.getString("commentdatedisplayvalue");
                                String commenttimedisplayvalue=jObj.getString("commenttimedisplayvalue");
                                String commentuserid=jObj.getString("commentuserid");
                                String commentusername=jObj.getString("commentusername");
                                String commentuserdepartment=jObj.getString("commentuserdepartment");
                                String commentuserprofilepic=jObj.getString("commentuserprofilepic");
                                String commentlevel=jObj.getString("commentlevel");
                                String parentcommentid=jObj.getString("parentcommentid");
                                String usercomment=jObj.getString("usercomment");
                                String taguser="";
                                String childcommentid="";
                                if(jObj.has("taguser"))
                                    taguser=jObj.getString("taguser");
                                if(jObj.has("childcommentid"))
                                    childcommentid=jObj.getString("childcommentid");


                                SrijanTOPComment srijanTOPComment=new SrijanTOPComment(policyid,policyname,policynamedisplayvalue,
                                        usercommentdisplayvalue,commentdatedisplayvalue,commenttimedisplayvalue,commentuserid,
                                        commentusername,commentuserdepartment,commentuserprofilepic,commentlevel,parentcommentid,usercomment,taguser,childcommentid);
                                commentsDao.insertOrReplace(srijanTOPComment);
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
                    responseString=responseString;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }


    public List<SrijanTOPComment> getTopComments(){
        DaoSession daoSession=getDBSessoin(context);
        SrijanTOPCommentDao commentsDao=daoSession.getSrijanTOPCommentDao();
        List<SrijanTOPComment> srijanTOPComments=commentsDao.loadAll();
        return srijanTOPComments;
    }

}
