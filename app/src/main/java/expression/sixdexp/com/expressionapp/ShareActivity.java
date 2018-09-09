package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.BulletinCategory;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.EmojiAdapter;
import expression.sixdexp.com.expressionapp.adapter.ShareBulletinAdapter;
import expression.sixdexp.com.expressionapp.manager.BulletinEmailSearchManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;
import expression.sixdexp.com.expressionapp.manager.ShareAnUpdateManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.CustomAutoCompleteTextView;
import expression.sixdexp.com.expressionapp.utility.ImageCompressor;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SetPhotoNew;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.utility.TextChangeListe;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;
import id.zelory.compressor.FileUtil;
import placesautocomplete.PickLocationActivity;

/**
 * Created by Praveen on 7/4/2016.
 */

  public class ShareActivity extends Activity implements View.OnClickListener
{

    EditText select_category;
    private AlertDialog myDialog;
    List<BulletinCategory> allbulletinCategories;
    List<String> productTitle1=new ArrayList<>();
    List<String> productTitle11=new ArrayList<>();
    int select_category_id=0;
    String select_category_id_new="0";

    String taguser="";
    String doctype = "";
    String file_name = "";
    SetPhotoNew selPhoto;
    Context mContext;
    ImageView cancelshare, cancelattachment;
    // RoundedImageView nominatorimg;
    EditText sharetxt;
    String place_location="";
    ImageView xpwayimg, cameraimg, videoimg, documentimg,jeo_tagging;
    LinearLayout linklay;
    TextView sharebtn;
    TextView videodoclink;
    //ImageView attachimg;
    ProgressDialog progressDialog;
    String sharedtxt = "";
    String viddoclink = "";
    String base64 = "";
    String ftype = "";
    String file_extension = "";
    String content_type = "";
    String xwaysubject_txt = "";
    String xprssway = "0";
    TextView location_place_txt;
    String temp_xp_sub="";
    String temp_video_url="";
    LinearLayout xpress_bulletin_ll,bulletin_show_ll;
    CheckBox CbTermAndServicesCheckBox;
    LinearLayout xpressway_subject_ll,jeo_tagging_ll;
    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 13;
    EditText xwaysubject;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    ImageView emojiicon,keyicon;
    LinearLayout parentlay;
    private EmoticonHandler mEmoticonHandler;

    ListView seltoorcc;
    LinearLayout views_lay;
    ImageView tagicon;
    TextView tagcount_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.shareanupdateviewtab);
        setContentView(R.layout.sharetest1);
        mContext = this;
        Constant.tag_txt=null;
        TagActivty.taged_users.clear();
        productTitle1.clear();

        allbulletinCategories=new GetMorePostManager(mContext).getBulletinCategories();
        for (int x=0;x<allbulletinCategories.size();x++)
        {
            productTitle1.add(allbulletinCategories.get(x).getName());
            productTitle11.add(allbulletinCategories.get(x).getId());
        }


        tagicon=(ImageView)findViewById(R.id.tagicon);
        tagicon.setVisibility(View.VISIBLE);
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(mContext,TagActivty.class);
                startActivity(intent);
            }
        });
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(mContext,R.layout.list_v, productTitle1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_category=(EditText)findViewById(R.id.select_category);
        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.show();
            }
        });
        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setTitle("Select Category:-");
/* builder2.setIcon(R.drawable.ac_type);*/
        builder2.setAdapter(dataAdapter1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                select_category.setText(productTitle1.get(i));
                select_category_id = Integer.parseInt(productTitle11.get(i));
                // Toast.makeText(mContext, "select_category_id=" + select_category_id, Toast.LENGTH_LONG).show();
            }
        });
        builder2.setCancelable(true);
        myDialog = builder2.create();
        initview();

        setToListOFBulletin();
        prepareAutoEmailSearch();


    }

    int heightDifference=100;
    public void initview() {
        tagcount_txt=(TextView)findViewById(R.id.tagcount_txt);
        views_lay=(LinearLayout)findViewById(R.id.views_lay);
        parentlay=(LinearLayout)findViewById(R.id.parentlay);


        emojiicon=(ImageView)findViewById(R.id.emojiicon);
        keyicon=(ImageView)findViewById(R.id.keyicon);


        keyicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyicon.setVisibility(View.GONE);
                emojiicon.setVisibility(View.VISIBLE);
                if(changeSortPopUp!=null){
                    if(changeSortPopUp.isShowing()){changeSortPopUp.dismiss();}
                }
                sharetxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(sharetxt, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        emojiicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiicon.setVisibility(View.GONE);
                keyicon.setVisibility(View.VISIBLE);


                if(changeSortPopUp!=null) {
                    if (!changeSortPopUp.isShowing()) {
                        View view = getCurrentFocus();
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        emojiWindow();
                    }
                }

                else{

                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    emojiWindow();
                }


            }
        });
        cancelattachment = (ImageView) findViewById(R.id.cancelattachment);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
   /*     nominatorimg = (RoundedImageView) findViewById(R.id.nominatorimg);
        nominatorimg.setOnClickListener(this);*/
        xwaysubject = (EditText) findViewById(R.id.xwaysubject);
        sharetxt = (EditText) findViewById(R.id.sharetxt);


        mEmoticonHandler = new EmoticonHandler(sharetxt);

        sharetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext,"HELLO",Toast.LENGTH_SHORT).show();

                keyicon.setVisibility(View.GONE);
                emojiicon.setVisibility(View.VISIBLE);
                if(changeSortPopUp!=null){
                    if(changeSortPopUp.isShowing()){changeSortPopUp.dismiss();}
                }
                sharetxt.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(sharetxt, InputMethodManager.HIDE_IMPLICIT_ONLY);

            }
        });

        // addImageBetweentext(getResources().getDrawable(R.drawable.tempsmle),"<img src=\"https://xpressions.tatapower.com/smiley/2.png\" height=\"100\" width=\"100\">");

        //sharetxt.addTextChangedListener(new TextChangeListe(sharetxt));
        location_place_txt=(TextView)findViewById(R.id.location_place_txt);
        xpwayimg = (ImageView) findViewById(R.id.xpwayimg);

        jeo_tagging=(ImageView)findViewById(R.id.jeo_tagging);
        jeo_tagging.setImageResource(R.drawable.checkin_tagging_disable);
        jeo_tagging_ll=(LinearLayout)findViewById(R.id.jeo_tagging_ll);
        jeo_tagging_ll.setVisibility(View.GONE);
        jeo_tagging.setOnClickListener(this);

        cameraimg = (ImageView) findViewById(R.id.cameraimg);
        videoimg = (ImageView) findViewById(R.id.videoimg);
        documentimg = (ImageView) findViewById(R.id.documentimg);

        xpressway_subject_ll=(LinearLayout)findViewById(R.id.xpressway_subject_ll);
        xpressway_subject_ll.setVisibility(View.GONE);
        bulletin_show_ll=(LinearLayout)findViewById(R.id.bulletin_show_ll);
        // bulletin_show_ll.setVisibility(View.GONE);
        xpress_bulletin_ll=(LinearLayout)findViewById(R.id.xpress_bulletin_ll);
        xpress_bulletin_ll.setVisibility(View.GONE);

        List<UserLoginInfo> userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mContext).getUserInfo();
        String profile_img_url = userDatas.get(0).getImageurl();

        if(userDatas.get(0).getPost_xpress().equalsIgnoreCase("1"))
        {
            xpwayimg.setVisibility(View.VISIBLE);
            xpress_bulletin_ll.setVisibility(View.VISIBLE);
            bulletin_show_ll.setVisibility(View.VISIBLE);
        }
        else
        {
            xpwayimg.setVisibility(View.GONE);
            xpress_bulletin_ll.setVisibility(View.GONE);
            bulletin_show_ll.setVisibility(View.GONE);
        }


        CbTermAndServicesCheckBox=(CheckBox)findViewById(R.id.CbTermAndServicesCheckBox);
        if (CbTermAndServicesCheckBox.isChecked())
        {
            xpress_bulletin_ll.setVisibility(View.VISIBLE);
            xpressway_subject_ll.setVisibility(View.GONE);
            xpwayimg.setImageResource(R.drawable.xpressway_disable);
        }
        else
        {
            xpress_bulletin_ll.setVisibility(View.GONE);
        }

        CbTermAndServicesCheckBox.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(CbTermAndServicesCheckBox.isChecked())
                {
                    FlipComplexLayoutActivity.isformbulletinpost="yes";
                    System.out.println("Checked");
                    xpress_bulletin_ll.setVisibility(View.VISIBLE);
                    xpressway_subject_ll.setVisibility(View.GONE);
                    //  xpwayimg.setImageResource(R.drawable.xpressway_disable);
                }
                else
                {FlipComplexLayoutActivity.isformbulletinpost="yes";
                    System.out.println("Un-Checked");
                    xpress_bulletin_ll.setVisibility(View.GONE);
                }
            }
        });






      /*  URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" ")) {


     *//*       Picasso.with(mContext)
                    .load(profile_img_url1)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.default_square_user)
                    .error(R.drawable.default_square_user)
                    .into(nominatorimg);*//*
        }*/






        xpwayimg.setOnClickListener(this);
        cameraimg.setOnClickListener(this);
        videoimg.setOnClickListener(this);
        documentimg.setOnClickListener(this);

        linklay = (LinearLayout) findViewById(R.id.linklay);
        //attachimglay=(LinearLayout)findViewById(R.id.attachimglay);
        videodoclink = (TextView) findViewById(R.id.videodoclink);
        // attachimg=(ImageView)findViewById(R.id.attachimg);
        sharebtn = (TextView) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(this);

        cancelattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftype = "";
                content_type = "";
                file_extension = "";
                file_name = "";
                base64 = "";
                viddoclink = "";
                videodoclink.setText("");
                linklay.setVisibility(View.GONE);
                selPhoto=null;
                //  cameraimg.setImageResource(R.drawable.image_disable);
                // videoimg.setImageResource(R.drawable.video_disable);
                // documentimg.setImageResource(R.drawable.file_disable);
            }
        });
        checkReceivedIntent();
    }

    ShareBulletinAdapter toAndCCListAdapter1 = null;
    List<String> toList = new ArrayList<String>();
    public void setToListOFBulletin(){
        seltoorcc = (ListView) findViewById(R.id.seltoorcc);

        toAndCCListAdapter1 = new ShareBulletinAdapter(mContext, toList, new ShareBulletinAdapter.ToAndCCChangeListener() {
            @Override
            public void changeCCAndTO(int pos) {
                adapter1.add(toList.get(pos));
                toList.remove(pos);
                //dialog.dismiss();
                //toAndccdialog();
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);
            }
        });

        seltoorcc.setAdapter(toAndCCListAdapter1);
    }

    public void checkReceivedIntent() {

        String userID = SharedPrefrenceManager.getUserID(mContext);
//Toast.makeText(mContext,""+userID,Toast.LENGTH_SHORT).show();
        Log.i("userid89123",""+userID);
        if (userID == null || userID.equals("")) {
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {


            Intent intent = getIntent();
            String action = intent.getAction();
            String type = intent.getType();

            Log.i("type 2454",""+type);

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleSendImage(intent); // Handle single image being sent
                }

                else if(type.startsWith("text/plain")){
                    handleSendTxt(intent);
                }

            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleSendMultipleImages(intent); // Handle multiple images being sent
                }
            }
        }
    }


    void handleSendTxt(Intent intent) {

        cameraimg.setVisibility(View.GONE);
        videoimg.setVisibility(View.GONE);
        documentimg.setVisibility(View.GONE);
        bulletin_show_ll.setVisibility(View.GONE);
        //  views_lay.setVisibility(View.INVISIBLE);
        String sharedText =intent.getStringExtra(Intent.EXTRA_STREAM);

        if(sharedText!=null) {
            sharetxt.setText("" + sharedText);
        }


    }

    void handleSendImage(Intent intent) {

        //views_lay.setVisibility(View.INVISIBLE);
        cameraimg.setVisibility(View.GONE);
        videoimg.setVisibility(View.GONE);
        documentimg.setVisibility(View.GONE);
        bulletin_show_ll.setVisibility(View.GONE);
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            Log.i("URI3432",""+imageUri.toString());

            setImageTosend(imageUri);
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Log.i("URI size3432",""+imageUris.size());
        }


    }

    public void setImageTosend(Uri uri){
        //setBase64ForDoc(uri);

      /*  double filesizeiInMB=getFileSizeinMB(uri);
        if(filesizeiInMB>1){
            compressImage(uri);
        }

        else{*/
        try {
            File actualImage = FileUtil.from(this,uri);
            Bitmap finalBitmap=  BitmapFactory.decodeFile(actualImage.getAbsolutePath());
            getStringImage(finalBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}

        String selectedImage=uri.toString();

        if(selectedImage.toString().contains("file")){

            file_extension=selectedImage.toString().substring((selectedImage.toString().lastIndexOf(".")));
            file_name=selectedImage.toString().substring((selectedImage.toString().lastIndexOf("/"))+1);
            String temp_file_extension=selectedImage.toString().substring((selectedImage.toString().lastIndexOf("."))+1);
            temp_file_extension=temp_file_extension.toLowerCase();
            content_type= MimeTypeMap.getSingleton().getMimeTypeFromExtension(temp_file_extension);
            ftype = "photo";
        }

        else{


            if(uri.toString().contains("whatsapp")) {
                try {
                    File f = FileUtil.from(mContext, uri);
                    String file_str = f.getAbsolutePath().toString();
                    Log.i("file 556677", "" + file_str);
                    file_extension = file_str.substring(file_str.lastIndexOf("."), file_str.length());
                    file_name = f.getName();
                    content_type = getMimeType(f.getAbsolutePath());
                    ftype = "photo";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                String mTmpGalleryPicturePath = getPath(uri);
                file_extension = mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());
                file_name = new File(mTmpGalleryPicturePath).getName();
                content_type = getMimeType(mTmpGalleryPicturePath);
                ftype = "photo";
            }


        }

        videodoclink.setText(""+file_name);
        linklay.setVisibility(View.VISIBLE);
    }

    public String getMimeType(String url) {
        String type="";
        String extension = url.substring((url.lastIndexOf(".")) + 1).toLowerCase();
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public void setBase64ForDoc(Uri filepath) {
        InputStream in = null;
        try {
            in = mContext.getContentResolver().openInputStream(filepath);
            byte[] imageBytes = IOUtils.toByteArray(in);
            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getStringImage(Bitmap finalBitmap) {
        if (finalBitmap != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                byte[] imageBytes = baos.toByteArray();
                baos.flush();
                baos.close();
                base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.i("my base3242",""+base64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    String emailID="";
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancelshare:
                onBackPressed();
                break;

            case R.id.jeo_tagging:
             /*   Intent intent=new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();*/
                Intent i= new Intent(mContext, PickLocationActivity.class);
                startActivityForResult(i, CUSTOM_AUTOCOMPLETE_REQUEST_CODE);
                break;

            case R.id.xpwayimg:
                FlipComplexLayoutActivity.isformbulletinpost="no";
                if (xpressway_subject_ll.getVisibility() == View.VISIBLE)
                {
                    xpressway_subject_ll.setVisibility(View.GONE);
                    // xpwayimg.setImageResource(R.drawable.xpressway_disable);
                }
                else {
                    // Either gone
                    xpressway_subject_ll.setVisibility(View.VISIBLE);
                    xpress_bulletin_ll.setVisibility(View.GONE);
                    CbTermAndServicesCheckBox.setChecked(false);
                    // xpwayimg.setImageResource(R.drawable.xpressway_enable_new);
                }

                // xwSubjecdialog();

                break;

            case R.id.cameraimg:
                selPhoto = new SetPhotoNew(mContext, videodoclink, linklay, "image",cameraimg,documentimg,videoimg);
                ftype = "photo";
                break;

            case R.id.videoimg:
                selPhoto = null;
                ftype = "video";
                videoLinkdialog();
                //videodoclink.setText("https://www.youtube.com/watch?v=m20n_GAsCtM");

                break;

            case R.id.documentimg:
                selPhoto = new SetPhotoNew(mContext, videodoclink, linklay, "doc",cameraimg,documentimg,videoimg);
                ftype = "photo";
                break;

            case R.id.sharebtn:

                Log.i("content_type", "" + content_type);

                sharedtxt = sharetxt.getText().toString().trim();
                viddoclink = videodoclink.getText().toString();
                if (selPhoto != null) {
                    base64 = selPhoto.getbase64();
                    file_extension = selPhoto.getextention();
                    if(file_extension==null) file_extension="";
                    content_type = selPhoto.getcontentType();
                    file_name = selPhoto.getFileName();
                    if(file_name==null)file_name="";

                }
                if (ftype.equals("photo")) {
                    if (base64.length() == 0) {
                        ftype = "";
                        content_type = "";
                        file_extension = "";
                        file_name = "";
                    }
                    viddoclink = "";

                }
                if (ftype.equals("video")) {
                    if (viddoclink.length() == 0) {
                        ftype = "";
                        content_type = "";
                        file_extension = "";
                        file_name = "";
                    }
                    content_type = "";
                    file_extension = "";
                    base64 = "";
                    file_name = "";
                }

                Log.i("file_extension", "" + file_extension);
                if(CbTermAndServicesCheckBox.isChecked()){
                    //String emailID=searchpostedit.getText().toString();

                    if(toList!=null && toList.size()>0){

                        for(int k=0;k<toList.size();k++){
                            if(k<toList.size()-1){
                                emailID=emailID+toList.get(k)+",";
                            }
                            else{
                                emailID=emailID+toList.get(k);
                            }
                        }
                    }

                    Log.i("emails 43254325",""+emailID);

                    if(emailID.length()>0){

                        if(toList.size()<=5) {
                            //  if(ValidationUtility.validEmailAddress(emailID)){

                            if (select_category_id > 0) {

                                if ((!sharedtxt.equals("") && ValidationUtility.validEditTextString(sharedtxt)) && (viddoclink.length() > 0 || base64.length() > 0)) {

                                    if (xpressway_subject_ll.getVisibility() == View.VISIBLE) {
                                        xwaysubject_txt = xwaysubject.getText().toString();
                                        temp_xp_sub = xwaysubject_txt;
                                        if (ValidationUtility.validEditTextString(xwaysubject_txt)) {
                                            xprssway = "1";
                                            new PostShare().execute();
                                        } else {
                                            Toast.makeText(mContext, "Please Enter Xpressway Subject!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // Either gone
                                        new PostShare().execute();
                                    }

                                } else {

                                    if (sharedtxt.equals("")) {
                                        Toast.makeText(mContext, "Please Enter some text!", Toast.LENGTH_LONG).show();
                                    } else if (!ValidationUtility.validEditTextString(sharedtxt) || viddoclink.length() <= 0 || base64.length() <= 0) {
                                        Toast.makeText(mContext, "Please select a file for Xpress Bulletin Post!", Toast.LENGTH_LONG).show();
                                    }


                                }


                            } else {
                                Toast.makeText(mContext, "Please Select a Category!", Toast.LENGTH_LONG).show();
                            }

                            // }

                      /*  else{
                            Toast.makeText(mContext, "Please Enter Valid Email Address!", Toast.LENGTH_LONG).show();
                        }*/
                        }
                        else{
                            Toast.makeText(mContext, "Maximum five emails you can add.", Toast.LENGTH_LONG).show();
                        }
                    }

                    else{
                        Toast.makeText(mContext, "Please Enter Valid Email Address!", Toast.LENGTH_LONG).show();
                    }


                }
                else{
                    if ((!sharedtxt.equals("") && ValidationUtility.validEditTextString(sharedtxt)) || viddoclink.length() > 0 || base64.length() > 0)
                    {

                        if (xpressway_subject_ll.getVisibility() == View.VISIBLE)
                        {
                            xwaysubject_txt = xwaysubject.getText().toString();
                            temp_xp_sub=xwaysubject_txt;
                            if (ValidationUtility.validEditTextString(xwaysubject_txt))
                            {
                                xprssway = "1";
                                new PostShare().execute();


                            }
                            else
                            {
                                Toast.makeText(mContext, "Please Enter Xpressway Subject!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            // Either gone
                            new PostShare().execute();

                        }

                    }
                    else
                    {
                        Toast.makeText(mContext, "Please Enter some text or select any content!", Toast.LENGTH_LONG).show();
                    }

                }



                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  CUSTOM_AUTOCOMPLETE_REQUEST_CODE)
        {
            if(data!=null)
            {

                String text1 = "<font color=#000000> - at </font>";
                jeo_tagging_ll.setVisibility(View.VISIBLE);
                //jeo_tagging.setImageResource(R.drawable.checkin_tagging_enable);
                // set the location recieved from picklocation activity
//                location_place_txt.setText(data.getStringExtra("Location Address"));
                location_place_txt.setText(Html.fromHtml(text1+""+data.getStringExtra("Location Address")));
                place_location=text1+""+data.getStringExtra("Location Address");
            }
        }
        else
        {
            selPhoto.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed()
    {
        FlipComplexLayoutActivity.isformbulletinpost="no";
        TagActivty.taged_users.clear();
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(changeSortPopUp!=null){
            if(changeSortPopUp.isShowing()){
                changeSortPopUp.dismiss();
            }

            else {
                super.onBackPressed();
            }
        }

        else {
            super.onBackPressed();
        }


    }


    public class PostShare extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        // String emailID="";
        StringBuilder taggedUSerID=null;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            select_category_id_new=String.valueOf(select_category_id);
            // Toast.makeText(mContext,""+select_category_id_new,Toast.LENGTH_SHORT).show();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            // emailID=searchpostedit.getText().toString();
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

                //(String base64, String videolink, String sharedtxt, String ftype, String file_extension)
                responseString = new ShareAnUpdateManager(mContext).postUpdateShare(file_name, base64, viddoclink, sharedtxt+place_location,
                        ftype, file_extension, content_type,
                        xprssway, xwaysubject_txt,emailID,select_category_id_new,taggedUSerID.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (responseString.equals("100")) {
                Toast.makeText(mContext, "Post Submitted Successfully!", Toast.LENGTH_LONG).show();

                Constant.lastposition_home=0;
                Constant.lastposition=0;
                TagActivty.taged_users.clear();
                linklay.setVisibility(View.GONE);
                searchpostedit.setText("");
                base64 = "";
                file_extension = "";
                content_type = "";
                file_name = "";
                new GetExpresswayPostTask().execute();
            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }


    public class GetMorePostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                Constant.today = 0;
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
            if (responseString.equals("100"))
            {
                // new RecentActivityTask().execute();
                Intent intent1 = getIntent();
                String type = intent1.getType();


                /*onBackPressed();*/
                Intent intent=new Intent(getApplicationContext(), FlipComplexLayoutActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                if(type!=null) {
                    setResult(431);
                }
                finish();

            } else {

                //Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }


    public class RecentActivityTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new RecentActivityManager(mContext).callRecentActivity();
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

                Intent intent1 = getIntent();
                String type = intent1.getType();


                /*onBackPressed();*/
                Intent intent=new Intent(getApplicationContext(), FlipComplexLayoutActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                if(type!=null) {
                    setResult(431);
                }
                finish();
            } else {

                //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                //finishActivity();
            }

        }
    }

    public class GetExpresswayPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetXpresswayPost();
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
                    new GetMorePostTask().execute();
                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }




    public String getVideoID(String url) {
        String vId = null;
        // String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            vId = matcher.group();
        }
        return vId;
    }



    public void videoLinkdialog() {
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.videodocurldialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final TextView dialogtitle = (TextView) window.findViewById(R.id.dialogtitle);
        final EditText xwaysubject = (EditText) window.findViewById(R.id.xwaysubject);
        final ImageButton submitbtn = (ImageButton) window.findViewById(R.id.submitbtn);
        final ImageView cancelshare = (ImageView) window.findViewById(R.id.cancelshare);

        xwaysubject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence pastetxt = clipboard.getText();
                if (pastetxt != null)
                    xwaysubject.setText(pastetxt);

                return false;
            }
        });


        xwaysubject.setHint("Enter Video URL...");
        dialogtitle.setText("Add Video URL");

        if(temp_video_url.length()>0)
            xwaysubject.setText(temp_video_url);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                dialog.dismiss();
            }
        });



        xwaysubject.addTextChangedListener(new TextChangeListe(xwaysubject));
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viddoclink = xwaysubject.getText().toString();
                temp_video_url=viddoclink;
                if (viddoclink != null && viddoclink.length() > 0) {



                    if(viddoclink.contains( "https://web.microsoftstream.com")){
                        linklay.setVisibility(View.VISIBLE);
                        videodoclink.setText(viddoclink);
                        dialog.dismiss();
                    }
                    else{
                        String tempID= getVideoID(viddoclink);
                        Log.i("tempID 89045645",""+tempID);
                        if(tempID!=null){
                            linklay.setVisibility(View.VISIBLE);
                            videodoclink.setText(viddoclink);
                            // videoimg.setImageResource(R.drawable.video_enable);
                            //cameraimg.setImageResource(R.drawable.image_disable);
                            //documentimg.setImageResource(R.drawable.file_disable);
                            dialog.dismiss();
                        }

                        else{
                           // videodoclink.setText("");
                            Toast.makeText(mContext, "Please Enter Video URL!", Toast.LENGTH_LONG).show();
                        }

                    }



                } else {
                   // videodoclink.setText("");
                    Toast.makeText(mContext, "Please Enter Video URL!", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

/*    public void xwSubjecdialog() {
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.videodocurldialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final EditText xwaysubject = (EditText) window.findViewById(R.id.xwaysubject);
        final TextView submitbtn = (TextView) window.findViewById(R.id.submitbtn);
        final ImageView cancelshare = (ImageView) window.findViewById(R.id.cancelshare);

        if(temp_xp_sub.length()>0)
            xwaysubject.setText(temp_xp_sub);

        xwaysubject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence pastetxt = clipboard.getText();
                if (pastetxt != null)
                    xwaysubject.setText(pastetxt);
                return false;
            }
        });

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                dialog.dismiss();
            }
        });
        xwaysubject.addTextChangedListener(new TextChangeListe(xwaysubject));
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                xwaysubject_txt = xwaysubject.getText().toString();
                temp_xp_sub=xwaysubject_txt;
                if (ValidationUtility.validEditTextString(xwaysubject_txt))
                {
                    xprssway = "1";
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(mContext, "please Enter Subject.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();

    }*/



    public void addImageBetweentext(Drawable drawable, String emojoCode) {
        // Create the ImageSpan

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable);

        // Get the selected text.
        int start = sharetxt.getSelectionStart();
        int end = sharetxt.getSelectionEnd();
        Editable message = sharetxt.getEditableText();

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

    PopupWindow changeSortPopUp=null;

    public void emojiWindow() {


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.emojiwindow, null);
        changeSortPopUp = new PopupWindow(layout,width,(int)(height/2.5),true);

        GridView gridView = (GridView) layout.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new EmojiAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                int id1 = getResources().getIdentifier("expression.sixdexp.com.expressionapp:drawable/a"+position, null, null);
                addImageBetweentext(getResources().getDrawable(id1),"<img src=\"https://xpressions.tatapower.com/smiley/"+position+".png\" height=\"25\" width=\"25\">");
            }
        });

        Rect rc = new Rect();
        emojiicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        emojiicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());
        changeSortPopUp.setOutsideTouchable(true);


//        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        changeSortPopUp.setHeight((int)(height/3));
        changeSortPopUp.setFocusable(false);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = -50;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.bottom + OFFSET_Y);
    }


    LinearLayout searchlay;
    CustomAutoCompleteTextView searchpostedit;
    String key_word = "";
    ArrayAdapter adapter1;

    public void prepareAutoEmailSearch(){
        searchlay = (LinearLayout)findViewById(R.id.searchlay);
        searchpostedit = createAutoTextSearch();
        searchpostedit.addTextChangedListener(new ListenToText());


        searchpostedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //searchpostedit.setText(adapter1.getItem(position).toString());
                searchpostedit.setText("");
                //Toast.makeText(mContext,""+adapter1.getItem(position).toString(),Toast.LENGTH_SHORT).show();
                toList.add(adapter1.getItem(position).toString());
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);
                Log.i("tolist size",""+toList.size());
            }
        });

        searchlay.addView(searchpostedit);


    }

    public CustomAutoCompleteTextView createAutoTextSearch() {
        CustomAutoCompleteTextView searchpostedit1 = new CustomAutoCompleteTextView(mContext);
        searchpostedit1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        searchpostedit1.setThreshold(2);
        //searchpostedit1.setBackgroundColor(Color.parseColor("#00000000"));
        searchpostedit1.setTextSize(15);
        searchpostedit1.setHint("TO");
        //searchpostedit1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
        //searchpostedit1.setPadding(15, 0, 0, 0);
        return searchpostedit1;
    }


    class ListenToText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i("onTextChanged", "" + s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.i("afterTextChanged", "" + s.toString());

            if ((s.toString().length() >= 2)&& !s.toString().contains("@")) {

                key_word = s.toString();
                new AutoTextSearch().execute();

            }
        }
    }

    public class AutoTextSearch extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            // progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new BulletinEmailSearchManager(mContext).callAutoText(key_word);
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

                fillAutoTextList(BulletinEmailSearchManager.autosearchresultList);
                searchpostedit.showDropDown();

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }

        }


    }

    public void fillAutoTextList(List<String> autoTolist) {
        if (adapter1 != null) {
            adapter1.clear();
            adapter1 = null;
        }

        adapter1 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, autoTolist);
        searchpostedit.setAdapter(adapter1);

     /*   searchpostedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("setOnItemClickListener", "setOnItemClickListener");
                //Toast.makeText(mContext, "" + adapter1.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                searchpostedit.setText(adapter1.getItem(position).toString());
            }
        });
*/

    }

    private String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        Cursor cursor;
        String[] projection = { MediaStore.Images.Media.DATA };

        if(uri.toString().contains("shell")){
            return uri.toString();
        }


        else{
            if (Build.VERSION.SDK_INT > 19) {

                Log.i("uri555", "" + uri.toString());
                if (uri.toString().contains("media/external") || uri.toString().contains("google")) {
                    cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
                    Log.i("if3432", "" + cursor);
                } else {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String sel = MediaStore.Images.Media._ID + "=?";
                    cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, sel, new String[]{id}, null);
                }
                Log.i("else3432", "" + cursor);


            } else {
                cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
            }

            String path = null;
            try {
                int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index).toString();
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            Log.i("path 425436", "" + path);

            return path;
        }
    }


    public void compressImage(Uri uri1){

        try {
            File actualImage;
            /*actualImage = FileUtil.from(mContext, uri1);
            Compressor.getDefault(mContext)
                    .compressToFileAsObservable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            Bitmap finalBitmap=  BitmapFactory.decodeFile(file.getAbsolutePath());
                            Uri uri=Uri.parse(file.getAbsolutePath());
                            Log.i("test uri 1234",""+uri.toString());
                            //setBase64ForDoc(uri);
                            getStringImage(finalBitmap);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            //showError(throwable.getMessage());
                        }
                    });*/

            String filepath=  new ImageCompressor().compressImage(uri1,mContext);

            Uri imageUri = FileProvider.getUriForFile(mContext, "expression.sixdexp.com.expressionapp.fileprovider",
                    new File(filepath));
            Bitmap finalBitmap=  BitmapFactory.decodeFile(filepath);
            getFileSizeinMB(imageUri);
            getStringImage(finalBitmap);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public void saveData(){

    }


    public double getFileSizeinMB(Uri uri){
        double fileSizeInMB=0.0;
        try {
            File actualImage = FileUtil.from(this,uri);
            long fileSizeInBytes = actualImage.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            double fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            fileSizeInMB = fileSizeInKB / 1024;
            Log.i("byte count 345435",""+fileSizeInMB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileSizeInMB;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TagActivty.taged_users.size()>0){
            tagcount_txt.setVisibility(View.VISIBLE);
            tagcount_txt.setText(""+TagActivty.taged_users.size());
        }
        else{
            tagcount_txt.setVisibility(View.GONE);
        }
    }




}
