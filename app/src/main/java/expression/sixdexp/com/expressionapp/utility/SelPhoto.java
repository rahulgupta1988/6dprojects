package expression.sixdexp.com.expressionapp.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Praveen on 7/8/2016.
 */
public class SelPhoto {


    String base64_data = "";
    String file_Name, file_ext;
    String content_type = "";


    public static final int SELECT_FILE = 1;
    public static final int REQUEST_CAMERA = 2;
    Bitmap finalBitmap = null;
    Context mContext;
    TextView videodoclink;
    LinearLayout linklay;
    String userChoosenTask;
    String doctype = "";

    public SelPhoto(Context context, TextView videodoclink, LinearLayout linklay, String doctype) {
        mContext = context;
        this.videodoclink = videodoclink;
        this.linklay = linklay;
        this.doctype = doctype;
        selectImage();
    }


    private void selectImage() {


        if (doctype.equalsIgnoreCase("doc")) {
            final CharSequence[] items1 = {"Choose Document\n(.doc, .docx, .xls, .xlsx & .pdf)",
                    "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Add Document!");

            builder.setItems(items1, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = true;//Utility.checkPermission();
                    if (items1[item].equals("Take Photo")) {
                        userChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();
                    } else if (items1[item].equals("Choose Document\n" +
                            "(.doc, .docx, .xls, .xlsx & .pdf)")) {
                        userChoosenTask = "Choose from Image Gallery";
                        if (result)
                            galleryIntent();
                    } else if (items1[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();

        } else {
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
            builder.setCancelable(false);
            builder.show();
        }

    }

    String capture_Photo_name="";
    private void cameraIntent() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) mContext).startActivityForResult(intent, REQUEST_CAMERA);*/
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
        if (doctype.equalsIgnoreCase("doc"))
            intent.setType("file/*");
        else intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
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
                finalBitmap = bm;
                galleryResult(data);
                //setFileExt(data);
                //getPathNadextention(bm, data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void onCaptureImageResult(Intent data) {
       /* Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        finalBitmap = thumbnail;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

            getStringImage();
            String sel_str = destination.getAbsolutePath();
            file_ext = sel_str.substring(sel_str.lastIndexOf("."), sel_str.length());
            file_Name = destination.getName();
            content_type = getMimeType(sel_str);
            if (videodoclink != null) {
                videodoclink.setText(file_Name);
                linklay.setVisibility(View.VISIBLE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        Uri takenPhotoUri = getPhotoFileUri(capture_Photo_name);
        Bitmap thumbnail=null;
        try {
            thumbnail=  BitmapFactory.decodeFile(takenPhotoUri.getPath());//(Bitmap) data.getExtras().get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finalBitmap = thumbnail;

        FileOutputStream fo;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            getStringImage();
            String sel_str=destination.getAbsolutePath();
            file_ext = sel_str.substring(sel_str.lastIndexOf("."), sel_str.length());
            file_Name = destination.getName();
            content_type = getMimeType(sel_str);

            if (videodoclink != null) {
                videodoclink.setText(file_Name);
                linklay.setVisibility(View.VISIBLE);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void getStringImage() {
        if (finalBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] imageBytes = baos.toByteArray();
            base64_data = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

    }

    public void setBase64ForDoc(Uri filepath) {
        InputStream in = null;
        try {
            in = mContext.getContentResolver().openInputStream(filepath);
            byte[] imageBytes = IOUtils.toByteArray(in);
            base64_data = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFileName() {
        return file_Name;
    }

    public String getbase64() {
        return base64_data;
    }

    public String getextention() {
        return file_ext;
    }

    public String getcontentType() {
        return content_type;
    }

    public void galleryResult(Intent data) {


        Log.i("Build.VERSION.SDK_INT", "" + Build.VERSION.SDK_INT);
        Bitmap bm = null;
        if (data != null) {
            try {

                String mTmpGalleryPicturePath = "";
                Uri selectedImage = data.getData();
                Log.i("Uri 756", "" + selectedImage);
                String sel_str = selectedImage.toString();

                if (!doctype.equalsIgnoreCase("doc")) {
                    getStringImage();
                    if (Build.VERSION.SDK_INT == 22) {
                        mTmpGalleryPicturePath = getPath(selectedImage);
                        file_ext = mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());

                        file_Name = new File(mTmpGalleryPicturePath).getName();
                        if (videodoclink != null) {
                            videodoclink.setText(file_Name);
                            linklay.setVisibility(View.VISIBLE);
                        }
                    } else if (Build.VERSION.SDK_INT == 23) {
                        mTmpGalleryPicturePath = getPath(selectedImage);
                        file_ext = mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());
                        file_Name = new File(mTmpGalleryPicturePath).getName();
                        if (videodoclink != null) {
                            videodoclink.setText(file_Name);
                            linklay.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mTmpGalleryPicturePath = getRealPathFromURI(selectedImage);
                        file_ext = mTmpGalleryPicturePath.substring(mTmpGalleryPicturePath.lastIndexOf("."), mTmpGalleryPicturePath.length());
                        file_Name = new File(mTmpGalleryPicturePath).getName();
                        if (videodoclink != null) {
                            videodoclink.setText(file_Name);
                            linklay.setVisibility(View.VISIBLE);
                        }
                    }

                    if (mTmpGalleryPicturePath != null)
                        content_type = getMimeType(mTmpGalleryPicturePath);
                } else {

                    setBase64ForDoc(selectedImage);
                    file_ext = sel_str.substring(sel_str.lastIndexOf("."), sel_str.length());
                    file_Name = new File(sel_str).getName();
                    content_type = getMimeType(sel_str);

                    if (videodoclink != null) {
                        fileValidation();
                        /*videodoclink.setText(file_Name);
                        linklay.setVisibility(View.VISIBLE);*/

                    }
                }
                //bm =getThumbnailBitmap(mTmpGalleryPicturePath,1000);
                //finalBitmap = bm;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //background_img_ll.setBackgroundResource(0);
        // profile_signup_img.setImageBitmap(bm);

    }

   /* @SuppressLint("NewApi")

    private String getPath(Uri uri) {
        String path = null;
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = null;
        if (Build.VERSION.SDK_INT > 19) {
            try {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
            } catch (Exception e) {
                e.printStackTrace();
                path = getRealPathFromURI(uri);
            }


        } else {
            cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        }

        try {
            if (cursor != null) {
                int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index).toString();
                cursor.close();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;
    }*/

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

   /* private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        Cursor cursor;
        cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
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
*/

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
    private Bitmap getThumbnailBitmap(final String path, final int thumbnailSize) {
        Bitmap bitmap;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            bitmap = null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }

    public String getMimeType(String url) {
        String type = null;
        //String extension = MimeTypeMap.getFileExtensionFromUrl("file://"+url);
        String extension = url.substring((url.lastIndexOf(".")) + 1).toLowerCase();
        //Toast.makeText(mContext, "url" + url, Toast.LENGTH_LONG).show();
        //Toast.makeText(mContext, "Extention" + extension, Toast.LENGTH_LONG).show();
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public void fileValidation() {
        if (file_Name != null && !file_Name.equalsIgnoreCase("")) {
            if (file_ext != null && !file_ext.equalsIgnoreCase("")) {
                if (validateextention(file_ext)) {
                    if (content_type != null && !content_type.equalsIgnoreCase("")) {
                        if (base64_data != null && !base64_data.equalsIgnoreCase("")) {
                        } else crearData();
                    }
                }
            } else crearData();
        } else crearData();
    }

    public void crearData() {

        if (videodoclink != null) {
            videodoclink.setText("");
            linklay.setVisibility(View.GONE);
        }
        Toast.makeText(mContext, "seleted file is not correct,please select another file.", Toast.LENGTH_LONG).show();
        file_Name = "";
        file_ext = "";
        content_type = "";
        base64_data = "";
    }

    public boolean validateextention(String extention) {

        //Toast.makeText(mContext, "extention "+extention, Toast.LENGTH_LONG).show();
        if (extention.equalsIgnoreCase(".doc") ||
                extention.equalsIgnoreCase(".docx") ||
                extention.equalsIgnoreCase(".xls") ||
                extention.equalsIgnoreCase(".xlsx") ||
                extention.equalsIgnoreCase(".pdf")) {
            videodoclink.setText(file_Name);
            linklay.setVisibility(View.VISIBLE);
            return true;


        } else {

            if (videodoclink != null) {
                videodoclink.setText("");
                linklay.setVisibility(View.GONE);
            }
            Toast.makeText(mContext, "File should be .doc, .docx, .xls, .xlsx & .pdf", Toast.LENGTH_LONG).show();
            file_Name = "";
            file_ext = "";
            content_type = "";
            base64_data = "";
            return false;
        }


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
