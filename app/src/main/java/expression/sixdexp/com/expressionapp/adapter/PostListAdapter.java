package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import db.GetMorePost;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;

/**
 * Created by Praveen on 7/1/2016.
 */
public class PostListAdapter extends BaseAdapter {
    Context mContext;

    List<GetMorePost> getMorePosts;

    public PostListAdapter(Context context) {
        mContext = context;
        getMorePosts = new GetMorePostManager(mContext).getMorePosts();
    }

    @Override
    public int getCount() {

        if (getMorePosts != null && getMorePosts.size() > 0) {
            return getMorePosts.size();
        }

        return 0;
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
        final int i = position;
        final HolderView holder;

        RoundedImageView nominatorimg;
        CircularImageView nominee_image;
        TextView nominator_name, nominator_designation, nominee_name, description, likescount, commentscount, recognise_date;
        LinearLayout likeit, commentit;

        ImageView thanksimg, postimg;
        ImageView postvideo;
        LinearLayout imagelay;
        FrameLayout thanksview,videolay;

        //if (convertView == null) {
        //holder = new HolderView();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.postchatitem, null);

           /* nominatorimg = (RoundedImageView) convertView.findViewById(R.id.nominatorimg);
            nominee_image = (CircularImageView) convertView.findViewById(R.id.nominee_image);
            nominator_name = (TextView) convertView.findViewById(R.id.nominator_name);
            nominator_designation = (TextView) convertView.findViewById(R.id.nominator_designation);
            nominee_name = (TextView) convertView.findViewById(R.id.nominee_name);
            description = (TextView) convertView.findViewById(R.id.description);
            likescount = (TextView) convertView.findViewById(R.id.likescount);
            commentscount = (TextView) convertView.findViewById(R.id.commentscount);
            recognise_date = (TextView) convertView.findViewById(R.id.recognise_date);
            likeit = (LinearLayout) convertView.findViewById(R.id.likeit);
            commentit = (LinearLayout) convertView.findViewById(R.id.commentit);

            thanksimg = (ImageView) convertView.findViewById(R.id.thanksimg);
            postimg = (ImageView) convertView.findViewById(R.id.postimg);
            postvideo = (VideoView) convertView.findViewById(R.id.postvideo);

            imagelay = (LinearLayout) convertView.findViewById(R.id.imagelay);
            videolay = (LinearLayout) convertView.findViewById(R.id.videolay);
            thanksview = (FrameLayout) convertView.findViewById(R.id.thanksview);*/


        nominatorimg = (RoundedImageView) convertView.findViewById(R.id.nominatorimg);
        nominee_image = (CircularImageView) convertView.findViewById(R.id.nominee_image);
        nominator_name = (TextView) convertView.findViewById(R.id.nominator_name);
        nominator_designation = (TextView) convertView.findViewById(R.id.nominator_designation);
        nominee_name = (TextView) convertView.findViewById(R.id.nominee_name);
        description = (TextView) convertView.findViewById(R.id.description);
        likescount = (TextView) convertView.findViewById(R.id.likescount);
        commentscount = (TextView) convertView.findViewById(R.id.commentscount);
        recognise_date = (TextView) convertView.findViewById(R.id.recognise_date);
        likeit = (LinearLayout) convertView.findViewById(R.id.likeit);
        commentit = (LinearLayout) convertView.findViewById(R.id.commentit);

        thanksimg = (ImageView) convertView.findViewById(R.id.thanksimg);
        postimg = (ImageView) convertView.findViewById(R.id.postimg);
        postvideo = (ImageView) convertView.findViewById(R.id.postvideo);

        imagelay = (LinearLayout) convertView.findViewById(R.id.imagelay);
        videolay = (FrameLayout) convertView.findViewById(R.id.videolay);
        thanksview = (FrameLayout) convertView.findViewById(R.id.thanksview);
        //convertView.setTag(holder);

       /* } else {
            holder = (HolderView) convertView.getTag();
        }*/


        GetMorePost getMorePost = getMorePosts.get(position);

        String nominator_imageurl = getMorePost.getNominator_imageurl();
        String nominee_image1 = getMorePost.getNominee_imageurl();
        String nominator_name1 = getMorePost.getNominator_name();
        String nominator_designation1 = getMorePost.getNominator_designation();
        String nominee_name1 = getMorePost.getNominee_name();
        String description1 = getMorePost.getDescription();
        String likescount1 = getMorePost.getLikes();
        String commentscount1 = getMorePost.getComments();
        String recognise_date1 = getMorePost.getRecognise_date();
        String postType = getMorePost.getType();

        Log.i("postType",""+postType);
        if (postType.equalsIgnoreCase("image")) {

            Log.i("image111",""+"iamge111");
            imagelay.setVisibility(View.VISIBLE);
            videolay.setVisibility(View.GONE);
            thanksview.setVisibility(View.GONE);
            String image_url = getMorePost.getUrl();
            URI nominator_uri = null;
            try {
                nominator_uri = new URI(image_url.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String nominator_imageurl1 = nominator_uri.toString();
            if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                    && !nominator_imageurl1.equalsIgnoreCase(" ")) {
                Picasso.with(mContext)
                        .load(nominator_imageurl1)
                        .resize(70, 70)
                        .centerCrop()
                        .placeholder(R.drawable.default_profile_picture)
                        .error(R.drawable.default_profile_picture)
                        .into(postimg);
            }
        }

        URI nominator_uri = null;
        try {
            nominator_uri = new URI(nominator_imageurl.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String nominator_imageurl1 = nominator_uri.toString();


        if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                && !nominator_imageurl1.equalsIgnoreCase(" ")) {


            Picasso.with(mContext)
                    .load(nominator_imageurl1)
                    .resize(70, 70)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(nominatorimg);
        }


        URI nominee_uri = null;
        try {
            nominee_uri = new URI(nominee_image1.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String nominee_imageurl1 = nominee_uri.toString();

        if (!nominee_imageurl1.equalsIgnoreCase("null") && !nominee_imageurl1.equalsIgnoreCase("")
                && !nominee_imageurl1.equalsIgnoreCase(" ")) {


            Picasso.with(mContext)
                    .load(nominee_imageurl1)
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(nominee_image);
        }


        if (nominator_name1 != null && !nominator_name1.equalsIgnoreCase(""))
            nominator_name.setText(nominator_name1);

        if (nominator_designation1 != null && !nominator_designation1.equalsIgnoreCase(""))
            nominator_designation.setText(nominator_designation1);

        if (nominee_name1 != null && !nominee_name1.equalsIgnoreCase(""))
            nominee_name.setText(nominee_name1);

        if (description1 != null && !description1.equalsIgnoreCase(""))
            description.setText(description1);

        if (likescount1 != null && !likescount1.equalsIgnoreCase(""))
            likescount.setText(likescount + " Likes");

        if (commentscount1 != null && !commentscount1.equalsIgnoreCase(""))
            commentscount.setText(commentscount1 + " Comments");

        if (recognise_date1 != null && !recognise_date1.equalsIgnoreCase(""))
            recognise_date.setText(recognise_date1);

        return convertView;
    }


    public class HolderView {
        RoundedImageView nominatorimg;
        CircularImageView nominee_image;
        TextView nominator_name, nominator_designation, nominee_name, description, likescount, commentscount, recognise_date;
        LinearLayout likeit, commentit;

        ImageView thanksimg, postimg;
        VideoView postvideo;
        LinearLayout imagelay, videolay;
        FrameLayout thanksview;
    }

}
