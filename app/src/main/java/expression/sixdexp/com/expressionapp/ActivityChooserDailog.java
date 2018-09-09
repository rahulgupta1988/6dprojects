package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 12-Apr-17.
 */

public class ActivityChooserDailog extends Activity {

    TextView shupdate,recogsm;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext=this;

        String userID = SharedPrefrenceManager.getUserID(mContext);
        if (userID == null || userID.equals("")) {
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        else{
            setContentView(R.layout.activtychooserdialog);
            initView();
        }

    }

    public void initView(){
        shupdate=(TextView)findViewById(R.id.shupdate);
        recogsm=(TextView)findViewById(R.id.recogsm);

        final Intent receivedIntent = getIntent();
        final String action = receivedIntent.getAction();
        final String type = receivedIntent.getType();

        shupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(ActivityChooserDailog.this,ShareActivity.class);
                intent1.setAction(action);
                intent1.setType(type);

                if (Intent.ACTION_SEND.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        Uri imageUri = (Uri) receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                        intent1.putExtra(Intent.EXTRA_STREAM,imageUri);
                    }
                } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        ArrayList<Uri> imageUris = receivedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        intent1.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
                    }
                }


                startActivity(intent1);
                finish();
            }
        });

        recogsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ActivityChooserDailog.this,RecogniseActivity.class);
                intent1.setAction(action);
                intent1.setType(type);

                if (Intent.ACTION_SEND.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        Uri imageUri = (Uri) receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
                        intent1.putExtra(Intent.EXTRA_STREAM,imageUri);
                    }
                } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                    if (type.startsWith("image/")) {
                        ArrayList<Uri> imageUris = receivedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        intent1.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
                    }
                }


                startActivity(intent1);
                finish();
            }
        });
    }

}
