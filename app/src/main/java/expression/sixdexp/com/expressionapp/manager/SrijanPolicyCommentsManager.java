package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expression.sixdexp.com.expressionapp.PolicyCommentActivity;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 16-Aug-17.
 */

public class SrijanPolicyCommentsManager extends BaseManager {

    Context context;
    String responseString = "";
    public static List<ParentCommentModel>listDataHeader = new ArrayList<ParentCommentModel>();
    public static Map<ParentCommentModel,List<ChildCommentModel>>listDataChild = new HashMap<ParentCommentModel,List<ChildCommentModel>>();

    public SrijanPolicyCommentsManager(Context context) {
        this.context = context;

    }

    public String callSrijanPolicyComments(String policyid) {

        try {
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getpolicycommentsbypolicyid serviceUrl-->" + Constant.BASEURL + "api/srijan/getpolicycommentsbypolicyid");

            entitymap.put("userid",userid);
            entitymap.put("policyid",policyid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/getpolicycommentsbypolicyid");

            if (responseString != null)
                Log.i("getpolicycomments", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
        String responseCode = "";
        listDataHeader.clear();
        listDataChild.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

                            JSONObject responseData_json = jsonObject.getJSONObject("responseData");
                            PolicyCommentActivity.policycmt=responseData_json.getString("comments");
                            PolicyCommentActivity.policy_desc=responseData_json.getString("policydescription");

                            JSONArray policycomments=responseData_json.getJSONArray("policycomments");

                            for (int i=0;i<policycomments.length();i++){
                                ParentCommentModel parentCommentModel=new ParentCommentModel();

                                JSONObject parent_json= policycomments.getJSONObject(i);

                                parentCommentModel.setParentcommentid(parent_json.getString("parentcommentid"));
                                parentCommentModel.setCommenttext(parent_json.getString("commenttext"));
                                parentCommentModel.setCommentdocumentpath(parent_json.getString("commentdocumentpath"));
                                parentCommentModel.setUserid(parent_json.getString("userid"));
                                parentCommentModel.setUsername(parent_json.getString("username"));
                                parentCommentModel.setUserprofileimage(parent_json.getString("userprofileimage"));
                                parentCommentModel.setCommentdate(parent_json.getString("commentdate"));
                                parentCommentModel.setCommentdatedisplayvalue(parent_json.getString("commentdatedisplayvalue"));
                                parentCommentModel.setCommentdatetimedisplayvalue(parent_json.getString("commentdatetimedisplayvalue"));
                                parentCommentModel.setSrijanadminfeedbackstatus(parent_json.getString("srijanadminfeedbackstatus"));
                                parentCommentModel.setTaguser(parent_json.getString("taguser"));
                                listDataHeader.add(parentCommentModel);


                                List<ChildCommentModel>childCMModel = new ArrayList<ChildCommentModel>();
                                JSONArray chlidComment_array=parent_json.getJSONArray("policychildcomments");
                                for (int j=0;j<chlidComment_array.length();j++){
                                    ChildCommentModel childCommentModel=new ChildCommentModel();
                                    JSONObject jsonObject1=chlidComment_array.getJSONObject(j);

                                    childCommentModel.setCommentid(jsonObject1.getString("commentid"));
                                    childCommentModel.setParentcommentid(jsonObject1.getString("parentcommentid"));
                                    childCommentModel.setCommenttext(jsonObject1.getString("commenttext"));
                                    childCommentModel.setUserid(jsonObject1.getString("userid"));
                                    childCommentModel.setUsername(jsonObject1.getString("username"));
                                    childCommentModel.setUserprofileimage(jsonObject1.getString("userprofileimage"));
                                    childCommentModel.setCommentdate(jsonObject1.getString("commentdate"));
                                    childCommentModel.setCommentdatedisplayvalue(jsonObject1.getString("commentdatedisplayvalue"));
                                    childCommentModel.setCommentDateTimeDisplayValue(jsonObject1.getString("commentdatetimedisplayvalue"));
                                    childCommentModel.setSrijanadminfeedbackstatus(jsonObject1.getString("srijanadminfeedbackstatus"));
                                    childCommentModel.setTaguser(jsonObject1.getString("taguser"));
                                    childCMModel.add(childCommentModel);
                                }

                                listDataChild.put(parentCommentModel,childCMModel);
                            }

                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = responseString;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }



    public class ParentCommentModel{
        String parentcommentid;
        String commenttext;
        String commentdocumentpath;
        String userid;
        String username;
        String userprofileimage;
        String commentdate;
        String commentdatedisplayvalue;
        String commentdatetimedisplayvalue;
        String srijanadminfeedbackstatus;
        String taguser;

        public String getTaguser() {
            return taguser;
        }

        public void setTaguser(String taguser) {
            this.taguser = taguser;
        }

        public String getParentcommentid() {
            return parentcommentid;
        }

        public void setParentcommentid(String parentcommentid) {
            this.parentcommentid = parentcommentid;
        }

        public String getCommenttext() {
            return commenttext;
        }

        public void setCommenttext(String commenttext) {
            this.commenttext = commenttext;
        }

        public String getCommentdocumentpath() {
            return commentdocumentpath;
        }

        public void setCommentdocumentpath(String commentdocumentpath) {
            this.commentdocumentpath = commentdocumentpath;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserprofileimage() {
            return userprofileimage;
        }

        public void setUserprofileimage(String userprofileimage) {
            this.userprofileimage = userprofileimage;
        }

        public String getCommentdate() {
            return commentdate;
        }

        public void setCommentdate(String commentdate) {
            this.commentdate = commentdate;
        }

        public String getCommentdatedisplayvalue() {
            return commentdatedisplayvalue;
        }

        public void setCommentdatedisplayvalue(String commentdatedisplayvalue) {
            this.commentdatedisplayvalue = commentdatedisplayvalue;
        }

        public String getCommentdatetimedisplayvalue() {
            return commentdatetimedisplayvalue;
        }

        public void setCommentdatetimedisplayvalue(String commentdatetimedisplayvalue) {
            this.commentdatetimedisplayvalue = commentdatetimedisplayvalue;
        }

        public String getSrijanadminfeedbackstatus() {
            return srijanadminfeedbackstatus;
        }

        public void setSrijanadminfeedbackstatus(String srijanadminfeedbackstatus) {
            this.srijanadminfeedbackstatus = srijanadminfeedbackstatus;
        }
    }

    public  class ChildCommentModel{

        String commentid;
        String parentcommentid;
        String commenttext;
        String userid;
        String username;
        String userprofileimage;

        public String getTaguser() {
            return taguser;
        }

        public void setTaguser(String taguser) {
            this.taguser = taguser;
        }

        String commentdate;
        String commentdatedisplayvalue;
        String CommentDateTimeDisplayValue;
        String srijanadminfeedbackstatus;
        String taguser;

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getParentcommentid() {
            return parentcommentid;
        }

        public void setParentcommentid(String parentcommentid) {
            this.parentcommentid = parentcommentid;
        }

        public String getCommenttext() {
            return commenttext;
        }

        public void setCommenttext(String commenttext) {
            this.commenttext = commenttext;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserprofileimage() {
            return userprofileimage;
        }

        public void setUserprofileimage(String userprofileimage) {
            this.userprofileimage = userprofileimage;
        }

        public String getCommentdate() {
            return commentdate;
        }

        public void setCommentdate(String commentdate) {
            this.commentdate = commentdate;
        }

        public String getCommentdatedisplayvalue() {
            return commentdatedisplayvalue;
        }

        public void setCommentdatedisplayvalue(String commentdatedisplayvalue) {
            this.commentdatedisplayvalue = commentdatedisplayvalue;
        }

        public String getCommentDateTimeDisplayValue() {
            return CommentDateTimeDisplayValue;
        }

        public void setCommentDateTimeDisplayValue(String commentDateTimeDisplayValue) {
            CommentDateTimeDisplayValue = commentDateTimeDisplayValue;
        }

        public String getSrijanadminfeedbackstatus() {
            return srijanadminfeedbackstatus;
        }

        public void setSrijanadminfeedbackstatus(String srijanadminfeedbackstatus) {
            this.srijanadminfeedbackstatus = srijanadminfeedbackstatus;
        }
    }
}


