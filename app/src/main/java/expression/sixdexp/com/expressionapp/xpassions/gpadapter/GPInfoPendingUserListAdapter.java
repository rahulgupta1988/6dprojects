package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.xpassions.GPInfoActivity;
import model.GroupUser;

/**
 * Created by Praveen on 08-Jan-18.
 */

public class GPInfoPendingUserListAdapter extends BaseAdapter {

    Context mContext;
    List<GroupUser> usersList;

    public GPInfoPendingUserListAdapter(Context context, List<GroupUser> usersList) {
        mContext = context;
        this.usersList = usersList;

    }


    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        GPInfoPendingUserListAdapter.HolderView holder;
        if (convertView == null) {
            holder = new GPInfoPendingUserListAdapter.HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpinfopendiguserlistadpterview, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.user_desg = (TextView) convertView.findViewById(R.id.user_desg);
            holder.user_img = (CircularImageView) convertView.findViewById(R.id.user_img);
            holder.accept_btn=(Button)convertView.findViewById(R.id.accept_btn);
            holder.decline_btn=(Button)convertView.findViewById(R.id.decline_btn);
            convertView.setTag(holder);
        } else {
            holder = (GPInfoPendingUserListAdapter.HolderView) convertView.getTag();
        }


        final int pos=position;
        holder.name.setText(""+usersList.get(position).getCoi_gmname());
        holder.user_desg.setText(""+usersList.get(position).getCoi_gmdepartment());



        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String requestUserID=usersList.get(position).getCoi_gmuserid();
                ((GPInfoActivity)mContext).acceptJoinRequest(requestUserID,"accept");
            }
        });

        holder.decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestUserID=usersList.get(position).getCoi_gmuserid();
                ((GPInfoActivity)mContext).acceptJoinRequest(requestUserID,"del");
            }
        });


        try {

            String image_url = usersList.get(position).getCoi_gmprofilepic();
            String user_image_url = image_url.replace(" ", "%20");
            Log.i("457669 url",user_image_url);
            Picasso.with(mContext)
                    .load(user_image_url)
                    .centerInside()
                    .resize(50,50)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_default)
                    .error(R.drawable.user_default)
                    .into(holder.user_img);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return convertView;
    }

    public class HolderView {

        TextView name, user_desg;
        CircularImageView user_img;
        Button accept_btn,decline_btn;

    }
}


