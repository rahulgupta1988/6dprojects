package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.xpassions.GPInfoActivity;
import model.GroupUser;

/**
 * Created by Praveen on 08-Jan-18.
 */

public class GPUsersManager extends BaseManager {

    Context _mContext;
    String responseString = "";
    public List<GroupUser> groupUsers=new ArrayList<>();
    public List<GroupUser> groupUsers_pending=new ArrayList<>();


    public  GPUsersManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String getGroupMembers(String groupid) {

        try {
            Log.i("responstring", "get group members serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getmemberbygroupid");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("gid",groupid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getmemberbygroupid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseData(responseString);
    }

    public String parseData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;

                            JSONArray gp_array=jsonObject.getJSONArray("responseData");
                            JSONObject gp_user_onj=gp_array.getJSONObject(0);
                            JSONArray gp_user_array=gp_user_onj.getJSONArray("listgroupMemberDetail");


                             if(GPInfoActivity.isfromGpinfo){
                                 GroupListAdapter.groupObject.setCoi_gispublic(gp_user_onj.getString("coi_isprivate"));
                                 GroupListAdapter.groupObject.setCoi_gtitle(gp_user_onj.getString("coi_gtitle"));
                                 GroupListAdapter.groupObject.setCoi_gdescription(gp_user_onj.getString("coi_gdescription"));
                                 GroupListAdapter.groupObject.setCoi_gicon(gp_user_onj.getString("coi_gicon"));
                                 GroupListAdapter.groupObject.setNoofmember(gp_user_onj.getString("noofmember"));
                             }


                            for(int l=0;l<gp_user_array.length();l++){

                                GroupUser groupUser=new GroupUser();
                                JSONObject user_obj=gp_user_array.getJSONObject(l);
                                groupUser.setCoi_gid(user_obj.getString("coi_gid"));
                                groupUser.setCoi_gmname(user_obj.getString("coi_gmname"));
                                groupUser.setCoi_gmdepartment(user_obj.getString("coi_gmdepartment"));
                                groupUser.setCoi_gmprofilepic(user_obj.getString("coi_gmprofilepic"));
                                groupUser.setCoi_gmuserid(user_obj.getString("coi_gmuserid"));
                                groupUser.setCoi_gmisadmin(user_obj.getString("coi_gmisadmin"));
                                groupUser.setCoi_gmaddeddate(user_obj.getString("coi_gmaddeddate"));
                                groupUsers.add(groupUser);
                            }



                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }


    public String getGroupPendingMembers(String groupid) {

        try {
            Log.i("responstring", "get pendingrequest serviceUrl-->" + Constant.BASEURL+"api/coi/coi_pendingrequestview");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("groupid",groupid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_pendingrequestview");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsePendingData(responseString);
    }

    public String parsePendingData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;

                            JSONArray gp_array=jsonObject.getJSONArray("responseData");
                            for(int l=0;l<gp_array.length();l++){
                                GroupUser groupUser=new GroupUser();
                                JSONObject user_obj=gp_array.getJSONObject(l);
                                groupUser.setCoi_gid(user_obj.getString("coi_gid"));
                                groupUser.setCoi_gmname(user_obj.getString("coi_gmname"));
                                groupUser.setCoi_gmdepartment(user_obj.getString("coi_gmdepartment"));
                                groupUser.setCoi_gmprofilepic(user_obj.getString("coi_gmprofilepic"));
                                groupUser.setCoi_gmuserid(user_obj.getString("coi_gmuserid"));
                                groupUser.setCoi_gmisadmin(user_obj.getString("coi_gmisadmin"));
                                groupUser.setCoi_gmaddeddate(user_obj.getString("coi_gmaddeddate"));
                                groupUsers_pending.add(groupUser);

                            }



                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
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
