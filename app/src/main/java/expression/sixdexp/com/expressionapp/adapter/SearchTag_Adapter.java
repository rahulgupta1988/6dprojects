package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 06-Oct-17.
 */

public class SearchTag_Adapter extends RecyclerView.Adapter<SearchTag_Adapter.SearchViewHolder> {

    Context mContext;
    List<AllUsers> fillteredUsers;
    public interface ItemClickedListener{
        public void item(int pos);
    }

    ItemClickedListener itemClickedListener=null;
    public SearchTag_Adapter(Context context, List<AllUsers> fillteredUsers, ItemClickedListener itemClickedListener) {
        mContext = context;
        this.itemClickedListener=itemClickedListener;
        this.fillteredUsers=fillteredUsers;
    }

    @Override
    public SearchTag_Adapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_adapter_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchTag_Adapter.SearchViewHolder holder, final int position) {

        String txtCustomer=fillteredUsers.get(position).getName();
        String user_desg=fillteredUsers.get(position).getDesignation();

        if (txtCustomer != null)
            holder.txtCustomer.setText(""+fillteredUsers.get(position).getName());

        if (user_desg != null)
            holder.user_desg.setText(""+fillteredUsers.get(position).getDesignation());

        // ivCustomerImage.setImageResource(R.drawable.default_profile_picture);


        try {

            String image_url = fillteredUsers.get(position).getImageurl();
            String user_image_url = image_url.replace(" ", "%20");
            Picasso.with(mContext)
                    .load(user_image_url)
                    .centerInside()
                    .resize(50,50)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_default)
                    .error(R.drawable.user_default)
                    .into(holder.ivCustomerImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.parent_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickedListener.item(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fillteredUsers.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parent_lay;
        TextView txtCustomer ;
        TextView user_desg;
        ImageView ivCustomerImage ;

        public SearchViewHolder(View itemView) {
            super(itemView);
            parent_lay=(LinearLayout)itemView.findViewById(R.id.parent_lay);
             txtCustomer = (TextView) itemView.findViewById(R.id.username);
             user_desg = (TextView) itemView.findViewById(R.id.user_desg);

             ivCustomerImage = (ImageView) itemView.findViewById(R.id.user_img);
        }
    }
}

