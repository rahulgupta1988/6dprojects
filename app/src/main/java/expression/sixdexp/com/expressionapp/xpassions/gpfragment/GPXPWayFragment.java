package expression.sixdexp.com.expressionapp.xpassions.gpfragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;

import java.util.List;

import db.DaoSession;
import db.UserLoginInfo;
import db.XPGPWALLPOST;
import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LogOutManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.XPWallAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.XPGPWallPostManager;
import utils.AppData;

/**
 * Created by Praveen on 05-Jan-18.
 */

public class GPXPWayFragment extends Fragment {
    private FlipViewController flipView;
    View view;
    Context mContext;
    List<XPGPWALLPOST> getWallPosts;
    static int val = 1;
    String postdate = "";
    ProgressDialog progressDialog;
    String gpID;

    public GPXPWayFragment() {
    }


    public void initView(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        getWallPosts = new XPGPWallPostManager(mContext).getxpWallPosts();


     if(getWallPosts.size()==0){
            view= inflater.inflate(R.layout.gpxpadapterview, container, false);
            return view;
        }

        else{


        if(GroupListAdapter.groupObject!=null){
            gpID=GroupListAdapter.groupObject.getCoi_gid();
        }
        flipView = new FlipViewController(getActivity());
        flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
        flipView.setAdapter(new XPWallAdapter(getActivity(), flipView, GPXPWayFragment.this));
        flipView.setOverFlipEnabled(false);

         Constant.lastposition_home_wall=0;



        flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {


            @Override
            public void onViewFlipped(View view, int position) {

                Log.i("pos1132", "" + position);
                Log.i("pos2324", "" + (getWallPosts.size() - 1));

                //Toast.makeText(mContext,""+position+"="+(getMorePosts.size()-1),Toast.LENGTH_SHORT).show();
                Constant.lastposition_home_wall = position;

                //Toast.makeText(getActivity(), "position="+position, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    String store_val = AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Refresh_Home, mContext);
                    //Toast.makeText(getActivity(),"val_check="+store_val,Toast.LENGTH_LONG).show();
                    if (store_val.equalsIgnoreCase("1")) {
                        // Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();


                        if (getWallPosts.size() == 1) {
                            //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                            Constant.lastpostofPOST = position;
                            postdate = getWallPosts.get(Constant.lastpostofPOST).getEvent_date();
                            new GPXPWayFragment.GetMoreGPPost().execute();
                        } else {
                            new GPXPWayFragment.GetWallPostTask().execute();
                        }


                    } else if (store_val.equalsIgnoreCase("2")) {
                        String val = "1";
                        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_Home, val, mContext);
                    }


                } else if (position == 1) {
                    String val = "2";
                    AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_Home, val, mContext);

                    if (position == ((getWallPosts.size()) - 1)) {
                        //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                        Constant.lastpostofPOST = position;
                        postdate = getWallPosts.get(Constant.lastpostofPOST).getEvent_date();
                        new GPXPWayFragment.GetMoreGPPost().execute();
                    }

                } else if (position == ((getWallPosts.size()) - 1)) {
                    //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                    Constant.lastpostofPOST = position;
                    postdate = getWallPosts.get(Constant.lastpostofPOST).getEvent_date();
                    new GPXPWayFragment.GetMoreGPPost().execute();
                }

            }


        });


        return flipView;
      }
    }




    public class GetMoreGPPost extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

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
                responseString = new XPGPWallPostManager(mContext).callxpGPMoreWallPost(gpID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                //listener.getMorePost();
                //Toast.makeText(mContext, "Refresh successfully!", Toast.LENGTH_SHORT).show();
                int tem_size = (getWallPosts.size());
                getWallPosts = new XPGPWallPostManager(mContext).getxpWallPosts();
                //Toast.makeText(mContext, "GetHomePostSize=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();
                flipView.setAdapter(new XPWallAdapter(mContext, flipView, GPXPWayFragment.this));
                flipView.setSelection(tem_size - 1);
                /*if(tem_size<getMorePosts.size()){
                    flipView.setSelection(tem_size-1);
                }
                else{
                    flipView.setSelection(tem_size-1);
                }*/

            } else {
                progressDialog.dismiss();
                if (Constant.web_app_version.equals("300")) {
                    Toast.makeText(mContext, "New Android version is now available.\n" +
                            "Please update your app through Google Play Store.", Toast.LENGTH_LONG).show();
                    new GPXPWayFragment.TaskLogout().execute();
                } else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }


            }

        }


    }





    public class GetWallPostTask extends AsyncTask<String, Void, Void> {

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
                responseString = new XPGPWallPostManager(mContext).callxpGPWallPost(gpID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            try {
                progressDialog.dismiss();
                if (responseString.equals("100")) {

                } else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();



                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class TaskLogout extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        ProgressDialog progressDialog = null;

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
                UserLoginInfo userLoginInfo = new LoginManager(mContext).getUserInfo().get(0);
                String user_id = userLoginInfo.getUserid();
                responseString = new LogOutManager(mContext).logoutUser(user_id);
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
                if (responseString.equals("100")) {
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    daoSession.getUserLoginInfoDao().deleteAll();
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(loginIntent);
                    ((Activity) mContext).finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                } else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if(flipView!=null) {
                XPWallAdapter XPWallAdapter = new XPWallAdapter(getActivity(), flipView, GPXPWayFragment.this);
                flipView.setAdapter(XPWallAdapter);
                int count = XPWallAdapter.getCount();
                if (count > 0) {
                    if (count >= Constant.lastposition_home_wall) {
                        flipView.setSelection(Constant.lastposition_home_wall);
                    } else {
                        flipView.setSelection(0);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void topTest() {
        flipView.setSelection(0);
    }


    public void setCountAfterRefersh() {
        getWallPosts = new XPGPWallPostManager(mContext).getxpWallPosts();
    }


}