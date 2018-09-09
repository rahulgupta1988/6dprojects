package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.manager.LoginManager;

/**
 * Created by VINAY on 1/9/17.
 */
public class EventDetailsActivity extends Activity implements View.OnClickListener
{
    ImageView cancelshare;
    Context mcontext;
    ImageView posttypeimg;
    TextView posttext, postdate,events_desc;
    String nominator_name_test="",eventmaset_id="",event_date="",decription="",nominator_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_details);
        mcontext=this;
        posttext=(TextView)findViewById(R.id.posttext);
        postdate=(TextView)findViewById(R.id.postdate);
        events_desc=(TextView)findViewById(R.id.events_desc);
        posttypeimg = (ImageView)findViewById(R.id.posttypeimg);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);

        nominator_name_test = getIntent().getStringExtra("name");
        eventmaset_id= getIntent().getStringExtra("eventmasterid");
        event_date= getIntent().getStringExtra("eventdate");
        decription= getIntent().getStringExtra("description");

        nominator_name=nominator_name_test;

        List<UserLoginInfo> userDatas = null;
        userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mcontext).getUserInfo();
        String  UserName =userDatas.get(0).getName();

        events_desc.setText(Html.fromHtml(decription));

        if (eventmaset_id.equalsIgnoreCase("1"))
        {
//            String text = "<font color=#272727><b>"+nominator_name+"-"+ "</b></font> <font color=#565656>Happy Birthday </font><font color=#272727><b>"+ UserName+"</b></font>";
            String text = "<font color=#1919ff><b>"+nominator_name+ "</b></font> <font color=#D9000000>has commented on your's </font><font color=#009900>Birthday</font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.birthday_cake_img);
            postdate.setText(Html.fromHtml(event_date));
        }
        else if (eventmaset_id.equalsIgnoreCase("2"))
        {
//            String text = "<font color=#272727><b>"+nominator_name+"-"+"</b></font> <font color=#565656>Happy Work Anniversary </font><font color=#272727><b>"+ UserName+"</b></font>";
            String text = "<font color=#1919ff><b>"+nominator_name+ "</b></font> <font color=#D9000000>has commented on your's </font><font color=#009900>Work Anniversary</font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.anniversary_img);
            postdate.setText(Html.fromHtml(event_date));
        }
        else if (eventmaset_id.equalsIgnoreCase("3"))
        {
//            String text = "<font color=#272727><b>"+nominator_name+"-"+"</b></font> <font color=#565656>Happy Wedding Anniversary </font><font color=#272727><b>"+ UserName+"</b></font>";
            String text = "<font color=#1919ff><b>"+nominator_name+ "</b></font> <font color=#D9000000>has commented on your's </font><font color=#009900>Wedding Anniversary</font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.wedding_img);
            postdate.setText(Html.fromHtml(event_date));
        }


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
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
