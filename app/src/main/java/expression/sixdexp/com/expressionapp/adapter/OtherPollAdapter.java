package expression.sixdexp.com.expressionapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import expression.sixdexp.com.expressionapp.OtherPollDetailsActivty;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.OtherPoolMamager;
import model.OtherPollModel;

/**
 * Created by Praveen on 30-Aug-17.
 */

public class OtherPollAdapter extends RecyclerView.Adapter<OtherPollAdapter.PollViewHolder> {

    Context mContext;
    List<OtherPollModel> otherPollModels=null;
    public OtherPollAdapter(Context mContext){
        this.mContext=mContext;
        otherPollModels= OtherPoolMamager.otherPollModels;
    }

    @Override
    public PollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_poll_list_item, parent, false);
        return new PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PollViewHolder holder, final int position) {
        OtherPollModel otherPollModel=otherPollModels.get(position);
        String questiontitle=otherPollModel.getQuestiontitle();
        String enddate=otherPollModel.getEnddate();

        if(position%2==0) {
            holder.item_card.setCardBackgroundColor(Color.parseColor("#9FD6EF"));
            holder.poll_title.setTextColor(Color.parseColor("#BF433A"));
        }
        else{
            holder.item_card.setCardBackgroundColor(Color.parseColor("#CEE0FF"));
            holder.poll_title.setTextColor(Color.parseColor("#1150B9"));
        }

        holder.poll_title.setText(""+questiontitle);
        holder.date_time_txt.setText(""+enddate);

        holder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext,""+otherPollModels.get(position).getOpinionpollid(),Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(mContext, OtherPollDetailsActivty.class);
                intent.putExtra("pollid",otherPollModels.get(position).getOpinionpollid());
                ((Activity)mContext).startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return otherPollModels.size();
    }

    public class PollViewHolder extends RecyclerView.ViewHolder{
        CardView item_card;
        TextView poll_title,date_time_txt;
        public PollViewHolder(View itemView) {
            super(itemView);
            item_card=(CardView) itemView.findViewById(R.id.item_card);
            poll_title=(TextView)itemView.findViewById(R.id.poll_title);
            date_time_txt=(TextView)itemView.findViewById(R.id.date_time_txt);
        }
    }
}
