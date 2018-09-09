package expression.sixdexp.com.expressionapp.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import expression.sixdexp.com.expressionapp.HostActivty;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.UploadImageManager;

/**
 * Created by Praveen on 7/6/2016.
 */
public class SettingMenuUtility {

    String file_name;
    private String[] arrPath;
    private boolean[] thumbnailsselection;
    private int ids[];
    private int count;
    String userid = "5";
    String filename = "";
    String file_extension = "";
    String content_type = "";

    public static final int SELECT_FILE = 1;
    public static final int REQUEST_CAMERA = 2;
    PopupWindow changeSortPopUp;
    Context mContext;
    ImageView settingicon, setImageFrame, imageViewRound = null;
    Bitmap finalBitmap = null;
    int height_titlebar;

    public SettingMenuUtility(Context mContext, ImageView settingicon, int height_titlebar, ImageView imageViewRound) {
        this.mContext = mContext;
        this.settingicon = settingicon;
        this.height_titlebar = height_titlebar;
       /* if(imageViewRound==null) {
            settingMenu();
        }
        else{*/
        this.imageViewRound = imageViewRound;
        showAndSelectPhoto();
        //}
    }



    private void showAndSelectPhoto() {
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.selectphoto);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Button btnSelectPhoto = (Button) window.findViewById(R.id.btnSelectPhoto);
        final Button btnUploadPhoto = (Button) window.findViewById(R.id.btnUploadPhoto);
        setImageFrame = (ImageView) window.findViewById(R.id.setImageFrame);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalBitmap != null) {

                    new TaskUploadImage().execute();
                    dialog.dismiss();

                } else {

                    Toast.makeText(mContext, "Please Select An Image", Toast.LENGTH_LONG).show();
                }
            }
        });

        final ImageView cancel = (ImageView) window.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalBitmap = null;
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    String userChoosenTask;

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Image Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = true;//Utility.checkPermission();
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Image Gallery")) {
                    userChoosenTask = "Choose from Image Gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    String capture_Photo_name="";
    private void cameraIntent() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Constant.today);
        capture_Photo_name=dateFormat.format(cal.getTime());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                getPhotoFileUri(capture_Photo_name));
        ((Activity) mContext).startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    public void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), data.getData());
                //getPathNadextention(bm, data);
                galleryResult(data);
                finalBitmap = bm;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setImageFrame.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {

        Uri takenPhotoUri = getPhotoFileUri(capture_Photo_name);
        // by this point we have the camera photo on disk


        Bitmap thumbnail=null;
        try {

            thumbnail=  BitmapFactory.decodeFile(takenPhotoUri.getPath());//(Bitmap) data.getExtras().get("data");
            setImageFrame.setImageBitmap(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //getPathNadextention(thumbnail, data);
        //galleryResult(data);
        finalBitmap = thumbnail;
        FileOutputStream fo;
        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            //destination_image_file_path=destination.getPath().toString();

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

            String sel_str=destination.getAbsolutePath();
            file_extension = sel_str.substring(sel_str.lastIndexOf("."), sel_str.length());
            file_name = destination.getName();
            content_type = getMimeType(sel_str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
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
                imageStr = getStringImage(finalBitmap);
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

                finalBitmap = null;

                URI profiler_uri = null;
                try {
                    profiler_uri = new URI(profile_picture.replace(" ", "%20"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String profile_imageurl1 = profiler_uri.toString();

                Log.i("profile_imageurl1",""+profile_imageurl1);
                if (!profile_picture.equalsIgnoreCase("null") && !profile_picture.equalsIgnoreCase("")
                        && !profile_picture.equalsIgnoreCase(" ")) {
                    Picasso.with(mContext)
                            .load(profile_imageurl1)
                            .resize(60, 60)
                            .centerCrop()
                            .placeholder(R.drawable.default_profile_picture)
                            .error(R.drawable.default_profile_picture)
                            .into(imageViewRound);
                    ((HostActivty)mContext).initnavigationdrawer();

                } else {
                    Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_profile_picture);
                    imageViewRound.setImageBitmap(finalBitmap);
                }

                Toast.makeText(mContext, successMessage, Toast.LENGTH_LONG).show();
            } else {
                finalBitmap = null;
                progressDialog.dismiss();
                Toast.makeText(mContext, successMessage, Toast.LENGTH_LONG).show();

            }
        }


    }


   /* public void getPathNadextention(Bitmap bm, Intent data) {

        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        @SuppressWarnings("deprecation")
        Cursor imagecursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.arrPath = new String[this.count];
        ids = new int[count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            ids[i] = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex);

        }

        String file_Name = new File(arrPath[0]).getName();
        if(file_Name!=null && !file_Name.equals("")) {
            String arr[] = file_Name.split("\\.");
            filename = arr[0];
            content_type = getMimeType(arrPath[0]);
            String format = arrPath[0].substring(arrPath[0].lastIndexOf("."));
            file_extension = format;

        }

    }
*/
    public void galleryResult(Intent data) {


        Log.i("Build.VERSION.SDK_INT",""+ Build.VERSION.SDK_INT);
        Bitmap bm = null;
        if (data != null) {
            try {

                String mTmpGalleryPicturePath = "";
                Uri selectedImage = data.getData();
                if(Build.VERSION.SDK_INT ==22)
                {
                    mTmpGalleryPicturePath = getPath(selectedImage);
                    file_extension=mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."),mTmpGalleryPicturePath.length());
                }
                else if(Build.VERSION.SDK_INT ==23)
                {
                    mTmpGalleryPicturePath = getPath(selectedImage);
                    file_extension=mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());
                }
                else
                {
                    mTmpGalleryPicturePath = getRealPathFromURI(selectedImage);
                    file_extension=mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());
                }

                if(mTmpGalleryPicturePath!=null)
                    content_type = getMimeType(mTmpGalleryPicturePath);
                    //bm =getThumbnailBitmap(mTmpGalleryPicturePath,1000);
                    //finalBitmap = bm;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //background_img_ll.setBackgroundResource(0);
        // profile_signup_img.setImageBitmap(bm);

    }
    @SuppressLint("NewApi")

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

    private String getRealPathFromURI(Uri contentURI) {
        String result="";
        Cursor cursor=null;

        if(contentURI.toString().contains("shell")){
            return contentURI.toString();
        }


            cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);

        //cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }


    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
       /* if (isExternalStorageAvailable()) {*/
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.



           File mediaStorageDir = new File(
                    mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "expressionApp");


            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d("expressionApp", "failed to create directory");
            }



            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
       /* }
        return null;*/
    }
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }




}
