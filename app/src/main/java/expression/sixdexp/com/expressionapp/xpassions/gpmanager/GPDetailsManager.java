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
import model.GroupInfoModel;
import model.GroupUser;

/**
 * Created by Praveen on 12-Feb-18.
 */

public class GPDetailsManager extends BaseManager {

    Context _mContext;
    String responseString = "";
    GroupInfoModel groupInfoModel=null;
    public List<GroupUser> groupUsers=null;
    public List<GroupUser> groupUsers_pending=null;

    public  GPDetailsManager(Context _mContext, GroupInfoModel groupInfoModel,List<GroupUser> groupUsers,List<GroupUser> groupUsers_pending){
        this._mContext = _mContext;
        this.groupInfoModel=groupInfoModel;
        this.groupUsers=groupUsers;
        this.groupUsers_pending=groupUsers_pending;
    }


    public String getGPDetails(String gid) {

        try {
            Log.i("responstring", "coi_getgroupdetailbygid serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getgroupdetailbygid");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("gid",gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getgroupdetailbygid");

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
                            groupUsers.clear();
                            groupUsers_pending.clear();
                            responseString=responseCode;
                            JSONArray gpingoarray=jsonObject.getJSONArray("responseData");
                            if(gpingoarray.length()>0) {
                                JSONObject goinfobj = gpingoarray.getJSONObject(0);
                                groupInfoModel.setStatus(goinfobj.getString("status"));
                                groupInfoModel.setDiffdays(goinfobj.getString("diffdays"));
                                groupInfoModel.setIsadmin(goinfobj.getString("isadmin"));
                                groupInfoModel.setPrivategsatus(goinfobj.getString("privategsatus"));
                                groupInfoModel.setIsnoatmember(goinfobj.getString("isnoatmember"));
                                groupInfoModel.setCoi_gid(goinfobj.getString("coi_gid"));
                                groupInfoModel.setCoi_gdescription(goinfobj.getString("coi_gdescription"));
                                groupInfoModel.setCoi_gicon(goinfobj.getString("coi_gicon"));
                                groupInfoModel.setCoi_gtitle(goinfobj.getString("coi_gtitle"));
                                groupInfoModel.setCoi_gispublic(goinfobj.getString("coi_gispublic"));
                                groupInfoModel.setThemeid(goinfobj.getString("themeid"));
                                groupInfoModel.setNoofview(goinfobj.getString("noofview"));







                                JSONArray gpmemper = goinfobj.getJSONArray("memberdetails");




                                if(GroupListAdapter.groupObject!=null) {
                                    GroupListAdapter.groupObject.setCoi_gtitle(goinfobj.getString("coi_gtitle"));
                                    GroupListAdapter.groupObject.setCoi_gdescription(goinfobj.getString("coi_gdescription"));
                                    GroupListAdapter.groupObject.setCoi_gicon(goinfobj.getString("coi_gicon"));
                                    GroupListAdapter.groupObject.setNoofmember(""+gpmemper.length());
                                }




                                for(int k=0;k<gpmemper.length();k++){
                                    GroupUser groupUser=new GroupUser();
                                    JSONObject memberObj=gpmemper.getJSONObject(k);

                                    groupUser.setCoi_gid(memberObj.getString("coiGid"));
                                    groupUser.setCoi_gmisadmin(memberObj.getString("coiMisAdmin"));
                                    groupUser.setCoi_gmuserid(memberObj.getString("userid"));
                                    groupUser.setCoi_gmname(memberObj.getString("shortname"));
                                    groupUser.setCoi_gmdepartment(memberObj.getString("department"));
                                    groupUser.setCoi_gmprofilepic(memberObj.getString("imageurl"));
                                    groupUser.setCoi_gmaddeddate(memberObj.getString("addeddate"));
                                    groupUser.setMxway(memberObj.getString("mxway"));
                                    groupUsers.add(groupUser);
                                }


                                JSONArray gpPendingmemper = goinfobj.getJSONArray("lpendingList");

                                for(int k=0;k<gpPendingmemper.length();k++){
                                    GroupUser groupUser=new GroupUser();
                                    JSONObject memberObj=gpPendingmemper.getJSONObject(k);

                                    groupUser.setCoi_gid(memberObj.getString("coiGid"));
                                    groupUser.setCoi_gmisadmin(memberObj.getString("coiMisAdmin"));
                                    groupUser.setCoi_gmuserid(memberObj.getString("userid"));
                                    groupUser.setCoi_gmname(memberObj.getString("shortname"));
                                    groupUser.setCoi_gmdepartment(memberObj.getString("department"));
                                    groupUser.setCoi_gmprofilepic(memberObj.getString("imageurl"));
                                    groupUser.setCoi_gmaddeddate(memberObj.getString("addeddate"));

                                    groupUsers_pending.add(groupUser);
                                }

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
