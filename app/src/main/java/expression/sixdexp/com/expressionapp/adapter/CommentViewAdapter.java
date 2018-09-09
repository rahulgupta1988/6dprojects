package expression.sixdexp.com.expressionapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import db.Comments;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.CommentManager;
import expression.sixdexp.com.expressionapp.manager.CommentTagManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.URLImageParser;

/**
 * Created by Praveen on 18-Jan-17.
 */

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.CommentHolder>  {

    List<Comments> commentses;
    Context _mContext;


    public CommentViewAdapter(Context context) {
        _mContext=context;

        commentses= CommentManager.commentses;

    }


    @Override
    public CommentViewAdapter.CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentgiven_item, parent, false);
        return new CommentViewAdapter.CommentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentViewAdapter.CommentHolder holder, final int position) {

        Comments comments=commentses.get(position);

        String imageUrl=comments.getImageurl();
        String username=comments.getUserName();
        String designamtion_txt=comments.getDesignation();
        String comment_txt=comments.getComments();
        String tag_count=comments.getTagcount();

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

        if (!designamtion_txt.equalsIgnoreCase("null") && designamtion_txt!=null) {
            holder.department.setText(designamtion_txt);
        }

        if (!username.equalsIgnoreCase("null") && username!=null) {
            holder.username.setText(username);
        }

        if (!comment_txt.equalsIgnoreCase("null") && comment_txt!=null) {
            URLImageParser p = new URLImageParser(holder.comment_txt,_mContext);
            Spanned htmlSpan = Html.fromHtml(comment_txt, p, null);
            holder.comment_txt.setText(htmlSpan);
        }


        if (!tag_count.equalsIgnoreCase("null") && tag_count!=null) {
            if(tag_count.equals("0"))
                holder.tag_lay.setVisibility(View.GONE);
            else {
                holder.tag_lay.setVisibility(View.VISIBLE);
                holder.tagcount_txt.setText("" + tag_count);
                holder.tag_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String recogID =commentses.get(position).getRecognition_id();
                        String commentID=commentses.get(position).getCommentid();
                        tagWondow(recogID,commentID, holder.tag_lay, 300);
                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {

        if(commentses!=null && commentses.size()>0) return commentses.size();
        return 0;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        TextView department,username,comment_txt,tagcount_txt;
        CircularImageView nominatorimg;
        FrameLayout tag_lay;

        public CommentHolder(View convertView) {
            super(convertView);

            nominatorimg = (CircularImageView) convertView.findViewById(R.id.nominatorimg);
            department = (TextView) convertView.findViewById(R.id.department);
            username = (TextView) convertView.findViewById(R.id.username);
            comment_txt= (TextView) convertView.findViewById(R.id.comment_txt);
            tag_lay=(FrameLayout)convertView.findViewById(R.id.tag_lay);
            tagcount_txt=(TextView)convertView.findViewById(R.id.tagcount_txt);
        }
    }


    ProgressDialog progressDialog=null;
    public void tagWondow(final String recogID,final String commentID, FrameLayout likeicon, int windowsize) {
        final PopupWindow changeSortPopUp = new PopupWindow(_mContext);
        changeSortPopUp.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LayoutInflater layoutInflater = (LayoutInflater) _mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.tagwindow_view, null);

        LinearLayout arraow_lay=(LinearLayout)layout.findViewById(R.id.arraow_lay);
        arraow_lay.setVisibility(View.INVISIBLE);
        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView taglist = (RecyclerView) layout.findViewById(R.id.likelist);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });


        new AsyncTask<Void, Void, Void>() {

            String responseString = "";
            String s = "Plase Wait...";

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressLoaderUtilities.getProgressDialog(_mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new CommentTagManager(_mContext).callTagedUser(recogID,commentID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                progressDialog.dismiss();
                if (responseString.equals("100")) {
                    TagPostAdapter tagPostAdapter = new TagPostAdapter(_mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(_mContext);
                    taglist.setLayoutManager(layoutManager);
                    taglist.setItemAnimator(new DefaultItemAnimator());
                    taglist.setAdapter(tagPostAdapter);
                } else {

                }

            }


        }.execute();




        Rect rc = new Rect();
        likeicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        likeicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(layout);

        DisplayMetrics displaymetrics = _mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/1.5));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/1.5));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }
}

