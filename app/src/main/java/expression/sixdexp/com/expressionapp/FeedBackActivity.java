package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by VINAY on 1/4/17.
 */
public class FeedBackActivity extends Activity implements View.OnClickListener
{
    LinearLayout linear;
    Context mContext;
    ImageView cancelshare, cancelattachment;
    ProgressDialog progressDialog;
    TextView feedback_name,feedback_email;
    EditText feedback_comment;
    ImageView submmit_btn;
    String feedback_data="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_page);
        mContext = this;
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);

        linear = (LinearLayout)findViewById(R.id.linear);
        feedback_name=(TextView)findViewById(R.id.feedback_name);
        feedback_email=(TextView)findViewById(R.id.feedback_email);
        feedback_comment=(EditText)findViewById(R.id.feedback_comment);
        submmit_btn=(ImageView)findViewById(R.id.submmit_btn);
        submmit_btn.setOnClickListener(this);

        List<UserLoginInfo> userDatas = null;
        userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        String user_name = userDatas.get(0).getName();
        String user_email = userDatas.get(0).getEmailId();
        Log.i("21322",user_name);

        if (user_name != null && !user_name.equalsIgnoreCase(""))
        {
           /* String[] strArray = user_name.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap + " ");
            }
            feedback_name.setText(builder.toString());*/
            feedback_name.setText(user_name.toString());

        }
        if (user_email != null && !user_email.equalsIgnoreCase(""))
        {
            feedback_email.setText(user_email);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.cancelshare:
                onBackPressed();
                break;

            case R.id.submmit_btn:

                feedback_data=feedback_comment.getText().toString().trim();

                if (!feedback_data.equalsIgnoreCase(""))
                {
                    if (new InternetConnectionDetector(mContext).isConnectingToInternet())
                    {
                        new UserFeedBackTask().execute();
                    }
                    else {
                        showsnack(mContext.getString(R.string.nework_connect_error));
                    }
                }
                else
                {
                    showsnack("Please Enter FeedBack Comment!");
                }
                break;
        }
    }



    public class UserFeedBackTask extends AsyncTask<String, Void, Void> {

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
                responseString = new GetMorePostManager(mContext).UserFeedback(feedback_data);
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
                    Toast.makeText(mContext, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
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

    public void showsnack(String msg)
    {
        Snackbar snackbar = Snackbar
                .make(linear,msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onBackPressed();
    }
}
