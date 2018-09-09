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

import db.RecentActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecentPostdetails;
import expression.sixdexp.com.expressionapp.utility.Constant;

/**
 * Created by Praveen on 7/1/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context mContext;


    public SearchAdapter(Context context) {
        mContext = context;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentacivityitemview, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        final int pos=position;
        RecentActivity recentActivity = Constant.searchResultList.get(position);

        String nominator_name = recentActivity.getNominator_name();
        String nominee_name = recentActivity.getNominee_name();
        String nominee = recentActivity.getNominee();
        String award = recentActivity.getAward();
        String recognise_date = recentActivity.getRecognise_date();
        String recognition_name = recentActivity.getRecognition_name();
        String is_story = recentActivity.getIs_story();
        String count_str = recentActivity.getCount();
        String type_view=recentActivity.getType_view();


        int count=0;
        try {
            if(count_str!=null && !count_str.equalsIgnoreCase("")){
                 count=Integer.parseInt(count_str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String recognition_id = recentActivity.getRecognition_id();
        String isxpress = recentActivity.getIsxpress();


      /*  if (is_story.equalsIgnoreCase("0")) {


            if (isxpress.equalsIgnoreCase("1")) {
                holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

            } else {

                holder.posttypeimg.setImageResource(R.drawable.recent_share_2x);
            }

            if (nominator_name != null) {

                String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#474747>has sharded a post</font>";
                holder.posttext.setText(Html.fromHtml(text));
            }
        }

        else {

            if (isxpress.equalsIgnoreCase("1")) {
                holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

            } else {

                holder.posttypeimg.setImageResource(R.drawable.recent_trophy_2x);
            }

            if (nominator_name != null) {
                //String temtStr=nominator_name + " has given \"Drive to Lead\" to" + nominee_name;

                String text = "<font color=#272727><b>"+nominator_name+"</b></font> <font color=#474747>has given \"Drive to Lead\" to </font><font color=#272727><b>"+ nominee_name+"</b></font>";
                holder.posttext.setText(Html.fromHtml(text));
            }

        }*/

        if (isxpress.equalsIgnoreCase("1")) {
            holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

        }

        else if(is_story.equalsIgnoreCase("1") || is_story.equalsIgnoreCase("2")){
            holder.posttypeimg.setImageResource(R.drawable.recent_trophy_2x);

        }
        else {

            holder.posttypeimg.setImageResource(R.drawable.recent_share_2x);
        }

        if (nominee_name != null) {
            String text="";
            String receivedORgiven="";
            String fromORto="";
            if(type_view.equalsIgnoreCase("rec_rcvd")) {
                fromORto=" from ";
                receivedORgiven = "received ";
            }
            else {
                fromORto=" to ";
                receivedORgiven = "has given ";
            }


            if(count>0){
               // text = "<font color=#272727><b>" + nominee_name + " (" + recognition_name + ") with "+count+ " Others</b></font> <font color=#474747>" + nomineedesignation + "</font>";

                text = "<font color=#272727><b>" + nominator_name + " +"+count+ " Others</b></font> <font color=#474747>" +receivedORgiven+ award + fromORto+" </font> <font color=#272727><b>"+ nominee +"</b></font>";
            }
            else {

                if(!is_story.equalsIgnoreCase("1") && !is_story.equalsIgnoreCase("2")){
                    text = "<font color=#272727><b>" + nominator_name + " </b></font> <font color=#747474>  has shared a post </font>";
                }
                else {
                    text = "<font color=#272727><b>" + nominator_name + " </b></font> <font color=#474747>"+receivedORgiven + award + fromORto+" </font> <font color=#272727><b>" + nominee + "</b></font>";
                }
                }
            holder.posttext.setText(Html.fromHtml(text));
        }

        if (recognise_date != null) {
            holder.postdate.setText(recognise_date);
        }

        holder.recentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActitivty=new Intent(mContext, RecentPostdetails.class);
                detailsActitivty.putExtra("postId",Constant.searchResultList.get(pos).getRecognition_id());
                mContext.startActivity(detailsActitivty);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (Constant.searchResultList != null && Constant.searchResultList.size() > 0) return Constant.searchResultList.size();
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView posttypeimg;
        TextView posttext, postdate;
        LinearLayout recentlay;
        public SearchViewHolder(View itemView) {
            super(itemView);
            posttypeimg = (ImageView) itemView.findViewById(R.id.posttypeimg);
            posttext = (TextView) itemView.findViewById(R.id.posttext);
            postdate = (TextView) itemView.findViewById(R.id.postdate);
            recentlay = (LinearLayout) itemView.findViewById(R.id.recentlay);
        }
    }
}
