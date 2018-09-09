package expression.sixdexp.com.expressionapp.adapter;

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

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 06-Sep-17.
 */

public class TaggingAdapter extends BaseAdapter {

    Context mContext;
    List<AllUsers> tagusers;

    public interface TaggingCallback{
        public void setPosition(int pos);
    }

    TaggingCallback taggingCallback;


    public TaggingAdapter(Context context, List<AllUsers> tagusers, TaggingCallback taggingCallback){
        mContext=context;
        this.tagusers=tagusers;
        this.taggingCallback=taggingCallback;
    }


    @Override
    public int getCount() {
        return tagusers.size();
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
        if(convertView==null) {
            holder=new HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.taguserlist_lay, parent, false);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.user_desg=(TextView)convertView.findViewById(R.id.user_desg);
            holder.cancel=(ImageView)convertView.findViewById(R.id.cancel);
            holder.user_img=(CircularImageView) convertView.findViewById(R.id.user_img);
            convertView.setTag(holder);
        }

        else{
            holder=(HolderView)convertView.getTag();
        }


        final int pos=position;
        holder.name.setText(""+tagusers.get(position).getName());
        holder.user_desg.setText(""+tagusers.get(position).getDesignation());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taggingCallback.setPosition(pos);
            }
        });


        try {

            String image_url = tagusers.get(position).getImageurl();
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


        return  convertView;
    }

    public class HolderView{

        TextView name,user_desg;
        ImageView cancel;
        CircularImageView user_img;

    }
}

