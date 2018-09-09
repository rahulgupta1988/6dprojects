package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import db.RecentActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecentPostdetails;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;

/**
 * Created by Praveen on 7/12/2016.
 */
public class RecentactivityAdapter extends RecyclerView.Adapter<RecentactivityAdapter.ActivityHolder> {

    Context mContext;
    List<RecentActivity> recentActivities;


    public RecentactivityAdapter(Context context) {
        mContext = context;
        recentActivities = new RecentActivityManager(mContext).getRecentActivityList();
    }

    @Override
    public RecentactivityAdapter.ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentacivityitemview, parent, false);
        return new ActivityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentactivityAdapter.ActivityHolder holder, int position) {

        final int pos=position;
        RecentActivity recentActivity = recentActivities.get(position);

        String nominator_name = recentActivity.getNominator_name();
        String nominee_name = recentActivity.getNominee_name();
        String recognise_date = recentActivity.getRecognise_date();
        String recognition_name = recentActivity.getRecognition_name();
        String is_story = recentActivity.getIs_story();

        String recognition_id = recentActivity.getRecognition_id();
        String isxpress = recentActivity.getIsxpress();

        String count_str = recentActivity.getCount();
        int count=0;
        try {
            if(count_str!=null && !count_str.equalsIgnoreCase("")){
                count=Integer.parseInt(count_str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }





        if (is_story.equalsIgnoreCase("0"))
        {
            if (isxpress.equalsIgnoreCase("1")) {
                holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

            } else {

                holder.posttypeimg.setImageResource(R.drawable.recent_share_2x);
            }

            if (nominator_name != null) {

                String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#565656>has shared a post</font>";
                holder.posttext.setText(Html.fromHtml(text));
            }
        }

         else
           {
                if (isxpress.equalsIgnoreCase("1"))
                {
                    holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

                } else {

                    holder.posttypeimg.setImageResource(R.drawable.recent_trophy_2x);
                }


                if(count>0)
                {
                    if (nominator_name != null) {
                        //String temtStr=nominator_name + " has given \"Drive to Lead\" to" + nominee_name;

                        String text = "<font color=#272727><b>"+nominator_name+"</b></font> " +
                                "<font color=#565656>has given \""+recognition_name+"\" to " + "</font>" +
                                "<font color=#272727><b>"+ nominee_name+"</b></font>"+"<font color=#565656><b> + "+count+" Others";
                        holder.posttext.setText(Html.fromHtml(text));
                    }
                }
                else
                {
                    if (nominator_name != null) {
                        //String temtStr=nominator_name + " has given \"Drive to Lead\" to" + nominee_name;

                        String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#565656>has given \""+recognition_name+"\" to </font><font color=#272727><b>"+ nominee_name+"</b></font>";
                        holder.posttext.setText(Html.fromHtml(text));
                    }
                }
          }


        if (recognise_date != null) {
            holder.postdate.setText(recognise_date);
        }

        holder.recentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActitivty=new Intent(mContext, RecentPostdetails.class);
                detailsActitivty.putExtra("postId", recentActivities.get(pos).getRecognition_id());
                mContext.startActivity(detailsActitivty);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (recentActivities != null && recentActivities.size() > 0) return recentActivities.size();
        return 0;
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {

        ImageView posttypeimg;
        TextView posttext, postdate;
        LinearLayout recentlay;

        public ActivityHolder(View itemView) {
            super(itemView);

            posttypeimg = (ImageView) itemView.findViewById(R.id.posttypeimg);
            posttext = (TextView) itemView.findViewById(R.id.posttext);
            postdate = (TextView) itemView.findViewById(R.id.postdate);
            recentlay = (LinearLayout) itemView.findViewById(R.id.recentlay);
        }
    }
}
