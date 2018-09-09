package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.SocialSharingAcitivty;
import expression.sixdexp.com.expressionapp.manager.ServerCall;

/**
 * Created by Praveen on 29-Jun-17.
 */

public class SharingInterface {

    Context mContext;
     String postid,userid,poston;
    ImageView share_img;
    public SharingInterface(Context mContext){
        this.mContext=mContext;
    }

    public void shareSocial(ImageView share_img, final LinearLayout top_lay, final String postid, final String userid) {
        this.postid=postid;
        this.userid=SharedPrefrenceManager.getUserID(mContext);
        this.share_img=share_img;
        getPack();
        final PopupWindow changeSortPopUp1 = new PopupWindow(mContext);

        changeSortPopUp1.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.social_sharing_dialog_view, null);

        ImageView facebook_icon,linkedin_icon,twitter_icon,whatsapp_icon;

        facebook_icon=(ImageView)layout.findViewById(R.id.facebook_icon);
        linkedin_icon=(ImageView)layout.findViewById(R.id.linkedin_icon);
        twitter_icon=(ImageView)layout.findViewById(R.id.twitter_icon);
        whatsapp_icon=(ImageView)layout.findViewById(R.id.whatsapp_icon);

        facebook_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pkManager = mContext.getPackageManager();


                PackageInfo pkgInfo = null;
                try {
                    pkgInfo = pkManager.getPackageInfo("com.facebook.katana", 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    if(pkgInfo==null) {
                        pkgInfo = pkManager.getPackageInfo("com.facebook.lite", 0);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    if(pkgInfo==null) {
                        pkgInfo = pkManager.getPackageInfo("com.facebook.android", 0);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    if(pkgInfo==null) {
                        pkgInfo = pkManager.getPackageInfo("com.facebook.mlite", 0);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                if (pkgInfo != null) {


                    String getPkgInfo = pkgInfo.toString();
                    Log.i("pag 1343245", "" + getPkgInfo);
                    if (getPkgInfo.contains("com.facebook")) {
                        changeSortPopUp1.dismiss();
                        changeSortPopUp1.dismiss();
                        Constant.shareBitmap = takeScreenshot(top_lay);
               /* Intent intent=new Intent(mContext, SocialSharingAcitivty.class);
                intent.putExtra("postid",postid);
                intent.putExtra("userid",userid);
                intent.putExtra("poston","Facebook");
                intent.putExtra("imagepath",mPath);
                mContext.startActivity(intent);*/
                        poston = "Facebook";
                        new GetImageANDPostUrl().execute();
                    }
                    else{
                        Toast.makeText(mContext,"To Connect with Facebook You need to install Facebook.",Toast.LENGTH_SHORT).show();
                    }

                }

                        else{
                            Toast.makeText(mContext,"To Connect with Facebook You need to install Facebook.",Toast.LENGTH_SHORT).show();
                        }

            }
        });

        linkedin_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PackageManager pkManager = mContext.getPackageManager();
                try {
                    PackageInfo pkgInfo = pkManager.getPackageInfo("com.linkedin.android", 0);
                    String getPkgInfo = pkgInfo.toString();

                    Log.i("pag 1343245",""+getPkgInfo);

                    if (getPkgInfo.contains("com.linkedin.android")) {
                        changeSortPopUp1.dismiss();

                        poston = "LinkedIn";
                        changeSortPopUp1.dismiss();
                        Constant.shareBitmap = takeScreenshot(top_lay);
                        Intent intent = new Intent(mContext, SocialSharingAcitivty.class);
                        intent.putExtra("postid", postid);
                        intent.putExtra("userid", userid);
                        intent.putExtra("poston", "LinkedIn");
                        intent.putExtra("imagepath", mPath);
                        mContext.startActivity(intent);
                    }
                    else{
                        Toast.makeText(mContext,"To Connect with LinkedIn You need to install LinkedIn.",Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"To Connect with LinkedIn You need to install LinkedIn.",Toast.LENGTH_SHORT).show();
                    // APP NOT INSTALLED

                }
            }
        });

        twitter_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pkManager = mContext.getPackageManager();
                try {
                    PackageInfo pkgInfo = pkManager.getPackageInfo("com.twitter.android", 0);
                    String getPkgInfo = pkgInfo.toString();

                    if (getPkgInfo.contains("com.twitter.android"))   {
                        changeSortPopUp1.dismiss();
                        Constant.shareBitmap=takeScreenshot(top_lay);
                /*Intent intent=new Intent(mContext, SocialSharingAcitivty.class);
                intent.putExtra("postid",postid);
                intent.putExtra("userid",userid);
                intent.putExtra("poston","Twitter");
                intent.putExtra("imagepath",mPath);
                mContext.startActivity(intent);*/
                        poston="Twitter";
                        new GetImageANDPostUrl().execute();
                    }

                    else{
                        Toast.makeText(mContext,"To Connect with Twitter You need to install Twitter.",Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"To Connect with Twitter You need to install Twitter.",Toast.LENGTH_SHORT).show();
                    // APP NOT INSTALLED

                }


            }
        });

        whatsapp_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pkManager = mContext.getPackageManager();
                try {
                    PackageInfo pkgInfo = pkManager.getPackageInfo("com.whatsapp", 0);
                    String getPkgInfo = pkgInfo.toString();

                    if (getPkgInfo.contains("com.whatsapp"))   {
                        changeSortPopUp1.dismiss();
                        Constant.shareBitmap=takeScreenshot(top_lay);
              /*  Intent intent=new Intent(mContext, SocialSharingAcitivty.class);
                intent.putExtra("postid",postid);
                intent.putExtra("userid",userid);
                intent.putExtra("poston","Whatsapp");
                intent.putExtra("imagepath",mPath);

                mContext.startActivity(intent);*/

                        poston="Whatsapp";
                        new GetImageANDPostUrl().execute();
                    }

                    else{
                        Toast.makeText(mContext,"To Connect with Whatsapp You need to install Whatsapp.",Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"To Connect with Whatsapp You need to install Whatsapp.",Toast.LENGTH_SHORT).show();
                    // APP NOT INSTALLED

                }



            }
        });




        Rect rc = new Rect();
        share_img.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        share_img.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
       // changeSortPopUp1.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp1.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //changeSortPopUp1.setHeight((int)(height/1.3));
        changeSortPopUp1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp1.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =-height;
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());


        final View container=(View)top_lay.getRootView();
        final WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.alpha=0.3f;
        // p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
        changeSortPopUp1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                p.alpha=1.0f;
                wm.updateViewLayout(container, p);
            }
        });




        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(layout, Gravity.BOTTOM, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }

    String mPath="";
    String mPath1="";
    private Bitmap takeScreenshot(View v1) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        Bitmap bitmap=null;
        try {

            File temp_path= new File(Environment.getExternalStorageDirectory().toString() + "/expression_sixd_exp_saved_images");

            if(!temp_path.exists())
                temp_path.mkdirs();

            mPath = Environment.getExternalStorageDirectory().toString() + "/expression_sixd_exp_saved_images/" + now + ".jpg";
            // create bitmap screen capture
            //  View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(v1.getDrawingCache());//Bitmap.createScaledBitmap(v1.getDrawingCache(), v1.getWidth(),627, false);
            v1.setDrawingCacheEnabled(false);

/*
            bitmap = Bitmap.createBitmap(v1.getWidth(), v1.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(bitmap);
            v1.layout(0, 0, v1.getLayoutParams().wid
            th, v1.getLayoutParams().height);
            v1.draw(c);*/



            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
        return  bitmap;
    }


    ProgressDialog progressDialog=null;
    public class GetImageANDPostUrl extends AsyncTask<Void, Void, Void> {

        String jsonResponse = "";

        @Override
        protected void onPreExecute() {
            getStringImage(Constant.shareBitmap);
            if (progressDialog == null) {
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("base64_string", base64_string);
            entitymap.put("userid", userid);
            entitymap.put("postid", postid);
            entitymap.put("social_account",poston.toLowerCase());
            jsonResponse = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/sharingonsocialnetwork");
            Log.i("json res34245", "" + jsonResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String responseCode = "";
            String responseString = "";
            progressDialog.dismiss();
            try {
                if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(mContext).isConnectingToInternet()) {
                    Object object = new JSONTokener(jsonResponse).nextValue();
                    if (object instanceof JSONObject) {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        if (jsonObject.has("responseCode")) {
                            responseCode = jsonObject.getString("responseCode");
                            if (responseCode.equalsIgnoreCase("100")) {
                                JSONObject responseData = jsonObject.getJSONObject("responseData");
                                String imageurl = responseData.getString("imageurl");
                                String posturl = responseData.getString("shareurl");

                                if(poston.equals("Twitter"))
                                    postToTwitter(posturl,imageurl);
                                else if(poston.equals("Facebook"))
                                    sharePhotoToFacebook(imageurl,posturl);

                                 else if(poston.equals("Whatsapp"))
                                    shareOnWhatsapp(posturl);

                            } else {
                                progressDialog.dismiss();
                                responseString = jsonObject.getString("responseData");
                                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            responseString = "Received data is not compatible.";
                            Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, jsonResponse, Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    responseString = "Please check your internet connection.";
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

    String base64_string = "";
    public void getStringImage(Bitmap finalBitmap) {
        if (finalBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] imageBytes = baos.toByteArray();
            base64_string = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

    }


    // posting on Twitter
   public static int TWEETER_REQ_CODE = 657;
    public void postToTwitter(String posturl,String imagepath) {
        progressDialog.dismiss();

        Uri imageUri = FileProvider.getUriForFile(mContext, "expression.sixdexp.com.expressionapp.fileprovider",
                new File(mPath));

        Log.i("content uri 4325346", "" + imageUri);

        TweetComposer.Builder builder = null;
        try {
            Intent i = new TweetComposer.Builder(mContext)
                   // .text("Test Hello")
                    .image(imageUri)
                    .url(new URL(posturl))
                    .createIntent();
            ((Activity)mContext).startActivityForResult(i,TWEETER_REQ_CODE);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //builder.show();

    }

    ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private void sharePhotoToFacebook(String imageurl, String posturl) {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog((Activity)mContext);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setImageUrl(Uri.parse(imageurl))
                .setContentUrl(Uri.parse(posturl))
                .build();



        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                //do something

            }

            @Override
            public void onError(FacebookException error) {
                // TODO Auto-generated method stub

            }

        });

        shareDialog.show(linkContent);

        Log.i("imageurl 234532", "" + imageurl);
        Log.i("posturl 234532", "" + posturl);
    }



    // share on whats app

    public static int whatsappCode = 981;
    public void shareOnWhatsapp(final String posturl) {

        final PopupWindow changeSortPopUp1 = new PopupWindow(mContext);

        changeSortPopUp1.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.socialsharingacitivty_lay, null);


        ImageView choosedimg;
        TextView cancel_txt, sharebtn, header_title;


        final EditText edit_share = (EditText) layout.findViewById(R.id.edit_share);
        choosedimg = (ImageView) layout.findViewById(R.id.choosedimg);
        cancel_txt = (TextView) layout.findViewById(R.id.cancel_txt);
        sharebtn = (TextView) layout.findViewById(R.id.sharebtn);
        header_title = (TextView) layout.findViewById(R.id.header_title);
        header_title.setText("Posting To Whatsapp");


        if (Constant.shareBitmap != null) {
            choosedimg.setImageBitmap(Constant.shareBitmap);
        }

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp1.dismiss();
            }
        });
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit_share_txt=edit_share.getText().toString().trim();
                changeSortPopUp1.dismiss();
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, edit_share_txt + "\n\n " +posturl);
                try {
                    ((Activity)mContext).startActivityForResult(whatsappIntent, whatsappCode);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mContext, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }


            }});

        Rect rc = new Rect();
        share_img.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        share_img.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // changeSortPopUp1.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp1.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //changeSortPopUp1.setHeight((int)(height/1.3));
        changeSortPopUp1.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp1.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =-height;
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }


    private void getPack(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);

        for (int i=0;i<pkgAppsList.size();i++){
            Log.i("pack12434",""+pkgAppsList.get(i).toString());
        }
    }

}
