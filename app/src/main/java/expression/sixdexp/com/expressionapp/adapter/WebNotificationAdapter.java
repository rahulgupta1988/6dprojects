package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import db.AllWebNotification;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;

/**
 * Created by VINAY on 4/26/16.
 */

public class WebNotificationAdapter extends BaseAdapter
{
    int y=0;
    String background_check="",ripple_background_check="";
    String colorcode="";
    Context mcontext;
    List<AllWebNotification> allWebNotifications;
    Intent intent;


    public WebNotificationAdapter(Context context)
    {
        this.mcontext = context;
        allWebNotifications = new GetMorePostManager(mcontext).getAllWebNotifications();
        Log.i("size 134234", "" + allWebNotifications.size());


    }

    @Override
    public int getCount() {
        return allWebNotifications.size();
    }

    @Override
    public Object getItem(int i) {
        return allWebNotifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        final int pos = i;
        CircularImageView posttypeimg;
        TextView posttext, postdate;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.web_notification, null);
        posttext=(TextView)convertView.findViewById(R.id.posttext);
        postdate=(TextView)convertView.findViewById(R.id.postdate);
        posttypeimg = (CircularImageView) convertView.findViewById(R.id.posttypeimg);

       // String nominator_name = allWebNotifications.get(pos).getNominator_name();
       // String nominee_name = allWebNotifications.get(pos).getNominee_name();
        String recognise_date = allWebNotifications.get(pos).getRecognize_date();
        String notificationTypeId = allWebNotifications.get(pos).getNotificationTypeId();


        String is_story_status = allWebNotifications.get(pos).getIs_story_status();
        String messagetext = allWebNotifications.get(pos).getMessagetext();
        posttext.setText(Html.fromHtml(messagetext));

        Log.i("size is_story_status", "" + is_story_status);


       // String count_str = allWebNotifications.get(pos).getTotal_count();
      //  int count=0;
      /*  try {
            if(count_str!=null && !count_str.equalsIgnoreCase("")){
                count=Integer.parseInt(count_str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if(count>0)
        {
            if (!is_story_status.equalsIgnoreCase("0"))
            {
                String text = "<font color=#272727><b>"+nominator_name+
                        "</b></font> <font color=#565656>has recognised to </font>" +
                        "<font color=#272727><b>"+ nominee_name+"</b></font></font> <font color=#565656> With </font><font color=#565656><b>"+ count+"</b></font><font color=#565656> Others</font>";
                posttext.setText(Html.fromHtml(text));
            }
        }
        else
        {
            //Share an update
            if (notificationTypeId.equalsIgnoreCase("1"))
            {
                //String temtStr=nominator_name + " has given \"Drive to Lead\" to" + nominee_name;
                //*//*<font color=#272727><b>"+ nominee_name+"</b></font>*//*
                String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#565656>has shared a post </font>";
                posttext.setText(Html.fromHtml(text));
                // posttypeimg.setImageResource(R.drawable.recent_share_2x);
            }

            // Recognized someone
            else if (notificationTypeId.equalsIgnoreCase("2")){
                //String temtStr=nominator_name + " has given \"Drive to Lead\" to" + nominee_name;
                String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#565656>has recognised to </font><font color=#272727><b>"+ nominee_name+"</b></font>";
                posttext.setText(Html.fromHtml(text));
//            posttypeimg.setImageResource(R.drawable.recent_trophy_2x);
            }

            //Like or Comment on post for share an update and recognize someone
            else if (notificationTypeId.equalsIgnoreCase("3") || notificationTypeId.equalsIgnoreCase("4")){
                String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                posttext.setText(Html.fromHtml(text));
            }

            // Opinion Poll
            else if (notificationTypeId.equalsIgnoreCase("5")){
                String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                posttext.setText(Html.fromHtml(text));
            }

            // Celebration event
                else if (notificationTypeId.equalsIgnoreCase("6") || notificationTypeId.equalsIgnoreCase("7")
                    || notificationTypeId.equalsIgnoreCase("8")){

                Log.i("story 565",""+is_story_status);
                // birthday
                if(is_story_status.equalsIgnoreCase("1") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> has "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

                //work anniversary
                else if(is_story_status.equalsIgnoreCase("2") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> has "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

                //wedding anniversary
                else if(is_story_status.equalsIgnoreCase("3") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> has "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

            }

            // Celebration  Like, Comment
            else if (notificationTypeId.equalsIgnoreCase("7")
                    || notificationTypeId.equalsIgnoreCase("8")){

                Log.i("story 565",""+is_story_status);
                // birthday
                if(is_story_status.equalsIgnoreCase("1") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

                //work anniversary
                else if(is_story_status.equalsIgnoreCase("2") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

                //wedding anniversary
                else if(is_story_status.equalsIgnoreCase("3") ){
                    String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                    posttext.setText(Html.fromHtml(text));
                }

            }


            // Srijan Policy
            else if (notificationTypeId.equalsIgnoreCase("9")){
                String text = "<font color=#272727><b>"+nominee_name +"</b></font><font color=#272727><b> by "+ nominator_name +"</b></font>";
                posttext.setText(Html.fromHtml(text));
            }

            else {
                String text = "<font color=#272727><b>"+nominator_name +"</b></font><font color=#272727><b> "+ nominee_name +"</b></font>";
                posttext.setText(Html.fromHtml(text));
            }

        }

*/



        if (recognise_date != null) {
            postdate.setText(recognise_date);
        }

        String profile_img_url = allWebNotifications.get(pos).getImage_url();
        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" "))
        {
            /*Picasso.with(mcontext)
                    .load(profile_img_url1)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(posttypeimg);*/

            Picasso.with(mcontext)
                    .load(profile_img_url1)
                    .resize(150, 150).centerInside()
                   /* .centerCrop()*/
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(posttypeimg);
        }

        else{
            posttypeimg.setImageResource(R.drawable.default_profile_picture);
        }



        return convertView;


    }


}



