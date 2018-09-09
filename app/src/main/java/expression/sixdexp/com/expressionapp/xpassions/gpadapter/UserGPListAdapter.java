package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.xpassions.SearchGPDetails;
import model.SearchGroupObject;

/**
 * Created by Praveen on 21-Feb-18.
 */

public class UserGPListAdapter extends RecyclerView.Adapter<UserGPListAdapter.RecyclerViewHolders> {

    private List<SearchGroupObject> itemList;
    private Context context;
    String []randomcolor={"#F1C411","#E87E23","#5518A1","#3699DB"};
    String userid="";

    public UserGPListAdapter(Context context, List<SearchGroupObject> itemList,String userid) {
        this.itemList = itemList;
        this.context = context;
        this.userid=userid;
    }

    @Override
    public UserGPListAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_group_card_view, null);
        UserGPListAdapter.RecyclerViewHolders rcv = new UserGPListAdapter.RecyclerViewHolders(layoutView);
        return rcv;
    }

    int tempPOS=0;

    @Override
    public void onBindViewHolder(UserGPListAdapter.RecyclerViewHolders holder, final int position) {

        GradientDrawable rect_Shape = (GradientDrawable) holder.btn_lay.getBackground();


        if(tempPOS==4)
            tempPOS=0;
        holder.color_line.setBackgroundColor(Color.parseColor(randomcolor[tempPOS]));
        tempPOS++;


        SearchGroupObject groupObject=itemList.get(position);
        holder.group_name.setText(groupObject.getCoi_gtitle());
        holder.member_count.setText(groupObject.getNoofmember());
        if(groupObject.getCoi_gispublic().equalsIgnoreCase("Public") || groupObject.getCoi_gispublic().equalsIgnoreCase("0")){
           /* holder.btn_txt.setText("Join");
            rect_Shape.setColor(Color.parseColor("#3BAE8C"));
            holder.gp_type_ic.setImageResource(R.drawable.join_gp_ic);*/
        }

        else if(groupObject.getCoi_gispublic().equalsIgnoreCase("Close") || groupObject.getCoi_gispublic().equalsIgnoreCase("1")){
           /* holder.btn_txt.setText("Request to Join");
            rect_Shape.setColor(Color.parseColor("#E67F24"));
            holder.gp_type_ic.setImageResource(R.drawable.request_gp_ic);*/
            holder.isprivate_lock.setVisibility(View.VISIBLE);
        }

        else if(groupObject.getCoi_gispublic().equalsIgnoreCase("requested")){
           /* holder.btn_txt.setText("Requested");
            rect_Shape.setColor(Color.parseColor("#E74D3E"));
            holder.gp_type_ic.setImageResource(R.drawable.requested_gp_ic);*/
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchGroupObject groupObject=itemList.get(position);
                Intent intent=new Intent(context, SearchGPDetails.class);
                intent.putExtra("userid",userid);
                intent.putExtra("coi_gid",groupObject.getCoi_gid());
                ((Activity)context).startActivity(intent);

                /*SearchGroupObject groupObject=itemList.get(position);

                String coi_isprivate=groupObject.getCoi_gisprivate();
                if(coi_isprivate.equalsIgnoreCase("Public")){
                    coi_isprivate="0";
                    ((GroupListActivity)context).sendJoinRequest(groupObject.getCoi_gid(),coi_isprivate);
                }
                else if(coi_isprivate.equalsIgnoreCase("Close")){
                    coi_isprivate="1";
                    ((GroupListActivity)context).sendJoinRequest(groupObject.getCoi_gid(),coi_isprivate);
                }

                else if(coi_isprivate.equalsIgnoreCase("requested")){
                    Toast.makeText(context,"Request is pending...",Toast.LENGTH_SHORT).show();
                }
*/
            }
        });



        String image_url = groupObject.getCoi_gicon();
        URI gp_uri = null;
        try {
            gp_uri = new URI(image_url.replace(" ", "%20"));
            if (gp_uri.toString().length()!=0){
                Glide.with(context)
                        .load(gp_uri.toString())
                        .placeholder(R.drawable.group_icon_default)
                        .error(R.drawable.group_icon_default)
                        .crossFade()
                        .thumbnail(0.1f)
                        .centerCrop()
                        .into(holder.gp_photo);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView group_name,member_count,btn_txt;
        public ImageView gp_photo,gp_type_ic,isprivate_lock;
        RelativeLayout btn_lay;
        View color_line;
        LinearLayout card_view;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            group_name = (TextView)itemView.findViewById(R.id.group_name);
            member_count = (TextView)itemView.findViewById(R.id.member_count);
            btn_txt= (TextView)itemView.findViewById(R.id.btn_txt);
            gp_photo = (ImageView)itemView.findViewById(R.id.gp_photo);
            isprivate_lock= (ImageView)itemView.findViewById(R.id.isprivate_lock);
            gp_type_ic= (ImageView)itemView.findViewById(R.id.gp_type_ic);
            btn_lay=(RelativeLayout)itemView.findViewById(R.id.btn_lay);
            color_line=(View)itemView.findViewById(R.id.color_line);
            card_view=(LinearLayout)itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}

