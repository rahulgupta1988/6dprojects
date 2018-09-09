package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.manager.VisitorCounter;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 9/5/2016.
 */
public class VisitorCounterActivty extends Activity {

    Context mContext;
    ProgressDialog progressDialog = null;
    TextView digit1,digit2,digit3,digit4,digit5,digit6;
    ImageView cancelshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.visitorcounterview);
        digit1=(TextView)findViewById(R.id.digit1);
        digit2=(TextView)findViewById(R.id.digit2);
        digit3=(TextView)findViewById(R.id.digit3);
        digit4=(TextView)findViewById(R.id.digit4);
        digit5=(TextView)findViewById(R.id.digit5);
        digit6=(TextView)findViewById(R.id.digit6);
        digit1.setVisibility(View.GONE);
        digit2.setVisibility(View.GONE);
        digit3.setVisibility(View.GONE);
        digit4.setVisibility(View.GONE);
        digit5.setVisibility(View.GONE);
        digit6.setVisibility(View.GONE);


        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new GetVisitorCounter().execute();
    }


    public void setCounter(){

     String vistor_counter=VisitorCounter.vistor_counter;

        Log.i("counter", "" + vistor_counter);
        if(vistor_counter!=null && !vistor_counter.equalsIgnoreCase("")){

            int length_of_counter=vistor_counter.length();
            Log.i("length_of_counter",""+length_of_counter);
            for (int i=0;i<length_of_counter;i++){
                char digit=vistor_counter.charAt(i);
                if(i==0) {
                    digit1.setVisibility(View.VISIBLE);
                    digit1.setText("" + digit);

                }
                if(i==1) {
                    digit2.setVisibility(View.VISIBLE);
                    digit2.setText("" + digit);
                }
                if(i==2){
                    digit3.setVisibility(View.VISIBLE);
                    digit3.setText(""+digit);
                }
                if(i==3) {
                    digit4.setVisibility(View.VISIBLE);
                    digit4.setText("" + digit);
                }

                if(i==4) {
                    digit5.setVisibility(View.VISIBLE);
                    digit5.setText("" + digit);
                }
                if(i==5) {
                    digit6.setVisibility(View.VISIBLE);
                    digit6.setText("" + digit);
                }

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
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString =   new VisitorCounter().callVisitorCounter();
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
            progressDialog.dismiss();
            if (responseString.equals("100"))
            {
                setCounter();
            }
            else{
                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
