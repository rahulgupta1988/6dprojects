package expression.sixdexp.com.expressionapp.adapter;

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

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.TagPostManager;
import model.TagPostModel;

/**
 * Created by Praveen on 12-Sep-17.
 */

public class TagPostAdapter extends RecyclerView.Adapter<TagPostAdapter.LikeHolder>  {

    List<TagPostModel> tagPostModels;
    Context _mContext;


    public TagPostAdapter(Context context) {
        _mContext=context;

        tagPostModels= TagPostManager.tagPostModels;

    }


    @Override
    public TagPostAdapter.LikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.likegiven_item, parent, false);
        return new TagPostAdapter.LikeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TagPostAdapter.LikeHolder holder, int position) {

        TagPostModel tagPostModel=tagPostModels.get(position);

        String name=tagPostModel.getName();
        String userid=tagPostModel.getUserid();
        String imageurl=tagPostModel.getImageurl();
        String designamtion_txt=tagPostModel.getDesignation();

        if (!imageurl.equalsIgnoreCase("null") && !imageurl.equalsIgnoreCase("")
                && !imageurl.equalsIgnoreCase(" ")) {

            URI nominator_uri = null;
            try {
                nominator_uri = new URI(imageurl.replace(" ", "%20"));
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

        if (!designamtion_txt.equalsIgnoreCase("null") && designamtion_txt!=null) {
            holder.department.setText(designamtion_txt);
        }

        if (!name.equalsIgnoreCase("null") && name!=null) {
            holder.username.setText(name);
        }




    }

    @Override
    public int getItemCount() {

        if(tagPostModels!=null && tagPostModels.size()>0) return tagPostModels.size();
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

