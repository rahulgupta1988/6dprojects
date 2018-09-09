package expression.sixdexp.com.expressionapp;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.AsyncTask;
        import android.os.Bundle;

        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.text.SpannableString;
        import android.text.style.ForegroundColorSpan;
        import android.text.style.RelativeSizeSpan;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.Window;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapterExpressway;
        import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
        import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
        import expression.sixdexp.com.expressionapp.utility.Constant;

/**
 * Created by Praveen on 7/14/2016.
 */
public class PostDetailsAcitivityExpressway extends Activity implements View.OnClickListener {


    RecyclerView postlist;
    Context mContext;
    GetMorePostAdapterExpressway gettaTabFragmentFourthTest;
   // TabFragmentFourthTest gettaTabFragmentFourthTest;
    LinearLayout cancelshare;
    String postId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailsview);
        mContext = this;
        Constant.singlePostList1.clear();
        postId =getIntent().getStringExtra("postId");
        Log.i("postId", "" + postId);
        initView();
    }

    public void initView() {
        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (LinearLayout) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (postId != null && !postId.equalsIgnoreCase("")) {

            Constant.singlePostList1=new GetMorePostManager(mContext).getXPPostsByPostID(postId);
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


    @Override
    protected void onResume() {
        super.onResume();
       // setPost();
    }

    public void setPost() {


        gettaTabFragmentFourthTest = new GetMorePostAdapterExpressway(mContext, new GetMorePostAdapterExpressway.MorePostInterface() {
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        postlist.setLayoutManager(layoutManager);
        postlist.setItemAnimator(new DefaultItemAnimator());
        postlist.setAdapter(gettaTabFragmentFourthTest);

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
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
            progressDialog = new ProgressDialog(mContext,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            Window window = progressDialog.getWindow();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setMessage(ss2);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callPostExpressway(postId);
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

                setPost();

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
        TagActivty.taged_users.clear();
        Constant.tag_txt=null;
       /* finish();
        Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(mContext,HostActivty.class);
        startActivity(intent);*/
/*

        Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
        startActivity(intent);
        finish();
*/


    }
}
