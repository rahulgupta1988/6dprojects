package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.GPWALLPOST;
import expression.sixdexp.com.expressionapp.FullimageActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.adapter.EmojiAdapter;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.GetMorePostByDate;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ImageGetterAsyncTask;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharingInterface;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;
import expression.sixdexp.com.expressionapp.utility.YouTubePlayer;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPTagPostManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallComentManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallLikeManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallPostManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GpPostCommentManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.XPLikeManager;

/**
 * Created by Praveen on 14-Feb-18.
 */

public class GPWallPostAdapter_details extends RecyclerView.Adapter<GPWallPostAdapter_details.PostHolder> {

    Context mContext;
    List<GPWALLPOST> getMorePosts;
    String videoid = "";
    TextView likescountview;
    private GPWallPostAdapter_details.MorePostInterface listener;
    String postID = "";
    String postdate="";
    Typeface typeface;

    boolean alreadyClickedemoticon=false;

    public static int temp_coumt=0;
    String gpID="";
    public interface MorePostInterface {
        public void getMorePost();

        public void commentAt(int commentat);

        public void scrollList();
    }


    public GPWallPostAdapter_details(Context context, GPWallPostAdapter_details.MorePostInterface listener, String postID) {
        mContext = context;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");

        this.postID = postID;
        getMorePosts = Constant.singlePostList_gp;//new GetMorePostManager(mContext).getPostsByPostID(postID);
        this.listener = listener;

        if(GroupListAdapter.groupObject!=null){
            gpID=GroupListAdapter.groupObject.getCoi_gid();
        }
    }

    @Override
    public GPWallPostAdapter_details.PostHolder onCreateViewHolder(ViewGroup parent, int i) {

        // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.postchatitem, parent, false);floating_bar
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.floating_bar_details_new, parent, false);

        return new GPWallPostAdapter_details.PostHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final GPWallPostAdapter_details.PostHolder postHolder, final int position) {

        final int pos = position;
        final GPWALLPOST getMorePost = getMorePosts.get(position);

       /* String nominator_imageurl = getMorePost.getNominator_imageurl();
        String nominee_image1 = getMorePost.getNominee_imageurl();
        String nominator_name1 = getMorePost.getNominator_name();
        String nominator_designation1 = getMorePost.getNominator_designation();
        String nominee_name1 = getMorePost.getNominee_name();
        String description1 = getMorePost.getDescription();
        String details = getMorePost.getDetails();
        String likescount1 = getMorePost.getLikes();
        String commentscount1 = getMorePost.getComments();
        String tagcount=getMorePost.getTagcount();

        String isNominatorDonar=getMorePost.getIsNominatorDonar();
        String isNomineeDonor=getMorePost.getIsNomineeDonor();*/

        String nominator_imageurl = getMorePost.getNominee_imageurl();
        //String nominee_image1 = getMorePost.getNominee_imageurl();
        String nominator_name1 = getMorePost.getNominator_name();
        String nominator_designation1 = getMorePost.getNominee_designation();
        // String nominee_name1 = getMorePost.getNominee_name();
        // String description1 = getMorePost.getDescription();
        final String details = getMorePost.getDetails();
        String likescount1 = getMorePost.getLikes();
        final String commentscount1 = getMorePost.getComments();
        String event_date1 = getMorePost.getEvent_date();
        final String postType = getMorePost.getType();
        final String is_story = "0";//getMorePost.getIs_story();
        final String is_story_status=getMorePost.getIsstorystatus();
        // String awardid = getMorePost.getAwardid();
        final String isxpress = getMorePost.getIsxpress();
        String xwSubject = getMorePost.getSubject();
        // String recognizeDate = getMorePost.getRecognise_date();
        //  String otherNominess = getMorePost.getNominee();

        // String isNominatorDonar = getMorePost.getIsNominatorDonar();
        //  String isNomineeDonor = getMorePost.getIsNomineeDonor();

        String taguserid = getMorePost.getTaguserid();
        String tagcount = getMorePost.getTagcount();


        /*if(isNominatorDonar.equals("1")){
            postHolder.nominator_donor_icon.setVisibility(View.VISIBLE);
            postHolder.donor_icon.setVisibility(View.VISIBLE);
        }

        else{
            postHolder.nominator_donor_icon.setVisibility(View.GONE);
            postHolder.donor_icon.setVisibility(View.GONE);
        }

        if(isNomineeDonor.equals("1")){
            postHolder.nominee_donor_icon.setVisibility(View.VISIBLE);

        }

        else{
            postHolder.nominee_donor_icon.setVisibility(View.GONE);

        }*/



        try {
            int comment_count=(Integer.parseInt(commentscount1));
            temp_coumt=comment_count;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }







        Log.i("postType", "" + postType);

        int nomineecoun=0;
       /* try {
            nomineecoun=Integer.parseInt(getMorePost.getCount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/


     /*   postHolder.emojiicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupImoticon(postHolder.commenttxt, postHolder.emojiicon);
            }
        });*/

        postHolder.keyicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alreadyClickedemoticon=true;
                postHolder.keyicon.setVisibility(View.GONE);
                postHolder.emojiicon.setVisibility(View.VISIBLE);
                if(changeSortPopUp1!=null){
                    if(changeSortPopUp1.isShowing()){changeSortPopUp1.dismiss();}
                }
                postHolder.commenttxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(postHolder.commenttxt, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        postHolder.emojiicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHolder.emojiicon.setVisibility(View.GONE);
                postHolder.keyicon.setVisibility(View.VISIBLE);
                listener.scrollList();

                if(changeSortPopUp1!=null) {
                    if (!changeSortPopUp1.isShowing()) {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(postHolder.emojiicon.getWindowToken(), 0);
                        setupImoticon(postHolder.commenttxt, postHolder.emojiicon);
                    }
                }

                else{


                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(postHolder.emojiicon.getWindowToken(), 0);
                    setupImoticon(postHolder.commenttxt, postHolder.emojiicon);
                }


            }
        });






    /*    if (nomineecoun> 0) {
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
        }*/


        Log.i("is_story_status34",""+is_story_status);
        if(is_story_status.equals("1")){
            postHolder.tag_count_txt.setVisibility(View.GONE);
            postHolder.tag_icon.setVisibility(View.GONE);
        }

        else{
            postHolder.tag_count_txt.setVisibility(View.VISIBLE);
            postHolder.tag_icon.setVisibility(View.VISIBLE);
        }



        if (isxpress.equalsIgnoreCase("1")) {
            postHolder.xwicon.setVisibility(View.VISIBLE);
            postHolder.xwsubject.setText("" + xwSubject);
        } else
            postHolder.xwicon.setVisibility(View.GONE);

        if (is_story.equals("2")) {
            postHolder.recognise_date_lay.setVisibility(View.VISIBLE);
          //  postHolder.recognise_date.setText("" + recognizeDate);
        } else
            postHolder.recognise_date_lay.setVisibility(View.GONE);


       /* if (is_story.equals("1") || is_story.equals("2")||is_story.equals("4")) {
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
            else if (awardid.equals("8"))
                postHolder.thanksimg.setImageResource(R.drawable.thank_you_book_icon);
        }*/

        if (postType.equalsIgnoreCase("") || postType.equalsIgnoreCase(" ")) {


            if (is_story.equals("1") || is_story.equals("2")) {
                //  postHolder.testforawardandthankview.setVisibility(View.GONE);
                postHolder.user_details_ll.setVisibility(View.GONE);
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
                // postHolder.testforawardandthankview.setVisibility(View.GONE);
                postHolder.user_details_ll.setVisibility(View.VISIBLE);
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
            if (is_story.equals("1") || is_story.equals("2") ||is_story.equals("4")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.user_details_ll.setVisibility(View.GONE);
                //  postHolder.testforawardandthankview.setVisibility(View.VISIBLE);
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
                postHolder.user_details_ll.setVisibility(View.VISIBLE);
                //  postHolder.testforawardandthankview.setVisibility(View.GONE);
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


            if(doc_url_str.contains("https://web.microsoftstream.com")){

                postHolder.playvideo.setVisibility(View.GONE);
                try {
                    final String url_str1 = doc_url_str.replace(" ", "%20");
                    attachview.setImageResource(R.drawable.stream_preview);


                    postHolder.postvideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(url_str1));
                            mContext.startActivity(webIntent);
                        }
                    });

                    postHolder.award_postvideo.setOnClickListener(new View.OnClickListener() {
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

            else {

                postHolder.playvideo.setVisibility(View.VISIBLE);


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


          /*  postHolder.postvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.commentAt(pos);
                    Intent yiutubeviewActivty = new Intent(mContext, YouTubePlayer.class);
                    yiutubeviewActivty.putExtra("youtubeid", videoid);
                    mContext.startActivity(yiutubeviewActivty);
                }
            });*/

                postHolder.postvideo.setOnClickListener(new View.OnClickListener() {
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

                postHolder.award_postvideo.setOnClickListener(new View.OnClickListener() {
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




        if (postType.equalsIgnoreCase("doc")) {

            ImageView attachview = null;

            if (is_story.equals("1") || is_story.equals("2")||is_story.equals("4")) {
                //  postHolder.testforawardandthankview.setVisibility(View.VISIBLE);
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.award_doclay.setVisibility(View.VISIBLE);
                postHolder.user_details_ll.setVisibility(View.GONE);
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
                //  postHolder.testforawardandthankview.setVisibility(View.GONE);
                postHolder.user_details_ll.setVisibility(View.VISIBLE);
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


            if (is_story.equals("1") || is_story.equals("2")|| is_story.equals("4")) {
                postHolder.thanksview.setVisibility(View.VISIBLE);
                postHolder.award_imagelay.setVisibility(View.VISIBLE);
                //  postHolder.testforawardandthankview.setVisibility(View.VISIBLE);
                postHolder.user_details_ll.setVisibility(View.GONE);
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
                //  postHolder.testforawardandthankview.setVisibility(View.GONE);
                postHolder.user_details_ll.setVisibility(View.VISIBLE);
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
                        .load(nominator_imageurl1).resize(500, 500).centerInside()
                        /*.resize(400,400)*/
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


            attachview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                            && !nominator_imageurl1.equalsIgnoreCase(" ")) {

                        Intent intent = new Intent(mContext, FullimageActivity.class);
                        intent.putExtra("imageval", nominator_imageurl1);
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
                    .resize(150, 150).centerInside()
                   /* .centerCrop()*/
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(postHolder.nominatorimg);

            Picasso.with(mContext)
                    .load(nominator_imageurl1)
                    .resize(150, 150).centerInside()
                   /* .centerCrop()*/
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(postHolder.nominatorimg1);
        }


      /*  URI nominee_uri = null;
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
                    .resize(150, 150).centerInside()
                   *//* .centerCrop()*//*
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(postHolder.nominee_image);
        }

*/
        if (nominator_name1 != null && !nominator_name1.equalsIgnoreCase(""))
            postHolder.nominator_name.setText(nominator_name1);
        postHolder.nominator_name_new.setText(nominator_name1);

        if (nominator_designation1 != null && !nominator_designation1.equalsIgnoreCase(""))
            postHolder.nominator_designation.setText(nominator_designation1);
        postHolder.nominator_designation_new.setText(nominator_designation1);

      /*  if (nominee_name1 != null && !nominee_name1.equalsIgnoreCase(""))
            postHolder.nominee_name.setText(nominee_name1);*/

        if (details != null && !details.equalsIgnoreCase("")) {
            postHolder.descriptionlay.setVisibility(View.VISIBLE);
         /*   if (postID.equals("")){
                postHolder.description.setMaxLines(2);
                postHolder.description.setMinLines(1);
            }
            postHolder.description.setTypeface(typeface);*/
            // postHolder.description.setText(details);
            // postHolder.description.setText(Html.fromHtml(details));





            // postHolder.description.setMovementMethod(LinkMovementMethod.getInstance());
            postHolder.description.setOnTouchListener(new GPWallPostAdapter_details.LinkMovementMethodOverride());

          /*  postHolder.description.post(new Runnable() {
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
            });*/


          /*  postHolder.moreoption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.commentAt(pos);
                    Intent detailsActitivty = new Intent(mContext, PostDetailsAcitivity.class);
                    detailsActitivty.putExtra("postId", getMorePosts.get(pos).getRecognition_id());
                    mContext.startActivity(detailsActitivty);

                }
            });
*/


        }
        else{
            postHolder.descriptionlay.setVisibility(View.GONE);
        }

        if (likescount1 != null && !likescount1.equalsIgnoreCase(""))
            postHolder.likescount.setText(likescount1);

        if (commentscount1 != null && !commentscount1.equalsIgnoreCase(""))
            postHolder.commentscount.setText(commentscount1);

        if (event_date1 != null && !event_date1.equalsIgnoreCase(""))
            postHolder.event_date.setText(event_date1);



        if(getMorePosts.get(pos).getLikes().equalsIgnoreCase("1")){
            postHolder.likeit.setVisibility(View.GONE);
            postHolder.unlikeit.setVisibility(View.VISIBLE);
        }
        else{
            postHolder.unlikeit.setVisibility(View.GONE);
            postHolder.likeit.setVisibility(View.VISIBLE);

        }
        if (tagcount != null && !tagcount.equalsIgnoreCase(""))
            postHolder.tag_count_txt.setText(""+tagcount);

        if (!getMorePosts.get(position).getTagcount().equals("0")) {
            postHolder.tag_icon.setEnabled(true);
            postHolder.tag_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recogID = getMorePosts.get(pos).getGpostid();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        tagWondow(recogID, postHolder.tag_icon, 300,getMorePosts.get(pos));
                    } else {
                        tagWondow(recogID, postHolder.tag_icon, 500,getMorePosts.get(pos));
                    }


                }
            });
        } else {
            postHolder.tag_icon.setEnabled(false);
        }


        postHolder.likeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postHolder.likeit.setVisibility(View.GONE);
                postHolder.unlikeit.setVisibility(View.VISIBLE);

               /* String postid = getMorePosts.get(pos).getGpostid();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new GPWallPostAdapter_details.PostLike().execute(postid);*/

                String Gpostid = getMorePosts.get(pos).getGpostid();
                if (Gpostid != null && !Gpostid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new PostLike(getMorePosts.get(pos),true).execute(Gpostid);


            }
        });

        postHolder.unlikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postHolder.unlikeit.setVisibility(View.GONE);
                postHolder.likeit.setVisibility(View.VISIBLE);

               /* String postid = getMorePosts.get(pos).getGpostid();
                if (postid != null && !postid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new GPWallPostAdapter_details.PostLike().execute(postid);*/

                String Gpostid = getMorePosts.get(pos).getGpostid();
                if (Gpostid != null && !Gpostid.equalsIgnoreCase(""))
                    likescountview = postHolder.likescount;
                new PostLike(getMorePosts.get(pos),false).execute(Gpostid);




            }
        });




        final String postid = getMorePosts.get(pos).getGpostid();
        final String gpID = getMorePosts.get(pos).getGid();


        Constant.tag_txt=postHolder.tagcount_txt;
        postHolder.tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(mContext,TagActivty.class);
                ((Activity)mContext).startActivity(intent);
            }
        });



        postHolder.subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String comment = postHolder.commenttxt.getText().toString().trim();
                if (ValidationUtility.validEditTextString(comment)) {
                    Log.i("comment size", "" + comment.length());
                    new AsyncTask<String, Void, Void>() {

                        String responseString = "";
                        String s = "Please wait...";
                        StringBuilder taggedUSerID=null;
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
                        }

                        @Override
                        protected Void doInBackground(String... params) {
                            // TODO Auto-generated method stub
                            try {
                                responseString = new GPWallComentManager(mContext,false).postComment(comment, taggedUSerID.toString(),gpID,postid);
                                //responseString = new LikeAndCommentManager(mContext).postComment(postid, comment,taggedUSerID.toString());

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
                                TagActivty.taged_users.clear();
                                Constant.tag_txt.setText("");
                                Constant.tag_txt.setVisibility(View.GONE);
                                //  CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, true);
                                CommentListDetailsAdapter commentListAdapter=new CommentListDetailsAdapter(mContext, true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                                // postHolder.commentlist.setLayoutManager(layoutManager);
                                //  postHolder.commentlist.setItemAnimator(new DefaultItemAnimator());
                                postHolder.commentlist.setAdapter(commentListAdapter);
                                Utils.getListViewSize(postHolder.commentlist);
                               // new SingleLikeAndCommentManager(mContext).updateCommentsInPostList(postHolder.commenttxt.getText().toString());
                                postHolder.commenttxt.setText("");

                                //new CallMorePost().execute();

                                try {
                                    temp_coumt=temp_coumt+1;
                                    postHolder.commentscount.setText(""+temp_coumt);
                                    getMorePosts.get(0).setLikes(""+temp_coumt);

                                    notifyDataSetChanged();
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

/*
                                try {
                                    new SingleLikeAndCommentManager(mContext).updateExpressComment(String.valueOf(temp_coumt),postid);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }*/



                            } else {

          /*  mytoast.setText(responseString);
                mytoast.show();*/
                                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                    }.execute();


                } else {
                    postHolder.commenttxt.setText("");
                /*   mytoast.setText("Please Enter Comment");
                    mytoast.show();*/
                    Toast.makeText(mContext, "Please Enter Comment", Toast.LENGTH_SHORT).show();
                }




            }
        });


        postHolder.share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Coming Soon...", Toast.LENGTH_SHORT).show();
                String postid=getMorePosts.get(pos).getGpostid();
                String userid=getMorePosts.get(pos).getUserid();
                new SharingInterface(mContext).shareSocial(postHolder.share_img,postHolder.first_ll,postid,userid);
            }
        });

        postHolder.email_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Coming Soon...", Toast.LENGTH_SHORT).show();
            }
        });



        postHolder.commentit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String postid = getMorePosts.get(pos).getGpostid();

                postHolder.commenttxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(postHolder.commenttxt, InputMethodManager.HIDE_IMPLICIT_ONLY);



         /* if(postHolder.comment_view_lay.getVisibility()==View.VISIBLE){
               postHolder.comment_view_lay.setVisibility(View.GONE);
             }

            else {

               postHolder.comment_view_lay.setVisibility(View.VISIBLE);*/



                // }

            }
        });


        if(!getMorePosts.get(position).getLikes().equals("0")) {
            postHolder.like_icon.setEnabled(true);
            postHolder.like_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recogID = getMorePosts.get(pos).getGpostid();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        likeWondow(recogID,  postHolder.like_icon,300,getMorePosts.get(pos));
                    }

                    else{
                        likeWondow(recogID,  postHolder.like_icon,500,getMorePosts.get(pos));
                    }


                }
            });
        }

        else{
            postHolder.like_icon.setEnabled(false);
        }


        if(!getMorePosts.get(position).getComments().equals("0") || temp_coumt!=0) {
            postHolder.cmt_icon.setEnabled(true);
            postHolder.cmt_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recogID = getMorePosts.get(pos).getGpostid();

                    if (postType.equalsIgnoreCase("image") || postType.equalsIgnoreCase("video")) {
                        commentWondow(recogID, postHolder.cmt_icon,300,getMorePosts.get(position));
                    }

                    else{
                        commentWondow(recogID, postHolder.cmt_icon,500,getMorePosts.get(position));
                    }


                }
            });
        }

        else{
            postHolder.cmt_icon.setEnabled(false);
        }




        if (postID.equals("")) {
            if (position == getMorePosts.size() - 1) {
                postHolder.morepost.setVisibility(View.VISIBLE);
                postHolder.morepost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constant.lastpostofPOST = getMorePosts.size() - 1;
                        //Constant.today--;

                        postdate=getMorePosts.get(Constant.lastpostofPOST).getEvent_date();
                        new GPWallPostAdapter_details.GetMorePostTask().execute();
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

        setCommentList(postid,postHolder.commentlist,postHolder.listprogress);

      /*  URLImageParser p = new URLImageParser(postHolder.description,mContext);
        Spanned htmlSpan = Html.fromHtml(details, p, null);
        postHolder.description.setText(htmlSpan);*/

        Spanned spanned = Html.fromHtml(details,
                new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        LevelListDrawable d = new LevelListDrawable();
                        Drawable empty = mContext.getResources().getDrawable(R.drawable.default_emoji_circle);
                        d.addLevel(0, 0, empty);
                        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                        new ImageGetterAsyncTask(mContext, source, d).execute(postHolder.description);

                        return d;
                    }
                }, null);
        postHolder.description.setText(spanned);



    }



    @Override
    public int getItemCount() {
        if (getMorePosts != null && getMorePosts.size() > 0) {
            return getMorePosts.size();
        }

        return 0;
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        LinearLayout first_ll;
        CircularImageView nominatorimg,nominatorimg1;
        CircularImageView nominee_image;
        TextView nominator_name, nominator_designation, nominee_name, description, likescount, commentscount, event_date;
        TextView nominator_name_new,nominator_designation_new;
        LinearLayout likeit, unlikeit;
        ImageView  commentit;
        LinearLayout xwicon;
        TextView xwsubject;

        ImageView thanksimg, postimg, postdoc;
        ImageView postvideo;
        LinearLayout imagelay, doclay;
        FrameLayout thanksview, videolay;
        // LinearLayout testforawardandthankview;
        LinearLayout user_details_ll;
        LinearLayout morepost;
        LinearLayout comment_view_lay;


        // with award view
        LinearLayout award_imagelay, award_doclay;
        FrameLayout award_videolay;
        ImageView award_postvideo, award_postimg, award_postdoc;

        LinearLayout recognise_date_lay,descriptionlay;
        TextView recognise_date, othersnominee;
        TextView moreoption;

        ImageView notification_img,share_img,email_img;
        LinearLayout notification_ll;


        // comment views
        ImageView subcombtn;
        EditText commenttxt;
        ListView commentlist;


        ImageView like_icon,cmt_icon,tag_icon;
        TextView tag_count_txt;

        ImageView emojiicon,keyicon;

        ProgressBar listprogress;

        ImageView tagicon;
        TextView tagcount_txt;

        // Downer icon
        ImageView nominator_donor_icon,nominee_donor_icon,donor_icon;

        ImageView playvideo;

        public PostHolder(View convertView) {
            super(convertView);

            playvideo= (ImageView) convertView.findViewById(R.id.playvideo);
            tagicon=(ImageView)convertView.findViewById(R.id.tagicon);
            tagcount_txt=(TextView)convertView.findViewById(R.id.tagcount_txt);


            first_ll=(LinearLayout)convertView.findViewById(R.id.first_ll);
            // donor icon views
            nominator_donor_icon=(ImageView)convertView.findViewById(R.id.nominator_donor_icon);
            nominee_donor_icon=(ImageView)convertView.findViewById(R.id.nominee_donor_icon);
            donor_icon=(ImageView)convertView.findViewById(R.id.donor_icon);


            listprogress=(ProgressBar)convertView.findViewById(R.id.listprogress);

            emojiicon=(ImageView)convertView.findViewById(R.id.emojiicon);
            keyicon=(ImageView)convertView.findViewById(R.id.keyicon);

            like_icon=(ImageView) convertView.findViewById(R.id.like_icon);
            cmt_icon=(ImageView) convertView.findViewById(R.id.cmt_icon);
            tag_icon=(ImageView) convertView.findViewById(R.id.tag_icon);

            tag_count_txt=(TextView) convertView.findViewById(R.id.tag_count_txt);


            // comment views
            comment_view_lay=(LinearLayout)convertView.findViewById(R.id.comment_view_lay);

            subcombtn=(ImageView) convertView.findViewById(R.id.subcombtn);
            commenttxt=(EditText) convertView.findViewById(R.id.commenttxt);
            commentlist=(ListView) convertView.findViewById(R.id.commentlist);
            nominatorimg = (CircularImageView) convertView.findViewById(R.id.nominatorimg);
            nominatorimg1= (CircularImageView) convertView.findViewById(R.id.nominatorimg1);
            // nominee_image = (CircularImageView) convertView.findViewById(R.id.nominee_image);
            nominee_image = (CircularImageView) convertView.findViewById(R.id.nominee_image);
            nominator_name = (TextView) convertView.findViewById(R.id.nominator_name);
            nominator_name_new=(TextView) convertView.findViewById(R.id.nominator_name_new);
            nominator_designation = (TextView) convertView.findViewById(R.id.nominator_designation);
            nominator_designation_new= (TextView) convertView.findViewById(R.id.nominator_designation_new);
            nominee_name = (TextView) convertView.findViewById(R.id.nominee_name);
            description = (TextView) convertView.findViewById(R.id.description);
            likescount = (TextView) convertView.findViewById(R.id.likescount);
            commentscount = (TextView) convertView.findViewById(R.id.commentscount);
            othersnominee = (TextView) convertView.findViewById(R.id.othersnominee);
            event_date = (TextView) convertView.findViewById(R.id.event_date);
            postdoc = (ImageView) convertView.findViewById(R.id.postdoc);
            likeit = (LinearLayout) convertView.findViewById(R.id.likeit);
            unlikeit = (LinearLayout) convertView.findViewById(R.id.unlikeit);
            commentit = (ImageView) convertView.findViewById(R.id.commentit);
            thanksimg = (ImageView) convertView.findViewById(R.id.thanksimg);
            postimg = (ImageView) convertView.findViewById(R.id.postimg);
            postvideo = (ImageView) convertView.findViewById(R.id.postvideo);

            moreoption= (TextView) convertView.findViewById(R.id.moreoption);

            imagelay = (LinearLayout) convertView.findViewById(R.id.imagelay);
            videolay = (FrameLayout) convertView.findViewById(R.id.videolay);
            //  testforawardandthankview=(LinearLayout) convertView.findViewById(R.id.testforawardandthankview);
            user_details_ll= (LinearLayout) convertView.findViewById(R.id.user_details_ll);

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



            notification_ll=(LinearLayout) convertView.findViewById(R.id.notification_ll);
            notification_img=(ImageView)convertView.findViewById(R.id.notification_img);
            share_img=(ImageView)convertView.findViewById(R.id.share_img);
            email_img=(ImageView)convertView.findViewById(R.id.email_img);
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




        }



    }




    ProgressDialog progressDialog;

    public class PostLike extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String Gpostid = "";
        GPWALLPOST getMorePost = null;
        boolean isLike;

        public PostLike(GPWALLPOST getMorePost,boolean isLike) {
            this.getMorePost = getMorePost;
            this.isLike=isLike;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            gpID=getMorePost.getGid();
            Gpostid=getMorePost.getGpostid();

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
           /* try {
                postid = params[0];
                if (!postID.equalsIgnoreCase(""))
                    responseString = new SingleLikeAndCommentManager(mContext).postLike(postid);
                else
                    responseString = new LikeAndCommentManager(mContext).postLike(postid);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            try {

                responseString = new GPWallLikeManager(mContext).postLike(gpID,Gpostid);
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
               /* List<GetMorePost> postlist;
                if (!postID.equalsIgnoreCase(""))
                    postlist = Constant.singlePostList;
                else
                    postlist = new LikeAndCommentManager(mContext).getPostByRecognizationID(postid);
                try {
                    likescountview.setText(postlist.get(0).getLikes());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                List<GPWALLPOST> postlist;

                    postlist = new GPWallPostManager(mContext).getPostByPostID(Gpostid);

                    if(postlist.size()>0){
                        try {

                            getMorePost.setLikes(postlist.get(0).getLikes());
                            likescountview.setText("" + postlist.get(0).getLikes()/* + " Likes"*/);
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    else{

                        if(isLike){
                            int likes = Integer.parseInt(getMorePost.getLikes());
                            likes++;
                            getMorePost.setLikes(""+likes);
                            likescountview.setText(""+likes);
                        }

                        else{
                            int likes = Integer.parseInt(getMorePost.getLikes());
                            likes--;
                            getMorePost.setLikes(""+likes);
                            likescountview.setText(""+likes);
                        }

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


    public class CallMorePost extends AsyncTask<Void,Void,Void>{
        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
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


                try {

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }



            } else {

          /*  mytoast.setText(responseString);
                mytoast.show();*/
                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }
    }


    public void likeWondow(final String recogID, ImageView likeicon,int windowsize,final GPWALLPOST getMorePost) {
        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.givenlike_view, null);

        ImageView cancelshare=(ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView likelist=(RecyclerView)layout.findViewById(R.id.likelist);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });



        new AsyncTask<Void,Void,Void>(){

            String responseString = "";
            String s = "Plase Wait...";
            String gid="",gpid="";
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
                gid=getMorePost.getGid();
                gpid=getMorePost.getGpostid();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new XPLikeManager(mContext).callLikedUser(gid,gpid);
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
                    GPLikeGivenAdapter commentListAdapter = new GPLikeGivenAdapter(mContext);
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


    public void commentWondow(final String recogID, ImageView likeicon,int windowsize,final GPWALLPOST getMorePost) {

        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.givencomment_view, null);

        ImageView cancelshare=(ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView likelist=(RecyclerView)layout.findViewById(R.id.likelist);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });



        new AsyncTask<Void,Void,Void>(){

            String responseString = "";
            String s = "Plase Wait...";
            String gid="",gpid="";
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
                gid=getMorePost.getGid();
                gpid=getMorePost.getGpostid();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new GpPostCommentManager(mContext).commentCall(gid,gpid);
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
                    GPCommentViewAdapter commentViewAdapter = new GPCommentViewAdapter(mContext);
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



    public void setCommentList(final String postid,final ListView commentlist,final ProgressBar listprogress){




        new AsyncTask<String, Void, Void>(){
            String responseString = "";
            String s = "Please wait...";
            String gid="",gpid="";


            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                listprogress.setVisibility(View.VISIBLE);
                gid=getMorePosts.get(0).getGid();
                gpid=getMorePosts.get(0).getGpostid();
                //  Toast.makeText(mContext,"Going to get comments",Toast.LENGTH_SHORT).show();
                // progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                // progressDialog.show();
            }

            @Override
            protected Void doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {
                   // responseString = new CommentManager(mContext).commentCall(postid);
                    responseString = new GpPostCommentManager(mContext).commentCall(gid,gpid);
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
                listprogress.setVisibility(View.GONE);
                if (responseString.equals("100")) {
                    //CommentListAdapter commentListAdapter = new CommentListAdapter(mContext, false);
                    CommentListDetailsAdapter commentListAdapter=new CommentListDetailsAdapter(mContext, false);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    //postHolder.commentlist.setLayoutManager(layoutManager);
                    //postHolder.commentlist.setItemAnimator(new DefaultItemAnimator());
                    commentlist.setAdapter(commentListAdapter);
                    //Utils.getListViewSize(commentlist);
                    // int lay_height=comment_view_lay.getHeight();
                    //((PostDetailsAcitivity)mContext).setScroll(lay_height );



                } else {
                    //Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }
            }

        }.execute();

    }


    // emoticon setup

    private GPWallPostAdapter_details.EmoticonHandler mEmoticonHandler;
    EditText sharetxt_edit=null;

    public void setupImoticon(EditText sharetxt,ImageView emojiicon){
        sharetxt_edit=sharetxt;
        mEmoticonHandler = new GPWallPostAdapter_details.EmoticonHandler(sharetxt);
        emojiWindow(emojiicon);


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

    PopupWindow changeSortPopUp1=null;

    public void emojiWindow(ImageView emojiicon) {

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.emojiwindow, null);
        changeSortPopUp1 = new PopupWindow(layout,width,(int)(height/2.5),true);

        // changeSortPopUp1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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
        changeSortPopUp1.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp1.setContentView(layout);
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());
        changeSortPopUp1.setOutsideTouchable(true);


//        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        changeSortPopUp.setHeight((int)(height/3));
        changeSortPopUp1.setFocusable(false);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = 0;
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.bottom + OFFSET_Y);
    }

    public void addImageBetweentext(Drawable drawable, String emojoCode) {
        // Create the ImageSpan

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable);

        // Get the selected text.
        int start = sharetxt_edit.getSelectionStart();
        int end = sharetxt_edit.getSelectionEnd();
        Editable message = sharetxt_edit.getEditableText();

        // Insert the emoticon.
        message.replace(start, end, emojoCode);
        message.setSpan(span, start, start + emojoCode.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    public void tagWondow(final String recogID, ImageView likeicon, int windowsize,final GPWALLPOST getMorePost) {


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
            String gid="",gpid="";
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
                gid=getMorePost.getGid();
                gpid=getMorePost.getGpostid();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new GPTagPostManager(mContext).callTagedUser(gid,gpid);
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
                    GPTagPostAdapter tagPostAdapter = new GPTagPostAdapter(mContext);
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

}

