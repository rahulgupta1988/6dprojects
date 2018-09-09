package expression.sixdexp.com.expressionapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.SayThanksForBook;
import expression.sixdexp.com.expressionapp.ToReadActivity;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import model.Book;

/**
 * Created by Praveen on 20-Nov-17.
 */

public class ReceivedBookListAdapter  extends RecyclerView.Adapter<ReceivedBookListAdapter.CommentHolder> {

    Context mContext;
    RecyclerView recyclerView;
    public List<Book> receivedBooks;
    public ReceivedBookListAdapter(Context mContext, List<Book> receivedBooks) {
        this.mContext = mContext;
        this.receivedBooks=receivedBooks;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }



    @Override
    public ReceivedBookListAdapter.CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivedlistbook_item, parent, false);
        return new ReceivedBookListAdapter.CommentHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final ReceivedBookListAdapter.CommentHolder holder, final int position) {

        Book book=receivedBooks.get(position);



        Log.i("book_name091",""+book.getTitle());

        holder.book_name.setText(book.getTitle());
        holder.auther_name.setText(book.getAuthorname());


        String image_url = book.getBookicon();
        URI book_uri = null;
        try {
            book_uri = new URI(image_url.replace(" ", "%20"));
            if (book_uri.toString().length()!=0){
                Glide.with(mContext)
                        .load(book_uri.toString())
                        .placeholder(R.drawable.default_book_ic)
                        .error(R.drawable.default_book_ic)
                        .crossFade()
                        .thumbnail(0.1f)
                        .centerCrop()
                        .into(holder.book_img);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return receivedBooks.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {


        TextView book_name,auther_name;
        RoundedImageView book_img;

        public CommentHolder(final View convertView) {
            super(convertView);
            book_name=(TextView)convertView.findViewById(R.id.book_name);
            auther_name=(TextView)convertView.findViewById(R.id.auther_name);
            book_img=(RoundedImageView)convertView.findViewById(R.id.book_img);
        }

    }

    ProgressDialog progressDialog = null;








}



