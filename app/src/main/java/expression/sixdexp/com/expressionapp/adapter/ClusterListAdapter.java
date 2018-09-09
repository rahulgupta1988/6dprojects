package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import db.Cluster;
import expression.sixdexp.com.expressionapp.ClusterPolicyListActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.ClusterManager;

/**
 * Created by Praveen on 29-Jul-17.
 */

public class ClusterListAdapter extends RecyclerView.Adapter<ClusterListAdapter.ClusterHolder> {

    Context mContext;
    int colorIndex=0;
    List<Cluster> clusters;

    String[] colorArr={"#9c0959","#9c7d2c","#4145ee","#038995","#4d1787","#a5b40f","#3a97d9","#f1a31b","#038995"};
    public  ClusterListAdapter(Context mContext){
        this.mContext=mContext;
        clusters=new ClusterManager(mContext).getClusters();
    }
    @Override
    public ClusterListAdapter.ClusterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_list_item, parent, false);
        return new ClusterListAdapter.ClusterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClusterListAdapter.ClusterHolder holder, final int position) {

            GradientDrawable bgShape = (GradientDrawable) holder.circle_txt.getBackground();
            bgShape.setColor(Color.parseColor(colorArr[colorIndex]));
            colorIndex++;
            if(colorIndex==9)
                colorIndex=0;


            Cluster cluster=clusters.get(position);
        if(cluster.getClustername().length()>0)
          holder.circle_txt.setText(""+cluster.getClustername().toUpperCase().charAt(0));

        holder.cluster_txt.setText(cluster.getClustername());
        holder.top_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clusterID=clusters.get(position).getClusterid();
                String clusterName=clusters.get(position).getClustername();
                Intent intent=new Intent(mContext, ClusterPolicyListActivity.class);
                intent.putExtra("clusterID",clusterID);
                intent.putExtra("clusterName",clusterName);
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return clusters.size();
    }

    public class ClusterHolder extends RecyclerView.ViewHolder {
        TextView circle_txt,cluster_txt;
        RelativeLayout top_lay;
        public ClusterHolder(View convertView) {
            super(convertView);
            circle_txt=(TextView)convertView.findViewById(R.id.circle_txt);
            cluster_txt=(TextView)convertView.findViewById(R.id.cluster_txt);
            top_lay=(RelativeLayout)convertView.findViewById(R.id.top_lay);
        }

    }


}

