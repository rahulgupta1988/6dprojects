package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.ClipboardManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import db.AllUsers;
import db.Award;
import db.Expossor;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.ExpressorAdapter;
import expression.sixdexp.com.expressionapp.adapter.ToAndCCListAdapter1;
import expression.sixdexp.com.expressionapp.manager.AwardManager;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.RecentActivityManager;
import expression.sixdexp.com.expressionapp.manager.TellATaleManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.SelPhoto;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.utility.TextChangeListe;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.utility.ValidationUtility;

/**
 * Created by Praveen on 7/4/2016.
 */
public class TellaTaleActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    ProgressDialog progressDialog;
    List<Expossor> expossorsList;
    ImageView cancelattachment;
    TextView videodoclink;
    LinearLayout linklay;

    static int check = 0;
    Context mContext;
    SelPhoto selPhoto;
    ImageView cancelshare;
    Spinner spinaward;
    List<Award> awards;
    TextView submitbtn;
    ArrayAdapter adapter1, adapter2;
    ListView seltoorcc, selcc;
    TextView insideoutsidetxt;
    TextView datesel;
    LinearLayout choose_xpressor;
    //post data
    String arwadID = "";
    String extrnlusr = "";
    String uid_str = "";
    String cc_str = "";
    String descp;
    String userId;
    String cv_values = "";
    String reco_date="";
    // view
    AutoCompleteTextView toexpressor, ccexpressor;
    ImageView showselccandto;
    TextView sharebtn;
    //Attachment view
    ImageView xpwayimg, cameraimg, videoimg, documentimg;
    EditText whathappened;
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

    List<String> toList = new ArrayList<String>();
    List<String> ccList = new ArrayList<String>();
    List<String> cvList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tellataleview);
        mContext = this;

        initView();
    }

    private void initView() {

        setCheckBoxValues();
        cancelattachment = (ImageView) findViewById(R.id.cancelattachment);
        videodoclink=(TextView)findViewById(R.id.videodoclink);
        linklay=(LinearLayout)findViewById(R.id.linklay);
        choose_xpressor = (LinearLayout) findViewById(R.id.choose_xpressor);
        choose_xpressor.setOnClickListener(this);
        datesel=(TextView)findViewById(R.id.datesel);
        datesel.setOnClickListener(this);
        sharebtn = (TextView) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(this);
        whathappened = (EditText) findViewById(R.id.whathappened);
        whathappened.addTextChangedListener(new TextChangeListe(whathappened));
        insideoutsidetxt = (TextView) findViewById(R.id.insideoutsidetxt);
        insideoutsidetxt.setOnClickListener(this);
        seltoorcc = (ListView) findViewById(R.id.seltoorcc);
        selcc = (ListView) findViewById(R.id.selcc);
        showselccandto = (ImageView) findViewById(R.id.showselccandto);
        showselccandto.setOnClickListener(this);
        toexpressor = (AutoCompleteTextView) findViewById(R.id.toexpressor);
        ccexpressor = (AutoCompleteTextView) findViewById(R.id.ccexpressor);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);


        xpwayimg = (ImageView) findViewById(R.id.xpwayimg);
        cameraimg = (ImageView) findViewById(R.id.cameraimg);
        videoimg = (ImageView) findViewById(R.id.videoimg);
        documentimg = (ImageView) findViewById(R.id.documentimg);

        List<UserLoginInfo> userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mContext).getUserInfo();
        if(userDatas.get(0).getPost_xpress().equalsIgnoreCase("1"))
            xpwayimg.setVisibility(View.VISIBLE);
        else
            xpwayimg.setVisibility(View.INVISIBLE);


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

            }
        });


        setToList();
        setCcList();
        setAward();

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

        List<AllUsers> allUserses = new UsersManager(mContext).getAllUserList();//Constant.allUserses;
        for (int i = 0; i < allUserses.size(); i++) {
            autoTolist.add(allUserses.get(i).getName());

        }

        adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, autoTolist);
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
                toList.add(adapter1.getItem(position).toString());
                adapter1.remove(adapter1.getItem(position));
                toAndCCListAdapter1.notifyDataSetChanged();
                Utils.getListViewSize(seltoorcc);
                toexpressor.setText("");
            }
        });
    }

    public void setCcList() {
        setCCAndToList2();
        ArrayList<String> list = new ArrayList<String>();
        List<AllUsers> allUserses = new UsersManager(mContext).getAllUserList();//Constant.allUserses;

        for (int i = 0; i < allUserses.size(); i++) {
            list.add(allUserses.get(i).getEmail());
        }

        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);

        ccexpressor.setAdapter(adapter2);

        ccexpressor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "added", Toast.LENGTH_SHORT).show();
                ccList.clear();
                ccList.add(adapter2.getItem(position).toString());
                //adapter2.remove(adapter2.getItem(position));
                toAndCCListAdapter2.notifyDataSetChanged();
                Utils.getListViewSize(selcc);
                ccexpressor.setText("");
            }
        });
    }

    ToAndCCListAdapter1 toAndCCListAdapter1 = null;
    ToAndCCListAdapter1 toAndCCListAdapter2 = null;

    public void setCCAndToList1() {

       /* toAndCCListAdapter1 = new ToAndCCListAdapter1(mContext, toList, new ToAndCCListAdapter1.ToAndCCChangeListener() {
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
*/
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        //seltoorcc.setLayoutManager(layoutManager);
        // seltoorcc.setItemAnimator(new DefaultItemAnimator());
        seltoorcc.setAdapter(toAndCCListAdapter1);
    }

    public void setCCAndToList2() {

      /*  toAndCCListAdapter2 = new ToAndCCListAdapter1(mContext, ccList, new ToAndCCListAdapter1.ToAndCCChangeListener() {
            @Override
            public void changeCCAndTO(int pos) {
                //adapter2.add(ccList.get(pos));
                ccList.remove(pos);
                //dialog.dismiss();
                //toAndccdialog();
                toAndCCListAdapter2.notifyDataSetChanged();
                Utils.getListViewSize(selcc);
            }
        });
*/
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

            case R.id.datesel:
                initdate();
                break;

            case R.id.insideoutsidetxt:

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

                break;

            case R.id.cancelshare:
                onBackPressed();
                break;

            case R.id.xpwayimg:
                xwSubjecdialog();
                break;

            case R.id.cameraimg:
                selPhoto = new SelPhoto(mContext, videodoclink, linklay, "image");
                ftype = "photo";
                break;

            case R.id.videoimg:
                selPhoto = null;
                ftype = "video";
                videoLinkdialog();
                break;

            case R.id.documentimg:
                selPhoto = new SelPhoto(mContext,videodoclink, linklay, "doc");
                ftype = "photo";
                break;

            case R.id.sharebtn:

                sharedtxt = whathappened.getText().toString().trim();
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


                // sharedtxt = sharetxt.getText().toString();
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



                if (arwadID != null && !arwadID.equalsIgnoreCase("")) {

                    if (toList != null && toList.size() > 0) {

                        if (cvList != null && cvList.size() > 0) {

                            if (ccList != null && ccList.size() > 0) {
                                reco_date = datesel.getText().toString();
                                if (reco_date != null && reco_date.length() > 0) {

                                    if (ValidationUtility.validEditTextString(sharedtxt) || viddoclink.length() > 0 || base64.length() > 0) {


                                        for (int a = 0; a < cvList.size(); a++) {
                                            cv_values = cv_values + cvList.get(a);
                                            if (a != cvList.size() - 1) cv_values = cv_values + ",";
                                            Log.i("cv_values", "" + cv_values);
                                        }


                                        if (check == 1) {
                                            extrnlusr = toexpressor.getText().toString();
                                        } else {


                                            for (int b = 0; b < toList.size(); b++) {

                                                List<AllUsers> allUserses = new UsersManager(mContext)
                                                        .getUserListByName(toList.get(b));

                                                uid_str = uid_str + allUserses.get(0).getUser_id();
                                                if (b != toList.size() - 1) uid_str = uid_str + ",";
                                            }

                                            Log.i("uid_str", "" + uid_str);
                                        }


                                        for (int c = 0; c < ccList.size(); c++) {
                                            List<AllUsers> allUserses = new UsersManager(mContext)
                                                    .getUserListByEmial(ccList.get(c));

                                            cc_str = cc_str + allUserses.get(0).getUser_id();
                                            if (c != ccList.size() - 1) cc_str = cc_str + ",";
                                            Log.i("cc_str", "" + cc_str);

                                        }

                                        descp = whathappened.getText().toString();
                                        userId = SharedPrefrenceManager.getUserID(mContext);

                              /*  String cv,String extrnlusr,String cc,String uid,String rid,
                                        String descp,String ftype,String vdourl,String xprssway,String xprsswaytxt,
                                        String userid,String base64_strig,String filename,String file_extension,
                                        String content_type*/

                              /*  new RecognizeSomeOneManager(mContext).postRecognizeSomeOne(cv_values,extrnlusr,cc_str,
                                uid_str,arwadID,descp,ftype,viddoclink,xprssway,xwaysubject_txt,userId,base64,
                                file_name,file_extension,content_type);*/

                                        new PostTellATale().execute();

                                    } else {
                                        Toast.makeText(mContext, "Please enter some text or select any content.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, "Please enter date.", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(mContext, "Please Select : By User.", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(mContext, "Please Select a Core Value.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please Select a Person to Recognize.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please Selcet Award.", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selPhoto.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onBackPressed();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(mContext, ""+awards.get(position).getAwardname(), Toast.LENGTH_SHORT).show();
        arwadID = awards.get(position).getAwardid();
        expossorsList = awards.get(position).getExpossorList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    linklay.setVisibility(View.VISIBLE);
                    videodoclink.setText(viddoclink);
                    dialog.dismiss();
                } else {
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




    public class PostTellATale extends AsyncTask<String, Void, Void> {

        String responseString = "";
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


                responseString = new TellATaleManager(mContext).postTellATale(cv_values, extrnlusr, cc_str,
                        uid_str, arwadID, descp, ftype, viddoclink, xprssway, xwaysubject_txt, userId, base64,
                        file_name, file_extension, content_type,reco_date);
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
                base64 = "";
                file_extension = "";
                content_type = "";
                file_name = "";
                new GetMorePostTask().execute();

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
            //progressDialog.dismiss();
            if (responseString.equals("100")) {
               new RecentActivityTask().execute();
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
            if (responseString.equals("100")) {
                onBackPressed();

            } else {

                //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                //finishActivity();
            }

        }


    }

    public void initdate(){

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datesel.setText(sdf.format(myCalendar.getTime()));

            }

        };

        DatePickerDialog mDatePicker=new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();
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

}
