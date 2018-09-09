package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapterTest;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;

/**
 * Created by Praveen on 29-Sep-17.
 */

public class NotificationPostDeital_new extends Activity implements View.OnClickListener {


    RecyclerView postlist;
    Context mContext;
    GetMorePostAdapterTest getMorePostAdapter;
    String postId;
    String notificationmasterid;
    String recoid;
    String fromnotification;
    ProgressDialog progressDialog;
    LinearLayout cancelshare;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailsview);
        mContext = this;
        Constant.singlePostList.clear();
        postId =getIntent().getStringExtra("postId");
        notificationmasterid=getIntent().getStringExtra("notificationmasterid");
        recoid=getIntent().getStringExtra("recoid");
        fromnotification=getIntent().getStringExtra("fromnotification");
        initView();

    }

    public void initView() {
        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (LinearLayout) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (postId != null && !postId.equalsIgnoreCase("")) {


            Constant.singlePostList=new GetMorePostManager(mContext).getPostsByPostID(recoid);
            if(Constant.singlePostList!=null){
                if(Constant.singlePostList.size()>0){
                    setPost();
                    new GetReadNotification().execute();
                }

                else{
                    new GetPost().execute();
                }
            }

            else{
                new GetPost().execute();
            }

        } else {
            Toast.makeText(mContext, "PostID Should not be null", Toast.LENGTH_LONG).show();
        }


        postlist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if(rv.getChildCount() > 0) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if(rv.getChildPosition(childView) ==0) {
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


        getMorePostAdapter = new GetMorePostAdapterTest(mContext, new GetMorePostAdapterTest.MorePostInterface() {
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
        }, postId);
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
                responseString = new SinglePostManager(mContext).callPost(recoid);

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
        super.onBackPressed();
        Constant.tag_txt=null;
        TagActivty.taged_users.clear();
        //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
       /* Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
        startActivity(intent);
        finish();*/

    }


   /* public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(postId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            if (responseString.equals("100")) {

            } else {
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }
*/


    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
            if(progressDialog==null){
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }

        }
        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(postId,notificationmasterid);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            if (responseString.equals("100")) {

            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }



}
