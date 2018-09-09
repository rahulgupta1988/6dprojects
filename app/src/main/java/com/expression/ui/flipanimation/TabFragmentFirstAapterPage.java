package com.expression.ui.flipanimation;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.aphidmobile.utils.AphidLog;
import com.bumptech.glide.Glide;
import com.expression.ui.TabFragment1;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.Comments;
import db.DaoSession;
import db.GetMorePost;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.FullimageActivity;
import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.PostDetailsAcitivity;
import expression.sixdexp.com.expressionapp.ProfileDetails_Search;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.adapter.CommentListAdapter;
import expression.sixdexp.com.expressionapp.adapter.CommentViewAdapter;
import expression.sixdexp.com.expressionapp.adapter.EmojiAdapter;
import expression.sixdexp.com.expressionapp.adapter.LikeGivenAdapter;
import expression.sixdexp.com.expressionapp.adapter.TagPostAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.CommentManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostByDate;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LikeAndCommentManager;
import expression.sixdexp.com.expressionapp.manager.LikeManager;
import expression.sixdexp.com.expressionapp.manager.LogOutManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.SingleLikeAndCommentManager;
import expression.sixdexp.com.expressionapp.manager.TagPostManager;
import expression.sixdexp.com.expressionapp.manager.UserInfoManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.DynamicImageView;
import expression.sixdexp.com.expressionapp.utility.ImageGetterAsyncTask;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.utility.SharingInterface;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;
import expression.sixdexp.com.expressionapp.utility.YouTubePlayer;

public class TabFragmentFirstAapterPage extends BaseAdapter {
    /*Context mcontext;*/
    private LayoutInflater inflater;
    private int repeatCount = 1;
    private List<Travels.Data> travelData;
    ProgressDialog progressDialog;
    Context mContext;
    List<GetMorePost> getMorePosts;
    String videoid = "";
    TextView likescountview;
    String postID = "";
    String postdate = "";
    Typeface typeface;
    FragmentManager frgManager;
    FlipViewController flipView1;
    TabFragment1 tabFragment1 = null;

    public TabFragmentFirstAapterPage(Context context, FlipViewController flipView, TabFragment1 tabFragment1) {
        /*this.mcontext=context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);

        getMorePosts = new GetMorePostManager(context).getMorePosts();
        Toast.makeText(context, "AdapterPostSizeNew=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();*/

        this.tabFragment1 = tabFragment1;
        flipView1 = flipView;
        mContext = context;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
        this.postID = postID;
        if (postID.equals(""))
            getMorePosts = new GetMorePostManager(mContext).getMorePosts();
        else
            getMorePosts = Constant.singlePostList;//new GetMorePostManager(mContext).getPostsByPostID(postID);


        Log.i("getMorePosts size11111",""+getMorePosts.size());

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
    public View getView(final int position, View convertView, ViewGroup parent) {


        CircularImageView nominatorimg, nominatorimg1, nominee_image;
        //expression.sixdexp.com.expressionapp.utility.RoundedImageView nominee_image;
        final TextView nominator_name, nominator_name_new, nominator_designation, nominator_designation_new,
                nominee_name, description, likescount, commentscount,tag_count_txt, event_date;
        final LinearLayout likeit, unlikeit;
        final ImageView commentit, likeicon, commenticon,tag_icon;
        LinearLayout xwicon;
        TextView xwsubject;

        ImageView thanksimg, postimg, postdoc;
        ImageView postvideo;
        LinearLayout imagelay, doclay;
        FrameLayout thanksview, videolay;
        LinearLayout morepost, up_arrow_icon;

        //  LinearLayout testforawardandthankview;
        LinearLayout user_details_ll;

        // with award view
        LinearLayout award_imagelay, award_doclay;
        FrameLayout award_videolay;
        ImageView award_postvideo, award_postimg, award_postdoc;

        final LinearLayout recognise_date_lay, descriptionlay;
        TextView recognise_date, othersnominee;
        final TextView moreoption;


        // donor icon
        ImageView nominator_donor_icon,nominee_donor_icon,donor_icon;

        ImageView playvideo;
        final ImageView notification_img, share_img, email_img;
        LinearLayout notification_ll;
        View layout = convertView;
        if (convertView == null) {
            new DynamicImageView(mContext);
      /* layout = inflater.inflate(R.layout.homepageadapterflip, null);  //homepageflip*/
            //layout = inflater.inflate(R.layout.postchatitem, null);  //homepageflip
//            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabfragment_adapter, parent, false);

//            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfinal, parent, false);
            if (Constant.sc_height < 900) {
                layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.floating_bar_small, parent, false);
            } else {
                layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.floating_bar, parent, false);
            }

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
        final String details = getMorePost.getDetails();
        String likescount1 = getMorePost.getLikes();
        final String commentscount1 = getMorePost.getComments();
        String event_date1 = getMorePost.getEvent_date();
        final String postType = getMorePost.getType();
        final String is_story = getMorePost.getIs_story();
        String awardid = getMorePost.getAwardid();
        final String isxpress = getMorePost.getIsxpress();
        String xwSubject = getMorePost.getSubject();
        String recognizeDate = getMorePost.getRecognise_date();
        String otherNominess = getMorePost.getNominee();

        String isNominatorDonar=getMorePost.getIsNominatorDonar();
        String isNomineeDonor=getMorePost.getIsNomineeDonor();

        String taguserid=getMorePost.getTaguserid();
        String tagcount=getMorePost.getTagcount();



        int nomineecoun=0;
        try {
            nomineecoun=Integer.parseInt(getMorePost.getCount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Log.i("postType", "" + postType);

        notification_ll = (LinearLayout) layout.findViewById(R.id.notification_ll);
        notification_img = (ImageView) layout.findViewById(R.id.notification_img);
        share_img = (ImageView) layout.findViewById(R.id.share_img);
        email_img = (ImageView) layout.findViewById(R.id.email_img);
        share_img.setVisibility(View.GONE);
        email_img.setVisibility(View.GONE);
        notification_img.setBackgroundResource(R.drawable.more_disable);
        notification_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (share_img.getVisibility() == View.VISIBLE) {
                    // first visible
                    share_img.setVisibility(View.GONE);
                    email_img.setVisibility(View.GONE);
                    notification_img.setBackgroundResource(R.drawable.more_disable);
                } else {
                    // first gone
                    notification_img.setBackgroundResource(R.drawable.more_enable);
                    share_img.setVisibility(View.VISIBLE);
                    email_img.setVisibility(View.VISIBLE);

                }
            }
        });

        final LinearLayout first_ll=(LinearLayout)layout.findViewById(R.id.first_ll);
        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Coming Soon...", Toast.LENGTH_SHORT).show();
                String postid=getMorePosts.get(pos).getRecognition_id();

                String userid=getMorePosts.get(pos).getUserid();
                new SharingInterface(mContext).shareSocial(share_img,first_ll,postid,userid);
            }
        });

        email_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Coming Soon...", Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayout bottom_ll=(LinearLayout)layout.findViewById(R.id.bottom_ll);

        nominatorimg = (CircularImageView) layout.findViewById(R.id.nominatorimg);
        nominatorimg1 = (CircularImageView) layout.findViewById(R.id.nominatorimg1);
        // nominee_image = (CircularImageView) layout.findViewById(R.id.nominee_image);
        nominee_image = (CircularImageView) layout.findViewById(R.id.nominee_image);
        nominator_name = (TextView) layout.findViewById(R.id.nominator_name);
        nominator_name_new = (TextView) layout.findViewById(R.id.nominator_name_new);
        nominator_designation = (TextView) layout.findViewById(R.id.nominator_designation);
        nominator_designation_new = (TextView) layout.findViewById(R.id.nominator_designation_new);
        nominee_name = (TextView) layout.findViewById(R.id.nominee_name);
        description = (TextView) layout.findViewById(R.id.description);
        likescount = (TextView) layout.findViewById(R.id.likescount);
        commentscount = (TextView) layout.findViewById(R.id.commentscount);
        tag_count_txt = (TextView) layout.findViewById(R.id.tag_count_txt);
        othersnominee = (TextView) layout.findViewById(R.id.othersnominee);
        event_date = (TextView) layout.findViewById(R.id.event_date);
        postdoc = (ImageView) layout.findViewById(R.id.postdoc);
        likeit = (LinearLayout) layout.findViewById(R.id.likeit);
        unlikeit = (LinearLayout) layout.findViewById(R.id.unlikeit);
        commentit = (ImageView) layout.findViewById(R.id.commentit);
        likeicon = (ImageView) layout.findViewById(R.id.likeicon);
        commenticon = (ImageView) layout.findViewById(R.id.commenticon);
        tag_icon = (ImageView) layout.findViewById(R.id.tag_icon);
        thanksimg = (ImageView) layout.findViewById(R.id.thanksimg);
        postimg = (ImageView) layout.findViewById(R.id.postimg);
        postvideo = (ImageView) layout.findViewById(R.id.postvideo);

        playvideo= (ImageView) layout.findViewById(R.id.playvideo);

        moreoption = (TextView) layout.findViewById(R.id.moreoption);

        imagelay = (LinearLayout) layout.findViewById(R.id.imagelay);
        videolay = (FrameLayout) layout.findViewById(R.id.videolay);
        doclay = (LinearLayout) layout.findViewById(R.id.doclay);
        thanksview = (FrameLayout) layout.findViewById(R.id.thanksview);
        //  testforawardandthankview=(LinearLayout) layout.findViewById(R.id.testforawardandthankview);
        user_details_ll = (LinearLayout) layout.findViewById(R.id.user_details_ll);

        morepost = (LinearLayout) layout.findViewById(R.id.morepost);
        up_arrow_icon = (LinearLayout) layout.findViewById(R.id.up_arrow_icon);

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

        descriptionlay = (LinearLayout) layout.findViewById(R.id.descriptionlay);


        // donor icon views
        nominator_donor_icon=(ImageView)layout.findViewById(R.id.nominator_donor_icon);
        nominee_donor_icon=(ImageView)layout.findViewById(R.id.nominee_donor_icon);
        donor_icon=(ImageView)layout.findViewById(R.id.donor_icon);




        /*=========================================================================*/



        if(isNominatorDonar.equals("1")){
            nominator_donor_icon.setVisibility(View.VISIBLE);
            donor_icon.setVisibility(View.VISIBLE);
        }

        else{
            nominator_donor_icon.setVisibility(View.GONE);
            donor_icon.setVisibility(View.GONE);
        }

        if(isNomineeDonor.equals("1")){
            nominee_donor_icon.setVisibility(View.VISIBLE);

        }

        else{
            nominee_donor_icon.setVisibility(View.GONE);

        }



        if (nomineecoun > 0) {
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

        if (is_story.equals("2") ) {
            recognise_date_lay.setVisibility(View.VISIBLE);
            recognise_date.setText("" + recognizeDate);
        } else
            recognise_date_lay.setVisibility(View.GONE);


        if (is_story.equals("1") || is_story.equals("2")||is_story.equals("4")) {
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
            else if (awardid.equals("8"))
                thanksimg.setImageResource(R.drawable.thank_you_book_icon);
        }

        if (postType.equalsIgnoreCase("") || postType.equalsIgnoreCase(" ")) {


            if (is_story.equals("1") || is_story.equals("2")||is_story.equals("4")) {
//                testforawardandthankview.setVisibility(View.VISIBLE);
                //  testforawardandthankview.setVisibility(View.GONE);
                thanksview.setVisibility(View.VISIBLE);
                user_details_ll.setVisibility(View.GONE);
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
                // testforawardandthankview.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);
                user_details_ll.setVisibility(View.VISIBLE);
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
                // testforawardandthankview.setVisibility(View.VISIBLE);
                thanksview.setVisibility(View.VISIBLE);
                user_details_ll.setVisibility(View.GONE);
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
                //testforawardandthankview.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);
                user_details_ll.setVisibility(View.VISIBLE);

                // with award view
                award_imagelay.setVisibility(View.GONE);
                award_videolay.setVisibility(View.GONE);
                award_doclay.setVisibility(View.GONE);

                attachview = postvideo;
            }

            String doc_url_str = getMorePosts.get(position).getUrl();
            URI doc_uri = null;

            if(doc_url_str.contains("https://web.microsoftstream.com")){

                playvideo.setVisibility(View.GONE);
                try {
                    final String url_str1 = doc_url_str.replace(" ", "%20");
                    attachview.setImageResource(R.drawable.stream_preview);


                    postvideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(url_str1));
                            mContext.startActivity(webIntent);
                        }
                    });

                    award_postvideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(url_str1));
                            mContext.startActivity(webIntent);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }





            }

            else{

                playvideo.setVisibility(View.VISIBLE);

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
                                .centerInside()
                                .resize(200,200)
                                .onlyScaleDown()
                                .placeholder(R.drawable.default_video_icon)
                                .error(R.drawable.default_image_icon)
                                .into(attachview);

                        Glide.with(mContext)
                                .load(thumbnail_url)
                                .placeholder(R.drawable.default_image_icon)
                                .error(R.drawable.default_image_icon)
                                .crossFade()
                                .thumbnail(0.1f)
                                .fitCenter()
                                .into(attachview);


                    }

                    else{
                        attachview.setImageResource(R.drawable.default_image_icon);
                    }

                }


        /*    postvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //listener.commentAt(pos);  //comment1 by ramji
                    Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                    yiutubeviewActivty.putExtra("youtubeid", videoid);
                    mContext.startActivity(yiutubeviewActivty);
                }
            });*/

                postvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final String doc_url_str = getMorePosts.get(position).getUrl();
                        Log.i("4534", "" + doc_url_str);
                        String videoid = getVideoID(doc_url_str);
                        //listener.commentAt(pos);  //comment1 by ramji
                        Log.i("45341", "" + videoid);
                        Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                        yiutubeviewActivty.putExtra("youtubeid", videoid);
                        Constant.lastposition_home = pos;
                        mContext.startActivity(yiutubeviewActivty);


                    }
                });

                award_postvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //listener.commentAt(pos);  //comment1 by ramji

                        final String doc_url_str = getMorePosts.get(position).getUrl();
                        Log.i("4534", "" + doc_url_str);
                        String videoid = getVideoID(doc_url_str);
                        //listener.commentAt(pos);  //comment1 by ramji
                        Log.i("45341", "" + videoid);
                        Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                        yiutubeviewActivty.putExtra("youtubeid", videoid);
                        Constant.lastposition_home = pos;
                        mContext.startActivity(yiutubeviewActivty);
                    }
                });

            }




        }

               /* ===============================doc view=========================================================*/


        if (postType.equalsIgnoreCase("doc")) {

            ImageView attachview = null;

            if (is_story.equals("1") || is_story.equals("2")) {
                //  testforawardandthankview.setVisibility(View.VISIBLE);
                thanksview.setVisibility(View.VISIBLE);
                user_details_ll.setVisibility(View.GONE);
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
                // testforawardandthankview.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);
                user_details_ll.setVisibility(View.VISIBLE);

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
                    Constant.lastposition_home = pos;
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


            if (is_story.equals("1") || is_story.equals("2") ||is_story.equals("4")) {
                // testforawardandthankview.setVisibility(View.VISIBLE);
                thanksview.setVisibility(View.VISIBLE);
                user_details_ll.setVisibility(View.GONE);
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
                // testforawardandthankview.setVisibility(View.GONE);
                thanksview.setVisibility(View.GONE);
                user_details_ll.setVisibility(View.VISIBLE);
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
              /*  Picasso.with(mContext)
                        .load(nominator_imageurl1)
                        .resize(200,200)
                        .onlyScaleDown()
                        .centerInside()
                        .placeholder(R.drawable.default_image_icon)
                        .error(R.drawable.default_image_icon)
                        .into(attachview);*/
                Glide.with(mContext)
                        .load(nominator_imageurl1)
                        .placeholder(R.drawable.default_image_icon)
                        .error(R.drawable.default_image_icon)
                        .crossFade()
                        .thumbnail(0.1f)
                        .centerCrop()
                        .into(attachview);
            }

            else{
                attachview.setImageResource(R.drawable.default_image_icon);

            }


           /* attachview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(nominator_imageurl1));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    mContext.startActivity(intent);
                }
            });*/



            attachview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                            && !nominator_imageurl1.equalsIgnoreCase(" ")) {

                        Intent intent = new Intent(mContext, FullimageActivity.class);
                        intent.putExtra("imageval", nominator_imageurl1);
                        Constant.lastposition_home = pos;
                        mContext.startActivity(intent);

                    }
                }
            });


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
                    .resize(100, 100)
                    .onlyScaleDown().centerInside()
                    /*.centerCrop()*/
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(nominatorimg);

            Picasso.with(mContext)
                    .load(nominator_imageurl1)
                    .resize(100, 100)
                    .onlyScaleDown().centerInside()
                    /*.centerCrop()*/
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(nominatorimg1);


        }

        else{
            nominatorimg.setImageResource(R.drawable.default_square_user);
            nominatorimg1.setImageResource(R.drawable.default_square_user);
        }


        nominatorimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchUserID = getMorePosts.get(pos).getUserid();
                new GetUserProfile().execute(searchUserID);
            }
        });


        nominatorimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchUserID = getMorePosts.get(pos).getUserid();
                new GetUserProfile().execute(searchUserID);
            }
        });


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
                    .resize(100, 100)
                    .onlyScaleDown().centerInside()
                  /*  .centerCrop()*/
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(nominee_image);
        }

        else{
            nominee_image.setImageResource(R.drawable.default_profile_picture);
        }

        if (nominator_name1 != null) {
            nominator_name.setText(nominator_name1);
            nominator_name_new.setText(nominator_name1);
        }

        if (nominator_designation1 != null) {
            nominator_designation.setText(nominator_designation1);
            nominator_designation_new.setText(nominator_designation1);
        }

        if (nominee_name1 != null)
            nominee_name.setText(nominee_name1);

        if (details != null && !details.equalsIgnoreCase("")) {
            description.setText("");
            // description.setEllipsize(TextUtils.TruncateAt.END);


            descriptionlay.post(new Runnable() {
                @Override
                public void run() {


                    // description.setMaxLines(2);
                    // description.setMinLines(1);

                  /*  float x=descriptionlay.getX();
                    float y=descriptionlay.getY();

                    float x1=bottom_ll.getX();
                    float y1=bottom_ll.getY();

                    double dis=Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));

                    Log.i("dis892",""+ bottom_ll.getHeight());

               *//*     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, (int)dis);
                   // layoutParams.setMargins(15,15,15,10);
                    descriptionlay.setLayoutParams(layoutParams);*//*
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, (int)dis-bottom_ll.getHeight());



                    descriptionlay.setLayoutParams(layoutParams1);*/

                    // description.setMovementMethod(new ScrollingMovementMethod());

                    if (postID.equals("")) {
              /*  description.setMaxLines(2);
                description.setMinLines(1);*/


                        Log.i("Screen size",""+getdeviceInch());


                        if(getdeviceInch()>=4.9){

                            if(is_story.equals("1") || is_story.equals("2") ||is_story.equals("4")){

                                if (isxpress.equalsIgnoreCase("1") &&(postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc"))){
                                    description.setMaxLines(3);
                                    description.setMinLines(1);
                                }
                                else if (isxpress.equalsIgnoreCase("1")){
                                    description.setMaxLines(11);
                                    description.setMinLines(1);
                                }

                                else if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc")) {
                                    description.setMaxLines(4);
                                    description.setMinLines(1);
                                }
                                else{
                                    description.setMaxLines(13);
                                    description.setMinLines(1);
                                }
                            }

                            else{

                                if (isxpress.equalsIgnoreCase("1") &&(postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc"))){
                                    description.setMaxLines(3);
                                    description.setMinLines(1);
                                }
                                else if (isxpress.equalsIgnoreCase("1")){
                                    description.setMaxLines(12);
                                    description.setMinLines(1);
                                }

                                else if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc")) {
                                    description.setMaxLines(5);
                                    description.setMinLines(1);
                                }
                                else{
                                    description.setMaxLines(14);
                                    description.setMinLines(1);
                                }
                            }


                        }

                        else{

                            if(is_story.equals("1")|| is_story.equals("2") ||is_story.equals("4")){

                                if (isxpress.equalsIgnoreCase("1") &&(postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc"))){
                                    description.setMaxLines(3);
                                    description.setMinLines(1);
                                }
                                else if (isxpress.equalsIgnoreCase("1")){
                                    description.setMaxLines(9);
                                    description.setMinLines(1);
                                }

                                else if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video") || postType.equalsIgnoreCase("doc")) {
                                    description.setMaxLines(5);
                                    description.setMinLines(1);
                                }
                                else{
                                    description.setMaxLines(11);
                                    description.setMinLines(1);
                                }
                            }

                            else{
                                if (isxpress.equalsIgnoreCase("1") &&(postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")||postType.equalsIgnoreCase("doc"))){
                                    description.setMaxLines(4);
                                    description.setMinLines(1);
                                }
                                else if (isxpress.equalsIgnoreCase("1")){
                                    description.setMaxLines(10);
                                    description.setMinLines(1);
                                }

                                else if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")||postType.equalsIgnoreCase("doc")) {
                                    description.setMaxLines(6);
                                    description.setMinLines(1);
                                }
                                else{
                                    description.setMaxLines(12);
                                    description.setMinLines(1);
                                }

                            }


                        }
                    }
                    description.setTypeface(typeface);
                    // description.setText(details);
                    //description.setText(Html.fromHtml(details));

/*
            String strMsg1=  StringEscapeUtils.unescapeJava(details);
            description.setText(strMsg1);*/

                    description.setOnTouchListener(new LinkMovementMethodOverride());

                  /*  URLImageParser p = new URLImageParser(description, mContext);
                    Spanned htmlSpan = Html.fromHtml(details, p, null);
                    description.setText(htmlSpan);*/
                    //description.setText(details);


                    Spanned spanned = Html.fromHtml(details,
                            new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String source) {
                                    LevelListDrawable d = new LevelListDrawable();
                                    Drawable empty = mContext.getResources().getDrawable(R.drawable.default_emoji_circle);
                                    d.addLevel(0, 0, empty);
                                    d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                                    new ImageGetterAsyncTask(mContext, source, d).execute(description);

                                    return d;
                                }
                            }, null);
                    description.setText(spanned);


                    description.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // listener.commentAt(pos);
                            Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                            detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                            Constant.lastposition_home = pos;
                            mContext.startActivity(detailsActitivty);
                            //((FlipComplexLayoutActivity)mContext).finish();

                        }
                    });

                    descriptionlay.setVisibility(View.VISIBLE);
               /*     description.post(new Runnable() {
                        @Override
                        public void run() {
                            int countlins = description.getLineCount();
                            Log.i("countlins", "" + countlins);

                            if (countlins >= 2 && postID.equals("")) {
                                description.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // listener.commentAt(pos);
                                        Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                                        detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                                        Constant.lastposition_home = pos;
                                        mContext.startActivity(detailsActitivty);
                                        //((FlipComplexLayoutActivity)mContext).finish();

                                    }
                                });
                            }

                        }
                    });*/

                }
            });






        } else {
            descriptionlay.setVisibility(View.GONE);
        }


           /* description.post(new Runnable() {
                @Override
                public void run() {
                    int countlins = description.getLineCount();
                    Log.i("countlins", "" + countlins);

                    if (countlins >=2 && postID.equals(""))
                    {
                        // Toast.makeText(mContext,"if visible="+description.length(),Toast.LENGTH_SHORT).show();
                        moreoption.setVisibility(View.VISIBLE);
                        description.setEllipsize(TextUtils.TruncateAt.END);
                    }
                    else if (description.length()>=70 && postID.equals(""))
                    {
                        // Toast.makeText(mContext,"else if visible="+description.length(),Toast.LENGTH_SHORT).show();
                        moreoption.setVisibility(View.VISIBLE);
                        description.setEllipsize(TextUtils.TruncateAt.END);
                    }

                    else
                    {
                        // Toast.makeText(mContext,"gone="+description.length(),Toast.LENGTH_SHORT).show();
                        moreoption.setVisibility(View.GONE);
                    }
                }
            });*/


           /* moreoption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // listener.commentAt(pos);
                    Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                    detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                    Constant.lastposition_home=pos;
                    mContext.startActivity(detailsActitivty);
                    //((FlipComplexLayoutActivity)mContext).finish();

                }
            });*/




        if (tagcount != null && !tagcount.equalsIgnoreCase(""))
            tag_count_txt.setText(""+tagcount);


        if (likescount1 != null && !likescount1.equalsIgnoreCase(""))
            likescount.setText(likescount1/* + " Likes"*/);

        if (commentscount1 != null && !commentscount1.equalsIgnoreCase(""))
            commentscount.setText(commentscount1/* + " Comments"*/);

        if (event_date1 != null)
            event_date.setText(event_date1);

        if (getMorePosts.get(pos).getUlikes().equalsIgnoreCase("1")) {
            likeit.setVisibility(View.GONE);
            unlikeit.setVisibility(View.VISIBLE);
        } else {
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
                new PostLike(getMorePosts.get(pos)).execute(postid);
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
                new PostLike(getMorePosts.get(pos)).execute(postid);
            }
        });


        commentit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //listener.commentAt(pos);
                String postid = getMorePosts.get(pos).getRecognition_id();
               /* Intent commnetIntent = new Intent(mContext, CommentActivity.class);
                commnetIntent.putExtra("recognizationID", postid);
                Constant.lastposition_home=pos;
                if (!postID.equalsIgnoreCase(""))
                    commnetIntent.putExtra("singlepost", "1");
                else
                    commnetIntent.putExtra("singlepost", "0");
                mContext.startActivity(commnetIntent);*/




                giveCommentWindow(postid,commentit,commentscount1,commentscount,getMorePosts.get(pos));
            }
        });


        if (!getMorePosts.get(position).getTagcount().equals("0")) {
            tag_icon.setEnabled(true);
            tag_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recogID = getMorePosts.get(pos).getRecognition_id();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        tagWondow(recogID, tag_icon, 300);
                    } else {
                        tagWondow(recogID, tag_icon, 500);
                    }


                }
            });
        } else {
            tag_icon.setEnabled(false);
        }



        likeicon.setEnabled(true);
        likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getMorePosts.get(position).getLikes().equals("0")) {
                    String recogID = getMorePosts.get(pos).getRecognition_id();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        likeWondow(recogID, likeicon, 300);
                    } else {
                        likeWondow(recogID, likeicon, 500);
                    }
                }
                   /* else {
                        likeicon.setEnabled(false);
                    }*/

            }
        });




        commenticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getMorePosts.get(position).getComments().equals("0")) {
                    commenticon.setEnabled(true);
                    String recogID = getMorePosts.get(pos).getRecognition_id();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        commentWondow(recogID, commenticon, 300);
                    } else {
                        commentWondow(recogID, commenticon, 500);
                    }
                }


            }
        });
        /*} else {
            commenticon.setEnabled(false);
        }*/

        if (postID.equals("")) {
            // if ((position == getMorePosts.size() - 1))
            if (position == 0) {
                up_arrow_icon.setVisibility(View.GONE);
                morepost.setVisibility(View.VISIBLE);
                morepost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Constant.lastpostofPOST = getMorePosts.size() - 1;
                        //Constant.today--;
                        postdate=getMorePosts.get(Constant.lastpostofPOST).getDate();
                        new GetMorePostTask().execute();*/
                        new GetMorePostTask1().execute();
                    }
                });
            } else {
                morepost.setVisibility(View.GONE);
                up_arrow_icon.setVisibility(View.VISIBLE);
                up_arrow_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tabFragment1.topTest();
                    }
                });

            }
        } else {
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
        GetMorePost getMorePost=null;
        public PostLike(GetMorePost getMorePost){
            this.getMorePost=getMorePost;
        }
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

                    likescountview.setText(""+postlist.get(0).getLikes()/* + " Likes"*/);
                    getMorePost.setLikes(postlist.get(0).getLikes());
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
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostByDate(mContext, postdate).callGetMorePost();
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
                //listener.getMorePost();
                Toast.makeText(mContext, "Refresh successfully!", Toast.LENGTH_SHORT).show();
                getMorePosts = new GetMorePostManager(mContext).getMorePosts();
                Toast.makeText(mContext, "GetHomePostSize=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();
                flipView1.setAdapter(new TabFragmentFirstAapterPage(mContext, flipView1, tabFragment1));

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }

    public class GetMorePostTask1 extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).callGetMorePost();
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
                Toast.makeText(mContext, "Refresh successfully!", Toast.LENGTH_SHORT).show();
                flipView1.setAdapter(new TabFragmentFirstAapterPage(mContext, flipView1, tabFragment1));
                tabFragment1.setCountAfterRefersh();
            } else {
                progressDialog.dismiss();
                if(Constant.web_app_version.equals("300")){
                    Toast.makeText(mContext, "New Android version is now available.\n" +
                            "Please update your app through Google Play Store.", Toast.LENGTH_LONG).show();
                    new TaskLogout().execute();
                }
                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }


            }

        }


    }

    public class TaskLogout extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        ProgressDialog progressDialog=null;
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
                UserLoginInfo userLoginInfo = new LoginManager(mContext).getUserInfo().get(0);
                String user_id = userLoginInfo.getUserid();
                responseString = new LogOutManager(mContext).logoutUser(user_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    daoSession.getUserLoginInfoDao().deleteAll();
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(loginIntent);
                    ((Activity)mContext).finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                }
                else
                {
                    Toast.makeText(mContext,""+responseString,Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
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


    public String getVideoID(String url) {
        String vId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            vId = matcher.group();
        }
        return vId;
    }



    public void tagWondow(final String recogID, ImageView likeicon, int windowsize) {


        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.tagwindow_view, null);

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
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new TagPostManager(mContext).callTagedUser(recogID);
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
                    TagPostAdapter tagPostAdapter = new TagPostAdapter(mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
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
        // changeSortPopUp.setAnimationStyle(R.style.animationpopup);
        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        float height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height-xy[1]-30));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -10;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }

    public void likeWondow(final String recogID, ImageView likeicon, int windowsize) {


        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.givenlike_view, null);

        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView likelist = (RecyclerView) layout.findViewById(R.id.likelist);

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
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new LikeManager(mContext).callLikedUser(recogID);
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
                    LikeGivenAdapter commentListAdapter = new LikeGivenAdapter(mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    likelist.setLayoutManager(layoutManager);
                    likelist.setItemAnimator(new DefaultItemAnimator());
                    likelist.setAdapter(commentListAdapter);
                } else {

                }

            }


        }.execute();


        Rect rc = new Rect();
        likeicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        likeicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // changeSortPopUp.setAnimationStyle(R.style.animationpopup);
        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        float height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height-xy[1]-30));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -10;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }


    public void commentWondow(final String recogID, final ImageView likeicon, int windowsize) {

        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.givencomment_view, null);

        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView likelist = (RecyclerView) layout.findViewById(R.id.likelist);

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
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new CommentManager(mContext).commentCall(recogID);
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
                    CommentViewAdapter commentViewAdapter = new CommentViewAdapter(mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    likelist.setLayoutManager(layoutManager);
                    likelist.setItemAnimator(new DefaultItemAnimator());
                    likelist.setAdapter(commentViewAdapter);
                } else {

                }

            }


        }.execute();


        Rect rc = new Rect();
        likeicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        likeicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // changeSortPopUp.setAnimationStyle(R.style.animationpopup);
        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        float height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height-xy[1]-30));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -10;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.


        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }



    ImageView emojiicon,keyicon;
    LinearLayout parentlay;
    EmoticonHandler mEmoticonHandler;
    EditText commenttxt=null;

    public void giveCommentWindow(final String postid, ImageView commentit, final String commentscount1, final TextView commentscount, final GetMorePost getMorePost) {

        final PopupWindow changeSortPopUp1 = new PopupWindow(mContext);


        changeSortPopUp1.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.commentview, null);

        ImageView subcombtn = (ImageView) layout.findViewById(R.id.subcombtn);
        commenttxt = (EditText) layout.findViewById(R.id.commenttxt);
        final RecyclerView commentlist = (RecyclerView) layout.findViewById(R.id.commentlist);
        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final CoordinatorLayout searchlayparent = (CoordinatorLayout) layout.findViewById(R.id.searchlayparent);
        ImageView tagicon=(ImageView)layout.findViewById(R.id.tagicon);
        TextView tagcount_txt=(TextView)layout.findViewById(R.id.tagcount_txt);
        Constant.tag_txt=tagcount_txt;
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent  intent=new Intent(mContext,TagActivty.class);
                Intent  intent=new Intent(mContext,TagActivty.class);
                ((Activity)mContext).startActivity(intent);
            }
        });



        emojiicon=(ImageView)layout.findViewById(R.id.emojiicon);
        keyicon=(ImageView)layout.findViewById(R.id.keyicon);


        keyicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyicon.setVisibility(View.GONE);
                emojiicon.setVisibility(View.VISIBLE);
                if(emoji_changeSortPopUp!=null){
                    if(emoji_changeSortPopUp.isShowing()){emoji_changeSortPopUp.dismiss();}
                }
                commenttxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(commenttxt, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        emojiicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiicon.setVisibility(View.GONE);
                keyicon.setVisibility(View.VISIBLE);

                if(emoji_changeSortPopUp!=null) {
                    if (!emoji_changeSortPopUp.isShowing()) {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        emojiWindow();
                    }
                }

                else{

                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    emojiWindow();
                }


            }
        });



        mEmoticonHandler = new EmoticonHandler(commenttxt);

        commenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext,"HELLO",Toast.LENGTH_SHORT).show();

                keyicon.setVisibility(View.GONE);
                emojiicon.setVisibility(View.VISIBLE);
                if(emoji_changeSortPopUp!=null){
                    if(emoji_changeSortPopUp.isShowing()){emoji_changeSortPopUp.dismiss();}
                }
                commenttxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(commenttxt, InputMethodManager.HIDE_IMPLICIT_ONLY);

            }
        });


        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoji_changeSortPopUp.dismiss();
            }
        });


        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String comment = commenttxt.getText().toString().trim();
                if (ValidationUtility.validEditTextString(comment)) {
                    Log.i("comment size", "" + comment.length());
                    new PostComment(commentlist, commenttxt, searchlayparent, postid,commentscount1,commentscount,getMorePost).execute(comment);
                } else {
                    commenttxt.setText("");
                   /* mytoast.setText("Please Enter Comment");
                    mytoast.show();*/

                    showsnack("Please Enter Comment", searchlayparent);

                }

            }
        });


        new AsyncTask<String, Void, Void>() {

            String responseString = "";
            String s = "Please wait...";
            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {
                    responseString = new CommentManager(mContext).commentCall(postid);
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
                    CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, false);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    commentlist.setLayoutManager(layoutManager);
                    commentlist.setItemAnimator(new DefaultItemAnimator());
                    commentlist.setAdapter(commentListAdapter);
                } else {
                    showsnack(responseString, searchlayparent);
                    progressDialog.dismiss();
                }
            }

        }.execute();


        Rect rc = new Rect();
        commenttxt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        commenttxt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp1.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp1.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp1.setHeight((int)(height/1.3));
        changeSortPopUp1.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/1.3));
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

        changeSortPopUp1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Constant.tag_txt=null;
                TagActivty.taged_users.clear();

                if(emoji_changeSortPopUp!=null) emoji_changeSortPopUp.dismiss();
            }
        });
    }


    public void showsnack(String msg, CoordinatorLayout searchlayparent) {

        Snackbar snackbar = Snackbar
                .make(searchlayparent, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        textView.setTextColor(Color.parseColor("#ffffff"));

        snackbar.show();

    }


    public class PostComment extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String comment = "";

        RecyclerView commentlist;
        EditText commenttxt;
        CoordinatorLayout searchlayparent;
        String postid = "";
        String commentscount1;
        TextView commentscount;
        GetMorePost getMorePost;
        StringBuilder taggedUSerID=null;
        PostComment(RecyclerView commentlist, EditText commenttxt, CoordinatorLayout searchlayparent, String postid,
                    String commentscount1,TextView commentscount,GetMorePost getMorePost) {
            this.commentlist = commentlist;
            this.commenttxt = commenttxt;
            this.searchlayparent = searchlayparent;
            this.postid = postid;
            this.commentscount1=commentscount1;
            this.commentscount=commentscount;
            this.getMorePost=getMorePost;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            taggedUSerID=new StringBuilder();
            for(int i=0;i<TagActivty.taged_users.size();i++){
                taggedUSerID.append(TagActivty.taged_users.get(i).getUser_id());
                if(i<(TagActivty.taged_users.size()-1))
                    taggedUSerID.append(",");
            }


            Log.i("tagged users 666",""+TagActivty.taged_users.size());
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                comment = params[0];
                responseString = new SingleLikeAndCommentManager(mContext).postComment(postid, comment,taggedUSerID.toString());
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
                Constant.tag_txt.setText("");
                Constant.tag_txt.setVisibility(View.GONE);
                TagActivty.taged_users.clear();

                CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                commentlist.setLayoutManager(layoutManager);
                commentlist.setItemAnimator(new DefaultItemAnimator());
                commentlist.setAdapter(commentListAdapter);
                commenttxt.setText("");
                int cm_count=Integer.parseInt(commentscount1);
                cm_count=Integer.parseInt(getMorePost.getComments())+1;
                commentscount.setText(""+cm_count);
                Comments comments = new CommentManager(mContext).getCommentsList().get(0);
                new SingleLikeAndCommentManager(mContext).updateCommentsInPostList(comments.getCount());
                new SingleLikeAndCommentManager(mContext).updateExpressComment(comments.getCount(), postid);
                getMorePost.setComments(comments.getCount());

            } else {

               /* mytoast.setText(responseString);
                mytoast.show();*/
                showsnack(responseString, searchlayparent);
                progressDialog.dismiss();
            }

        }

    }

    public void addImageBetweentext(Drawable drawable, String emojoCode) {
        // Create the ImageSpan

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable);

        // Get the selected text.
        int start = commenttxt.getSelectionStart();
        int end = commenttxt.getSelectionEnd();
        Editable message = commenttxt.getEditableText();

        // Insert the emoticon.
        message.replace(start, end, emojoCode);
        message.setSpan(span, start, start + emojoCode.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private  class EmoticonHandler implements TextWatcher {


        private final EditText mEditor;
        private final ArrayList<ImageSpan> mEmoticonsToRemove = new ArrayList<ImageSpan>();

        public EmoticonHandler(EditText editor) {
            // Attach the handler to listen for text changes.
            mEditor = editor;
            mEditor.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            // Check if some text will be removed.
            if (count > 0) {
                int end = (start + count);
                Editable message = mEditor.getEditableText();
                ImageSpan[] list = message.getSpans(start, end, ImageSpan.class);

                for (ImageSpan span : list) {
                    // Get only the emoticons that are inside of the changed
                    // region.
                    int spanStart = message.getSpanStart(span);
                    int spanEnd = message.getSpanEnd(span);
                    if ((spanStart < end) && (spanEnd > start)) {
                        // Add to remove list
                        mEmoticonsToRemove.add(span);
                    }
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Editable message = mEditor.getEditableText();

            // Commit the emoticons to be removed.
            for (ImageSpan span : mEmoticonsToRemove) {
                int start = message.getSpanStart(span);
                int end = message.getSpanEnd(span);

                // Remove the span
                message.removeSpan(span);

                // Remove the remaining emoticon text.
                if (start != end) {
                    message.delete(start, end);

                }
            }
            mEmoticonsToRemove.clear();
        }
    }

    PopupWindow emoji_changeSortPopUp=null;

    public void emojiWindow() {


        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.emojiwindow, null);
        emoji_changeSortPopUp = new PopupWindow(layout,width,(int)(height/2.5),true);

        GridView gridView = (GridView) layout.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new EmojiAdapter(mContext));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                int id1 = mContext.getResources().getIdentifier("expression.sixdexp.com.expressionapp:drawable/a"+position, null, null);
                addImageBetweentext(mContext.getResources().getDrawable(id1),"<img src=\"https://xpressions.tatapower.com/smiley/"+position+".png\" height=\"25\" width=\"25\">");
            }
        });

        Rect rc = new Rect();
        emojiicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        emojiicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        emoji_changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        emoji_changeSortPopUp.setContentView(layout);
        emoji_changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());
        emoji_changeSortPopUp.setOutsideTouchable(true);


//        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        changeSortPopUp.setHeight((int)(height/3));
        emoji_changeSortPopUp.setFocusable(false);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -100;
        emoji_changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        emoji_changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.bottom + OFFSET_Y);


    }


    public double getdeviceInch(){
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double wi=(double)width/(double)displaymetrics.xdpi;
        double hi=(double)height/(double)displaymetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        Log.i("height133",""+height);



        return screenInches;
    }



    public class LinkMovementMethodOverride implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            TextView widget = (TextView) v;
            Object text = widget.getText();
            if (text instanceof Spanned) {
                Spanned buffer = (Spanned) text;

                int action = event.getAction();

                if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off,
                            ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        } else if (action == MotionEvent.ACTION_DOWN) {
                            // Selection only works on Spannable text. In our case setSelection doesn't work on spanned text
                            //Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                        }
                        return true;
                    }
                }

            }

            return false;
        }

    }

    public class GetUserProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String searchUserID="0";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                searchUserID=params[0];
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new UserInfoManager(mContext).userInfo(searchUserID);
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

              /*  Intent proifleIntent = new Intent(mContext, UserProfileActivity.class);*/
                Intent proifleIntent = new Intent(mContext, ProfileDetails_Search.class);
                mContext.startActivity(proifleIntent);

            } else {


            }

        }
    }





}
