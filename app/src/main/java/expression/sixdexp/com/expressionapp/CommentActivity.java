package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import db.Comments;
import expression.sixdexp.com.expressionapp.adapter.CommentListAdapter;
import expression.sixdexp.com.expressionapp.manager.CommentManager;
import expression.sixdexp.com.expressionapp.manager.LikeAndCommentManager;
import expression.sixdexp.com.expressionapp.manager.SingleLikeAndCommentManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.TextChangeListe;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;

/**
 * Created by Praveen on 7/7/2016.
 */
public class CommentActivity extends Activity {

    Context mContext;
    ProgressDialog progressDialog;
    String postid = "";
    ImageView subcombtn;
    EditText commenttxt;
    RecyclerView commentlist;
    ImageView cancelshare;
    Toast mytoast;
    String singlepost;
    CoordinatorLayout searchlayparent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentview);
        mContext = this;
        Intent intent = getIntent();
        postid = intent.getStringExtra("recognizationID");
        singlepost = intent.getStringExtra("singlepost");
        subcombtn = (ImageView) findViewById(R.id.subcombtn);
        commenttxt = (EditText) findViewById(R.id.commenttxt);
        commentlist = (RecyclerView) findViewById(R.id.commentlist);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        searchlayparent = (CoordinatorLayout) findViewById(R.id.searchlayparent);

        mytoast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);


        commenttxt.addTextChangedListener(new TextChangeListe(commenttxt));

        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String comment = commenttxt.getText().toString();
                if (ValidationUtility.validEditTextString(comment)) {
                    Log.i("comment size", "" + comment.length());
                    new PostComment().execute(comment);
                } else {
                    commenttxt.setText("");
                   /* mytoast.setText("Please Enter Comment");
                    mytoast.show();*/

                    showsnack("Please Enter Comment");

                }

            }
        });

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        new CommentTask().execute();
    }

    @Override
    public void onBackPressed() {

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (mytoast != null)
            mytoast.cancel();
        super.onBackPressed();
    }

    public class CommentTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

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
                responseString = new CommentManager(mContext).commentCall(postid);
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
                CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, false);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                commentlist.setLayoutManager(layoutManager);
                commentlist.setItemAnimator(new DefaultItemAnimator());
                commentlist.setAdapter(commentListAdapter);
            } else {

                //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                //mytoast.setText(responseString);
               /* mytoast.setText(responseString);
                mytoast.show();*/
                showsnack(responseString);
                progressDialog.dismiss();
            }
        }
    }


    public class PostComment extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String comment = "";

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
                comment = params[0];

                if (singlepost.equalsIgnoreCase("0"))
                    responseString = new LikeAndCommentManager(mContext).postComment(postid, comment,"");
                else
                    responseString = new SingleLikeAndCommentManager(mContext).postComment(postid, comment,"");
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

                CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                commentlist.setLayoutManager(layoutManager);
                commentlist.setItemAnimator(new DefaultItemAnimator());
                commentlist.setAdapter(commentListAdapter);
                commenttxt.setText("");

                Comments comments=new CommentManager(mContext).getCommentsList().get(0);
                new SingleLikeAndCommentManager(mContext).updateCommentsInPostList(comments.getCount());
                new SingleLikeAndCommentManager(mContext).updateExpressComment(comments.getCount(),postid);

                onBackPressed();

            } else {

               /* mytoast.setText(responseString);
                mytoast.show();*/
                showsnack(responseString);
                progressDialog.dismiss();
            }

        }


    }

    public void showsnack(String msg) {

        Snackbar snackbar = Snackbar
                .make(searchlayparent, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        textView.setTextColor(Color.parseColor("#ffffff"));

        snackbar.show();

    }


}
