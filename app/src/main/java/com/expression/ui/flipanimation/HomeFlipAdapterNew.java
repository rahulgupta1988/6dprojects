package com.expression.ui.flipanimation;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.aphidmobile.utils.AphidLog;
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


public class HomeFlipAdapterNew extends BaseAdapter
{
    Context mcontext;
    private LayoutInflater inflater;
    private int repeatCount = 1;
    private List<Travels.Data> travelData;
    ProgressDialog progressDialog;
    Context mContext;
    List<GetMorePost> getMorePosts;
    String videoid = "";
    TextView likescountview;
    String postID = "";
    String postdate="";
    Typeface typeface;

    public HomeFlipAdapterNew(Context context)
    {
        /*this.mcontext=context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);

        getMorePosts = new GetMorePostManager(context).getMorePosts();
        Toast.makeText(context, "AdapterPostSizeNew=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();*/

        mContext = context;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
        this.postID = postID;
        if (postID.equals(""))
            getMorePosts = new GetMorePostManager(mContext).getMorePosts();
        else
            getMorePosts = Constant.singlePostList;//new GetMorePostManager(mContext).getPostsByPostID(postID);
    }

    @Override
    public int getCount() {
        return getMorePosts.size() * repeatCount;
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RoundedImageView nominatorimg;
        expression.sixdexp.com.expressionapp.utility.RoundedImageView nominee_image;
        final TextView nominator_name, nominator_designation, nominee_name, description, likescount, commentscount, event_date;
        final LinearLayout likeit, unlikeit, commentit;
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
        final TextView moreoption;

        final ImageView photo,notification_img,share_img,email_img;
        View layout = convertView;
        if (convertView == null)
        {
      /* layout = inflater.inflate(R.layout.homepageadapterflip, null);  //homepageflip*/
            //layout = inflater.inflate(R.layout.postchatitem, null);  //homepageflip
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.postchatitemnew, parent, false);

            AphidLog.d("created new view from adapter: %d", position);
        }
      //  final Travels.Data data = travelData.get(position % travelData.size());

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


        nominatorimg = (RoundedImageView) layout.findViewById(R.id.nominatorimg);
        // nominee_image = (CircularImageView) layout.findViewById(R.id.nominee_image);
        nominee_image = (expression.sixdexp.com.expressionapp.utility.RoundedImageView) layout.findViewById(R.id.nominee_image);
        nominator_name = (TextView) layout.findViewById(R.id.nominator_name);
        nominator_designation = (TextView) layout.findViewById(R.id.nominator_designation);
        nominee_name = (TextView) layout.findViewById(R.id.nominee_name);
        description = (TextView) layout.findViewById(R.id.description);
        likescount = (TextView) layout.findViewById(R.id.likescount);
        commentscount = (TextView) layout.findViewById(R.id.commentscount);
        othersnominee = (TextView) layout.findViewById(R.id.othersnominee);
        event_date = (TextView) layout.findViewById(R.id.event_date);
        postdoc = (ImageView) layout.findViewById(R.id.postdoc);
        likeit = (LinearLayout) layout.findViewById(R.id.likeit);
        unlikeit = (LinearLayout) layout.findViewById(R.id.unlikeit);
        commentit = (LinearLayout) layout.findViewById(R.id.commentit);
        thanksimg = (ImageView) layout.findViewById(R.id.thanksimg);
        postimg = (ImageView) layout.findViewById(R.id.postimg);
        postvideo = (ImageView) layout.findViewById(R.id.postvideo);

        moreoption= (TextView) layout.findViewById(R.id.moreoption);

        imagelay = (LinearLayout) layout.findViewById(R.id.imagelay);
        videolay = (FrameLayout) layout.findViewById(R.id.videolay);
        doclay = (LinearLayout) layout.findViewById(R.id.doclay);
        thanksview = (FrameLayout) layout.findViewById(R.id.thanksview);

        morepost = (LinearLayout) layout.findViewById(R.id.morepost);


        // with award view
        award_imagelay = (LinearLayout) layout.findViewById(R.id.award_imagelay);
        award_videolay = (FrameLayout) layout.findViewById(R.id.award_videolay);
        award_doclay = (LinearLayout) layout.findViewById(R.id.award_doclay);

        award_postimg = (ImageView) layout.findViewById(R.id.award_postimg);
        award_postvideo = (ImageView) layout.findViewById(R.id.award_postvideo);
        award_postdoc = (ImageView) layout.findViewById(R.id.award_postdoc);
        //xw icon
        xwicon = (LinearLayout) layout.findViewById(R.id.xwicon);
        xwsubject = (TextView) layout.findViewById(R.id.xwsubject);

        recognise_date_lay = (LinearLayout) layout.findViewById(R.id.recognise_date_lay);
        recognise_date = (TextView) layout.findViewById(R.id.recognise_date);

        descriptionlay=(LinearLayout)layout.findViewById(R.id.descriptionlay);

        /*=========================================================================*/



        if (otherNominess.length() > 0) {
            final String[] otherstr = otherNominess.split(",");
            othersnominee.setVisibility(View.VISIBLE);
            othersnominee.setText("With " + otherstr.length + " others");
            othersnominee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expressorDialog(otherstr);
                }
            });
        }

        else{
            othersnominee.setVisibility(View.INVISIBLE);
        }


        if (isxpress.equalsIgnoreCase("1")) {
            xwicon.setVisibility(View.VISIBLE);
            xwsubject.setText("" + xwSubject);
        } else
            xwicon.setVisibility(View.GONE);

        if (is_story.equals("2")) {
            recognise_date_lay.setVisibility(View.VISIBLE);
            recognise_date.setText("" + recognizeDate);
        } else
            recognise_date_lay.setVisibility(View.GONE);


        if (is_story.equals("1") || is_story.equals("2")) {
            if (awardid.equals("3"))
                thanksimg.setImageResource(R.drawable.awar_applause);
            else if (awardid.equals("5"))
                thanksimg.setImageResource(R.drawable.awar_cheerapeer);
            else if (awardid.equals("4"))
                thanksimg.setImageResource(R.drawable.awar_dricetolead);
            else if (awardid.equals("7"))
                thanksimg.setImageResource(R.drawable.awar_ivalueyou);
            else if (awardid.equals("6"))
                thanksimg.setImageResource(R.drawable.awar_thankyou);
        }

        if (postType.equalsIgnoreCase("") || postType.equalsIgnoreCase(" ")) {


            if (is_story.equals("1") || is_story.equals("2")) {
                thanksview.setVisibility(View.VISIBLE);
                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);

                //with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
            } else {

                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);

                //with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
            }

        }


       /* ===============================video view=========================================================*/

        if (postType.equalsIgnoreCase("video")) {

            ImageView attachview = null;
            if (is_story.equals("1") || is_story.equals("2")) {
                thanksview.setVisibility(View.VISIBLE);
                award_videolay.setVisibility(View.VISIBLE);

                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
                attachview = award_postvideo;
            } else {
                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.VISIBLE);
                doclay.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);

                attachview = postvideo;
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


            postvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //listener.commentAt(pos);  //comment1 by ramji
                    Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                    yiutubeviewActivty.putExtra("youtubeid", videoid);
                    mContext.startActivity(yiutubeviewActivty);
                }
            });
        }

               /* ===============================doc view=========================================================*/



        if (postType.equalsIgnoreCase("doc")) {

            ImageView attachview = null;

            if (is_story.equals("1") || is_story.equals("2")) {
                thanksview.setVisibility(View.VISIBLE);
                award_doclay.setVisibility(View.VISIBLE);

                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                attachview = award_postdoc;

            } else {
                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.VISIBLE);
                thanksview.setVisibility(View.GONE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
                attachview = postdoc;

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
                    //listener.commentAt(pos);   //comment2 by ramji
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
                thanksview.setVisibility(View.VISIBLE);
                award_imagelay.setVisibility(View.VISIBLE);

                imagelay.setVisibility(View.GONE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);

                // with award view

                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
                attachview = award_postimg;
            } else {
                imagelay.setVisibility(View.VISIBLE);
                videolay.setVisibility(View.GONE);
                doclay.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);
                attachview = postimg;
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

        if (details != null && !details.equalsIgnoreCase("")) {
            descriptionlay.setVisibility(View.VISIBLE);
            if (postID.equals("")){
                description.setMaxLines(2);
                description.setMinLines(1);
            }
            description.setTypeface(typeface);
            description.setText(details);

           description.post(new Runnable() {
                @Override
                public void run() {
                    int countlins = description.getLineCount();
                    Log.i("countlins", "" + countlins);
                    if (countlins >=2 && postID.equals("")) {
                        moreoption.setVisibility(View.VISIBLE);
                        description.setEllipsize(TextUtils.TruncateAt.END);
                    } else {
                        moreoption.setVisibility(View.GONE);
                    }
                }
            });


            moreoption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // listener.commentAt(pos);
                    Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                    detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                    mContext.startActivity(detailsActitivty);

                }
            });



        }
        else{
            descriptionlay.setVisibility(View.GONE);
        }

        if (likescount1 != null && !likescount1.equalsIgnoreCase(""))
            likescount.setText(likescount1 + " Likes");

        if (commentscount1 != null && !commentscount1.equalsIgnoreCase(""))
            commentscount.setText(commentscount1 + " Comments");

        if (event_date1 != null && !event_date1.equalsIgnoreCase(""))
            event_date.setText(event_date1);

        if(getMorePosts.get(pos).getUlikes().equalsIgnoreCase("1")){
           likeit.setVisibility(View.GONE);
           unlikeit.setVisibility(View.VISIBLE);
        }
        else{
            unlikeit.setVisibility(View.GONE);
            likeit.setVisibility(View.VISIBLE);

        }

        likeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               likeit.setVisibility(View.GONE);
               unlikeit.setVisibility(View.VISIBLE);

                String postid = getMorePosts.get(pos).getRecognition_id();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = likescount;
                new PostLike().execute(postid);
            }
        });

        unlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unlikeit.setVisibility(View.GONE);
                likeit.setVisibility(View.VISIBLE);

                String postid = getMorePosts.get(pos).getRecognition_id();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = likescount;
                new PostLike().execute(postid);
            }
        });


        commentit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //listener.commentAt(pos);
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
                morepost.setVisibility(View.VISIBLE);
                morepost.setOnClickListener(new View.OnClickListener() {
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
                morepost.setVisibility(View.GONE);
            }
        }

        else{
            morepost.setVisibility(View.GONE);
        }




        return layout;
    }

    public void removeData(int index) {
        if (travelData.size() > 1) {
            travelData.remove(index);
        }
    }


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
            if (responseString.equals("100"))
            {
                //listener.getMorePost();

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
