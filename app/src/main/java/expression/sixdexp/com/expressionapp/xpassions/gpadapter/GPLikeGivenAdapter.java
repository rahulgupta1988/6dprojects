package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import db.GivenLike;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.XPLikeManager;

/**
 * Created by Praveen on 15-Feb-18.
 */

public class GPLikeGivenAdapter extends RecyclerView.Adapter<GPLikeGivenAdapter.LikeHolder>  {

    List<GivenLike> givenLikes;
    Context _mContext;


    public GPLikeGivenAdapter(Context context) {
        _mContext=context;

        givenLikes=new XPLikeManager(_mContext).getGivenLikeList();

    }




    @Override
    public GPLikeGivenAdapter.LikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.likegiven_item, parent, false);
        return new GPLikeGivenAdapter.LikeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GPLikeGivenAdapter.LikeHolder holder, int position) {

        GivenLike givenLike=givenLikes.get(position);

        String imageUrl=givenLike.getImageurl();
        String username=givenLike.getUserName();
        String designamtion_txt=givenLike.getDesignation();
        String department_txt=givenLike.getDepartment();

        if (!imageUrl.equalsIgnoreCase("null") && !imageUrl.equalsIgnoreCase("")
                && !imageUrl.equalsIgnoreCase(" ")) {

            URI nominator_uri = null;
            try {
                nominator_uri = new URI(imageUrl.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            String nominator_imageurl1 = nominator_uri.toString();
            Picasso.with(_mContext)
                    .load(nominator_imageurl1)
                    .resize(70,70)
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(holder.nominatorimg);
        }

        else{
            holder.nominatorimg.setImageResource(R.drawable.default_profile_picture);
        }

        if (!designamtion_txt.equalsIgnoreCase("null") && designamtion_txt!=null) {
            holder.department.setText(designamtion_txt);
        }

        if (!username.equalsIgnoreCase("null") && username!=null) {
            holder.username.setText(username);
        }
    }

    @Override
    public int getItemCount() {

        if(givenLikes!=null && givenLikes.size()>0) return givenLikes.size();
        return 0;
    }



    public class LikeHolder extends RecyclerView.ViewHolder {

        TextView department,username;
        CircularImageView nominatorimg;

        public LikeHolder(View convertView) {
            super(convertView);

            nominatorimg = (CircularImageView) convertView.findViewById(R.id.nominatorimg);
            department = (TextView) convertView.findViewById(R.id.department);
            username = (TextView) convertView.findViewById(R.id.username);

        }
    }
}

