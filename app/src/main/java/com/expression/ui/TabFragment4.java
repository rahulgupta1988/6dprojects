package com.expression.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.expression.ui.flipanimation.TabFragmentFourthTest;

import java.util.List;

import db.GetXpresswayPost;
import expression.sixdexp.com.expressionapp.manager.GetMorePostByDate;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import utils.AppData;

public class TabFragment4 extends Fragment
{
    private FlipViewController flipView;
    Context mContext;
    List<GetXpresswayPost> getXpresswayPosts;
    ProgressDialog progressDialog = null;
    String postdate="";
    public TabFragment4(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /*mContext = getActivity();
        getXpresswayPosts = new GetMorePostManager(mContext).getXpresswayPosts();
        Toast.makeText(getActivity(), "GetXpresswayPostSize=" + getXpresswayPosts.size(), Toast.LENGTH_SHORT).show();
        flipView = new FlipViewController(getActivity());
        flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
//        flipView.setAdapter(new HomePageFlipAdapter(getActivity()));HomeFlipAdapterNew
        //flipView.setAdapter(new HomeFlipAdapterNew(getActivity()));
        flipView.setAdapter(new TabFragmentFourthAapterPage(getActivity()));
        return flipView;*/

        mContext = getActivity();
        getXpresswayPosts = new GetMorePostManager(mContext).getXpresswayPosts();
       // Toast.makeText(getActivity(), "GetXpresswayPostSize=" + getXpresswayPosts.size(), Toast.LENGTH_SHORT).show();
        flipView = new FlipViewController(getActivity());
        flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
        flipView.setAdapter(new TabFragmentFourthTest(getActivity(), flipView,TabFragment4.this));
        flipView.setOverFlipEnabled(false);
       // new GetExpresswayPostTask().execute();
        Constant.lastposition=0;
       /* Constant.lastposition=0;*/

        flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
            @Override
            public void onViewFlipped(View view, int position) {
                // Toast.makeText(getActivity(), "position="+position, Toast.LENGTH_SHORT).show();
                Constant.lastposition=position;
                if (position == 0)
                {
                    String store_val = AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Refresh_ExpresssWay,mContext);
                    //Toast.makeText(getActivity(),"val_check="+store_val,Toast.LENGTH_LONG).show();
                    if (store_val.equalsIgnoreCase("1"))
                    {
                        // Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                        new GetExpresswayPostTask().execute();
                    } else if (store_val.equalsIgnoreCase("2"))
                    {
                        String val = "1";
                        AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_ExpresssWay, val,mContext);
                    }
                }

                else if (position == 1) {
                    String val = "2";
                    AppData.getSharedAppData().saveDataInPreference(Constant.SHARED_PREFERENCE_Refresh_ExpresssWay, val,mContext);

                     /* if(position==((getXpresswayPosts.size())-1)){
                    //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                    Constant.lastpostofPOST = getXpresswayPosts.size() - 1;
                    postdate=getXpresswayPosts.get(Constant.lastpostofPOST).getDate();
                    new GetExpresswayPostTaskByDate().execute();
                }*/
                }

               /* else if(position==((getXpresswayPosts.size())-1)){
                    //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
                    Constant.lastpostofPOST = getXpresswayPosts.size() - 1;
                    postdate=getXpresswayPosts.get(Constant.lastpostofPOST).getDate();
                    new GetExpresswayPostTaskByDate().execute();
                }*/

            }
        });


        return flipView;
    }

    public class GetExpresswayPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetXpresswayPost();
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
                    //Toast.makeText(getActivity(), "Refresh successfully!", Toast.LENGTH_SHORT).show();
                    getXpresswayPosts = new GetMorePostManager(mContext).getXpresswayPosts();
                    flipView.setAdapter(new TabFragmentFourthTest(getActivity(), flipView,TabFragment4.this));

                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public class GetExpresswayPostTaskByDate extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostByDate(mContext,postdate).callGetMoreXPPost();
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
                    //Toast.makeText(getActivity(), "Refresh successfully!", Toast.LENGTH_SHORT).show();
                    int tem_size=(getXpresswayPosts.size());
                    getXpresswayPosts = new GetMorePostManager(mContext).getXpresswayPosts();
                    flipView.setAdapter(new TabFragmentFourthTest(getActivity(), flipView,TabFragment4.this));
                    flipView.setSelection(tem_size-1);

                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        TabFragmentFourthTest tabFragmentFourthTest=new TabFragmentFourthTest(getActivity(), flipView,TabFragment4.this);
        flipView.setAdapter(tabFragmentFourthTest);
        int count=tabFragmentFourthTest.getCount();


        if(count>0){
            if(count>=Constant.lastposition){
                flipView.setSelection(Constant.lastposition);
            }
            else{
                flipView.setSelection(0);
            }

        }
    }

    public void topTest(){
        flipView.setSelection(0);
    }
}