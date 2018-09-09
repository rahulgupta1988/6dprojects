package com.expression.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.flipanimation.BlurTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import db.AllUserEvent;
import db.CountRG;
import db.RecentActivity;
import db.UserLoginInfo;
import db.UserWedding;
import expression.sixdexp.com.expressionapp.CustomPhotoGalleryActivity;
import expression.sixdexp.com.expressionapp.EventDetailsActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.ToReadActivity;
import expression.sixdexp.com.expressionapp.adapter.RecentactivityAdapter;
import expression.sixdexp.com.expressionapp.adapter.UserEventsAdapter;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.GivenReceivedManage;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;
import expression.sixdexp.com.expressionapp.manager.UpdateProfileManager;
import expression.sixdexp.com.expressionapp.manager.UploadImageManager;
import expression.sixdexp.com.expressionapp.manager.UserInfoUpdate;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SettingMenuUtility;
import expression.sixdexp.com.expressionapp.xpassions.GroupListActivity;

public class TabFragment8 extends Fragment implements View.OnClickListener
{

    String category_val_id="";
    String to_date_val="";
    String from_date_val="";

    TextView recentnodata_txt,eventdata_txt;

    ListView events_list;
    TextView birthday,work_anniversory,wedding_anniversary;
    LinearLayout linear,date_calender,filter_ll;
    String Userprofileimage="";
    ImageView user_profile_img,user_profile_img_events;
    SettingMenuUtility settingMenuUtility;
    ImageView photo,imageView,photo_events,is_subscribe_img;
    private AlertDialog myDialog;
    String filename = "abc";
    String file_extension = ".jpg";
    String content_type = "";
    String weddingchangedate="";
    //EditText edit_username;
    TextView company_txt,department_txt,experience_txt;
    EditText txt_username,txt_username_events,quote,roleprofile_txt,hobbies_txt;
    String user_name_edit="", user_role_edit="", user_hobby_edit="", user_quote_edit="";
    Bitmap mBitmap = null;
    TextView profile_tab,recentactivity_tab,events_tab;
    LinearLayout first_ll,second_ll,third_ll;
    RecyclerView recentactivitylist;
    ProgressDialog progressDialog;
    Context mContext;
    TextView recognition_given,recognition_recieved;
    View view;
    int clickCount=0;
    String clickCount1="0";
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private final int PICK_IMAGE_MULTIPLE =1;
    public static ArrayList<String> f = new ArrayList<String>();
    static File[] listFile;
    static String shipmentId="";
    ArrayList<Bitmap> mybitmap;
    private ArrayList<String> imagesPathList;
    String[] imagesPath;
    Map<String,String> imagesMap=null;
    List<String> productTitle1=new ArrayList<>();
    List<AllUserEvent> allUserEvents;

    ImageView donor_icon,donor_icon_events;
    ImageView connecttosolve_icon,mvoice_icon,toread,gp_tan_ic;


    SwitchCompat switchWedding,switchWork,switchBirth;

    public TabFragment8(){};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        View view= inflater.inflate(R.layout.myprofile_fragmentpage, container, false);
       /* view= inflater.inflate(R.layout.myprofile_test, container, false);*/
        view= inflater.inflate(R.layout.my_profile_frag, container, false);

        mContext = getActivity();

        getFromSdcard();

        connecttosolve_icon=(ImageView)view.findViewById(R.id.connecttosolve_icon);
        mvoice_icon=(ImageView)view.findViewById(R.id.mvoice_icon);
        toread=(ImageView)view.findViewById(R.id.toread);
        gp_tan_ic=(ImageView)view.findViewById(R.id.gp_tan_ic);

        switchWork=(SwitchCompat)view.findViewById(R.id.switchWork);
        switchBirth=(SwitchCompat)view.findViewById(R.id.switchBirth);
        switchWedding=(SwitchCompat)view.findViewById(R.id.switchWedding);







        connecttosolve_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl="https://webapps2.tatapower.com/connecttosolve";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pathUrl));
                startActivity(intent);
            }
        });


        mvoice_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl="https://play.google.com/store/apps/details?id=com.tata.servpro_pokt_tata_feedback&hl=en";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pathUrl));
                startActivity(intent);
            }
        });


        toread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, ToReadActivity.class);
                startActivity(intent);

            }
        });

        gp_tan_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(mContext, GroupListActivity.class);
                startActivity(intent);


               // Toast.makeText(mContext,"Coming Soon...",Toast.LENGTH_SHORT).show();
            }
        });

        donor_icon=(ImageView)view.findViewById(R.id.donor_icon);
        donor_icon_events=(ImageView)view.findViewById(R.id.donor_icon_events);
        filter_ll=(LinearLayout)view.findViewById(R.id.filter_ll);
        filter_ll.setOnClickListener(this);
        birthday=(TextView)view.findViewById(R.id.birthday);
        work_anniversory=(TextView)view.findViewById(R.id.work_anniversory);
        wedding_anniversary=(TextView)view.findViewById(R.id.wedding_anniversary);
        is_subscribe_img=(ImageView)view.findViewById(R.id.is_subscribe_img);
        is_subscribe_img.setOnClickListener(this);


        date_calender=(LinearLayout)view.findViewById(R.id.date_calender);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
// TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//    String myFormat = "yy-MM-dd"; //In which you need put here
                String myFormat = "yyyy/MM/dd";
// String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //wedding_anniversary.setText(sdf.format(myCalendar.getTime()));
                weddingchangedate=sdf.format(myCalendar.getTime());
                new WeddingChangeDateTask().execute();
            }
        };

        date_calender.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
// TODO Auto-generated method stub
            @SuppressLint("WrongConstant") DatePickerDialog mDatePicker=new DatePickerDialog(mContext, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            mDatePicker.show();
        }
    });


        wedding_anniversary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
               DatePickerDialog mDatePicker=new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });






        recentnodata_txt=(TextView)view.findViewById(R.id.recentnodata_txt);
        eventdata_txt=(TextView)view.findViewById(R.id.eventdata_txt);

        user_profile_img=(ImageView)view.findViewById(R.id.user_profile_img);
        user_profile_img.setOnClickListener(this);
        user_profile_img_events=(ImageView)view.findViewById(R.id.user_profile_img_events);
        user_profile_img_events.setOnClickListener(this);
        linear = (LinearLayout)view.findViewById(R.id.linear);
        photo=(ImageView)view.findViewById(R.id.photo);
        photo_events=(ImageView)view.findViewById(R.id.photo_events);
        imageView=(ImageView)view.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        txt_username=(EditText) view.findViewById(R.id.txt_username);
        txt_username_events=(EditText) view.findViewById(R.id.txt_username_events);

        //edit_username=(EditText) view.findViewById(R.id.edit_username);
        quote= (EditText) view.findViewById(R.id.quote);
        company_txt=(TextView)view.findViewById(R.id.company_txt);
        department_txt=(TextView)view.findViewById(R.id.department_txt);
        roleprofile_txt=(EditText) view.findViewById(R.id.roleprofile_txt);
        experience_txt=(TextView)view.findViewById(R.id.experience_txt);
        hobbies_txt=(EditText) view.findViewById(R.id.hobbies_txt);



        first_ll=(LinearLayout)view.findViewById(R.id.first_ll);
        first_ll.setVisibility(View.VISIBLE);
        second_ll=(LinearLayout)view.findViewById(R.id.second_ll);
        second_ll.setVisibility(View.GONE);
        third_ll=(LinearLayout)view.findViewById(R.id.third_ll);
        third_ll.setVisibility(View.GONE);

        recognition_given=(TextView)view.findViewById(R.id.recognition_given);
        recognition_recieved=(TextView)view.findViewById(R.id.recognition_recieved);




      /*  if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new RecognitionRecievedCountTask().execute();
        }
        else {
            showsnack(getResources().getString(R.string.nework_connect_error));
        }*/
        //setUserInfo();
        if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new GetUserUpdateData().execute();
        }
        else {
            showsnack(getResources().getString(R.string.nework_connect_error));
        }

        if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new GetWeddingTask().execute();
        }
        else {
            showsnack(getResources().getString(R.string.nework_connect_error));
        }

        if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new GetEventsTask().execute();
        }
        else {
            showsnack(getResources().getString(R.string.nework_connect_error));
        }


        allUserEvents=new GetMorePostManager(getActivity()).getAllUserEvents();
        events_list=(ListView)view.findViewById(R.id.events_list);
        events_list.setDivider(null);

        /*Start FloatingActionsMenu*/


       /* final FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu)view.findViewById(R.id.fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded()
            {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        final FloatingActionButton actionA = (FloatingActionButton)view.findViewById(R.id.fab_search);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fabMenu.collapse();
                SharedPrefrenceManager.putSelectedTab(mContext, "searchtab");
                //actionA.setTitle("Search clicked");
                Intent searchactiivity = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(searchactiivity);
            }
        });

        final FloatingActionButton actionB = (FloatingActionButton)view.findViewById(R.id.fab_recognize);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionB.setTitle("Recognize clicked");
                fabMenu.collapse();
                Intent recognizeActivityIntent = new Intent(mContext, RecogniseActivity.class);
                mContext.startActivity(recognizeActivityIntent);

            }
        });

        final FloatingActionButton actionC = (FloatingActionButton)view.findViewById(R.id.fab_shareupdate);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionC.setTitle("Share and Update clicked");
                fabMenu.collapse();
                Intent shareactvityintent = new Intent(mContext, ShareActivity.class);
                mContext.startActivity(shareactvityintent);

            }
        });
*/

  /*End FloatingActionsMenu*/


        recentactivitylist = (RecyclerView)view.findViewById(R.id.recentactivitylist);







        profile_tab=(TextView)view.findViewById(R.id.profile_tab);
        recentactivity_tab=(TextView)view.findViewById(R.id.recentactivity_tab);
        events_tab=(TextView)view.findViewById(R.id.events_tab);
        profile_tab.setOnClickListener(this);
        recentactivity_tab.setOnClickListener(this);
        events_tab.setOnClickListener(this);
/*
        List<UserLoginInfo> userDatas = null;
        userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(getActivity()).getUserInfo();
        Userprofileimage = userDatas.get(0).getImageurl();

        String profile_img_url = userDatas.get(0).getImageurl();
        String user_name=userDatas.get(0).getName();
        String quotetext=userDatas.get(0).getQuote();
        String company=userDatas.get(0).getCompany();
        String department=userDatas.get(0).getDepartment();
        String roleprofile=userDatas.get(0).getRole();
        String experience=userDatas.get(0).getExperience();
        String hobbies=userDatas.get(0).getHobbies();


        if (user_name != null && !user_name.equalsIgnoreCase(""))
        {
            txt_username.setText(user_name);
            txt_username_events.setText(user_name);
        }
        if (quotetext != null && !quotetext.equalsIgnoreCase(""))
        {
            String value = "\"" + quotetext + "\"";
            quote.setText(value);
        }
        if (company != null && !company.equalsIgnoreCase(""))
        {
            company_txt.setText(company);
        }
        if (department != null && !department.equalsIgnoreCase(""))
        {
            department_txt.setText(department);
        }
        if (roleprofile != null && !roleprofile.equalsIgnoreCase(""))
        {
            roleprofile_txt.setText(roleprofile);
        }
        if (experience != null && !experience.equalsIgnoreCase(""))
        {
            if(experience.equals("1")){
                experience_txt.setText(experience+" year");
            }
            else{
                experience_txt.setText(experience+" years");
            }

        }
        if (hobbies != null && !hobbies.equalsIgnoreCase(""))
        {
            hobbies_txt.setText(hobbies);
        }

        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" "))
        {


            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img_events);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .placeholder(R.drawable.default_profile_picture)
                    .transform(new BlurTransform(getActivity()))
                    .into(photo);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .placeholder(R.drawable.default_profile_picture)
                    .transform(new BlurTransform(getActivity()))
                    .into(photo_events);
        }*/



        switchWedding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String isActiove=b?"1":"0";
                new IsSubscribeTask("300",isActiove).execute();
            }
        });




        switchWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String isActiove=b?"1":"0";
                new IsSubscribeTask("200",isActiove).execute();
            }
        });


        switchBirth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String isActiove=b?"1":"0";
                new IsSubscribeTask("100",isActiove).execute();
            }
        });



        return view;
    }
/*
    @Override
    public void onClick(View v) {

    }*/

    public ProgressDialog initProgressDialog() {
        String s = "Please wait...";
        SpannableString ss2 = new SpannableString(s);
        ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
        ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                android.R.style.Theme_DeviceDefault_Light_Dialog);
        Window window = progressDialog.getWindow();
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setMessage(ss2);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        return progressDialog;
    }


    public class RecognitionRecievedCountTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        boolean recognitionGiven = false;

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
                responseString = new GivenReceivedManage(getActivity()).recognitionRecievedCount();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                progressDialog.dismiss();
                if (responseString.equals("100")) {
                    List<CountRG> countRGs;
                    countRGs = new GivenReceivedManage(getActivity()).getCount();
                    // Toast.makeText(getActivity(),"size="+recognitionRecievedLists.size(),Toast.LENGTH_SHORT).show();
                    if (countRGs.size() > 0) {
                        recognition_given.setText("Given\n" + " " + countRGs.get(0).getGrecognition());

                        recognition_recieved.setText("Received\n" + " " + countRGs.get(0).getRrecognition());
                    }

                    //recognition_given.setText("jhhj");

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), responseString, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


  /*  public class RecognitionGivenListTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        boolean recognitionGiven = true;

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
                responseString = new RecognitionRecivedAndGivenListManager(getActivity(), recognitionGiven).callRecognitionRecivedAndGivenListManager("", "", "", "");
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
                    List<RecognitionGiven> recognitionGivenLists;
                    recognitionGivenLists=new RecognitionRecivedAndGivenListManager(getActivity()).getRecognitionGivens();
                    if (recognitionGivenLists.size() > 0) {
                        recognition_given.setText("Given\n"+" "+recognitionGivenLists.get(0).getTotal());
                    }


                    new RecognitionRecievedListTask().execute();

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class RecognitionRecievedListTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        boolean recognitionGiven = false;

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
                responseString = new RecognitionRecivedAndGivenListManager(getActivity(), recognitionGiven).RecognitionRecievedListTaskData("", "", "", "");
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
                    List<RecognitionRecieved> recognitionRecieveds;
                    recognitionRecieveds=new RecognitionRecivedAndGivenListManager(getActivity()).getRecognitionRecieveds();
                   // Toast.makeText(getActivity(),"size="+recognitionRecievedLists.size(),Toast.LENGTH_SHORT).show();
                    if(recognitionRecieveds.size()>0){
                        recognition_recieved.setText("Recieved\n" + " " + recognitionRecieveds.get(0).getTotal());
                    }

                    //recognition_given.setText("jhhj");

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }*/



    @Override
    public void onClick(View v)
    {
        final int id = v.getId();
        switch(id)
        {

            case R.id.filter_ll:
                //filter popup
                // Toast.makeText(getActivity(),"filter",Toast.LENGTH_SHORT).show();
                Filterdialog();
                break;

          /*  case R.id.is_subscribe_img:
                if (clickCount1.equalsIgnoreCase("0"))
                {
                    is_subscribe_img.setImageResource(R.drawable.check_on);
                    clickCount1 = "1";
                    //post service call
                    new IsSubscribeTask().execute();
                }
                else if(clickCount1.equalsIgnoreCase("1"))
                {
                    is_subscribe_img.setImageResource(R.drawable.check_off);
                    clickCount1 = "0";
                    //post service call
                    new IsSubscribeTask().execute();
                }

                break;*/

            case R.id.profile_tab:
                profile_tab.setTextColor(Color.parseColor("#2f67ff"));
                recentactivity_tab.setTextColor(Color.parseColor("#000000"));
                events_tab.setTextColor(Color.parseColor("#000000"));
                second_ll.setVisibility(View.GONE);
                third_ll.setVisibility(View.GONE);
                first_ll.setVisibility(View.VISIBLE);
                break;

            case R.id.recentactivity_tab:
                profile_tab.setTextColor(Color.parseColor("#000000"));
                recentactivity_tab.setTextColor(Color.parseColor("#2f67ff"));
                events_tab.setTextColor(Color.parseColor("#000000"));
                third_ll.setVisibility(View.GONE);
                first_ll.setVisibility(View.GONE);
                second_ll.setVisibility(View.VISIBLE);

                new RecentActivityTask().execute();

                break;

            case R.id.events_tab:
                profile_tab.setTextColor(Color.parseColor("#000000"));
                recentactivity_tab.setTextColor(Color.parseColor("#000000"));
                events_tab.setTextColor(Color.parseColor("#2f67ff"));
                first_ll.setVisibility(View.GONE);
                second_ll.setVisibility(View.GONE);
                third_ll.setVisibility(View.VISIBLE);

                break;
            case R.id.imageView:
                // Toast.makeText(getActivity(),"edit",Toast.LENGTH_SHORT).show();


                //EditText txt_username,quote,roleprofile_txt,hobbies_txt;

                if (clickCount == 0){
                    txt_username.setEnabled(true);
                    quote.setEnabled(true);
                    roleprofile_txt.setEnabled(true);
                    hobbies_txt.setEnabled(true);
                    imageView.setImageResource(R.drawable.save_img);


                    clickCount = 1;
                }else if(clickCount == 1){
                    imageView.setImageResource(R.drawable.edit_profile);
                    txt_username.setEnabled(false);
                    quote.setEnabled(false);
                    roleprofile_txt.setEnabled(false);
                    hobbies_txt.setEnabled(false);
                    clickCount = 0;

                    user_name_edit=txt_username.getText().toString();
                    user_role_edit=roleprofile_txt.getText().toString();
                    user_hobby_edit=hobbies_txt.getText().toString();
                    user_quote_edit=quote.getText().toString();



                    if (user_name_edit != null && !user_name_edit.equalsIgnoreCase(""))
                    {
                        if (user_role_edit != null && !user_role_edit.equalsIgnoreCase(""))
                        {
                            if (user_hobby_edit != null && !user_hobby_edit.equalsIgnoreCase(""))
                            {
                                if (user_quote_edit != null && !user_quote_edit.equalsIgnoreCase(""))
                                {
                                    new UpdateProfile().execute();


                                } else {
                                    showsnack("Please enter Quote");
                                }

                            } else {
                                showsnack("Please enter Hobby");
                            }

                        } else {
                            showsnack("Please enter Role Profile");
                        }


                    } else {
                        showsnack("Please enter Name");
                    }


                }


                break;

            case R.id.user_profile_img:
                //settingMenuUtility = new SettingMenuUtility(mContext, null, 0, user_profile_img);
                selectImage();
                break;

        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Image Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = true;//Utility.checkPermission();
                if (items[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                }
                else if (items[item].equals("Choose from Image Gallery"))
                {
                    Intent intent = new Intent(mContext,CustomPhotoGalleryActivity.class);
                    startActivityForResult(intent,PICK_IMAGE_MULTIPLE);
                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    public static void getFromSdcard()
    {
        f = new ArrayList<String>();
        File file = new File(
                Environment.getExternalStorageDirectory(),
                "Xochitl/Shippment"+shipmentId);
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }
    /*
    * Creating file uri to store image
    */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    /*
    * returning image
    */
    private static File getOutputMediaFile(int type) {
        String root = Environment.getExternalStorageDirectory().toString();
        File mediaStorageDir = new File(root + "/Xochitl/Shippment"+shipmentId);
// Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs())
            {
//                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
//                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
// Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "shippment_item" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }



    public class UpdateProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
            progressDialog = new ProgressDialog(mContext,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            Window window = progressDialog.getWindow();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setMessage(ss2);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new UpdateProfileManager(mContext).callUpdateProfile(user_name_edit, user_role_edit,
                        user_hobby_edit, user_quote_edit);
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
                Toast.makeText(mContext, "Profile Updated.", Toast.LENGTH_LONG).show();
                txt_username.setText(user_name_edit);
                txt_username_events.setText(user_name_edit);
                roleprofile_txt.setText(user_role_edit);
                hobbies_txt.setText(user_hobby_edit);
                quote.setText(user_quote_edit);

                new UserInfoUpdate(mContext).updateUserName(user_name_edit);
                ((FlipComplexLayoutActivity)mContext).updateUserInfo();
/*
                ((HostActivty)getActivity()).initnavigationdrawer();*/

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }
    }


    int x=0;
    List<ImageView> imglist=new ArrayList<ImageView>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_IMAGE_MULTIPLE)
            {
                imagesPathList = new ArrayList<String>();
                imagesPath = data.getStringExtra("data").split("\\|");
                try
                {
                    mybitmap=new ArrayList<Bitmap>();
                    for (int i = 0; i < imagesPath.length; i++)
                    {
                        ImageView imageView = new ImageView(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 220);
                        lp.setMargins(10, 0, 10, 0);
                        imageView.setLayoutParams(lp);
                        imagesPathList.add(imagesPath[i]);
                        Bitmap tempbitmap=getThumbnailBitmap(imagesPath[i],1000);
                        mBitmap=tempbitmap;
                      /*  user_profile_img.setImageBitmap(tempbitmap);
                        user_profile_img.setScaleType(ImageView.ScaleType.FIT_XY);
                        photo.setImageBitmap(tempbitmap);
                        photo.setScaleType(ImageView.ScaleType.FIT_XY);*/
                        new TaskUploadImage().execute();
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            else if(requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
            {
                try {
                    ImageView imageView = new ImageView(mContext);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 220);
                    lp.setMargins(10, 0, 10, 0);
                    imageView.setLayoutParams(lp);
                    BitmapFactory.Options options = new BitmapFactory.Options();
// downsizing image as it throws OutOfMemory Exception for larger
// images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                            options);
                    mBitmap = bitmap;
                    x++;
                    imagesMap.put("store_picture", getStringImage(mBitmap));
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
             /*       user_profile_img.setImageBitmap(bitmap);
                    user_profile_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    photo.setImageBitmap(bitmap);
                    photo.setScaleType(ImageView.ScaleType.FIT_XY);*/
                    // new TaskUploadImage().execute();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                   /* user_profile_img.setImageBitmap(mBitmap);
                    user_profile_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    photo.setImageBitmap(mBitmap);
                    photo.setScaleType(ImageView.ScaleType.FIT_XY);*/
                    new TaskUploadImage().execute();
                }
                catch (OutOfMemoryError error) {
                    error.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //ramji
    private Bitmap getThumbnailBitmap(final String path, final int thumbnailSize)
    {
        try
        {
            Bitmap bitmap;
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bounds);
            if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            {
                bitmap = null;
            }
            int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                    : bounds.outWidth;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = originalSize / thumbnailSize;
            bitmap = BitmapFactory.decodeFile(path, opts);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public class TaskUploadImage extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String imageStr;
        ProgressDialog progressDialog;
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SpannableString ss2 = new SpannableString(s);
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
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                imageStr = getStringImage(mBitmap);
                responseString = new UploadImageManager(mContext).uploadProfilePicture(imageStr,filename,file_extension,content_type);
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

            String profile_image_url="";

            JSONObject jsonObject;
            JSONObject responseData;
            String responseCode = "";
            String jsonresponce;
            String successMessage = "";
            String profile_picture = "";
            try {
                jsonObject = new JSONObject(responseString);
                responseCode = jsonObject.getString("responseCode");
                responseData=jsonObject.getJSONObject("responseData");
                successMessage = responseData.getString("message");
                profile_picture = responseData.getString("imageurl");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (responseCode.equals("100")) {

                mBitmap = null;

                URI profiler_uri = null;
                try {
                    profiler_uri = new URI(profile_picture.replace(" ", "%20"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                profile_image_url = profiler_uri.toString();
                new UserInfoUpdate(mContext).updateImageURL(profile_image_url);
                ((FlipComplexLayoutActivity)mContext).updateUserInfo();

                Log.i("profile_imageurl1",""+profile_image_url);
                if (!profile_picture.equalsIgnoreCase("null") && !profile_picture.equalsIgnoreCase("")
                        && !profile_picture.equalsIgnoreCase(" "))
                {
                    // Toast.makeText(mContext, "Done!", Toast.LENGTH_LONG).show();
                    Picasso.with(mContext)
                            .load(profile_image_url)
                            .resize(800, 800)
                            .centerInside()
                            .placeholder(R.drawable.default_profile_picture)
                            .into(user_profile_img);

                    Picasso.with(mContext)
                            .load(profile_image_url)
                            .resize(800, 800)
                            .centerInside()
                            .placeholder(R.drawable.default_profile_picture)
                            .into(user_profile_img_events);

                    Picasso.with(getActivity())
                            .load(profile_image_url)
                            .placeholder(R.drawable.default_profile_picture)
                            .transform(new BlurTransform(getActivity()))
                            .into(photo);

                    Picasso.with(getActivity())
                            .load(profile_image_url)
                            .placeholder(R.drawable.default_profile_picture)
                            .transform(new BlurTransform(getActivity()))
                            .into(photo_events);

                }
               /* else
                {
                    Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_profile_picture);
                    user_profile_img.setImageBitmap(mBitmap);
                }*/

                Toast.makeText(mContext, "Profile picture has been save successfully!", Toast.LENGTH_LONG).show();
            } else {
                mBitmap = null;
                progressDialog.dismiss();
                Toast.makeText(mContext, successMessage, Toast.LENGTH_LONG).show();

            }
        }


    }

    public void showsnack(String msg)
    {
        Snackbar snackbar = Snackbar
                .make(linear,msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }



    public class GetWeddingTask extends AsyncTask<String, Void, Void> {

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
                responseString = new GetMorePostManager(mContext).getWedding();
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
                    List<UserWedding> userWeddings;
                    userWeddings=new GetMorePostManager(getActivity()).getUserWeddings();
                    birthday.setText(userWeddings.get(0).getDob());
                    work_anniversory.setText(userWeddings.get(0).getDoj());
                    wedding_anniversary.setText(userWeddings.get(0).getDow());

                    String isbirthday_subscribe = userWeddings.get(0).getIsbirthday_subscribe();
                    String iswork_subscribe = userWeddings.get(0).getIswork_subscribe();
                    String iswedding_subscribe =userWeddings.get(0).getIswedding_subscribe();


                    if(isbirthday_subscribe.equals("1"))switchBirth.setChecked(true);
                    if(iswork_subscribe.equals("1"))switchWork.setChecked(true);
                    if(iswedding_subscribe.equals("1"))switchWedding.setChecked(true);

                 /*   String issubscribe=userWeddings.get(0).getIssubcribe();
                    if (issubscribe.equalsIgnoreCase("0"))
                    {
                        is_subscribe_img.setImageResource(R.drawable.check_off);
                        clickCount1="0";
                    }
                    else if (issubscribe.equalsIgnoreCase("1"))
                    {
                        is_subscribe_img.setImageResource(R.drawable.check_on);
                        clickCount1="1";
                    }
*/

                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class GetEventsTask extends AsyncTask<String, Void, Void> {

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
                responseString = new GetMorePostManager(mContext).getUserEvents();
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
                    // Toast.makeText(mContext, "Events successfully!", Toast.LENGTH_SHORT).show();

                    final List<AllUserEvent> allUserEvents;
                    allUserEvents=new GetMorePostManager(getActivity()).getAllUserEvents();
                    // Toast.makeText(getActivity(),"allUserEvents size="+allUserEvents.size(),Toast.LENGTH_SHORT).show();

                    if(allUserEvents!=null){
                        if(allUserEvents.size()>0){
                            eventdata_txt.setVisibility(View.GONE);
                            events_list.setVisibility(View.VISIBLE);
                            events_list.setAdapter(new UserEventsAdapter(mContext));
                        }

                        else {
                            eventdata_txt.setVisibility(View.VISIBLE);
                            events_list.setVisibility(View.GONE);
                        }
                    }


                    if (allUserEvents.size()==0)
                    {
                        //Toast.makeText(mContext, "No Data Available!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(mContext, "Events successfully!", Toast.LENGTH_SHORT).show();
                    }



                    events_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Intent detailsActitivty = new Intent(mContext, EventDetailsActivity.class);
                            detailsActitivty.putExtra("name", allUserEvents.get(position).getName());
                            detailsActitivty.putExtra("eventmasterid", allUserEvents.get(position).getEventmaster_id());
                            detailsActitivty.putExtra("eventdate", allUserEvents.get(position).getEvent_date());
                            detailsActitivty.putExtra("description", allUserEvents.get(position).getEvent_desc());
                            mContext.startActivity(detailsActitivty);
                        }
                    });

                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public class IsSubscribeTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;
        String eventtype="", isactive="";

        public  IsSubscribeTask(String eventtype,String isactive){
            this.eventtype=eventtype;
            this.isactive=isactive;

        }

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
                responseString = new GetMorePostManager(mContext).issubscribe(eventtype,isactive);
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
                    // Toast.makeText(mContext, "SubsCribe Successfully", Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class WeddingChangeDateTask extends AsyncTask<String, Void, Void> {

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
                responseString = new GetMorePostManager(mContext).ChangeWeddingDate(weddingchangedate);
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
                    //Toast.makeText(mContext, "Wedding Successfully", Toast.LENGTH_LONG).show();
                    new GetWeddingTask().execute();
                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }



    public void Filterdialog()
    {
        final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        // final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        dialog.setContentView(R.layout.filter_dialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);

/*        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        //window.setBackgroundDrawable();

      //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.8f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);


/*        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.x = 100; // left margin
        layoutParams.y = 170; // bottom margin
        dialog.getWindow().setAttributes(layoutParams);*/


        final EditText fromdate = (EditText) window.findViewById(R.id.from_date);
        final EditText to_date = (EditText) window.findViewById(R.id.to_date);
        final TextView all_event=(TextView) window.findViewById(R.id.all_event);
        final ImageButton submitbtn = (ImageButton) window.findViewById(R.id.submitbtn);
        // final ImageView cancelshare = (ImageView) window.findViewById(R.id.cancelshare);


        productTitle1.add("All");
        productTitle1.add("Birthday");
        productTitle1.add("Work Anniversary");
        productTitle1.add("Wedding Anniversary");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(mContext,R.layout.list_v, productTitle1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        all_event.setOnClickListener(new View.OnClickListener() {
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
                all_event.setText(productTitle1.get(i));
                String category_val = productTitle1.get(i);
//                Toast.makeText(mContext, "category_val=" + category_val, Toast.LENGTH_LONG).show();
            }
        });

        myDialog = builder2.create();



        /*===============================fromdate=====================================*/

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
// TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//    String myFormat = "yy-MM-dd"; //In which you need put here
                String myFormat = "yyyy/MM/dd";
// String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromdate.setText(sdf.format(myCalendar.getTime()));
                from_date_val=sdf.format(myCalendar.getTime());
            }
        };
        fromdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                @SuppressLint("WrongConstant") DatePickerDialog mDatePicker=new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });


        /*================================todate==================================*/

        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
// TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//    String myFormat = "yy-MM-dd"; //In which you need put here
                String myFormat = "yyyy/MM/dd";
// String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                to_date.setText(sdf.format(myCalendar1.getTime()));
                to_date_val=sdf.format(myCalendar1.getTime());
            }
        };
        to_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                @SuppressLint("WrongConstant") DatePickerDialog mDatePicker=new DatePickerDialog(mContext, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });


      /*  cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                productTitle1.clear();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) (mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                dialog.dismiss();
            }
        });*/

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //validation and service call

                String category_val_data=all_event.getText().toString();

                if (category_val_data.equalsIgnoreCase("All"))
                {
                    category_val_id="0";
                }
                else if (category_val_data.equalsIgnoreCase("Birthday"))
                {
                    category_val_id="1";
                }
                else if (category_val_data.equalsIgnoreCase("Work Anniversary"))
                {
                    category_val_id="2";
                }
                else if (category_val_data.equalsIgnoreCase("Wedding Anniversary"))
                {
                    category_val_id="3";
                }
                // Toast.makeText(mContext, "category_val_id=" + category_val_id, Toast.LENGTH_LONG).show();


                if (!from_date_val.equalsIgnoreCase(""))
                {
                    if (!to_date_val.equalsIgnoreCase(""))
                    {
                        //Service Call
                        new GetEventsTaskPopUp().execute();
                        if (v != null)
                        {
                            InputMethodManager imm = (InputMethodManager) (mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(mContext, "Please Select To Date" , Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, "Please Select From Date" , Toast.LENGTH_LONG).show();
                }


            }
        });
        dialog.show();
    }



    public class GetEventsTaskPopUp extends AsyncTask<String, Void, Void> {

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
                responseString = new GetMorePostManager(mContext).getUserEventsPOPUP(from_date_val,to_date_val,
                        category_val_id);
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
                    from_date_val="";
                    to_date_val="";
                    productTitle1.clear();
                    //  Toast.makeText(mContext, "Events successfully!", Toast.LENGTH_SHORT).show();

                    //  Toast.makeText(getActivity(),"allUserEvents size="+allUserEvents.size(),Toast.LENGTH_SHORT).show();
                    events_list.setAdapter(new UserEventsAdapter(mContext));
                    if (allUserEvents.size()==0)
                    {
                        Toast.makeText(mContext, "No Events Available!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //  Toast.makeText(mContext, "Events successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void setUserInfo(){

        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getActivity()).getUserInfo();
        Userprofileimage = userDatas.get(0).getImageurl();
        String profile_img_url = userDatas.get(0).getImageurl();
        String user_name=userDatas.get(0).getName();
        String quotetext=userDatas.get(0).getQuote();
        String company=userDatas.get(0).getCompany();
        String department=userDatas.get(0).getDepartment();
        String roleprofile=userDatas.get(0).getRole();
        String experience=userDatas.get(0).getExperience();
        String hobbies=userDatas.get(0).getHobbies();
        String rrecognition=userDatas.get(0).getRrecognition();
        String grecognition=userDatas.get(0).getGrecognition();


        recognition_given.setText("Given\n" + " " + grecognition);
        recognition_recieved.setText("Received\n" + " " + rrecognition);



        final String isdonor = userDatas.get(0).getIsdonor();


        if(isdonor.equals("1")) {
            donor_icon.setVisibility(View.VISIBLE);
            donor_icon_events.setVisibility(View.VISIBLE);
        }
        else {
            donor_icon.setVisibility(View.GONE);
            donor_icon_events.setVisibility(View.GONE);
        }


        if (user_name != null && !user_name.equalsIgnoreCase(""))
        {
            txt_username.setText(user_name);
            txt_username_events.setText(user_name);
        }
        if (quotetext != null && !quotetext.equalsIgnoreCase(""))
        {
            String value = "\"" + quotetext + "\"";
            quote.setText(value);
        }
        if (company != null && !company.equalsIgnoreCase(""))
        {
            company_txt.setText(company);
        }
        if (department != null && !department.equalsIgnoreCase(""))
        {
            department_txt.setText(department);
        }
        if (roleprofile != null && !roleprofile.equalsIgnoreCase(""))
        {
            roleprofile_txt.setText(roleprofile);
        }
        if (experience != null && !experience.equalsIgnoreCase(""))
        {
            if(experience.equals("1")){
                experience_txt.setText(experience+" year");
            }
            else{
                experience_txt.setText(experience+" years");
            }

        }
        if (hobbies != null && !hobbies.equalsIgnoreCase(""))
        {
            hobbies_txt.setText(hobbies);
        }

        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" "))
        {


            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img_events);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .placeholder(R.drawable.default_profile_picture)
                    .transform(new BlurTransform(getActivity()))
                    .into(photo);

            Picasso.with(getActivity())
                    .load(profile_img_url1)
                    .placeholder(R.drawable.default_profile_picture)
                    .transform(new BlurTransform(getActivity()))
                    .into(photo_events);
        }
    }


    public class GetUserUpdateData extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;
        String userID="";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            List<UserLoginInfo> userDatas = null;
            userDatas = new LoginManager(getActivity()).getUserInfo();
            userID = userDatas.get(0).getUserid();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new UserInfoUpdate(mContext).UserData(userID);
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
                    //Toast.makeText(mContext, "Wedding Successfully", Toast.LENGTH_LONG).show();

                    setUserInfo();
                    ((FlipComplexLayoutActivity)mContext).updateUserInfo();

                }
                else {

                    //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public class RecentActivityTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog=null;

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
                responseString = new RecentActivityManager(mContext).callRecentActivity();
            } catch (Exception e) {
                progressDialog.dismiss();
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

                if (responseString.equals("100")) {


                    List<RecentActivity> recentActivities = new RecentActivityManager(mContext).getRecentActivityList();
                    if(recentActivities!=null){
                        if(recentActivities.size()>0){

                            recentnodata_txt.setVisibility(View.GONE);
                            recentactivitylist.setVisibility(View.VISIBLE);

                            RecentactivityAdapter recentactivityAdapter = new RecentactivityAdapter(getActivity());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            recentactivitylist.setLayoutManager(layoutManager);
                            recentactivitylist.setItemAnimator(new DefaultItemAnimator());
                            recentactivitylist.setAdapter(recentactivityAdapter);
                        }
                        else{
                            recentactivitylist.setVisibility(View.GONE);
                            recentnodata_txt.setVisibility(View.VISIBLE);

                        }
                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {progressDialog.dismiss();
                e.printStackTrace();
            }

        }


    }



}
