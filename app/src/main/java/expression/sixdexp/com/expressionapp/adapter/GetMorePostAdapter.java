package expression.sixdexp.com.expressionapp.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.GetMorePost;
import expression.sixdexp.com.expressionapp.CommentActivity;
import expression.sixdexp.com.expressionapp.PostDetailsAcitivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.GetMorePostByDate;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LikeAndCommentManager;
import expression.sixdexp.com.expressionapp.manager.SingleLikeAndCommentManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.YouTubePlayer;

/**
 * Created by Praveen on 7/6/2016.
 */
public class GetMorePostAdapter extends RecyclerView.Adapter<GetMorePostAdapter.PostHolder> {

    Context mContext;
    List<GetMorePost> getMorePosts;
    String videoid = "";
    TextView likescountview;
    private MorePostInterface listener;
    String postID = "";
    String postdate="";
    Typeface typeface;
    public interface MorePostInterface {
        public void getMorePost();

        public void commentAt(int commentat);
    }


    public GetMorePostAdapter(Context context, MorePostInterface listener, String postID) {
        mContext = context;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
        this.postID = postID;
        if (postID.equals(""))
            getMorePosts = new GetMorePostManager(mContext).getMorePosts();
        else
            getMorePosts = Constant.singlePostList;//new GetMorePostManager(mContext).getPostsByPostID(postID);
        this.listener = listener;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.postchatitem, parent, false);
        return new PostHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final PostHolder postHolder, int position) {

        final int pos = position;
        GetMorePost getMorePost = getMorePosts.get(position);

        String nominator_imageurl = getMorePost.getNominator_imageurl();
        String nominee_image1 = getMorePost.getNominee_imageurl();
        String nominator_name1 = getMorePost.getNominator_name();
        String nominator_designation1 = getMorePost.getNominator_designation();
        String nominee_name1 = getMorePost.getNominee_name();
        String description1 = getMorePost.getDescription();
        String details = getMorePost.getDetails();
        String likescount1 = getMorePost.getLikes();
        String commentscount1 = getMorePost.getComments();
        String event_date1 = getMorePost.getEvent_date();
        String postType = getMorePost.getType();
        String is_story = getMorePost.getIs_story();
        String awardid = getMorePost.getAwardid();
        String isxpress = getMorePost.getIsxpress();
        String xwSubject = getMorePost.getSubject();
        String recognizeDate = getMorePost.getRecognise_date();
        String otherNominess = getMorePost.getNominee();

        Log.i("postType", "" + postType);


        if (otherNominess.length() > 0) {
            final String[] otherstr = otherNominess.split(",");
            postHolder.othersnominee.setVisibility(View.VISIBLE);
            postHolder.othersnominee.setText("With " + otherstr.length + " others");
            postHolder.othersnominee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expressorDialog(otherstr);
                }
            });
        }

        else{
            postHolder.othersnominee.setVisibility(View.INVISIBLE);
        }


        if (isxpress.equalsIgnoreCase("1")) {
            postHolder.xwicon.setVisibility(View.VISIBLE);
            postHolder.xwsubject.setText("" + xwSubject);
        } else
            postHolder.xwicon.setVisibility(View.GONE);

        if (is_story.equals("2")) {
            postHolder.recognise_date_lay.setVisibility(View.VISIBLE);
            postHolder.recognise_date.setText("" + recognizeDate);
        } else
            postHolder.recognise_date_lay.setVisibility(View.GONE);


        if (is_story.equals("1") || is_story.equals("2")) {
            if (awardid.equals("3"))
                postHolder.thanksimg.setImageResource(R.drawable.awar_applause);
            else if (awardid.equals("5"))
                postHolder.thanksimg.setImageResource(R.drawable.awar_cheerapeer);
            else if (awardid.equals("4"))
                postHolder.thanksimg.setImageResource(R.drawable.awar_dricetolead);
            else if (awardid.equals("7"))
                postHolder.thanksimg.setImageResource(R.drawable.awar_ivalueyou);
            else if (awardid.equals("6"))
                postHolder.thanksimg.setImageResource(R.drawable.awar_thankyou);
        }

        if (postType.equalsIgnoreCase("") || postType.equalsIgnoreCase(" ")) {


            if (is_story.equals("1") || is_story.equals("2")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);

                //with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
            } else {

                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.thanksview.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);

                //with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
            }

        }

        if (postType.equalsIgnoreCase("video")) {

            ImageView attachview = null;
            if (is_story.equals("1") || is_story.equals("2")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.award_videolay.setVisibility(View.VISIBLE);

                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);

                // with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
                attachview = postHolder.award_postvideo;
            } else {
                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.VISIBLE);
                postHolder.doclay.setVisibility(View.GONE);
                postHolder.thanksview.setVisibility(View.GONE);

                // with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);

                attachview = postHolder.postvideo;
            }

            String doc_url_str = getMorePosts.get(position).getUrl();
            URI doc_uri = null;
            try {
                doc_uri = new URI(doc_url_str.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            final String doc_url = doc_uri.toString();

            String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

            Pattern compiledPattern = Pattern.compile(pattern);
            final Matcher matcher = compiledPattern.matcher(doc_url);


            if (matcher.find()) {
                Log.i("video id", "" + matcher.group());
                videoid = matcher.group();
                String thumbnail_url = "http://img.youtube.com/vi/" + videoid + "/0.jpg";

                if (!thumbnail_url.equalsIgnoreCase("null") && !thumbnail_url.equalsIgnoreCase("")
                        && !doc_url.equalsIgnoreCase(" ")) {
                    Picasso.with(mContext)
                            .load(thumbnail_url)
                            .placeholder(R.drawable.default_video_icon)
                            .error(R.drawable.default_video_icon)
                            .into(attachview);
                }

            }


            postHolder.postvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.commentAt(pos);
                    Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                    yiutubeviewActivty.putExtra("youtubeid", videoid);
                    mContext.startActivity(yiutubeviewActivty);
                }
            });
        }




        if (postType.equalsIgnoreCase("doc")) {

            ImageView attachview = null;

            if (is_story.equals("1") || is_story.equals("2")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.award_doclay.setVisibility(View.VISIBLE);

                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);

                // with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                attachview = postHolder.award_postdoc;

            } else {
                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.VISIBLE);
                postHolder.thanksview.setVisibility(View.GONE);

                // with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
                attachview = postHolder.postdoc;

            }


            String doc_url_str = getMorePosts.get(position).getUrl();
            URI doc_uri = null;
            try {
                doc_uri = new URI(doc_url_str.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            final String doc_url = doc_uri.toString();

            attachview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.commentAt(pos);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(doc_url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    mContext.startActivity(intent);

                    /* Log.i("file url",""+doc_url);
                     DownloadTask downloadTask = new DownloadTask(mContext);
                     downloadTask.execute(doc_url);*/
                }
            });


/*
            String text = "<a href='"+doc_url+"'> Download Document</a>\n <a href=https://docs.google.com/viewer?url='\"+doc_url+\"'> View Document</a>";
            Log.i("href23",""+Html.fromHtml(text));
            postHolder.postdoc.setMovementMethod(LinkMovementMethod.getInstance());
            postHolder.postdoc.setText(Html.fromHtml(text));*/
        }






        if (postType.equalsIgnoreCase("image")) {

            ImageView attachview = null;


            if (is_story.equals("1") || is_story.equals("2")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.award_imagelay.setVisibility(View.VISIBLE);

                postHolder.imagelay.setVisibility(View.GONE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);

                // with award view

                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
                attachview = postHolder.award_postimg;
            } else {
                postHolder.imagelay.setVisibility(View.VISIBLE);
                postHolder.videolay.setVisibility(View.GONE);
                postHolder.doclay.setVisibility(View.GONE);
                postHolder.thanksview.setVisibility(View.GONE);

                // with award view
                postHolder.award_imagelay.setVisibility(View.GONE);
                postHolder.award_videolay.setVisibility(View.GONE);
                postHolder.award_doclay.setVisibility(View.GONE);
                attachview = postHolder.postimg;
            }


            Log.i("image111", "" + "iamge111");
            String image_url = getMorePosts.get(position).getUrl();
            URI nominator_uri = null;
            try {
                nominator_uri = new URI(image_url.replace(" ", "%20"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            final String nominator_imageurl1 = nominator_uri.toString();
            if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                    && !nominator_imageurl1.equalsIgnoreCase(" ")) {
                Picasso.with(mContext)
                        .load(nominator_imageurl1)
                        .resize(400,400)
                        .placeholder(R.drawable.default_image_icon)
                        .error(R.drawable.default_image_icon)
                        .into(attachview);
            }

           /* attachview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(nominator_imageurl1));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    mContext.startActivity(intent);
                }
            });*/


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
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(postHolder.nominatorimg);
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
                    .into(postHolder.nominee_image);
        }


        if (nominator_name1 != null && !nominator_name1.equalsIgnoreCase(""))
            postHolder.nominator_name.setText(nominator_name1);

        if (nominator_designation1 != null && !nominator_designation1.equalsIgnoreCase(""))
            postHolder.nominator_designation.setText(nominator_designation1);

        if (nominee_name1 != null && !nominee_name1.equalsIgnoreCase(""))
            postHolder.nominee_name.setText(nominee_name1);

        if (details != null && !details.equalsIgnoreCase("")) {
            postHolder.descriptionlay.setVisibility(View.VISIBLE);
            if (postID.equals("")){
                postHolder.description.setMaxLines(2);
                postHolder.description.setMinLines(1);
            }
            postHolder.description.setTypeface(typeface);
            postHolder.description.setText(details);

            postHolder.description.post(new Runnable() {
                @Override
                public void run() {
                    int countlins = postHolder.description.getLineCount();
                    Log.i("countlins", "" + countlins);
                    if (countlins >=2 && postID.equals("")) {
                        postHolder.moreoption.setVisibility(View.VISIBLE);
                        postHolder.description.setEllipsize(TextUtils.TruncateAt.END);
                    } else {
                        postHolder.moreoption.setVisibility(View.GONE);
                    }
                }
            });


            postHolder.moreoption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.commentAt(pos);
                    Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                    detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                    mContext.startActivity(detailsActitivty);
                   // ((HostActivty)mContext).finish();

                }
            });



        }
        else{
            postHolder.descriptionlay.setVisibility(View.GONE);
        }

        if (likescount1 != null && !likescount1.equalsIgnoreCase(""))
            postHolder.likescount.setText(likescount1 + " Likes");

        if (commentscount1 != null && !commentscount1.equalsIgnoreCase(""))
            postHolder.commentscount.setText(commentscount1 + " Comments");

        if (event_date1 != null && !event_date1.equalsIgnoreCase(""))
            postHolder.event_date.setText(event_date1);



      if(getMorePosts.get(pos).getUlikes().equalsIgnoreCase("1")){
          postHolder.likeit.setVisibility(View.GONE);
          postHolder.unlikeit.setVisibility(View.VISIBLE);
      }
        else{
          postHolder.unlikeit.setVisibility(View.GONE);
          postHolder.likeit.setVisibility(View.VISIBLE);

      }

        postHolder.likeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postHolder.likeit.setVisibility(View.GONE);
                postHolder.unlikeit.setVisibility(View.VISIBLE);

                String postid = getMorePosts.get(pos).getRecognition_id();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new PostLike().execute(postid);
            }
        });

        postHolder.unlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postHolder.unlikeit.setVisibility(View.GONE);
                postHolder.likeit.setVisibility(View.VISIBLE);

                String postid = getMorePosts.get(pos).getRecognition_id();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new PostLike().execute(postid);
            }
        });



        postHolder.commentit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.commentAt(pos);
                String postid = getMorePosts.get(pos).getRecognition_id();
                Intent commnetIntent = new Intent(mContext, CommentActivity.class);
                commnetIntent.putExtra("recognizationID", postid);
                if (!postID.equalsIgnoreCase(""))
                    commnetIntent.putExtra("singlepost", "1");
                else
                    commnetIntent.putExtra("singlepost", "0");
                mContext.startActivity(commnetIntent);
            }
        });

        if (postID.equals("")) {
            if (position == getMorePosts.size() - 1) {
                postHolder.morepost.setVisibility(View.VISIBLE);
                postHolder.morepost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.lastpostofPOST = getMorePosts.size() - 1;
                        //Constant.today--;

                        postdate=getMorePosts.get(Constant.lastpostofPOST).getDate();
                        new GetMorePostTask().execute();
                    }
                });
            }

            else{
                postHolder.morepost.setVisibility(View.GONE);
            }
        }

        else{
            postHolder.morepost.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
        if (getMorePosts != null && getMorePosts.size() > 0) {
            return getMorePosts.size();
        }

        return 0;
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        RoundedImageView nominatorimg;
        expression.sixdexp.com.expressionapp.utility.RoundedImageView nominee_image;
        TextView nominator_name, nominator_designation, nominee_name, description, likescount, commentscount, event_date;
        LinearLayout likeit, unlikeit, commentit;
        LinearLayout xwicon;
        TextView xwsubject;

        ImageView thanksimg, postimg, postdoc;
        ImageView postvideo;
        LinearLayout imagelay, doclay;
        FrameLayout thanksview, videolay;
        LinearLayout morepost;


        // with award view
        LinearLayout award_imagelay, award_doclay;
        FrameLayout award_videolay;
        ImageView award_postvideo, award_postimg, award_postdoc;

        LinearLayout recognise_date_lay,descriptionlay;
        TextView recognise_date, othersnominee;
        TextView moreoption;

        public PostHolder(View convertView) {
            super(convertView);

            nominatorimg = (RoundedImageView) convertView.findViewById(R.id.nominatorimg);
           // nominee_image = (CircularImageView) convertView.findViewById(R.id.nominee_image);
            nominee_image = (expression.sixdexp.com.expressionapp.utility.RoundedImageView) convertView.findViewById(R.id.nominee_image);
            nominator_name = (TextView) convertView.findViewById(R.id.nominator_name);
            nominator_designation = (TextView) convertView.findViewById(R.id.nominator_designation);
            nominee_name = (TextView) convertView.findViewById(R.id.nominee_name);
            description = (TextView) convertView.findViewById(R.id.description);
            likescount = (TextView) convertView.findViewById(R.id.likescount);
            commentscount = (TextView) convertView.findViewById(R.id.commentscount);
            othersnominee = (TextView) convertView.findViewById(R.id.othersnominee);
            event_date = (TextView) convertView.findViewById(R.id.event_date);
            postdoc = (ImageView) convertView.findViewById(R.id.postdoc);
            likeit = (LinearLayout) convertView.findViewById(R.id.likeit);
            unlikeit = (LinearLayout) convertView.findViewById(R.id.unlikeit);
            commentit = (LinearLayout) convertView.findViewById(R.id.commentit);
            thanksimg = (ImageView) convertView.findViewById(R.id.thanksimg);
            postimg = (ImageView) convertView.findViewById(R.id.postimg);
            postvideo = (ImageView) convertView.findViewById(R.id.postvideo);

            moreoption= (TextView) convertView.findViewById(R.id.moreoption);

            imagelay = (LinearLayout) convertView.findViewById(R.id.imagelay);
            videolay = (FrameLayout) convertView.findViewById(R.id.videolay);
            doclay = (LinearLayout) convertView.findViewById(R.id.doclay);
            thanksview = (FrameLayout) convertView.findViewById(R.id.thanksview);

            morepost = (LinearLayout) convertView.findViewById(R.id.morepost);


            // with award view
            award_imagelay = (LinearLayout) convertView.findViewById(R.id.award_imagelay);
            award_videolay = (FrameLayout) convertView.findViewById(R.id.award_videolay);
            award_doclay = (LinearLayout) convertView.findViewById(R.id.award_doclay);

            award_postimg = (ImageView) convertView.findViewById(R.id.award_postimg);
            award_postvideo = (ImageView) convertView.findViewById(R.id.award_postvideo);
            award_postdoc = (ImageView) convertView.findViewById(R.id.award_postdoc);
            //xw icon
            xwicon = (LinearLayout) convertView.findViewById(R.id.xwicon);
            xwsubject = (TextView) convertView.findViewById(R.id.xwsubject);

            recognise_date_lay = (LinearLayout) convertView.findViewById(R.id.recognise_date_lay);
            recognise_date = (TextView) convertView.findViewById(R.id.recognise_date);

            descriptionlay=(LinearLayout)convertView.findViewById(R.id.descriptionlay);
        }
    }


    ProgressDialog progressDialog;

    public class PostLike extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String postid = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                postid = params[0];
                if (!postID.equalsIgnoreCase(""))
                    responseString = new SingleLikeAndCommentManager(mContext).postLike(postid);
                else
                    responseString = new LikeAndCommentManager(mContext).postLike(postid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (responseString.equals("100")) {
                List<GetMorePost> postlist;
                if (!postID.equalsIgnoreCase(""))
                    postlist = Constant.singlePostList;
                else
                    postlist = new LikeAndCommentManager(mContext).getPostByRecognizationID(postid);
                try {
                    likescountview.setText(postlist.get(0).getLikes() + " Likes");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

            }

        }


    }


    public class GetMorePostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostByDate(mContext,postdate).callGetMorePost();
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
                listener.getMorePost();

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }

    public void expressorDialog(String[] otherNominees) {

        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.xpressordialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView xpressorlist = (ListView) window.findViewById(R.id.xpressorlist);
        ImageView cancel = (ImageView) window.findViewById(R.id.cancel);
        Button submit = (Button) window.findViewById(R.id.submit);
        LinearLayout titlebar = (LinearLayout) window.findViewById(R.id.titlebar);
        TextView title = (TextView) window.findViewById(R.id.title);
        //titlebar.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        title.setTextSize(20);
        title.setText("Other Nominees");
        final List<String> selexpressorstr = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, otherNominees);
        xpressorlist.setAdapter(adapter);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


    }





}
