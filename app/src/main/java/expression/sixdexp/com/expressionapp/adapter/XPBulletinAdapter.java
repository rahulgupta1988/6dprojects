package expression.sixdexp.com.expressionapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import db.BulletinList;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.ImageGetterAsyncTask;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 19-Jan-17.
 */

public class XPBulletinAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context _mContext;
    List<BulletinList> bulletinLists;

    public XPBulletinAdapter(Context context, List<BulletinList> bulletinLists) {
        _mContext = context;
        layoutInflater = LayoutInflater.from(_mContext);
        this.bulletinLists=bulletinLists;
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        if(bulletinLists!=null && bulletinLists.size()>0) return bulletinLists.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return bulletinLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final XPBulletinAdapter.ViewHolder holder;

        if(convertView==null){
            holder = new XPBulletinAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.bullentlist_item,null);
            holder.sharedto = (TextView) convertView.findViewById(R.id.sharedto);
            holder.desciption = (TextView) convertView.findViewById(R.id.desciption);
            holder.doc=(ImageView)convertView.findViewById(R.id.doc);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.desciption.setMinLines(1);
            holder.desciption.setMaxLines(2);
            holder.desciption.setEllipsize(TextUtils.TruncateAt.END);


            convertView.setTag(holder);
        }else {
            holder = (XPBulletinAdapter.ViewHolder) convertView.getTag();
        }



        BulletinList bulletinList_item=bulletinLists.get(position);

        String postedbyuser=bulletinList_item.getPostedbyuser();
        String postbyemail=bulletinList_item.getPostbyemail();
        String detail=bulletinList_item.getDetails().trim();
        String create_date=bulletinList_item.getCreate_date();
        String groupemail=bulletinList_item.getGroupemail();
        final String type=bulletinList_item.getType();
        final String fileurl=bulletinList_item.getFileurl();
        final String url=bulletinList_item.getUrl();


        if(type.equals("")){
            holder.doc.setVisibility(View.GONE);
        }

      else{
            holder.doc.setVisibility(View.VISIBLE);
            holder.doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String link="";
                    if(type.equals("video"))
                        link=url;
                    else
                        link= fileurl;
                    Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                    webIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                    _mContext.startActivity(webIntent);

                  /* if(type.contains("image") || type.contains("pdf")){
                        docsDialog(type,fileurl);
                    }
                    else{
                        try {

                           String filename= FilenameUtils.getName(new URL(fileurl).getPath());
                            File file = new File("/sdcard/"+filename);
                            if(file.exists()){
                                File targetFile = new File("/sdcard/"+filename);
                                Uri targetUri = Uri.fromFile(targetFile);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(targetUri,type);
                                ((Activity)_mContext).startActivityForResult(intent, 100);
                            }

                            else{
                              DownLoadtDailogBox(new URL(fileurl),type);

*//*
                                String link= fileurl;
                                Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                                webIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                                _mContext.startActivity(webIntent);*//*
                            }


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }

*/



                }
            });
        }
        holder.sharedto.setText(postedbyuser+" has shared Xpress Bulletin to "+groupemail);

       /* URLImageParser p = new URLImageParser(holder.desciption,_mContext);
        Spanned htmlSpan = Html.fromHtml(detail, p, null);*/


        Spanned spanned = Html.fromHtml(detail,new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        LevelListDrawable d = new LevelListDrawable();
                        Drawable empty = _mContext.getResources().getDrawable(R.drawable.default_emoji_circle);
                        d.addLevel(0, 0, empty);
                        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                        new ImageGetterAsyncTask(_mContext, source, d).execute(holder.desciption);

                        return d;
                    }
                }, null);


        holder.desciption.setText(spanned);

        holder.date.setText(create_date);





/*
        holder.desciption.post(new Runnable() {
            @Override
            public void run() {

                if( holder.desciption.getLineCount()>=2) {*/
                    holder.desciption.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            BulletinList bulletinList_item = bulletinLists.get(position);

                            String postedbyuser = bulletinList_item.getPostedbyuser();
                            String postbyemail = bulletinList_item.getPostbyemail();
                            String detail = bulletinList_item.getDetails().trim();
                            String create_date = bulletinList_item.getCreate_date();
                            String groupemail = bulletinList_item.getGroupemail();

                            detailWondow(postedbyuser, detail, create_date, groupemail);
                        }
                    });
               /* }

            }
        });*/


        return convertView;
    }


    class ViewHolder {
        TextView sharedto,desciption,date;
        ImageView doc;
    }


    public void detailWondow(String postedbyuser,String detail,String create_date,String groupemail) {
        final PopupWindow changeSortPopUp = new PopupWindow(_mContext);
        LayoutInflater layoutInflater = (LayoutInflater) _mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.bulletin_details, null);

        TextView sharedto=(TextView)layout.findViewById(R.id.sharedto);
        final TextView desciption=(TextView)layout.findViewById(R.id.desciption);
        TextView date=(TextView)layout.findViewById(R.id.date);


        sharedto.setText(postedbyuser+" has shared Xpress Bulletin to "+groupemail);

       /* URLImageParser p = new URLImageParser(desciption,_mContext);
        Spanned htmlSpan = Html.fromHtml(detail, p, null);*/

        Spanned spanned = Html.fromHtml(detail,new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = _mContext.getResources().getDrawable(R.drawable.default_emoji_circle);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                new ImageGetterAsyncTask(_mContext, source, d).execute(desciption);

                return d;
            }
        }, null);


        desciption.setText(spanned);
        date.setText(create_date);


        Rect rc = new Rect();
        //commentit.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        //commentit.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // changeSortPopUp.setAnimationStyle(R.style.animationpopup);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = 0;
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }


    public void docsDialog(String file_type,String url){
        final Dialog dialog = new Dialog(_mContext,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.xpressbulletin_doc_viewer);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView img = (ImageView) window.findViewById(R.id.img);
        WebView webview=(WebView)window.findViewById(R.id.webview);

        if(file_type.contains("image")){
            webview.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            Glide.with(_mContext)
                    .load(url)
                    .placeholder(R.drawable.default_image_icon)
                    .error(R.drawable.default_image_icon)
                    .crossFade()
                    .thumbnail(0.1f)
                    .fitCenter()
                    .into(img);
        }

        else  if(file_type.contains("pdf")){
            img.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);

            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
            final ProgressDialog progressDialog = ProgressLoaderUtilities.getProgressDialog(_mContext);
            progressDialog.show();

            webview.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressDialog.dismiss();
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }
            });
        }


        dialog.show();

    }


    ProgressDialog pDialog;

    public void DownLoadtDailogBox(final URL _m_url,final String type) {


        new AsyncTask<String, String, String>() {
            String filename="";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(_mContext);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... f_url) {
                int count;
                try {

                    URLConnection conection = _m_url.openConnection();
                    conection.connect();
                    int lenghtOfFile = conection.getContentLength();
                    InputStream input = new BufferedInputStream(_m_url.openStream(), 8192);

                     filename= FilenameUtils.getName(_m_url.getPath());
                    OutputStream output = new FileOutputStream("/sdcard/"+filename);
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress(""+(int)((total*100)/lenghtOfFile));
                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }


            /**
             * Updating progress bar
             * */
            protected void onProgressUpdate(String... progress) {
                // setting progress percentage
                pDialog.setProgress(Integer.parseInt(progress[0]));

            }

            /**
             * After completing background task
             * Dismiss the progress dialog
             * **/
            @Override
            protected void onPostExecute(String file_url) {

                pDialog.dismiss();

                File targetFile = new File("/sdcard/"+filename);
                Uri targetUri = Uri.fromFile(targetFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(targetUri,type);
                ((Activity)_mContext).startActivityForResult(intent, 100);
            }

        }.execute();



    /*    dialog.show();*/
    }



}

