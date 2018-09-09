package expression.sixdexp.com.expressionapp.xpassions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPWallPostAdapter_details;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallPostManager;

/**
 * Created by Praveen on 14-Feb-18.
 */

public class GPWallPostDetailsAcitivity extends Activity implements View.OnClickListener {


    RecyclerView postlist;
    Context mContext;
    GPWallPostAdapter_details getMorePostAdapter;
    String postId;
    ProgressDialog progressDialog;
    LinearLayout cancelshare;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailsview);
        mContext = this;
        Constant.singlePostList_gp.clear();
        postId =getIntent().getStringExtra("postId");
        Log.i("postId",""+postId);
        initView();

    }

    public void initView() {

        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (LinearLayout) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (postId != null && !postId.equalsIgnoreCase("")) {
            Constant.singlePostList_gp=new GPWallPostManager(mContext).getPostByPostID(postId);
            setPost();
            //new GetPost().execute();
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




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.tag_txt=null;
        TagActivty.taged_users.clear();


    }






}

