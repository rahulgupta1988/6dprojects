package expression.sixdexp.com.expressionapp.utility;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import db.AllUsers;
import db.AllUsersDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.manager.ServerCall;

/**
 * Created by Praveen on 8/23/2016.
 */
public class TestIntentService extends IntentService {

    public TestIntentService() {
        super(TestIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        long totalusers = intent.getLongExtra("totalusers", 0);

        HashMap<String, String> entitymap = new HashMap<String, String>();
        /*Log.i("total users", "" + Constant.allUserses.size());

        entitymap.put("maxlength", String.valueOf(Constant.allUserses.size()));*/
        Log.i("total users 12434", "" +String.valueOf(totalusers));
        entitymap.put("maxlength",String.valueOf(totalusers));
        String responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/getusersbylastuserid");
        Log.i("Intent service result", "" + responseString.toString());
        parseUsersData(responseString);

    }


    public void parseUsersData(String jsonResponse) {
        //Constant.allUserses.clear();
        String responseCode = "";
        DaoSession daoSession;
        AllUsersDao allUsersDao=null;
        try {
             daoSession = Constant.daoSession;
             allUsersDao = daoSession.getAllUsersDao();
            if (jsonResponse != null && !jsonResponse.equals(null)) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            //responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");
                            Type listType = new TypeToken<ArrayList<AllUsers>>() {
                            }.getType();
                            Iterable<AllUsers> iterable = new GsonBuilder().create().fromJson(jsonarray.toString(), listType);
                            //Constant.allUserses = new GsonBuilder().create().fromJson(jsonarray.toString(),listType);
                            /*for (AllUsers user : allUserses) {
                                Constant.allUserses.add(user);
                            }
*/

                            allUsersDao.insertInTx(iterable);

                            /*for (AllUsers user : allUserses) {

                                allUsersDao.insertOrReplace(allUserses);

                            }*/


                        } else {
                            String responseString = jsonObject.getString("responseData");
                            Log.i("responseString", "" + responseString);

                        }
                    } else {
                        String responseString = "Received data is not compatible.";
                        Log.i("responseString", "" + responseString);
                    }
                } else {
                    String responseString = "Received data is not compatible.";
                    Log.i("responseString", "" + responseString);
                }
            } else {
                String responseString = "Please check your internet connection.";
                Log.i("responseString", "" + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            allUsersDao.getDatabase().close();
        }
        // return responseString;
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
