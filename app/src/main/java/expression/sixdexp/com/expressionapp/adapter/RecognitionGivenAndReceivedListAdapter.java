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

import db.RecognitionGivenList;
import expression.sixdexp.com.expressionapp.PostDetailsAcitivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.RecognitionRecivedAndGivenListManager;

/**
 * Created by Praveen on 8/8/2016.
 */
public class RecognitionGivenAndReceivedListAdapter extends RecyclerView.Adapter<RecognitionGivenAndReceivedListAdapter.RecognitionAdapter> {

    Context mContext;
    List<RecognitionGivenList> recognitionGivenLists;


    public RecognitionGivenAndReceivedListAdapter(Context context,boolean recognitionGiven) {
        mContext = context;
        recognitionGivenLists = new RecognitionRecivedAndGivenListManager(mContext,recognitionGiven).getRecognitionGivenList();
    }

    @Override
    public RecognitionGivenAndReceivedListAdapter.RecognitionAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentacivityitemview, parent, false);
        return new RecognitionAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(RecognitionGivenAndReceivedListAdapter.RecognitionAdapter holder, int position) {
        final int pos = position;
        RecognitionGivenList recognitionGiven = recognitionGivenLists.get(position);

        String nominee_name = recognitionGiven.getNominee_name();
        String nominee = recognitionGiven.getNominee();
        String recognise_date = recognitionGiven.getRecognise_date();
        String recognition_name = recognitionGiven.getRecognition_name();
        String is_story = recognitionGiven.getIs_story();
        String count = recognitionGiven.getCount();
        String recognition_id = recognitionGiven.getRecognition_id();
        String isxpress = recognitionGiven.getIsxpress();
        String nomineedesignation=recognitionGiven.getNomineedesignation();

        //if (is_story.equalsIgnoreCase("0")) {


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

                if(nominee!=null && !nominee.equalsIgnoreCase("") && nominee.length()>0){
                    final String[] otherstr = nominee.split(",");
                    text = "<font color=#272727><b>" + nominee_name + " (" + recognition_name + ") with "+otherstr.length + " Others</b></font> <font color=#474747>" + nomineedesignation + "</font>";
                }
                else {
                    text = "<font color=#272727><b>" + nominee_name + "(" + recognition_name + ")" + "</b></font> <font color=#474747>" + nomineedesignation + "</font>";
                }
                    holder.posttext.setText(Html.fromHtml(text));
            }
       /* } else {

            if (isxpress.equalsIgnoreCase("1")) {
                holder.posttypeimg.setImageResource(R.drawable.recent_xw_2x);

            } else {

                holder.posttypeimg.setImageResource(R.drawable.recent_trophy_2x);
            }

            if (nominee_name != null) {
                String text = "<font color=#272727><b>" + nominee_name+"("+recognition_name+")" + "</b></font> <font color=#474747>"+nomineedesignation+"</font>";
                holder.posttext.setText(Html.fromHtml(text));
            }

        }*/

        if (recognise_date != null) {
            holder.postdate.setText(recognise_date);
        }

        holder.recentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                detailsActitivty.putExtra("postId", recognitionGivenLists.get(pos).getRecognition_id());
                mContext.startActivity(detailsActitivty);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (recognitionGivenLists != null && recognitionGivenLists.size() > 0) return recognitionGivenLists.size();
        return 0;
    }

    public class RecognitionAdapter extends RecyclerView.ViewHolder {

        ImageView posttypeimg;
        TextView posttext, postdate;
        LinearLayout recentlay;

        public RecognitionAdapter(View itemView) {
            super(itemView);

            posttypeimg = (ImageView) itemView.findViewById(R.id.posttypeimg);
            posttext = (TextView) itemView.findViewById(R.id.posttext);
            postdate = (TextView) itemView.findViewById(R.id.postdate);
            recentlay = (LinearLayout) itemView.findViewById(R.id.recentlay);
        }
    }

}
