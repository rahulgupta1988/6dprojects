package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.SrijanPolicydetailActivity;
import expression.sixdexp.com.expressionapp.manager.ClusesterPolicyManager;

/**
 * Created by hp on 8/15/2017.
 */

public class ClusterPolicyAdapter extends RecyclerView.Adapter<ClusterPolicyAdapter.ClusterHolder> {

    Context mContext;
    int colorIndex = 0;


    String[] colorArr = {"#9c0959", "#9c7d2c", "#4145ee", "#038995", "#4d1787", "#a5b40f", "#3a97d9", "#f1a31b", "#038995"};

    public ClusterPolicyAdapter(Context mContext) {
        this.mContext = mContext;
        Collections.sort(ClusesterPolicyManager.clusterPolicyModels,clusterPolicyComparator);
    }

    @Override
    public ClusterPolicyAdapter.ClusterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_list_item, parent, false);
        return new ClusterPolicyAdapter.ClusterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClusterPolicyAdapter.ClusterHolder holder, final int position) {
        ClusesterPolicyManager.ClusterPolicyModel clusterPolicyModel = ClusesterPolicyManager.clusterPolicyModels.get(position);
        GradientDrawable bgShape = (GradientDrawable) holder.circle_txt.getBackground();
        bgShape.setColor(Color.parseColor(colorArr[colorIndex]));
        colorIndex++;
        if (colorIndex == 9)
            colorIndex = 0;


        if (clusterPolicyModel.getPolicyname().length() > 0)
            holder.circle_txt.setText("" + clusterPolicyModel.getPolicyname().toUpperCase().charAt(0));

        holder.cluster_txt.setText(clusterPolicyModel.getPolicyname());
        holder.top_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String policyid = ClusesterPolicyManager.clusterPolicyModels.get(position).getPolicyid();
                Log.i("polidc 9090",""+policyid);
                Intent intent=new Intent(mContext, SrijanPolicydetailActivity.class);
                intent.putExtra("policyid",policyid);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ClusesterPolicyManager.clusterPolicyModels.size();
    }

    public class ClusterHolder extends RecyclerView.ViewHolder {
        TextView circle_txt, cluster_txt;
        RelativeLayout top_lay;

        public ClusterHolder(View convertView) {
            super(convertView);
            circle_txt = (TextView) convertView.findViewById(R.id.circle_txt);
            cluster_txt = (TextView) convertView.findViewById(R.id.cluster_txt);
            top_lay = (RelativeLayout) convertView.findViewById(R.id.top_lay);
        }

    }



    private Comparator<ClusesterPolicyManager.ClusterPolicyModel> clusterPolicyComparator = new Comparator<ClusesterPolicyManager.ClusterPolicyModel>() {

        @Override
        public int compare(ClusesterPolicyManager.ClusterPolicyModel s1, ClusesterPolicyManager.ClusterPolicyModel s2) {
            String cp1 = s1.getPolicyname().toUpperCase();
            String cp2 = s2.getPolicyname().toUpperCase();

            //ascending order
            return cp1.compareTo(cp2);

            //descending order
            // return cluster2.compareTo(cluster1);
        }
    };
}
