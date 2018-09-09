package expression.sixdexp.com.expressionapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.manager.ShareAnUpdateManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 16-Jun-17.
 */

public class SocialSharingAcitivty extends AppCompatActivity {

    Context mContext;
    ImageView choosedimg;
    TextView cancel_txt, sharebtn, header_title;
    String postID = "";
    String userid = "";
    String base64_string = "";
    String poston = "";
    String imagepath = "";
    ProgressDialog progressDialog = null;
    EditText edit_share;
    String edit_txt = "";
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialsharingacitivty_lay);
       /* Twitter.initialize(this);
        FacebookSdk.sdkInitialize(this);
 */ mContext = this;
        Intent intent = getIntent();
        postID = intent.getStringExtra("postid");
        userid = SharedPrefrenceManager.getUserID(mContext);//intent.getStringExtra("userid");
        poston = intent.getStringExtra("poston");
        imagepath = intent.getStringExtra("imagepath");

        initViews();

        Log.i("local path 324324", "" + imagepath);

    }

    public void initViews() {

        edit_share = (EditText) findViewById(R.id.edit_share);
        choosedimg = (ImageView) findViewById(R.id.choosedimg);
        cancel_txt = (TextView) findViewById(R.id.cancel_txt);
        sharebtn = (TextView) findViewById(R.id.sharebtn);
        header_title = (TextView) findViewById(R.id.header_title);

        if (poston.equals("Facebook")) {
            edit_share.setVisibility(View.INVISIBLE);
            shareDialog = new ShareDialog(this);

        }


        if (poston != null)
            header_title.setText("Posting To " + poston);

        if (Constant.shareBitmap != null) {
            choosedimg.setImageBitmap(Constant.shareBitmap);
            getStringImage(Constant.shareBitmap);
        }

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postID != null && userid != null) {

                    edit_txt = edit_share.getText().toString().trim();

                    if (poston.equals("Twitter"))
                        new GetImageANDPostUrl().execute();
                    else if (poston.equals("LinkedIn"))
                        getLinkedinAccessToken();
                    else if (poston.equals("Facebook"))
                        new GetImageANDPostUrl().execute();
                    else if (poston.equals("Whatsapp")) {
                        new GetImageANDPostUrl().execute();
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class GetImageANDPostUrl extends AsyncTask<Void, Void, Void> {

        String jsonResponse = "";

        @Override
        protected void onPreExecute() {
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
            entitymap.put("postid", postID);
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


                                if (poston.equals("Twitter"))
                                    postToTwitter(posturl);
                                else if (poston.equals("LinkedIn"))
                                    shareLinkedIn(imageurl, posturl);
                                else if (poston.equals("Facebook")) {
                                    sharePhotoToFacebook(imageurl, posturl);
                                    //facebookShare(imageurl, posturl);
                                } else if (poston.equals("Whatsapp")) {
                                    progressDialog.dismiss();
                                    shareOnWhatsapp(posturl);
                                }


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


    public void getStringImage(Bitmap finalBitmap) {
        if (finalBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] imageBytes = baos.toByteArray();
            base64_string = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

    }


    // share on linkedin

    public void shareLinkedIn(String imageurl, String posturl) {
        String url = "https://api.linkedin.com/v1/people/~/shares";
        try {

            JSONObject body = new JSONObject();
            body.put("comment", edit_txt);

            JSONObject contentJson = new JSONObject();
            contentJson.put("title", "Tata Xpression Post");
            //contentJson.put("description",edit_txt);
            contentJson.put("submitted-url", posturl);
            contentJson.put("submitted-image-url", imageurl);

            JSONObject visibilityJson = new JSONObject();
            visibilityJson.put("code", "anyone");

            body.put("content", contentJson);
            body.put("visibility", visibilityJson);

            APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
            apiHelper.postRequest(this, url, body, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                @Override
                public void onApiError(LIApiError liApiError) {

                    progressDialog.dismiss();
                    Toast.makeText(mContext, "" + liApiError.getApiErrorResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // Linkedin Authentication and access token

    public void getLinkedinAccessToken() {
        LISessionManager.getInstance(getApplicationContext())
                .init(this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {

                        Log.i("hello linked 324", "" + LISessionManager
                                .getInstance(getApplicationContext())
                                .getSession().getAccessToken().toString());

                        new GetImageANDPostUrl().execute();

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Toast.makeText(getApplicationContext(), "failed "
                                        + error.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.i("hello linked 324", "" + error.toString());
                    }
                }, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        //Toast.makeText(mContext,"Hi"+resultCode,Toast.LENGTH_SHORT).show();

        if (poston.equals("Twitter") && requestCode == TWEETER_REQ_CODE) {
            Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();
            //onBackPressed();
        }

        if (poston.equals("Whatsapp") && requestCode == whatsappCode) {
            Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        if (poston.equals("Facebook")) {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        } else if (poston.equals("LinkedIn")) {
            LISessionManager.getInstance(getApplicationContext())
                    .onActivityResult(this,
                            requestCode, resultCode, data);
        }


        Log.i("result code 324325", "" + resultCode);
    }

    // set the permission to retrieve basic information of User's linkedIn account
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS, Scope.W_SHARE);
    }


    int TWEETER_REQ_CODE = 657;

    // posting on Twitter
    public void postToTwitter(String posturl) {
        progressDialog.dismiss();

        Uri imageUri = FileProvider.getUriForFile(mContext, "expression.sixdexp.com.expressionapp.fileprovider",
                new File(imagepath));

        Log.i("content uri 4325346", "" + imageUri);

        TweetComposer.Builder builder = null;
        try {
            Intent i = new TweetComposer.Builder(this)
                    .text(edit_txt)
                    .image(imageUri)
                    .url(new URL(posturl))
                    .createIntent();
            startActivityForResult(i,TWEETER_REQ_CODE);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //builder.show();

    }


    // Facebook Share

    private CallbackManager callbackManager;
    private LoginManager manager;

    public void facebookShare(final String imageurl, final String posturl) {
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        manager = LoginManager.getInstance();
        manager.logInWithPublishPermissions(this, permissionNeeds);

        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { //Toast.makeText(getApplicationContext(), ""+loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();
                sharePhotoToFacebook(imageurl, posturl);

            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
                progressDialog.dismiss();
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                progressDialog.dismiss();
                Toast.makeText(mContext, "" + exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sharePhotoToFacebook(String imageurl, String posturl) {
        callbackManager = CallbackManager.Factory.create();
        /*SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(Constant.shareBitmap)
                //.setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();



       Uri imageUri = FileProvider.getUriForFile(mContext,"expression.sixdexp.com.expressionapp.fileprovider",
                new File(imagepath));*/

       /* ShareLinkContent content1 = new ShareLinkContent.Builder()
                .setContentTitle("Test post")
                .setImageUrl(Uri.parse("https://xpressions.tatapower.com//Content/images/Login/Landing%20page%20art%202.jpg"))
                .setContentDescription("HELLO")
                .setContentUrl(Uri.parse("https://xpressions.tatapower.com/")).build();*/
        progressDialog.dismiss();
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setImageUrl(Uri.parse(imageurl))
                .setContentUrl(Uri.parse(posturl))
                .build();


        //shareDialog.show(linkContent, ShareDialog.Mode.FEED);
        shareDialog.show(linkContent);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();

                onBackPressed();
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

        Log.i("imageurl 234532", "" + imageurl);
        Log.i("posturl 234532", "" + posturl);

/*

        ShareApi.share(linkContent, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(),"Shared successfully.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                progressDialog.dismiss();

                Log.i("3124324",""+error.getMessage());
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
*/


    }



    // share on whatsapp

    int whatsappCode = 981;

    public void shareOnWhatsapp(String posturl) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Xpression Post" + "\n\n " + posturl);
        try {
            startActivityForResult(whatsappIntent, whatsappCode);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }

       /* Uri imageUri = FileProvider.getUriForFile(mContext, "expression.sixdexp.com.expressionapp.fileprovider",
                new File(imagepath));

        Uri uri = Uri.parse("file://"+imagepath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Xpression Post" + "\n\nLink : " + posturl );
        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.setType("image*//*");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext,"Whatsapp have not been installed.",Toast.LENGTH_SHORT).show();
        }

*/

    }


}
