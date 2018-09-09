package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import model.GroupUser;

/**
 * Created by USER on 1/7/2018.
 */

public class GPInfoUserListAdapter extends BaseAdapter {

    Context mContext;
    List<GroupUser> usersList;

    public GPInfoUserListAdapter(Context context, List<GroupUser> usersList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderView holder;
        if (convertView == null) {
            holder = new HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpinfouserlistadpterview, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.user_desg = (TextView) convertView.findViewById(R.id.user_desg);
            holder.admin_ic = (ImageView) convertView.findViewById(R.id.admin_ic);
            holder.user_img = (CircularImageView) convertView.findViewById(R.id.user_img);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }


        final int pos=position;
        holder.name.setText(""+usersList.get(position).getCoi_gmname());
        holder.user_desg.setText(""+usersList.get(position).getCoi_gmdepartment());


        if(usersList.get(position).getCoi_gmisadmin().equals("0")) {
            holder.admin_ic.setVisibility(View.GONE);
        }

        else{
            holder.admin_ic.setVisibility(View.VISIBLE);
        }


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
        ImageView admin_ic;
        CircularImageView user_img;

    }
}

