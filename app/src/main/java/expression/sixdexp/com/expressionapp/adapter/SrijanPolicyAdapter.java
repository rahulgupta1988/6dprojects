package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expression.ui.SrijanFragment;

import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.SrijanPolicydetailActivity;
import expression.sixdexp.com.expressionapp.manager.ClusesterPolicyManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyListManager;
import model.PolicyModel;
import model.SrijanPolicyListModel;

/**
 * Created by Praveen on 28-Jul-17.
 */

public class SrijanPolicyAdapter  extends RecyclerView.Adapter<SrijanPolicyAdapter.PolicyHolder> {

    Context mContext;

    public  SrijanPolicyAdapter(Context mContext){
        this.mContext=mContext;
    }
    @Override
    public SrijanPolicyAdapter.PolicyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.srijan_policy_list_item, parent, false);
        return new SrijanPolicyAdapter.PolicyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SrijanPolicyAdapter.PolicyHolder holder, final int position) {
        final SrijanPolicyListModel srijanPolicyListModel=SrijanPolicyListManager.srijanPolicyListModels.get(position);

        holder.policy_name_lay2.setText(""+srijanPolicyListModel.getPolicyname());
        holder.like_count.setText(srijanPolicyListModel.getLikes());
        holder.coment_count.setText(srijanPolicyListModel.getComments());
        holder.view_count.setText(srijanPolicyListModel.getViews());

        List<PolicyModel> policyModels= srijanPolicyListModel.getPolicyemolist();

         for(int i=0;i<policyModels.size();i++){
             PolicyModel policyModel=policyModels.get(i);
             String emoid=policyModel.getEmoid();
             String emopercentage=policyModel.getEmopercentage();
             String emocount=policyModel.getEmocount();


             // emoji_a_txt(3),emoji_b_txt(4),emoji_c_txt(5),emoji_d_txt(6),emoji_e_txt(8),emoji_f_txt(9)

              if(emoid.equals("3")){
                  holder.emoji_a_txt.setText(""+emocount+"/"+emopercentage+"%");
              }

             else if(emoid.equals("4")){
                 holder.emoji_b_txt.setText(""+emocount+"/"+emopercentage+"%");
             }

              else if(emoid.equals("5")){
                  holder.emoji_c_txt.setText(""+emocount+"/"+emopercentage+"%");
              }

              else if(emoid.equals("6")){
                  holder.emoji_d_txt.setText(""+emocount+"/"+emopercentage+"%");
              }

              else if(emoid.equals("8")){
                  holder.emoji_e_txt.setText(""+emocount+"/"+emopercentage+"%");
              }

              else if(emoid.equals("9")){
                  holder.emoji_f_txt.setText(""+emocount+"/"+emopercentage+"%");
              }

         }

        holder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SrijanFragment.isPolicyTab=true;
                String policyid =SrijanPolicyListManager.srijanPolicyListModels.get(position).getPolicyid();
                Log.i("polidc 9090",""+policyid);
                Intent intent=new Intent(mContext, SrijanPolicydetailActivity.class);
                intent.putExtra("policyid",policyid);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SrijanPolicyListManager.srijanPolicyListModels.size();
    }

    public class PolicyHolder extends RecyclerView.ViewHolder {
        TextView policy_name_lay2,like_count,coment_count,view_count;
        TextView emoji_a_txt,emoji_b_txt,emoji_c_txt,emoji_d_txt,emoji_e_txt,emoji_f_txt;
        CardView item_card;
        public PolicyHolder(View convertView) {
            super(convertView);
            item_card=(CardView)convertView.findViewById(R.id.item_card);
            policy_name_lay2=(TextView)convertView.findViewById(R.id.policy_name_lay2);
            like_count=(TextView)convertView.findViewById(R.id.like_count);
            coment_count=(TextView)convertView.findViewById(R.id.coment_count);
            view_count=(TextView)convertView.findViewById(R.id.view_count);
            emoji_a_txt=(TextView)convertView.findViewById(R.id.emoji_a_txt);
            emoji_b_txt=(TextView)convertView.findViewById(R.id.emoji_b_txt);
            emoji_c_txt=(TextView)convertView.findViewById(R.id.emoji_c_txt);
            emoji_d_txt=(TextView)convertView.findViewById(R.id.emoji_d_txt);
            emoji_e_txt=(TextView)convertView.findViewById(R.id.emoji_e_txt);
            emoji_f_txt=(TextView)convertView.findViewById(R.id.emoji_f_txt);
        }

    }
}

