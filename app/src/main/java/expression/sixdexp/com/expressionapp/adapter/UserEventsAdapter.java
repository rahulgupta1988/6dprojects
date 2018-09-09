package expression.sixdexp.com.expressionapp.adapter;

        import android.content.Context;
        import android.content.Intent;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

        import db.AllUserEvent;
        import db.UserLoginInfo;
        import expression.sixdexp.com.expressionapp.R;
        import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
        import expression.sixdexp.com.expressionapp.manager.LoginManager;

/**
 * Created by VINAY on 4/26/16.
 */

public class UserEventsAdapter extends BaseAdapter
{
    int y=0;
    String background_check="",ripple_background_check="";
    String colorcode="";
    Context mcontext;
    List<AllUserEvent> allUserEvents;
    Intent intent;


    public UserEventsAdapter(Context context)
    {
        this.mcontext = context;
        allUserEvents = new GetMorePostManager(mcontext).getAllUserEvents();
        Log.i("size 134234", "" + allUserEvents.size());


    }

    @Override
    public int getCount() {
        return allUserEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return allUserEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        final int pos = i;
        ImageView posttypeimg;
        TextView posttext, postdate;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.user_events, null);
        posttext=(TextView)convertView.findViewById(R.id.posttext);
        postdate=(TextView)convertView.findViewById(R.id.postdate);
        posttypeimg = (ImageView) convertView.findViewById(R.id.posttypeimg);


        List<UserLoginInfo> userDatas = null;
        userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mcontext).getUserInfo();
        String  UserName =userDatas.get(0).getName();

        String nominator_name = "@ "+allUserEvents.get(pos).getName();
        String eventmaset_id= allUserEvents.get(pos).getEventmaster_id();
        String event_date= allUserEvents.get(pos).getEvent_date();


        if (eventmaset_id.equalsIgnoreCase("1"))
        {
            String text = "<font color=#272727><b>"+nominator_name+"-"+ "</b></font> <font color=#565656>Happy Birthday </font><font color=#272727><b>"+ UserName+"</b></font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.birthday_cake_img);
            postdate.setText(Html.fromHtml(event_date));
        }
        else if (eventmaset_id.equalsIgnoreCase("2"))
        {
            String text = "<font color=#272727><b>"+nominator_name+"-"+"</b></font> <font color=#565656>Happy Work Anniversary </font><font color=#272727><b>"+ UserName+"</b></font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.anniversary_img);
            postdate.setText(Html.fromHtml(event_date));
        }
       else if (eventmaset_id.equalsIgnoreCase("3"))
        {
            String text = "<font color=#272727><b>"+nominator_name+"-"+"</b></font> <font color=#565656>Happy Wedding Anniversary </font><font color=#272727><b>"+ UserName+"</b></font>";
            posttext.setText(Html.fromHtml(text));
            posttypeimg.setImageResource(R.drawable.wedding_img);
            postdate.setText(Html.fromHtml(event_date));
        }







        return convertView;


    }


}



