package expression.sixdexp.com.expressionapp;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.adapter.CommentViewAdapter;
import expression.sixdexp.com.expressionapp.adapter.ReceivedBookListAdapter;
import expression.sixdexp.com.expressionapp.adapter.ToReadTrending_Adapter;
import expression.sixdexp.com.expressionapp.adapter.WishListAdapter;
import expression.sixdexp.com.expressionapp.manager.AddToReadBookManager;
import expression.sixdexp.com.expressionapp.manager.CommentManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.SayThanksManager;
import expression.sixdexp.com.expressionapp.manager.ToReadBooksManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InitCall;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SetPhotoNew;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import model.Book;

/**
 * Created by Praveen on 20-Nov-17.
 */

public class ToReadActivity extends AppCompatActivity {

    Context mContext;
    ImageView toread_back,add_book;
    RecyclerView trendbooks_list,wish_list,received_list;
    Button wish_btn,receive_btn;
    String bookid="";
    View templay;
    SetPhotoNew selPhoto;
    public static final int SELECT_FILE = 1;
    public static final int REQUEST_CAMERA = 2;
    public List<Book> wishedBooks=new ArrayList<>();
    public List<Book> receivedBooks=new ArrayList<>();
    LinearLayout list_lay,text_lay;
    String isread="0";
    String fromNotification="no";

    public static boolean sayThanks=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toread_activity);
        mContext=this;

        Intent intent=getIntent();
        if(intent!=null){
            fromNotification=intent.getStringExtra("fromNotification");
        }
        initViews();
    }


    public void initViews(){
        list_lay=(LinearLayout)findViewById(R.id.list_lay);
        text_lay=(LinearLayout)findViewById(R.id.text_lay);
        templay=(View)findViewById(R.id.templay);
        toread_back=(ImageView)findViewById(R.id.toread_back);
        add_book=(ImageView)findViewById(R.id.add_book);
        trendbooks_list=(RecyclerView)findViewById(R.id.trendbooks_list);
        wish_list=(RecyclerView)findViewById(R.id.wish_list);
        received_list=(RecyclerView)findViewById(R.id.received_list);
        wish_btn=(Button)findViewById(R.id.wish_btn);
        receive_btn=(Button)findViewById(R.id.receive_btn);

        wish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isread="0";
                if(wish_list.getVisibility()==View.GONE) {
                    wish_btn.setBackgroundColor(Color.parseColor("#255AE8"));
                    receive_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                    wish_btn.setTextColor(Color.parseColor("#ffffff"));
                    receive_btn.setTextColor(Color.parseColor("#255AE8"));
                    received_list.setVisibility(View.GONE);
                    wish_list.setVisibility(View.VISIBLE);
                    wish_list.scrollToPosition(0);


                    if(wishedBooks.size()>0){
                        text_lay.setVisibility(View.GONE);
                        list_lay.setVisibility(View.VISIBLE);
                    }

                    else{
                        list_lay.setVisibility(View.GONE);
                        text_lay.setVisibility(View.VISIBLE);

                    }


                }
            }
        });

        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isread="1";
                if(received_list.getVisibility()==View.GONE) {
                    wish_btn.setBackgroundColor(Color.parseColor("#ffffff"));
                    receive_btn.setBackgroundColor(Color.parseColor("#255AE8"));
                    wish_btn.setTextColor(Color.parseColor("#255AE8"));
                    receive_btn.setTextColor(Color.parseColor("#ffffff"));
                    wish_list.setVisibility(View.GONE);
                    received_list.setVisibility(View.VISIBLE);
                    received_list.scrollToPosition(0);

                    if(receivedBooks.size()>0){
                        text_lay.setVisibility(View.GONE);
                        list_lay.setVisibility(View.VISIBLE);
                    }

                    else{
                        list_lay.setVisibility(View.GONE);
                        text_lay.setVisibility(View.VISIBLE);

                    }




                }


            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookdialog();
            }
        });


        toread_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setTrendingList();
        setWishList();
        setReceivedList();

        new getBooks().execute();
    }


    private void setTrendingList(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        trendbooks_list.setLayoutManager(layoutManager);
        trendbooks_list.setItemAnimator(new DefaultItemAnimator());
        trendbooks_list.setAdapter(new ToReadTrending_Adapter(mContext));
    }

     WishListAdapter wishListAdapter=null;
     private void setWishList(){
        wishListAdapter=new WishListAdapter(mContext,wishedBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        wish_list.setLayoutManager(layoutManager);
        wish_list.setItemAnimator(new DefaultItemAnimator());
        wish_list.setAdapter(wishListAdapter);
    }

     ReceivedBookListAdapter receivedBookListAdapter=null;
     private void setReceivedList(){
        receivedBookListAdapter=new ReceivedBookListAdapter(mContext,receivedBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        received_list.setLayoutManager(layoutManager);
        received_list.setItemAnimator(new DefaultItemAnimator());
        received_list.setAdapter(receivedBookListAdapter);
    }


    String base64 = "";
    String file_extension = "";
    String content_type = "";
    String file_name = "";

     CircularImageView bookimg=null;
    public void addBookdialog(){

        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        changeSortPopUp.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.addbookdailog, null);

        bookimg = (CircularImageView) layout.findViewById(R.id.bookimg);
        final EditText  bookname=(EditText)layout.findViewById(R.id.bookname);
        final EditText  authorname=(EditText)layout.findViewById(R.id.authorname);
        final Button    btndone=(Button)layout.findViewById(R.id.btndone);
        final Button    btncancel=(Button)layout.findViewById(R.id.btncancel);


        bookimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView=new TextView(mContext);
                LinearLayout linearLayout=new LinearLayout(mContext);
                selPhoto = new SetPhotoNew(mContext, textView, linearLayout,null);



            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64="";
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                changeSortPopUp.dismiss();
            }
        });


        btndone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                if(selPhoto==null){
                    Toast.makeText(mContext,""+mContext.getString(R.string.error_bookimg),Toast.LENGTH_SHORT).show();
                    return;
                }

                base64 = selPhoto.getbase64();
                file_extension = selPhoto.getextention();
                if (file_extension == null) file_extension = "";
                content_type = selPhoto.getcontentType();
                file_name = selPhoto.getFileName();


                final String bookname_str=bookname.getText().toString();
                final String authorname_str=authorname.getText().toString();
                final String description_str="description";


                if(bookname_str.length()==0){
                    Toast.makeText(mContext,""+mContext.getString(R.string.error_bookname),Toast.LENGTH_SHORT).show();;
                    return;
                }

                else if(authorname_str.length()==0){
                    Toast.makeText(mContext,""+mContext.getString(R.string.error_authorname),Toast.LENGTH_SHORT).show();;
                    return;
                }

                else if(base64.length()==0){
                    Toast.makeText(mContext,""+mContext.getString(R.string.error_bookimg),Toast.LENGTH_SHORT).show();
                    return;
                }



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
                            responseString = new AddToReadBookManager(mContext).addBook(bookname_str,description_str,base64,authorname_str);

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
                            Toast.makeText(mContext,""+mContext.getString(R.string.bookadd_success),Toast.LENGTH_SHORT).show();
                            if (v != null) {
                                InputMethodManager imm = (InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
                                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                            }

                            changeSortPopUp.dismiss();
                            new getBooks().execute();
                        } else {

                        }

                    }


                }.execute();
            }
        });


        Rect rc = new Rect();
        templay.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        templay.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;



        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =0;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

        changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                base64="";
            }
        });
    }





    ProgressDialog progressDialog = null;

    Bitmap bitmap=null;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), data.getData());
                    bookimg.setImageBitmap(bitmap);
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
        bookimg.setImageBitmap(bitmap);

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


    class getBooks extends AsyncTask<Void,Void,Void>{

        ToReadBooksManager toReadBooksManager;
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
             toReadBooksManager=new ToReadBooksManager(mContext);
             responseString =toReadBooksManager.getBooks("0");
             return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (responseString.equals("100")) {
                wishedBooks.clear();


                wishedBooks.addAll(toReadBooksManager.wishedBooks);
                    wishListAdapter.notifyDataSetChanged();

                    if(wishedBooks.size()>0){
                        text_lay.setVisibility(View.GONE);
                        list_lay.setVisibility(View.VISIBLE);
                    }

                    else{
                        list_lay.setVisibility(View.GONE);
                        text_lay.setVisibility(View.VISIBLE);

                    }





                     new getReceivedBooks().execute();


            } else {
                progressDialog.dismiss();
            }
        }
    }


    class getReceivedBooks extends AsyncTask<Void,Void,Void>{

        ToReadBooksManager toReadBooksManager;
        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
           /* progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            toReadBooksManager=new ToReadBooksManager(mContext);
            responseString =toReadBooksManager.getBooks("1");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

                receivedBooks.clear();
                    receivedBooks.addAll(toReadBooksManager.receivedBooks);
                    receivedBookListAdapter.notifyDataSetChanged();



            } else {

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(sayThanks){
            new getBooks().execute();
            sayThanks=false;
        }
    }

    @Override
    public void onBackPressed() {

        if(fromNotification!=null){
            if(fromNotification.equals("yes")){
                finish();
                Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            else {
                super.onBackPressed();
            }
        }

        else {
            super.onBackPressed();
        }

    }
}
