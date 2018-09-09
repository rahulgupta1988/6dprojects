package com.expression.ui;

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
import com.expression.ui.flipanimation.TabFragmentFirstAapterPage;

import java.util.List;

import db.DaoSession;
import db.GetMorePost;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostByDate;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LogOutManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import utils.AppData;

public class TabFragment1 extends Fragment
{
    private FlipViewController flipView;
    Context mContext;
    List<GetMorePost> getMorePosts;
    GetMorePostAdapter getMorePostAdapter;
    static int val=1;
    String postdate="";
    ProgressDialog progressDialog;

    public TabFragment1(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        getMorePosts = new GetMorePostManager(mContext).getMorePosts();

        flipView = new FlipViewController(getActivity());
        flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
        flipView.setAdapter(new TabFragmentFirstAapterPage(getActivity(), flipView,TabFragment1.this));
        flipView.setOverFlipEnabled(false);
      /*  String store_val= AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Refresh_Home);
        Toast.makeText(getActivity(),"val_check="+store_val,Toast.LENGTH_LONG).show();*/

        //new GetMorePostTask().execute();
        /*Constant.lastposition_home=0;*/
        Constant.lastposition_home=0;



        flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {


            @Override
            public void onViewFlipped(View view, int position)
            {

                Log.i("pos1132",""+position);
                Log.i("pos2324",""+(getMorePosts.size()-1));

                //Toast.makeText(mContext,""+position+"="+(getMorePosts.size()-1),Toast.LENGTH_SHORT).show();
                Constant.lastposition_home=position;

               //Toast.makeText(getActivity(), "position="+position, Toast.LENGTH_SHORT).show();
                if (position==0)
                {
                    String store_val= AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Refresh_Home,mContext);
                    //Toast.makeText(getActivity(),"val_check="+store_val,Toast.LENGTH_LONG).show();
                   if (store_val.equalsIgnoreCase("1"))
                   {
                      // Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();


                       if(getMorePosts.size()==1){
                           //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                           Constant.lastpostofPOST =position;
                           postdate=getMorePosts.get(Constant.lastpostofPOST).getDate();
                           new GetMorePostTaskByDate().execute();
                       }
                       else{
                           new GetMorePostTask().execute();
                       }


                   }
                   else if (store_val.equalsIgnoreCase("2"))
                   {
                       String val = "1";
                       AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_Home, val,mContext);
                   }



                }

                else if (position==1)
                {
                    String val = "2";
                    AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_Home, val,mContext);

                    if(position==((getMorePosts.size())-1)){
                        //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                        Constant.lastpostofPOST =position;
                        postdate=getMorePosts.get(Constant.lastpostofPOST).getDate();
                        new GetMorePostTaskByDate().execute();
                    }

                }

                else if(position==((getMorePosts.size())-1)){
                   //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                    Constant.lastpostofPOST =position;
                    postdate=getMorePosts.get(Constant.lastpostofPOST).getDate();
                    new GetMorePostTaskByDate().execute();
                }

            }


        });


        return flipView;
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
                    getMorePosts = new GetMorePostManager(mContext).getMorePosts();
                    //Toast.makeText(getActivity(), "Refresh successfully!", Toast.LENGTH_SHORT).show();
                    flipView.setAdapter(new TabFragmentFirstAapterPage(getActivity(), flipView,TabFragment1.this));
                }
                else {

                    progressDialog.dismiss();
                    if(Constant.web_app_version.equals("300")){
                        Toast.makeText(mContext,"New Android version is now available.\n" +
                                "Please update your app through Google Play Store.", Toast.LENGTH_LONG).show();
                        new TaskLogout().execute();
                    }
                    else {
                        Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    }



                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public class GetMorePostTaskByDate extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostByDate(mContext,postdate).callGetMorePost();
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
            if (responseString.equals("100"))
            {
                //listener.getMorePost();
                //Toast.makeText(mContext, "Refresh successfully!", Toast.LENGTH_SHORT).show();
                int tem_size=(getMorePosts.size());
                getMorePosts = new GetMorePostManager(mContext).getMorePosts();
                //Toast.makeText(mContext, "GetHomePostSize=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();
                flipView.setAdapter(new TabFragmentFirstAapterPage(mContext,flipView,TabFragment1.this));
                flipView.setSelection(tem_size-1);
                /*if(tem_size<getMorePosts.size()){
                    flipView.setSelection(tem_size-1);
                }
                else{
                    flipView.setSelection(tem_size-1);
                }*/

            } else {
                progressDialog.dismiss();
                if(Constant.web_app_version.equals("300")){
                    Toast.makeText(mContext,"New Android version is now available.\n" +
                            "Please update your app through Google Play Store.", Toast.LENGTH_LONG).show();
                    new TaskLogout().execute();
                }
                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }


            }

        }


    }


    public class TaskLogout extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        ProgressDialog progressDialog=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
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
            try
            {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    daoSession.getUserLoginInfoDao().deleteAll();
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(loginIntent);
                    ((Activity)mContext).finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                }
                else
                {
                    Toast.makeText(mContext,""+responseString,Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }




    @Override
    public void onResume() {
        super.onResume();
        try {
            TabFragmentFirstAapterPage tabFragmentFirstAapterPage=  new TabFragmentFirstAapterPage(getActivity(),flipView,TabFragment1.this);
            flipView.setAdapter(tabFragmentFirstAapterPage);
            int count=tabFragmentFirstAapterPage.getCount();
            if(count>0){
                if(count>=Constant.lastposition_home){
                    flipView.setSelection(Constant.lastposition_home);
                }
                else{
                    flipView.setSelection(0);
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void topTest(){
        flipView.setSelection(0);
    }


      public void setCountAfterRefersh(){
          getMorePosts = new GetMorePostManager(mContext).getMorePosts();
      }
}