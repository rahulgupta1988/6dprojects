package expression.sixdexp.com.expressionapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import db.AllUsers;
import db.Award;
import db.Expossor;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.EmojiAdapter;
import expression.sixdexp.com.expressionapp.adapter.ExpressorAdapter;
import expression.sixdexp.com.expressionapp.adapter.RecogToAdapter;
import expression.sixdexp.com.expressionapp.adapter.ToAndCCListAdapter1;
import expression.sixdexp.com.expressionapp.manager.AwardManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;
import expression.sixdexp.com.expressionapp.manager.RecognizeSomeOneManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SetPhotoNew;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.utility.TextChangeListe;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;
import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen on 7/4/2016.
 */
public class RecogniseActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    ProgressDialog progressDialog;
    List<Expossor> expossorsList;
    ImageView cancelattachment;
    TextView videodoclink;
    LinearLayout linklay,xpressway_subject_ll;

    public static int check = 0;
    Context mContext;
    //SetPhotoRecognize selPhoto;
     SetPhotoNew selPhoto;
    ImageView cancelshare;
    Spinner spinaward;
    List<Award> awards;
    TextView submitbtn,award_wise_content;
    ArrayAdapter adapter1, adapter2;
    ListView seltoorcc, selcc;
    CheckBox CbTermAndServicesCheckBox;
    TextView insideoutsidetxt;
    LinearLayout choose_xpressor;
    //post data
    String taguser="";
    String arwadID = "";
    String extrnlusr = "";
    String uid_str = "";
    String cc_str = "";
    String descp;
    String userId;
    String cv_values = "";
    // view
    AutoCompleteTextView toexpressor, ccexpressor;
    ImageView showselccandto;

    //ImageButton sharebtn;
    TextView sharebtn;

    //Attachment view
    ImageView xpwayimg, cameraimg, videoimg, documentimg;
    EditText whathappened,xwaysubject;
    CheckBox safety, agility, care, respect, ethic, diligence;
    //Attachment Param
    String file_name = "";
    String sharedtxt = "";
    String viddoclink = "";
    String base64 = "";
    String ftype = "";
    String file_extension = "";
    String content_type = "";
    String xwaysubject_txt = "";
    String xprssway = "0";
    String[] fruits = {"apple", "apc", "banana", "graps", "orrange", "berry", "gras"};

    String temp_xp_sub="";
    String temp_video_url="";

    List<AllUsers> toList = new ArrayList<AllUsers>();
    List<AllUsers> ccList = new ArrayList<AllUsers>();
    List<String> cvList = new ArrayList<String>();
    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 13;


    ScrollView scrolllay;
    ImageView emojiicon,keyicon;
    private EmoticonHandler mEmoticonHandler;

    RelativeLayout views_lay;

    String user_email="";

    LinearLayout award_text_lay,cc_icon_lay;

    ImageView tagicon;
    TextView tagcount_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.recogniseview);
        //setContentView(R.layout.recognize_test);
        setContentView(R.layout.recognize_activity_view);
        mContext = this;
        Constant.tag_txt=null;
        award_wise_content=(TextView)findViewById(R.id.award_wise_content);
        TagActivty.taged_users.clear();
        tagicon=(ImageView)findViewById(R.id.tagicon);
        tagicon.setVisibility(View.VISIBLE);
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(mContext,TagActivty.class);
                startActivity(intent);
            }
        });

        initView();
        initAwardView();
    }



    // award icons
    ImageView applause_icon,valueyou_icon,cheer_icon,lead_icon,thank_icon;
    TextView award_txt;


    ImageView cc_icon;
    LinearLayout cc_lay;

   public void initAwardView(){
       tagcount_txt=(TextView)findViewById(R.id.tagcount_txt);
       cc_icon_lay=(LinearLayout)findViewById(R.id.cc_icon_lay);

       award_text_lay=(LinearLayout)findViewById(R.id.award_text_lay);
       cc_lay=(LinearLayout)findViewById(R.id.cc_lay);
       cc_icon=(ImageView)findViewById(R.id.cc_icon);

       award_txt=(TextView)findViewById(R.id.award_txt);

       applause_icon=(ImageView)findViewById(R.id.applause_icon);
       valueyou_icon=(ImageView)findViewById(R.id.valueyou_icon);
       cheer_icon=(ImageView)findViewById(R.id.cheer_icon);
       lead_icon=(ImageView)findViewById(R.id.lead_icon);
       thank_icon=(ImageView)findViewById(R.id.thank_icon);

       selAward();
       ccHideandView();
       initValueViews();
   }


    LinearLayout sefty_lay,ethic_lay,agility_lay,respect_lay,diligence_lay,care_lay;
    ImageView sefty_icon,ethic_icon,agility_icon,respect_icon,diligence_icon,care_icon;
    TextView sefty_txt,ethic_txt,agility_txt,respect_txt,diligence_txt,care_txt;
    int sefty_flag=0,ethic_flag=0,agility_flag=0,respect_flag=0,diligence_flag=0,care_flag=0;

    public void initValueViews(){



        sefty_lay=(LinearLayout) findViewById(R.id.sefty_lay);
        ethic_lay=(LinearLayout)findViewById(R.id.ethic_lay);
        agility_lay=(LinearLayout)findViewById(R.id.agility_lay);
        respect_lay=(LinearLayout)findViewById(R.id.respect_lay);
        diligence_lay=(LinearLayout)findViewById(R.id.diligence_lay);
        care_lay=(LinearLayout)findViewById(R.id.care_lay);

        sefty_icon=(ImageView)findViewById(R.id.sefty_icon);
        ethic_icon=(ImageView)findViewById(R.id.ethic_icon);
        agility_icon=(ImageView)findViewById(R.id.agility_icon);
        respect_icon=(ImageView)findViewById(R.id.respect_icon);
        diligence_icon=(ImageView)findViewById(R.id.diligence_icon);
        care_icon=(ImageView)findViewById(R.id.care_icon);


        sefty_txt=(TextView) findViewById(R.id.sefty_txt);
        ethic_txt=(TextView)findViewById(R.id.ethic_txt);
        agility_txt=(TextView)findViewById(R.id.agility_txt);
        respect_txt=(TextView)findViewById(R.id.respect_txt);
        diligence_txt=(TextView)findViewById(R.id.diligence_txt);
        care_txt=(TextView)findViewById(R.id.care_txt);




        sefty_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cvList.contains("safety")) cvList.remove("safety");
                else cvList.add("safety");


                if(sefty_flag==0){
                    sefty_icon.setImageResource(R.drawable.safety_active);
                    sefty_txt.setTextColor(Color.parseColor("#2359F2"));
                    sefty_flag=1;
                }
                else{
                    sefty_icon.setImageResource(R.drawable.safety);
                    sefty_txt.setTextColor(Color.parseColor("#858585"));
                    sefty_flag=0;
                }


            }
        });

        ethic_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cvList.contains("ethic")) cvList.remove("ethic");
                else cvList.add("ethic");

                if(ethic_flag==0){
                    ethic_icon.setImageResource(R.drawable.ethic_active);
                    ethic_txt.setTextColor(Color.parseColor("#2359F2"));
                    ethic_flag=1;
                }
                else{
                    ethic_icon.setImageResource(R.drawable.ethic);
                    ethic_txt.setTextColor(Color.parseColor("#858585"));
                    ethic_flag=0;
                }

            }
        });

        agility_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (cvList.contains("agility")) cvList.remove("agility");
                else cvList.add("agility");

                if(agility_flag==0){
                    agility_icon.setImageResource(R.drawable.agility_active);
                    agility_txt.setTextColor(Color.parseColor("#2359F2"));
                    agility_flag=1;
                }
                else{
                    agility_icon.setImageResource(R.drawable.agility);
                    agility_txt.setTextColor(Color.parseColor("#858585"));
                    agility_flag=0;
                }

            }
        });

        respect_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cvList.contains("respect")) cvList.remove("respect");
                else cvList.add("respect");

                if(respect_flag==0){
                    respect_icon.setImageResource(R.drawable.respect_active);
                    respect_txt.setTextColor(Color.parseColor("#2359F2"));
                    respect_flag=1;
                }
                else{
                    respect_icon.setImageResource(R.drawable.respect);
                    respect_txt.setTextColor(Color.parseColor("#858585"));
                    respect_flag=0;
                }

            }
        });

        diligence_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cvList.contains("diligence")) cvList.remove("diligence");
                else cvList.add("diligence");

                if(diligence_flag==0){
                    diligence_icon.setImageResource(R.drawable.diligence_active);
                    diligence_txt.setTextColor(Color.parseColor("#2359F2"));
                    diligence_flag=1;
                }
                else{
                    diligence_icon.setImageResource(R.drawable.diligence);
                    diligence_txt.setTextColor(Color.parseColor("#858585"));
                    diligence_flag=0;
                }

            }
        });

        care_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cvList.contains("care")) cvList.remove("care");
                else cvList.add("care");

                if(care_flag==0){
                    care_icon.setImageResource(R.drawable.care_active);
                    care_txt.setTextColor(Color.parseColor("#2359F2"));
                    care_flag=1;
                }
                else{
                    care_icon.setImageResource(R.drawable.care);
                    care_txt.setTextColor(Color.parseColor("#858585"));
                    care_flag=0;
                }

            }
        });

    }


    private void initView()
    {

        views_lay=(RelativeLayout) findViewById(R.id.views_lay);
        setCheckBoxValues();
        scrolllay=(ScrollView)findViewById(R.id.scrolllay);
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
                whathappened.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(whathappened, InputMethodManager.HIDE_IMPLICIT_ONLY);
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

                    scrolllay.fullScroll(ScrollView.FOCUS_DOWN);

                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    emojiWindow();
                }


            }
        });

        xwaysubject = (EditText) findViewById(R.id.xwaysubject);
        xpressway_subject_ll=(LinearLayout)findViewById(R.id.xpressway_subject_ll);
        xpressway_subject_ll.setVisibility(View.GONE);
        cancelattachment = (ImageView) findViewById(R.id.cancelattachment);
        videodoclink=(TextView)findViewById(R.id.videodoclink);
        linklay=(LinearLayout)findViewById(R.id.linklay);
        //sharebtn = (ImageButton) findViewById(R.id.sharebtn);
        sharebtn= (TextView) findViewById(R.id.sharebtn);

        sharebtn.setOnClickListener(this);
        choose_xpressor = (LinearLayout) findViewById(R.id.choose_xpressor);
        choose_xpressor.setOnClickListener(this);
        whathappened = (EditText) findViewById(R.id.whathappened);
        // whathappened.addTextChangedListener(new TextChangeListe(whathappened));

        mEmoticonHandler = new EmoticonHandler(whathappened);
        whathappened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext,"HELLO",Toast.LENGTH_SHORT).show();

                keyicon.setVisibility(View.GONE);
                emojiicon.setVisibility(View.VISIBLE);
                if(changeSortPopUp!=null){
                    if(changeSortPopUp.isShowing()){changeSortPopUp.dismiss();}
                }
                whathappened.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(whathappened, InputMethodManager.HIDE_IMPLICIT_ONLY);

            }
        });


        insideoutsidetxt = (TextView) findViewById(R.id.insideoutsidetxt);
        insideoutsidetxt.setOnClickListener(this);


        CbTermAndServicesCheckBox=(CheckBox)findViewById(R.id.CbTermAndServicesCheckBox);
     /*   if (CbTermAndServicesCheckBox.isChecked())
        {
            insideoutsidetxt.setText("Would like to recognize someone inside Tata Power?");
            toexpressor.setHint("Type in the email id of external colleague.");
            emptyAutoTextList();
        }
        else
        {
            fillAutoTextList();
            insideoutsidetxt.setText("Would like to recognize someone outside Tata Power?");
            toexpressor.setHint("TO: I would like to recognize these colleagues.");
        }*/

        CbTermAndServicesCheckBox.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                toList.clear();
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);
                extrnlusr="";

                if(CbTermAndServicesCheckBox.isChecked())
                {
                    check = 1;
                    System.out.println("Checked");
                    //insideoutsidetxt.setText("Would like to recognize someone inside Tata Power?");
                    toexpressor.setHint("Type in the email id of external colleague.");
                    emptyAutoTextList();
                    toexpressor.setText("");
                }
                else
                {
                    check = 0;
                    System.out.println("Un-Checked");
                    fillAutoTextList();
                    // insideoutsidetxt.setText("Would like to recognize someone outside Tata Power?");
                    String text1 = "<font color=#000000>TO:</font> <font color=#a3a3a3>I would like to recognize these colleagues</font>";
                    toexpressor.setHint(Html.fromHtml(text1));
                    toexpressor.setText("");
                    // toexpressor.setHint("TO: I would like to recognize these colleagues.");
                }
            }
        });







        seltoorcc = (ListView) findViewById(R.id.seltoorcc);
        selcc = (ListView) findViewById(R.id.selcc);
        showselccandto = (ImageView) findViewById(R.id.showselccandto);
        showselccandto.setOnClickListener(this);
        toexpressor = (AutoCompleteTextView) findViewById(R.id.toexpressor);
        toexpressor.addTextChangedListener(new MyTextWatcher());
        ccexpressor = (AutoCompleteTextView) findViewById(R.id.ccexpressor);
        ccexpressor.addTextChangedListener(new MyCCTextWatcher());
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);


        String text1 = "<font color=#B70202>TO:</font> <font color=#a3a3a3>I would like to recognize these colleagues</font>";
        String text2 = "<font color=#B70202>CC:</font> <font color=#a3a3a3>Whom would like to share this information\n" +
                " with(on email)?</font>";
        // totalpayment_txt.setText(Html.fromHtml(text));
        // totalpayment_txt.setText(R.string.totalpayment + "" + text);
        //totalpayment_txt.setText(Html.fromHtml(text));
//        android:
//        hint = "TO: I would like to recognize these colleagues."

        // toexpressor.setText(Html.fromHtml(text1));

        toexpressor.setHint(Html.fromHtml(text1));
        ccexpressor.setHint(Html.fromHtml(text2));

    /*    final String prefix = "http://";
        toexpressor.setText(prefix);*/


        xpwayimg = (ImageView) findViewById(R.id.xpwayimg);
        cameraimg = (ImageView) findViewById(R.id.cameraimg);
        videoimg = (ImageView) findViewById(R.id.videoimg);
        documentimg = (ImageView) findViewById(R.id.documentimg);

        List<UserLoginInfo> userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mContext).getUserInfo();

        user_email=userDatas.get(0).getEmailId();


        if(userDatas.get(0).getPost_xpress().equalsIgnoreCase("1"))
            xpwayimg.setVisibility(View.VISIBLE);
        else
            xpwayimg.setVisibility(View.GONE);


        xpwayimg.setOnClickListener(this);
        cameraimg.setOnClickListener(this);
        videoimg.setOnClickListener(this);
        documentimg.setOnClickListener(this);

        cancelattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linklay.setVisibility(View.GONE);
                ftype = "";
                content_type = "";
                file_extension = "";
                file_name = "";
                base64 = "";
                viddoclink = "";

               // cameraimg.setImageResource(R.drawable.image_disable);
               // videoimg.setImageResource(R.drawable.video_disable);
               // documentimg.setImageResource(R.drawable.file_disable);

            }
        });



        setToList();
        setCcList();
        setAward();

        checkReceivedIntent();

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

       // views_lay.setVisibility(View.INVISIBLE);
        cameraimg.setVisibility(View.GONE);
        videoimg.setVisibility(View.GONE);
        documentimg.setVisibility(View.GONE);
        String sharedText =intent.getStringExtra(Intent.EXTRA_STREAM);

            if(sharedText!=null){
                whathappened.setText(""+sharedText);
            }


    }

    void handleSendImage(Intent intent) {
      //  views_lay.setVisibility(View.INVISIBLE);
        cameraimg.setVisibility(View.GONE);
        videoimg.setVisibility(View.GONE);
        documentimg.setVisibility(View.GONE);
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

    /*    double filesizeiInMB=getFileSizeinMB(uri);
        if(filesizeiInMB>1.3){
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
       // }



        String selectedImage=uri.toString();

        if(selectedImage.toString().contains("file")){

            file_extension=selectedImage.toString().substring((selectedImage.toString().lastIndexOf(".")));
            file_name=selectedImage.toString().substring((selectedImage.toString().lastIndexOf("/"))+1);
            String temp_file_extension=selectedImage.toString().substring((selectedImage.toString().lastIndexOf("."))+1);
            content_type= MimeTypeMap.getSingleton().getMimeTypeFromExtension(temp_file_extension.toLowerCase());
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

            else{
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


    public void setCheckBoxValues() {
        safety = (CheckBox) findViewById(R.id.safety);
        agility = (CheckBox) findViewById(R.id.agility);
        care = (CheckBox) findViewById(R.id.care);
        respect = (CheckBox) findViewById(R.id.respect);
        ethic = (CheckBox) findViewById(R.id.ethic);
        diligence = (CheckBox) findViewById(R.id.diligence);

        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("safety")) cvList.remove("safety");
                else cvList.add("safety");
            }
        });

        agility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("agility")) cvList.remove("agility");
                else cvList.add("agility");
            }
        });

        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("care")) cvList.remove("care");
                else cvList.add("care");
            }
        });
        respect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("respect")) cvList.remove("respect");
                else cvList.add("respect");
            }
        });
        ethic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("ethic")) cvList.remove("ethic");
                else cvList.add("ethic");
            }
        });

        diligence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cvList.contains("diligence")) cvList.remove("diligence");
                else cvList.add("diligence");
            }
        });

    }

    ArrayList<String> autoTolist = new ArrayList<String>();


    public void fillAutoTextList() {

        if (adapter1 != null) adapter1 = null;

        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;
        // Toast.makeText(getApplicationContext(),""+allUserses.size(),Toast.LENGTH_SHORT).show();
        /*for (int i = 0; i < allUserses.size(); i++)
        {
            if(!allUserses.get(i).getEmail().equals(user_email)) {
                autoTolist.add(allUserses.get(i).getName());
            }

            else{
                Log.i("fillAutoTextList 3535",""+allUserses.get(i).getEmail());
            }

        }*/

       /*adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, autoTolist);
*/

        String userid= SharedPrefrenceManager.getUserID(mContext);
        for (int i = 0; i < allUserses.size(); i++)
        {
            Log.i("userid 98102",""+allUserses.get(i).getUser_id()+"_"+userid);
            if(allUserses.get(i).getUser_id().equals(userid)) {
                Log.i("userid 98102",""+allUserses.get(i).getUser_id().equals(userid));
                allUserses.remove(i);
            }
        }


        adapter1= new RecogToAdapter(this, allUserses);
        toexpressor.setAdapter(adapter1);
    }

    public void emptyAutoTextList() {
        if (adapter1 != null) adapter1 = null;
        autoTolist.clear();
        adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, autoTolist);
        toexpressor.setAdapter(adapter1);
    }

    public void setToList() {
        setCCAndToList1();
        fillAutoTextList();

        toexpressor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "added", Toast.LENGTH_SHORT).show();


                    for(int k=0;k<toList.size();k++){

                        AllUsers users=(AllUsers) adapter1.getItem(position);
                        if(users==toList.get(k)){
                            toexpressor.setText("");
                            Toast.makeText(mContext, "Already added.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                if(toList.size()>=15){
                    Toast.makeText(mContext, "Maximum of 15 employees can be add.", Toast.LENGTH_SHORT).show();
                }

                else{
                    toList.add((AllUsers) adapter1.getItem(position));
                    adapter1.remove(adapter1.getItem(position));
                    toAndCCListAdapter1.notifyDataSetChanged();
                    Utils.getListViewSize(seltoorcc);
                    toexpressor.setText("");
                }


            }
        });
    }

    public void setCcList() {
        setCCAndToList2();
        ArrayList<AllUsers> list = new ArrayList<AllUsers>();
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;

       /* for (int i = 0; i < allUserses.size(); i++) {
            if(!allUserses.get(i).getEmail().equals(user_email)) {
                list.add(allUserses.get(i).getName());
            }
            else{
                Log.i("user email 9024335",""+allUserses.get(i).getName());
            }
        }*/

        /*adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);*/

        String userid= SharedPrefrenceManager.getUserID(mContext);
        for (int i = 0; i < allUserses.size(); i++)
        {
            Log.i("userid 98102",""+allUserses.get(i).getUser_id()+"_"+userid);
            if(allUserses.get(i).getUser_id().equals(userid)) {
                Log.i("userid 98102",""+allUserses.get(i).getUser_id().equals(userid));
                allUserses.remove(i);
            }
        }


        adapter2= new RecogToAdapter(this, allUserses);

        Log.i("Lisr9091431324",""+list.toString());

        ccexpressor.setAdapter(adapter2);

        ccexpressor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "added", Toast.LENGTH_SHORT).show();

                for(int k=0;k<ccList.size();k++){
                    if(adapter2.getItem(position).toString().equals(ccList.get(k))){
                        ccexpressor.setText("");
                        Toast.makeText(mContext, "Already added.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(ccList.size()>=15){
                    Toast.makeText(mContext, "Maximum of 15 employees can be add.", Toast.LENGTH_SHORT).show();
                }

                else{
                    ccList.add((AllUsers) adapter2.getItem(position));
                    adapter2.remove(adapter2.getItem(position));
                    toAndCCListAdapter2.notifyDataSetChanged();
                    Utils.getListViewSize(selcc);
                    ccexpressor.setText("");
                }



            }
        });
    }

    ToAndCCListAdapter1 toAndCCListAdapter1 = null;
    ToAndCCListAdapter1 toAndCCListAdapter2 = null;

    public void setCCAndToList1() {

        toAndCCListAdapter1 = new ToAndCCListAdapter1(mContext, toList, new ToAndCCListAdapter1.ToAndCCChangeListener() {
            @Override
            public void changeCCAndTO(int pos) {
                adapter1.add(toList.get(pos));
                toList.remove(pos);
                Log.i("toList",""+toList.toString());
                //Toast.makeText(mContext,""+pos,Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
                //toAndccdialog();
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);
            }
        });

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        //seltoorcc.setLayoutManager(layoutManager);
        // seltoorcc.setItemAnimator(new DefaultItemAnimator());
        seltoorcc.setAdapter(toAndCCListAdapter1);
    }

    public void setCCAndToList2() {

        toAndCCListAdapter2 = new ToAndCCListAdapter1(mContext, ccList, new ToAndCCListAdapter1.ToAndCCChangeListener() {
            @Override
            public void changeCCAndTO(int pos) {
                adapter2.add(ccList.get(pos));
                ccList.remove(pos);
                //dialog.dismiss();
                //toAndccdialog();
                toAndCCListAdapter2.notifyDataSetChanged();
                Utils.getListViewSize(selcc);
            }
        });

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        //selcc.setLayoutManager(layoutManager);
        // selcc.setItemAnimator(new DefaultItemAnimator());
        selcc.setAdapter(toAndCCListAdapter2);
    }

    public void setAward() {

        spinaward = (Spinner) findViewById(R.id.spinaward);
        awards = new AwardManager(mContext).getAwardList();
        List<String> awardName = null;

        if (awards != null && awards.size() > 0) {
            awardName = new ArrayList<String>();
            for (int i = 0; i < awards.size(); i++) {

                awardName.add(awards.get(i).getAwardname());
                Log.i("award name425435",""+awards.get(i).getAwardname());

            }

            ArrayAdapter<String> awardApdapterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, awardName);
            awardApdapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinaward.setAdapter(awardApdapterAdapter);
            spinaward.setOnItemSelectedListener(this);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

          /*  case R.id.showselccandto:
                toAndccdialog();
                break;

*/



            case R.id.choose_xpressor:
                if(expossorsList.size()!=0)
                    expressorDialog();
                else
                    Toast.makeText(mContext,"No expressor Available for given award.",Toast.LENGTH_LONG).show();
                break;

         /*   case R.id.insideoutsidetxt:

                toList.clear();
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);

                if (check == 1) {
                    check = 0;
                    fillAutoTextList();
                    insideoutsidetxt.setText("Would like to recognize someone outside Tata Power?");
                    toexpressor.setHint("TO: I would like to recognize these colleagues.");
                } else {
                    check = 1;
                    insideoutsidetxt.setText("Would like to recognize someone inside Tata Power?");
                    toexpressor.setHint("Type in the email id of external colleague.");
                    emptyAutoTextList();

                }

                break;*/

            case R.id.cancelshare:
                onBackPressed();
                break;

            case R.id.xpwayimg:

                xwaysubject.setText("");
                xprssway = "0";
                if (xpressway_subject_ll.getVisibility() == View.VISIBLE)
                {
                    xpressway_subject_ll.setVisibility(View.GONE);
                  //  xpwayimg.setImageResource(R.drawable.xpressway_disable);
                }
                else {
                    // Either gone
                    xpressway_subject_ll.setVisibility(View.VISIBLE);
                   // xpwayimg.setImageResource(R.drawable.xpressway_enable_new);
                }

                //xwSubjecdialog();
                break;

            case R.id.cameraimg:
                selPhoto = new SetPhotoNew(mContext, videodoclink, linklay, "image",cameraimg,documentimg,videoimg);
                //selPhoto = new SetPhotoRecognize(mContext, videodoclink, linklay, "image",cameraimg,documentimg,videoimg);


                ftype = "photo";
                break;

            case R.id.videoimg:
                selPhoto = null;
                ftype = "video";
                videoLinkdialog();
                break;

            case R.id.documentimg:
                // showChooser();
                selPhoto = new SetPhotoNew(mContext, videodoclink, linklay, "doc",cameraimg,documentimg,videoimg);
               // selPhoto = new SetPhotoRecognize(mContext,  videodoclink, linklay, "doc",cameraimg,documentimg,videoimg);
                ftype = "photo";
                break;

            case R.id.sharebtn:

                sharedtxt = whathappened.getText().toString().trim();


                xwaysubject_txt = xwaysubject.getText().toString().trim();
                temp_xp_sub=xwaysubject_txt;
                if (ValidationUtility.validEditTextString(xwaysubject_txt)) {
                    xprssway = "1";
                } else {
                    xprssway = "0";
                    //  Toast.makeText(mContext, "please Enter Subject.", Toast.LENGTH_LONG).show();
                }

                //viddoclink = videodoclink.getText().toString();
                if (selPhoto != null) {
                    base64 = selPhoto.getbase64();
                    file_extension = selPhoto.getextention();
                    content_type = selPhoto.getcontentType();
                    file_name = selPhoto.getFileName();

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


                // sharedtxt = whathappened.getText().toString();
                //viddoclink = videodoclink.getText().toString();
                if (selPhoto != null) {
                    base64 = selPhoto.getbase64();
                    file_extension = selPhoto.getextention();
                    content_type = selPhoto.getcontentType();
                    file_name = selPhoto.getFileName();

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


                Log.i("content_type",""+content_type);
                Log.i("file_name",""+file_name);
                Log.i("file_extension",""+file_extension);

                if (arwadID != null && !arwadID.equalsIgnoreCase("")) {

                   /* if ((toList != null && toList.size() > 0) || (check==1 && toexpressor.getText().toString().trim().length()>0
                    && ValidationUtility.validEmailAddress(toexpressor.getText().toString()))) {*/
                    if ((toList != null && toList.size() > 0)) {


                        if (check == 1) {
                            if (toexpressor.getText().toString().trim().length() > 0 && ValidationUtility.validEmailAddress(toexpressor.getText().toString())) {
                                extrnlusr = toexpressor.getText().toString().trim() + ",";
                                Log.i("test0912",""+extrnlusr);
                                //sendRecoPost();
                                sendFinalPost();
                            } else {

                                if (toexpressor.getText().toString().trim().length() > 0) {
                                    Toast.makeText(mContext, "Please enter valid Recognize email address", Toast.LENGTH_LONG).show();
                                } else {
                                    //sendRecoPost();
                                    sendFinalPost();
                                }

                            }


                        }

                        else{
                            //sendRecoPost();
                            sendFinalPost();
                        }
                    }


                     else {

                        if (check == 1) {
                            if (toexpressor.getText().toString().trim().length() > 0 && ValidationUtility.validEmailAddress(toexpressor.getText().toString())) {
                                extrnlusr = toexpressor.getText().toString().trim();
                                //sendRecoPost();
                                sendFinalPost();
                            }

                            else{
                                Toast.makeText(mContext, "Please enter valid email of external person.", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {

                            Toast.makeText(mContext, "Please Select a Person to Recognize.", Toast.LENGTH_LONG).show();

                        }



                    }
                } else {
                    Toast.makeText(mContext, "Please Select Award.", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        selPhoto.onActivityResult(requestCode, resultCode, data);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  CUSTOM_AUTOCOMPLETE_REQUEST_CODE)
        {
            if(data!=null)
            {

 /*               String text1 = "<font color=#000000> - at </font>";
                jeo_tagging.setImageResource(R.drawable.checkin_tagging_enable);
                // set the location recieved from picklocation activity
//                location_place_txt.setText(data.getStringExtra("Location Address"));
                location_place_txt.setText(Html.fromHtml(text1+""+data.getStringExtra("Location Address")));
                place_location=text1+""+data.getStringExtra("Location Address");*/
            }
        }
        else
        {
            selPhoto.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        TagActivty.taged_users.clear();
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        //  Toast.makeText(mContext, "val="+awards.get(position).getAwardname(), Toast.LENGTH_SHORT).show();

        try {
            String val=awards.get(position).getAwardname();
            if(val.startsWith("A"))
            {
                award_wise_content.setText("Appreciate a task/activtiy/project carried out by a colleague");
            }
            if(val.startsWith("C"))
            {
                award_wise_content.setText("Encourage a colleague for an act either completed or work in progress");
            }
            if(val.startsWith("D"))
            {
                award_wise_content.setText("Appreciate behaviors that exhibit Leadership by Example");
            }
            if(val.startsWith("I"))
            {
                award_wise_content.setText("Appreciate long term support, initiative and overall contribution");
            }
            if(val.startsWith("T"))
            {
                award_wise_content.setText("Acknowledge a help extended by a colleague");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            arwadID = awards.get(position).getAwardid();
            expossorsList = awards.get(position).getExpossorList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final TextView dialogtitle = (TextView) window.findViewById(R.id.dialogtitle);
        final EditText xwaysubject = (EditText) window.findViewById(R.id.xwaysubject);
        final ImageButton submitbtn = (ImageButton) window.findViewById(R.id.submitbtn);
        final ImageView cancelshare = (ImageView) window.findViewById(R.id.cancelshare);

        xwaysubject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence pastetxt=clipboard.getText();
                if(pastetxt!=null)
                    xwaysubject.setText(pastetxt);
                return false;
            }
        });

        xwaysubject.setHint("Enter URL...");
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
                    String tempID = getVideoID(viddoclink);
                    Log.i("tempID 89045645", "" + tempID);


                    if (viddoclink.contains("https://web.microsoftstream.com")) {
                        linklay.setVisibility(View.VISIBLE);
                        videodoclink.setText(viddoclink);
                        dialog.dismiss();
                    } else {
                    if (tempID != null) {
                        linklay.setVisibility(View.VISIBLE);
                        videodoclink.setText(viddoclink);
                        // videoimg.setImageResource(R.drawable.video_enable);
                        // cameraimg.setImageResource(R.drawable.image_disable);
                        // documentimg.setImageResource(R.drawable.file_disable);
                        dialog.dismiss();
                    } else {
                        //videodoclink.setText("");
                        Toast.makeText(mContext, "Please Enter Video URL!", Toast.LENGTH_LONG).show();
                    }
                }


                } else {
                    //videodoclink.setText("");
                    Toast.makeText(mContext, "please Enter Video URL.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    public void xwSubjecdialog() {
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.videodocurldialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final EditText xwaysubject = (EditText) window.findViewById(R.id.xwaysubject);
        final ImageButton submitbtn = (ImageButton) window.findViewById(R.id.submitbtn);
        final ImageView cancelshare = (ImageView) window.findViewById(R.id.cancelshare);
        if(temp_xp_sub.length()>0)
            xwaysubject.setText(temp_xp_sub);

        xwaysubject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence pastetxt=clipboard.getText();
                if(pastetxt!=null)
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
            public void onClick(View v) {
                xwaysubject_txt = xwaysubject.getText().toString();
                temp_xp_sub=xwaysubject_txt;
                if (ValidationUtility.validEditTextString(xwaysubject_txt)) {
                    xprssway = "1";
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "please Enter Subject.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();

    }


    public void expressorDialog() {
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.xpressordialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ListView xpressorlist = (ListView) window.findViewById(R.id.xpressorlist);
        ImageView cancel = (ImageView) window.findViewById(R.id.cancel);
        Button submit = (Button) window.findViewById(R.id.submit);
        final List<String> selexpressorstr = new ArrayList<String>();
        ExpressorAdapter expressorAdapter = new ExpressorAdapter(mContext, expossorsList, new ExpressorAdapter.ExpressorListener() {
            @Override
            public void selExpressor(String expressortxt) {
                //Toast.makeText(mContext, "" + expressortxt, Toast.LENGTH_LONG).show();
                if (selexpressorstr.contains(expressortxt)) selexpressorstr.remove(expressortxt);
                else selexpressorstr.add(expressortxt);
            }
        });


        xpressorlist.setAdapter(expressorAdapter);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressotstr = "";
                for (int i = 0; i < selexpressorstr.size(); i++) {
                    expressotstr = expressotstr + selexpressorstr.get(i).toString();
                    if (i != selexpressorstr.size() - 1) expressotstr = expressotstr + ",";
                }

                whathappened.setText(expressotstr);
                dialog.dismiss();
            }


        });

        dialog.show();

    }


    public class PostRecognize extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        StringBuilder taggedUSerID=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            /*SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.black)), 0, ss2.length(), 0);
            progressDialog = new ProgressDialog(mContext,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            Window window = progressDialog.getWindow();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setMessage(ss2);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            progressDialog.show();*/


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


                responseString = new RecognizeSomeOneManager(mContext).postRecognizeSomeOne(cv_values, extrnlusr, cc_str,
                        uid_str, arwadID, descp, ftype, viddoclink, xprssway, xwaysubject_txt, userId, base64,
                        file_name, file_extension, content_type,taggedUSerID.toString());
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
                Toast.makeText(mContext, "Post Submitted", Toast.LENGTH_LONG).show();
                Constant.lastposition_home=0;
                Constant.lastposition=0;

                base64 = "";
                file_extension = "";
                content_type = "";
                file_name = "";
                cc_str="";
                extrnlusr="";
                cv_values="";

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
            if (responseString.equals("100")) {
                //new RecentActivityTask().execute();
                TagActivty.taged_users.clear();
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


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

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
                if (responseString.equals("100"))
                {
                    new GetMorePostTask().execute();
                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }




    public class MyTextWatcher implements TextWatcher{


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {



            Log.i("onTextChanged",s.toString());

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.toString().contains(",") && check==1) {
                Log.i("afterTextChanged", s.toString().length()+"");
                Log.i("afterTextChanged length", s.toString());

                String userEmailID=s.toString().substring(0,s.toString().length()-1);
                Log.i("userEmailID","asdsd"+ userEmailID);
                if(ValidationUtility.validEmailAddress(userEmailID)) {

                    AllUsers temp_users=new AllUsers();
                    temp_users.setEmail(userEmailID);

                    toList.add(temp_users);
                    toAndCCListAdapter1.notifyDataSetChanged();
                    Utils.getListViewSize(seltoorcc);
                    toexpressor.setText("");
                }

                else{
                    Toast.makeText(mContext,"Please Enter valid Email.",Toast.LENGTH_LONG).show();
                    toexpressor.setText(userEmailID);
                }
            }
        }
    }

    public class MyCCTextWatcher implements TextWatcher{


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {



            Log.i("onTextChanged",s.toString());

        }

        @Override
        public void afterTextChanged(Editable s) {

           /* if(s.toString().contains(",")) {
                Log.i("afterTextChanged", s.toString().length()+"");
                Log.i("afterTextChanged length", s.toString());

                String userEmailID=s.toString().substring(0,s.toString().length()-1);
                Log.i("userEmailID","asdsd"+ userEmailID);
                if(ValidationUtility.validEmailAddress(userEmailID)) {
                    ccList.add(userEmailID);
                    toAndCCListAdapter2.notifyDataSetChanged();
                    Utils.getListViewSize(selcc);
                    ccexpressor.setText("");
                }

                else{
                    Toast.makeText(mContext,"Please Enter valid Email.",Toast.LENGTH_LONG).show();
                    toexpressor.setText(userEmailID);
                }
            }*/
        }
    }


    public void addImageBetweentext(Drawable drawable, String emojoCode) {
        // Create the ImageSpan

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable);

        // Get the selected text.
        int start = whathappened.getSelectionStart();
        int end = whathappened.getSelectionEnd();
        Editable message = whathappened.getEditableText();

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

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

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
        int OFFSET_Y = -(int)(height/2.5);
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.bottom + OFFSET_Y);
    }


   /* public void sendRecoPost(){


        if (cvList != null && cvList.size() > 0) {


            if(ccexpressor.getText().toString().trim().length()==0){
                sendFinalPost();
            }

            else{
                if(!ValidationUtility.validEmailAddress(ccexpressor.getText().toString().trim())){
                  Toast.makeText(mContext,"Please Enter Valid email adress in CC.",Toast.LENGTH_SHORT).show();
                }

                else{
                    sendFinalPost();
                }
            }


        } else {
            Toast.makeText(mContext, "Please Select a Core Value.", Toast.LENGTH_LONG).show();
        }
    }*/


    public void sendFinalPost(){
        if (ValidationUtility.validEditTextString(sharedtxt) || viddoclink.length() > 0 || base64.length() > 0) {

            for (int a = 0; a < cvList.size(); a++) {
                cv_values = cv_values + cvList.get(a);
                if (a != cvList.size() - 1) cv_values = cv_values + ",";
                Log.i("cv_values", "" + cv_values);
            }


           if(cvList.size()==0){
               Toast.makeText(mContext,"Please Select core value.",Toast.LENGTH_SHORT).show();
               return;
           }


            if (check == 1) {
                //extrnlusr = toexpressor.getText().toString();
                extrnlusr="";
                Log.i("12432",""+extrnlusr);
                if(toList.size()>0) {
                    for (int b = 0; b < toList.size(); b++) {

                        if (b != toList.size() - 1)
                            extrnlusr += toList.get(b).getEmail() + ",";
                        else
                            extrnlusr += toList.get(b).getEmail();
                    }
                }
                else {

                    String toexpressor_singleID=toexpressor.getText().toString().trim();
                    if(toexpressor_singleID.length()>0){
                        if(ValidationUtility.validEmailAddress(toexpressor_singleID)){
                            extrnlusr=toexpressor_singleID;
                        }

                        else{
                            Toast.makeText(mContext,"Please enter valid email.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    else{
                        Toast.makeText(mContext,"Please enter valid email.",Toast.LENGTH_SHORT).show();
                        return;
                    }
             }
                Log.i("124324dfdsf",""+extrnlusr);
                //Toast.makeText(mContext,extrnlusr,Toast.LENGTH_LONG).show();
            } else {
                uid_str="";
                Log.i("toList Size",""+toList.size());
                for (int b = 0; b < toList.size(); b++) {
/*
                    List<AllUsers> allUserses = new UsersManager(mContext)
                            .getUserListByName(toList.get(b));*/

                    uid_str = uid_str + toList.get(b).getUser_id();
                    if (b != toList.size() - 1) uid_str = uid_str + ",";
                }

                Log.i("uid_str", "" + uid_str);
            }


           /* if(ccList.size()>0 && ccexpressor.getText().toString().trim().length()>0)
                cc_str=ccexpressor.getText().toString().trim()+",";
            else
                cc_str=ccexpressor.getText().toString().trim();*/

           /* for (int c = 0; c < ccList.size(); c++) {
                cc_str = cc_str + ccList.get(c);
                if (c != ccList.size() - 1) cc_str = cc_str + ",";
                Log.i("cc_str", "" + cc_str);
            }
*/

            for (int c = 0; c < ccList.size(); c++) {

              /*  List<AllUsers> allUserses = new UsersManager(mContext)
                        .getUserListByName(ccList.get(c));*/

                cc_str = cc_str + ccList.get(c).getUser_id();
                if (c != ccList.size() - 1) cc_str = cc_str + ",";
            }


            descp = whathappened.getText().toString();
            userId = SharedPrefrenceManager.getUserID(mContext);


            new PostRecognize().execute();

        } else {
            Toast.makeText(mContext, "Please enter some text or select any content.", Toast.LENGTH_LONG).show();
        }
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


    public void getStringImage(Bitmap finalBitmap) {
        if (finalBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] imageBytes = baos.toByteArray();
            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

    }

    public void compressImage(Uri uri){

        try {
            File actualImage;
            actualImage = FileUtil.from(mContext, uri);
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
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void selAward(){
        //applause_icon,valueyou_icon,cheer_icon,lead_icon,thank_icon;

        applause_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                award_text_lay.setVisibility(View.VISIBLE);

                if(choose_xpressor.getVisibility()==View.GONE){
                    choose_xpressor.setVisibility(View.VISIBLE);
                }

                setArwadIDANDexpressotList("Applause a Deed");


                deselectAward(R.id.applause_icon);
                applause_icon.setBackgroundResource(R.drawable.award_selected);


                int x=(int)applause_icon.getX();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(x,0,0,0);
                award_txt.setLayoutParams(layoutParams);

                award_txt.setGravity(0);
                award_txt.setText("Applause a Deed");


                doBounceAnimation(award_txt);





        }
        });

        valueyou_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                award_text_lay.setVisibility(View.VISIBLE);
                if(choose_xpressor.getVisibility()==View.GONE){
                    choose_xpressor.setVisibility(View.VISIBLE);
                }

                setArwadIDANDexpressotList("I Value You");

                deselectAward(R.id.valueyou_icon);
                valueyou_icon.setBackgroundResource(R.drawable.award_selected);
                int x=(int)valueyou_icon.getX();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(x,0,0,0);
                award_txt.setLayoutParams(layoutParams);

                award_txt.setGravity(0);
                award_txt.setText("I Value You");
                doBounceAnimation(award_txt);
            }
        });

        cheer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                award_text_lay.setVisibility(View.VISIBLE);
                if(choose_xpressor.getVisibility()==View.GONE){
                    choose_xpressor.setVisibility(View.VISIBLE);
                }

                setArwadIDANDexpressotList("Cheer a Peer");

                deselectAward(R.id.cheer_icon);
                cheer_icon.setBackgroundResource(R.drawable.award_selected);
                int x=(int)cheer_icon.getX();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(x,0,0,0);
                award_txt.setLayoutParams(layoutParams);

                award_txt.setGravity(0);
                award_txt.setText("Cheer a Peer");
                doBounceAnimation(award_txt);
            }
        });

        lead_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                award_text_lay.setVisibility(View.VISIBLE);
                if(choose_xpressor.getVisibility()==View.GONE){
                    choose_xpressor.setVisibility(View.VISIBLE);
                }

                setArwadIDANDexpressotList("Drive to Lead");

                deselectAward(R.id.lead_icon);
                lead_icon.setBackgroundResource(R.drawable.award_selected);

                int x=(int)lead_icon.getX();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(x,0,0,0);
                award_txt.setLayoutParams(layoutParams);

                award_txt.setGravity(0);
                award_txt.setText("Drive to Lead");
                doBounceAnimation(award_txt);
            }
        });

        thank_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                award_text_lay.setVisibility(View.VISIBLE);
                if(choose_xpressor.getVisibility()==View.GONE){
                    choose_xpressor.setVisibility(View.VISIBLE);
                }

                setArwadIDANDexpressotList("Thank You");

                deselectAward(R.id.thank_icon);
                thank_icon.setBackgroundResource(R.drawable.award_selected);
                int x=(int)thank_icon.getX();
               /* LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(x/2,0,0,0);
                award_txt.setLayoutParams(layoutParams);*/

                award_txt.setGravity(Gravity.RIGHT);
                award_txt.setText("Thank You");

                doBounceAnimation(award_txt);
            }
        });
    }



    private void doBounceAnimation(View targetView) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(targetView, "translationY", -50f, 0f);
        animY.setDuration(500);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(0);
        animY.start();
    }

    public void deselectAward(int view_id){

        if(view_id!=R.id.applause_icon)
            applause_icon.setBackgroundResource(android.R.color.transparent);
        if(view_id!=R.id.valueyou_icon)
            valueyou_icon.setBackgroundResource(android.R.color.transparent);
        if(view_id!=R.id.cheer_icon)
            cheer_icon.setBackgroundResource(android.R.color.transparent);
        if(view_id!=R.id.lead_icon)
            lead_icon.setBackgroundResource(android.R.color.transparent);
        if(view_id!=R.id.thank_icon)
            thank_icon.setBackgroundResource(android.R.color.transparent);

    }



    public void ccHideandView(){

        cc_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(cc_lay.getVisibility()==View.VISIBLE){

                    ObjectAnimator CCanimX = ObjectAnimator.ofFloat(cc_icon, "translationY",cc_icon_lay.getPivotY(),0);
                    CCanimX.setDuration(500);//1sec
                    //animX.setInterpolator(new BounceInterpolator());
                    CCanimX.setRepeatCount(0);
                    CCanimX.start();
                    CCanimX.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ObjectAnimator animX = ObjectAnimator.ofFloat(ccexpressor, "translationX",0,700);
                            animX.setDuration(500);//1sec
                            //animX.setInterpolator(new BounceInterpolator());
                            animX.setRepeatCount(0);
                            animX.start();
                            animX.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    cc_lay.setVisibility(View.GONE);
                                    ccexpressor.setVisibility(View.GONE);


                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });





                }
                else{

                    cc_lay.setVisibility(View.VISIBLE);
                    ccexpressor.setVisibility(View.VISIBLE);
                    ObjectAnimator animX = ObjectAnimator.ofFloat(ccexpressor, "translationX",700, 0);
                    animX.setDuration(500);//1sec
                    //animX.setInterpolator(new BounceInterpolator());
                    animX.setRepeatCount(0);
                    animX.start();

                animX.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator CCanimX = ObjectAnimator.ofFloat(cc_icon, "translationY",0,cc_icon_lay.getPivotY()+20);
                        CCanimX.setDuration(500);//1sec
                        //animX.setInterpolator(new BounceInterpolator());
                        CCanimX.setRepeatCount(0);
                        CCanimX.start();

/*
                        ObjectAnimator animY = ObjectAnimator.ofFloat(cc_icon, "translationY",ccexpressor.getPivotX()-230, ccexpressor.getPivotX()-190);
                        animY.setDuration(500);//1sec
                        animY.setInterpolator(new BounceInterpolator());
                        animY.setRepeatCount(0);


                        AnimatorSet animatorSet=new AnimatorSet();
                        animatorSet.play(CCanimX).before(animY);

                        animatorSet.start();*/
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                }


            }
        });
    }


    public void setArwadIDANDexpressotList(String awardName){
        for(int i=0;i<awards.size();i++){
            if(awards.get(i).getAwardname().equalsIgnoreCase(awardName)){
                arwadID = awards.get(i).getAwardid();
                expossorsList = awards.get(i).getExpossorList();
                break;
            }
        }
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
