package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.AllUsers;
import db.AllUsersDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/13/2016.
 */
public class UsersManager extends BaseManager {

    Context context;
    String responseString = "";

    public UsersManager(){

    }
    public UsersManager(Context context) {
        this.context = context;

    }

    public String callAllUser() {
        try {

            DaoSession daoSession = getDBSessoin(context);
            AllUsersDao allUsersDao = daoSession.getAllUsersDao();

            long totalusers = allUsersDao.count();
            Log.i("totalusers", "" + totalusers);
            HashMap<String, String> entitymap = new HashMap<String, String>();

            entitymap.put("maxlength", String.valueOf(totalusers));

            Log.i("responstring111", "getusersbylastuserid serviceUrl-->" + Constant.BASEURL + "api/userApi/getusersbylastuserid"
                    + "&maxlength=" + totalusers);


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/getusersbylastuserid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseUsersData(responseString);
    }


    public String parseUsersData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        //daoSession.getAllUsersDao().deleteAll();
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");
                            Log.i("responseStringlength", "responseString=" + jsonarray.length());
                            Type listType = new TypeToken<ArrayList<AllUsers>>()
                            {
                            }.getType();
                            List<AllUsers> allUserses = new GsonBuilder().create().fromJson(jsonarray.toString(), listType);
                            for (AllUsers user : allUserses) {

                                allUsersDao.insertOrReplace(user);
                            }

                            /*

                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                                String name = jsonObject1.optString("name");
                                String department = jsonObject1.optString("department");
                                String designation = jsonObject1.optString("designation");
                                String user_id = jsonObject1.optString("user_id");
                                String email = jsonObject1.optString("email");

                                AllUsers allUsers = new AllUsers(name, department, designation, user_id, email);
                                allUsersDao.insertOrReplace(allUsers);

                            }*/

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


    public List<AllUsers> getAllUserList() {
        DaoSession daoSession = getDBSessoin(context);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        try {
            List<AllUsers> allUserses = new ArrayList<AllUsers>();
            allUserses = allUsersDao.loadAll();
            return allUserses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AllUsers> getUserListByName(String name) {


       /* List<AllUsers> allUserses = new ArrayList<AllUsers>();

        for(AllUsers users:Constant.allUserses){

           if(users.getName().equals(name)) {
               allUserses.add(users);
           }
        }

        return allUserses;*/
        DaoSession daoSession = getDBSessoin(context);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        try {
            List<AllUsers> allUserses = new ArrayList<AllUsers>();
            allUserses = allUsersDao.queryBuilder().where(AllUsersDao.Properties.Name.eq(name)).list();
            return allUserses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<AllUsers> getUserListByEmial(String emialID) {

       /* List<AllUsers> allUserses = new ArrayList<AllUsers>();

        for(AllUsers users:Constant.allUserses){

            if(users.getEmail().equals(emialID)) {
                allUserses.add(users);
            }
        }

        return allUserses;
*/
       DaoSession daoSession = getDBSessoin(context);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        try {
            List<AllUsers> allUserses = new ArrayList<AllUsers>();
            allUserses = allUsersDao.queryBuilder().where(AllUsersDao.Properties.Email.eq(emialID)).list();
            return allUserses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getdepartments(){
        DaoSession daoSession = getDBSessoin(context);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        List<String> departments;
        try {
            String SQL_DISTINCT_DEPARTMENT = "SELECT DISTINCT DEPARTMENT FROM "+AllUsersDao.TABLENAME;
            Log.i("Query233",""+SQL_DISTINCT_DEPARTMENT);
            departments= new ArrayList<String>();
            Cursor c = allUsersDao.getDatabase().rawQuery(SQL_DISTINCT_DEPARTMENT, null);

            Log.i("cursor1987",""+c.toString());
            if (c.moveToFirst()) {
                do {
                    departments.add(c.getString(0));
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            departments=null;

        }
        return departments;
    }

    public List<String> getlocation(){
        DaoSession daoSession = getDBSessoin(context);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        List<String> locatio;
        try {
            String SQL_DISTINCT_LOCATION = "SELECT DISTINCT LOCATION FROM "+AllUsersDao.TABLENAME;
            locatio= new ArrayList<String>();
            Cursor c = allUsersDao.getDatabase().rawQuery(SQL_DISTINCT_LOCATION, null);

            if (c.moveToFirst()) {
                do {
                    locatio.add(c.getString(0));
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            locatio=null;

        }
        return locatio;
    }
}
