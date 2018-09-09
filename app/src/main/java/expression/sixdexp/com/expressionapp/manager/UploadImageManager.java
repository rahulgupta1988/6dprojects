package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.GetMorePost;
import db.GetMorePostDao;
import db.UserLoginInfo;
import db.UserLoginInfoDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;


/**
 * Created by Praveen on 5/17/2016.
 */
public class UploadImageManager extends BaseManager {
    Context context;
    String responseString = "";

    public UploadImageManager(Context context) {
        this.context = context;
    }


    public String uploadProfilePicture(String base64_strig, String filename, String file_extension, String content_type) {
        String token;

        Log.i("base64_string", base64_strig);
        HashMap<String, String> entitymap = new HashMap<String, String>();
        String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
        entitymap.put("userid", userid);
        entitymap.put("base64_string", base64_strig);
        entitymap.put("filename", filename);
        entitymap.put("file_extension", file_extension);
        entitymap.put("content_type", content_type);
        responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/changedp");
        Log.i("userid", userid);
        Log.i("filename", filename);
        Log.i("file_extension", file_extension);
        Log.i("content_type", content_type);


        Log.i("responstring", "param – api/userapi/changedp serviceUrl-->" + Constant.BASEURL + "?api/userapi/changedp" + userid +  "filename" + filename + "file_extension" + file_extension);
        //Log.i("profile_image", "" +image_data);

        if (responseString != null)
            Log.i("responseString", "responseString=" + responseString);

        return parsesenduploadProfilePicture(responseString);
    }

    public String parsesenduploadProfilePicture(String jsonResponse) {
        String responseCode = "";
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = jsonResponse;
                            JSONObject jsonresponceOBJ = jsonObject.getJSONObject("responseData");
                            String profile_picture = jsonresponceOBJ.getString("imageurl");
                            Log.i("profile_picture test113", "" + profile_picture);
                            updateProfilePictureURL(profile_picture);
                            updatePost(profile_picture);

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


    public void updateProfilePictureURL(String profilepicture) {
        DaoSession daoSession=getDBSessoin(context);
        UserLoginInfoDao userDao=daoSession.getUserLoginInfoDao();

        try {
            List<UserLoginInfo> userDatas = null;
            userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(context).getUserInfo();
            String user_id = userDatas.get(0).getUserid();

            List<UserLoginInfo> users = new ArrayList<UserLoginInfo>();
            users = userDao.queryBuilder().where(UserLoginInfoDao.Properties.Userid.eq(user_id)).list();

            if (users != null) {
                for (UserLoginInfo p : users) {
                    p.setImageurl(profilepicture);
                    userDao.update(p);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }



    public void updatePost(String profilepicture) {
        DaoSession daoSession=getDBSessoin(context);
        GetMorePostDao getMorePostDao=daoSession.getGetMorePostDao();
        try {
            List<GetMorePost> getMorePosts = new ArrayList<GetMorePost>();
            getMorePosts = getMorePostDao.queryBuilder().where(GetMorePostDao.Properties.Userid.eq(
                    SharedPrefrenceManager.getUserID(context))).list();

            if (getMorePosts != null) {
                for (GetMorePost post : getMorePosts) {

                    post.setNominator_imageurl(profilepicture);
                    getMorePostDao.update(post);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}