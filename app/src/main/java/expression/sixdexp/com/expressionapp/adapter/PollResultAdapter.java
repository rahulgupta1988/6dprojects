package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import db.PollResult;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.PollResultManager;

/**
 * Created by Praveen on 7/27/2016.
 */
public class PollResultAdapter extends RecyclerView.Adapter<PollResultAdapter.PollViewHolder> {

    Context mContext;
    List<PollResult> pollResults;

    public PollResultAdapter(Context context) {
        mContext = context;
        pollResults=new PollResultManager(mContext).getPollResultList();
    }

    @Override
    public PollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pollresultview, parent, false);
        return new PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PollViewHolder holder, int position) {

        PollResult pollResult=pollResults.get(position);
        String opinionid=pollResult.getOpinionid();
        String question=pollResult.getQuestion();
        String isactive=pollResult.getIsactive();
        String enddate=pollResult.getEnddate();
        String answertype=pollResult.getAnswertype();
        String yesprog_val=pollResult.getAgree_perc();
        String noprog_val=pollResult.getDisagree_perc();
        String cantsayprog_val=pollResult.getCantsay_perc();
        String status=pollResult.getStatus();


        if(position==0) {
            holder.polltitlelay.setVisibility(View.VISIBLE);
            holder.polltitle.setText("Current Poll");
        }
        if(position==1) {
            holder.polltitlelay.setVisibility(View.VISIBLE);
            holder.polltitle.setText("Other Polls");
        }
        if (position>1)
            holder.polltitlelay.setVisibility(View.GONE);

        if(question!=null)
            holder.pollquestiontxt.setText("" + question);

        if(enddate!=null) {
            String text="";
            if(status!=null){
                if(status.equalsIgnoreCase("COMPLETED")){
                    text = "<font color=#d33308><b>Ended On: </b></font><font color=#3c3c3c>"+enddate+"</b></font>";
                }
                else {
                    text = "<font color=#d33308><b>Ending On: </b></font><font color=#3c3c3c>"+enddate+"</b></font>";
                }
            }


            holder.closeddateofpoll.setText(Html.fromHtml(text));
        }

        if (answertype != null && !answertype.equalsIgnoreCase("") && answertype.equalsIgnoreCase("1")) {

            holder.yestxt.setText("Agree");
            holder.notxt.setText("Disagree");
            holder.cantsayxt.setText("Can't say");
        }


        if(yesprog_val!=null && !yesprog_val.equalsIgnoreCase("")){
            int temp_prog=(int) Double.parseDouble(yesprog_val);
            holder.yesprog.setProgress(temp_prog);
            holder.yesprogtxt.setText(""+temp_prog+"%");
        }

        if(noprog_val!=null && !noprog_val.equalsIgnoreCase("")){
            int temp_prog=(int) Double.parseDouble(noprog_val);
            holder.noprog.setProgress(temp_prog);
            holder.noprogtxt.setText(""+temp_prog+"%");
        }

        if(cantsayprog_val!=null && !cantsayprog_val.equalsIgnoreCase("")){
            int temp_prog=(int) Double.parseDouble(cantsayprog_val);
            holder.cantsayprog.setProgress(temp_prog);
            holder.cantsayprogtxt.setText(""+temp_prog+"%");
        }

    }

    @Override
    public int getItemCount() {
        return pollResults.size();

    }

    public class PollViewHolder extends RecyclerView.ViewHolder {

        LinearLayout polltitlelay;
        TextView polltitle,pollquestiontxt,closeddateofpoll;
        TextView yestxt, notxt, cantsayxt, yesprogtxt, noprogtxt, cantsayprogtxt;
        ProgressBar yesprog, noprog, cantsayprog;

        public PollViewHolder(View itemView) {

            super(itemView);
            polltitlelay=(LinearLayout)itemView.findViewById(R.id.polltitlelay);
            polltitle=(TextView)itemView.findViewById(R.id.polltitle);
            pollquestiontxt=(TextView)itemView.findViewById(R.id.pollquestiontxt);
            closeddateofpoll=(TextView)itemView.findViewById(R.id.closeddateofpoll);
            yestxt=(TextView)itemView.findViewById(R.id.yestxt);
            notxt=(TextView)itemView.findViewById(R.id.notxt);
            cantsayxt=(TextView)itemView.findViewById(R.id.cantsayxt);
            yesprogtxt=(TextView)itemView.findViewById(R.id.yesprogtxt);
            noprogtxt=(TextView)itemView.findViewById(R.id.noprogtxt);
            cantsayprogtxt=(TextView)itemView.findViewById(R.id.cantsayprogtxt);
            yesprog=(ProgressBar)itemView.findViewById(R.id.yesprog);
            noprog=(ProgressBar)itemView.findViewById(R.id.noprog);
            cantsayprog=(ProgressBar)itemView.findViewById(R.id.cantsayprog);


        }
    }
}
