package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.manager.AwardManager;
import expression.sixdexp.com.expressionapp.manager.ExpressorManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.manager.VisitorCounter;

/**
 * Created by Praveen on 7/25/2016.
 */
public class InitCall {

    Context mContext;
    ProgressDialog progressDialog;

    public InitCall(Context context, ProgressDialog mProgressDialog) {
        mContext = context;
        new GetMorePostTask().execute();
    }

    public class GetMorePostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).callGetMorePost();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                if (responseString.equals("100")) {
                    //((HostActivty) mContext).initview();
                    // new XpresswaytTask().execute();
                    new AwardsTask().execute();
                } else {
                    progressDialog.dismiss();

                        Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();


                    //finishActivity();

                }
            }
            catch (Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }



        }


    }

    public class AwardsTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new AwardManager(mContext).callAward();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (responseString.equals("100")) {
                   // new RecentActivityTask().execute();
                    new GetExpressor().execute();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {progressDialog.dismiss();
                e.printStackTrace();
            }


        }


    }

    public class RecentActivityTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new RecentActivityManager(mContext).callRecentActivity();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                if (responseString.equals("100")) {
                    new GetExpressor().execute();

                } else {
                     progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {progressDialog.dismiss();
                e.printStackTrace();
            }

        }


    }


    public class GetExpressor extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new ExpressorManager(mContext).callExpressor();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {

                if (responseString.equals("100")) {
                    new GetVisitorCounter().execute();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {progressDialog.dismiss();
                e.printStackTrace();
            }

        }
    }




    public class GetVisitorCounter extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString =   new VisitorCounter().callVisitorCounter();
                //insetUserdata();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                if (responseString.equals("100"))
                {
                    String vistor_counter=VisitorCounter.vistor_counter;
                    String web_user_count=VisitorCounter.web_user_count;
                    Log.i("counter", "" + vistor_counter);
                   // AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Visitors_Counter, vistor_counter);
                    SharedPrefrenceManager.putVisitorCount(mContext,vistor_counter);
                    SharedPrefrenceManager.putWebVisitorCount(mContext,web_user_count);

                    new GetExpresswayPostTask().execute();

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }


    public class GetExpresswayPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetXpresswayPost();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                if (responseString.equals("100"))
                {
                    new GetBulletenCategory().execute();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            }
            catch(Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }



    public class GetBulletenCategory extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetBulletinCategory();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    Toast.makeText(mContext, "You have logged successfully!", Toast.LENGTH_SHORT).show();
                    Intent home_intent = new Intent(mContext, FlipComplexLayoutActivity.class);
                    mContext.startActivity(home_intent);
                    ((Activity) mContext).finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }




    public class GetAllUsersTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new UsersManager(mContext).callAllUser();
                //insetUserdata();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    Intent home_intent = new Intent(mContext, FlipComplexLayoutActivity.class);
                    mContext.startActivity(home_intent);
                    ((Activity) mContext).finish();
                }
                else{
                    Intent home_intent = new Intent(mContext, FlipComplexLayoutActivity.class);
                    mContext.startActivity(home_intent);
                    ((Activity) mContext).finish();
                }
            }
            catch (Exception e)
            {

            }


        }


    }




    public void finishActivity(){
        SharedPrefrenceManager.putUserID(mContext, null);
        Intent login_intent = new Intent(mContext, LoginActivity.class);
         mContext.startActivity(login_intent);
        ((Activity)mContext).finish();
    }


  /*  public void insetUserdata(){
        DaoSession daoSession = BaseManager.getDBSessoin(mContext);
        AllUsersDao allUsersDao = daoSession.getAllUsersDao();
        for (AllUsers user : Constant.allUserses) {
            allUsersDao.insertOrReplaceInTx(user);
        }

        allUsersDao.getDatabase().close();
    }*/
}
