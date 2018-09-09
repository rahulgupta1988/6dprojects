package expression.sixdexp.com.expressionapp.xpassions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.SearchTag_Adapter;
import expression.sixdexp.com.expressionapp.adapter.TaggingAdapter;
import expression.sixdexp.com.expressionapp.manager.CreateGroupManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SetPhotoNew;
import expression.sixdexp.com.expressionapp.utility.Utils;

/**
 * Created by Praveen on 04-Jan-18.
 */

public class CreateGroup extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    SetPhotoNew selPhoto;
    public static final int SELECT_FILE = 1;
    public static final int REQUEST_CAMERA = 2;
    ImageView toread_back;
    CircularImageView gp_img;
    EditText grp_name,grp_descrp,search_txt;
    RelativeLayout open_gp_lay,closed_gp_lay,search_lay,heade_lay;
    ListView taged_user_list;
    Button create_group;
    LinearLayout theamlay;

    ScrollView scroll_lay;
    String themeid="1";

    String closed_open="0";
    public List<AllUsers> taged_users=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup_lay);
        mContext=this;
        initViews();
        fillAutoTextList();
        setTeagedUserList();
    }

    public void initViews(){
        theamlay=(LinearLayout)findViewById(R.id.theamlay);
        create_group=(Button)findViewById(R.id.create_group);
        create_group.setOnClickListener(this);
        heade_lay=(RelativeLayout)findViewById(R.id.heade_lay);
        scroll_lay=(ScrollView)findViewById(R.id.scroll_lay);
        progress_show=(ProgressBar)findViewById(R.id.progress_show);
        search_lay=(RelativeLayout)findViewById(R.id.search_lay);
        toread_back=(ImageView)findViewById(R.id.toread_back);
        toread_back.setOnClickListener(this);
        gp_img=(CircularImageView)findViewById(R.id.gp_img);
        gp_img.setOnClickListener(this);
        grp_name=(EditText)findViewById(R.id.grp_name);
        grp_descrp=(EditText)findViewById(R.id.grp_descrp);
        search_txt=(EditText)findViewById(R.id.search_txt);
        open_gp_lay=(RelativeLayout)findViewById(R.id.open_gp_lay);
        closed_gp_lay=(RelativeLayout)findViewById(R.id.closed_gp_lay);
        open_gp_lay.setOnClickListener(this);
        closed_gp_lay.setOnClickListener(this);
        taged_user_list=(ListView)findViewById(R.id.taged_user_list);
        createTheamsView();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.toread_back:
                onBackPressed();
                break;

            case R.id.gp_img:
                TextView textView=new TextView(mContext);
                LinearLayout linearLayout=new LinearLayout(mContext);
                selPhoto = new SetPhotoNew(mContext, textView, linearLayout,null);
                break;

            case R.id.open_gp_lay:
                closed_open="0";
                open_gp_lay.setBackgroundColor(Color.parseColor("#0A4C8B"));
                closed_gp_lay.setBackgroundColor(Color.parseColor("#3699DB"));
                break;

            case R.id.closed_gp_lay:
                closed_open="1";
                open_gp_lay.setBackgroundColor(Color.parseColor("#3699DB"));
                closed_gp_lay.setBackgroundColor(Color.parseColor("#0A4C8B"));
                break;


            case R.id.create_group:

                if(selPhoto==null){
                    Toast.makeText(mContext,"Select group image.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(selPhoto!=null){
                    base64 = selPhoto.getbase64();
                    if(base64.length()==0){
                        Toast.makeText(mContext,"Select group image.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(grp_name.getText().toString().length()==0){
                        Toast.makeText(mContext,"Enter group name.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(grp_descrp.getText().toString().length()==0){
                        Toast.makeText(mContext,"Enter description.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(taged_users.size()==0){
                        Toast.makeText(mContext,"At least one person must be selected",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new CreateGroupsTask().execute();

                }



                break;


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void fillAutoTextList(){
        search_txt.addTextChangedListener(new ListenToText());

    }


    TaggingAdapter taggingAdapter=null;
    public void setTeagedUserList(){
        taggingAdapter=new TaggingAdapter(mContext, taged_users, new TaggingAdapter.TaggingCallback() {
            @Override
            public void setPosition(int pos) {
                taged_users.remove(pos);
                taggingAdapter.notifyDataSetChanged();
                Utils.getListViewSize(taged_user_list);
            }
        });
        taged_user_list.setAdapter(taggingAdapter);
        Utils.getListViewSize(taged_user_list);
    }


    FilterTask filterTask=null;
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
            if (s.toString().length() >= 3) {
                if(filterTask!=null)
                    filterTask.cancel(true);

                filterTask=new FilterTask();
                filterTask.execute(s.toString());
                //getFilterList(s.toString());

            }

            else{
                if(changeSortPopUp!=null)
                    changeSortPopUp.dismiss();
            }
        }
    }

    ProgressBar progress_show;

    public class FilterTask extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress_show.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String keyWord=strings[0];
            getFilterList(keyWord);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            settingMenu();

            progress_show.setVisibility(View.GONE);
            if(search_txt.getText().toString().equals("")){
                if(changeSortPopUp!=null)
                    changeSortPopUp.dismiss();
            }


        }
    }


    PopupWindow changeSortPopUp=null;
    RecyclerView searc_dialog_lis=null;
    SearchTag_Adapter searchAdapter=null;
    View layout=null;
    Rect rc=null;
    public void settingMenu() {


        int OFFSET_X = 0;
        int OFFSET_Y = 100;

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        if(changeSortPopUp==null) {
            changeSortPopUp = new PopupWindow(mContext);

            changeSortPopUp.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.tag_search_dialog, null);

            searc_dialog_lis = (RecyclerView) layout.findViewById(R.id.searc_dialog_lis);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            searc_dialog_lis.setLayoutManager(layoutManager);
            searc_dialog_lis.setItemAnimator(new DefaultItemAnimator());


            searchAdapter = new SearchTag_Adapter(mContext, fillteredUsers, new SearchTag_Adapter.ItemClickedListener() {
                @Override
                public void item(int pos) {
                    for (int i = 0; i < taged_users.size(); i++) {
                        if (fillteredUsers.get(pos).getUser_id().equals(taged_users.get(i).getUser_id())) {
                            Toast.makeText(mContext, "Already added.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if(taged_users.size()<25) {
                        taged_users.add(fillteredUsers.get(pos));
                        taggingAdapter.notifyDataSetChanged();
                        Utils.getListViewSize(taged_user_list);
                        changeSortPopUp.dismiss();
                        search_txt.setText("");
                    }
                    else{
                        changeSortPopUp.dismiss();
                        search_txt.setText("");
                        Toast.makeText(mContext,"Maximum of 25 employees can be tagged.",Toast.LENGTH_SHORT).show();
                    }


                }
            });

            searc_dialog_lis.setAdapter(searchAdapter);



            rc = new Rect();
            heade_lay.getWindowVisibleDisplayFrame(rc);
            int[] xy = new int[2];
            heade_lay.getLocationInWindow(xy);
            rc.offset(xy[0], xy[1]);
            // Creating the PopupWindow
            OFFSET_X = 0;
            OFFSET_Y = 80;


            //changeSortPopUp.setAnimationStyle(R.style.animationName);
            changeSortPopUp.setContentView(layout);
            changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            changeSortPopUp.setHeight(600);
            changeSortPopUp.setFocusable(false);
            changeSortPopUp.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

            // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.


            // Clear the default translucent background
            changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

            // Displaying the popup at the specified location, + offsets.
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        }
        else{
            OFFSET_X = 0;
            OFFSET_Y = 80;
            searchAdapter.notifyDataSetChanged();
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        }
    }


    List<AllUsers> fillteredUsers=new ArrayList<>();
    public void getFilterList(String keyWord){
        fillteredUsers.clear();
        int temp_pos=0;
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();
        Collections.sort(allUserses,usersComparator);
        for (AllUsers people : allUserses) {
                   /* String temp1=("Mr "+constraint.toString()).toLowerCase();
                    String temp2=("Ms "+constraint.toString()).toLowerCase();*/




            if (people.getName().toString().toLowerCase().contains(keyWord.toString().toLowerCase()))
                            /*|| people.getName().toString().toLowerCase().startsWith(temp2)*/
            {
                if (people.getName().toString().toLowerCase().startsWith(keyWord.toString().toLowerCase()))
                {
                    fillteredUsers.add(temp_pos,people);
                    temp_pos++;
                }
                else{
                    fillteredUsers.add(people);
                }
            }



            else{

                String temp_str[]=keyWord.toString().split("\\s+");
                if(temp_str.length>1) {
                    if (people.getName().toString().toLowerCase().contains(temp_str[0].toLowerCase())
                            && people.getName().toString().toLowerCase().contains(temp_str[1].toLowerCase())) {

                        fillteredUsers.add(people);
                    }
                }

            }



        }


    }

    public Comparator<AllUsers> usersComparator = new Comparator<AllUsers>() {

        public int compare(AllUsers s1, AllUsers s2) {
            String cluster1 = s1.getName().toUpperCase();
            String cluster2 = s2.getName().toUpperCase();

            //ascending order
            return cluster1.compareTo(cluster2);

            //descending order
            // return cluster2.compareTo(cluster1);
        }};

    String base64 = "";
    String file_extension = "";
    String content_type = "";
    String file_name = "";


    Bitmap bitmap=null;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), data.getData());
                    gp_img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }


        selPhoto.onActivityResult(requestCode, resultCode, data);
    }

    private void onCaptureImageResult(Intent data) {
        Uri takenPhotoUri = getPhotoFileUri(selPhoto.capture_Photo_name);
        Bitmap thumbnail=null;
        try {
            thumbnail=  BitmapFactory.decodeFile(takenPhotoUri.getPath());//(Bitmap) data.getExtras().get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap = thumbnail;
        gp_img.setImageBitmap(bitmap);

    }

    public Uri getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(
                mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "expressionApp");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d("expressionApp", "failed to create directory");
        }

        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));

    }


    ProgressDialog progressDialog = null;

    class CreateGroupsTask extends AsyncTask<Void,Void,Void> {
        CreateGroupManager createGroupManager;
        String responseString = "";
        String s = "Plase Wait...";


        String gp_name_str="",gp_des_str="",people_ids="";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            gp_name_str=grp_name.getText().toString();
            gp_des_str=grp_descrp.getText().toString();

            for(int l=0;l<taged_users.size();l++){
                if(l==0)
                    people_ids=taged_users.get(l).getUser_id();
                else
                    people_ids=people_ids+","+taged_users.get(l).getUser_id();
            }

            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            createGroupManager=new CreateGroupManager(mContext);
            responseString =createGroupManager.createGroup(gp_name_str,gp_des_str,people_ids,base64,closed_open,themeid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                GroupListActivity.isfromCreateGP=true;
                Toast.makeText(mContext,"Group has been added successfully.",Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String[] theamColors = {
            "#E74C3C","#16A085","#E67E22","#3398DB","#34495E"
    };


    private int[] mImageResources = {
            R.drawable.theme1,
            R.drawable.theme2,
            R.drawable.theme3,
            R.drawable.theme4,
            R.drawable.theme5,
            //R.drawable.theme6
    };


    ImageView lastClicked=null;

    public void createTheamsView(){

        for(int i=0;i<mImageResources.length;i++){
            LinearLayout linearLayout=new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300,300);
            layoutParams.leftMargin=10;
            layoutParams.rightMargin=10;
            layoutParams.topMargin=5;
            layoutParams.bottomMargin=5;
            layoutParams.gravity=Gravity.CENTER;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundResource(mImageResources[i]);

            final ImageView imageView=new ImageView(mContext);
            imageView.setImageResource(R.drawable.check_theamgp);
            LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(300,100);
            imageParams.gravity=Gravity.CENTER;
            imageView.setLayoutParams(imageParams);
            imageView.setTag((i+1));


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastClicked!=null)
                    lastClicked.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    lastClicked=imageView;
                    themeid=String.valueOf(imageView.getTag());

                }
            });




            if(i==0){
                lastClicked=imageView;
                imageView.setVisibility(View.VISIBLE);
            }
            else {
                imageView.setVisibility(View.GONE);
            }


            linearLayout.addView(imageView);




            theamlay.addView(linearLayout);

        }


    }


}
