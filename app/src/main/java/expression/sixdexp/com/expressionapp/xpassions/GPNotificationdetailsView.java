package expression.sixdexp.com.expressionapp.xpassions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapterTest;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPWallPostAdapter_details;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPSinglePostManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallPostManager;

/**
 * Created by Rahul Gupta on 08-05-2018.
 */

public class GPNotificationdetailsView extends Activity implements View.OnClickListener {

    RecyclerView postlist;
    String notificationmasterid;
    String gpostId,gpid,notifyID;
    String fromnotification;
    Context mContext;
    GPWallPostAdapter_details getMorePostAdapter;

    ProgressDialog progressDialog;
    LinearLayout cancelshare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailsview);
        mContext = this;
        Constant.singlePostList_gp.clear();
        gpid = getIntent().getStringExtra("gpID");
        notifyID=getIntent().getStringExtra("postId");
        notificationmasterid = getIntent().getStringExtra("notificationmasterid");
        gpostId = getIntent().getStringExtra("recoid");
        fromnotification = getIntent().getStringExtra("fromnotification");
        Log.i("postId", "" + gpid);
        initView();
    }


    public void initView() {

        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (LinearLayout) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (gpid != null && !gpid.equalsIgnoreCase("")
                && notificationmasterid != null && !notificationmasterid.equalsIgnoreCase("")
                && gpid != null && !gpid.equalsIgnoreCase("")) {


            Constant.singlePostList_gp = new GPWallPostManager(mContext).getPostByPostID(gpid);
            if (Constant.singlePostList_gp != null) {
                if (Constant.singlePostList_gp.size() > 0) {
                    setPost();
                    new GetReadNotification().execute();
                } else {
                    new GetPost().execute();
                }
            } else {
                new GetPost().execute();
            }

        } else {
            Toast.makeText(mContext, "PostID Should not be null", Toast.LENGTH_LONG).show();
        }


        postlist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (rv.getChildCount() > 0) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (rv.getChildPosition(childView) == 0) {
                        int action = e.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                rv.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    LinearLayoutManager layoutManager;
    /*public void setScroll(final int lay_height){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (postlist != null) {
                    postlist.scrollBy(0,postlist.getHeight());
                }
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //setPost();
    }

    public void setPost() {


        getMorePostAdapter = new GPWallPostAdapter_details(mContext, new GPWallPostAdapter_details.MorePostInterface() {
            @Override
            public void getMorePost() {


            }

            @Override
            public void commentAt(int commentat) {

            }

            @Override
            public void scrollList() {
                DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
                int height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;
                postlist.scrollBy(0,(int)(height/3.5));
            }
        }, notifyID);
        layoutManager = new LinearLayoutManager(mContext);
        postlist.setLayoutManager(layoutManager);
        postlist.setItemAnimator(new DefaultItemAnimator());
        postlist.setAdapter(getMorePostAdapter);

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancelshare:
                onBackPressed();
                break;
        }

    }

    public class GetPost extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

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
                responseString = new GPSinglePostManager(mContext).callGPWallPost(gpid,gpostId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //progressDialog.dismiss();
            if (responseString.equals("100")) {

                setPost();
                new GetReadNotification().execute();

            } else {

                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                // setPost();
            }

        }


    }


    @Override
    public void onBackPressed() {
        if (fromnotification != null) {
            finish();
            //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, GroupListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }


    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }

        }

        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(notifyID, notificationmasterid);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (responseString.equals("100")) {
                if (fromnotification != null) {
                    new GetMorePostRefresh().execute();
                } else {
                    progressDialog.dismiss();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }


    public class GetMorePostRefresh extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            //progressDialog.show();
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
            progressDialog.dismiss();
            if (responseString.equals("100")) {

            } else {

                // progressDialog.dismiss();
            }

        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

