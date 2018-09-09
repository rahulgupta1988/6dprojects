package expression.sixdexp.com.expressionapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.SayThanksForBook;
import model.Book;

/**
 * Created by Praveen on 02-Jan-18.
 */

public class ToReadUserAdapter  extends RecyclerView.Adapter<ToReadUserAdapter.CommentHolder> {

    Context mContext;
    RecyclerView recyclerView;
    public List<Book> wishedBooks;

    public ToReadUserAdapter(Context mContext,  List<Book> wishedBooks) {
        this.mContext = mContext;
        this.wishedBooks=wishedBooks;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }



    @Override
    public ToReadUserAdapter.CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.toreaduseradapter, parent, false);
        return new ToReadUserAdapter.CommentHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final ToReadUserAdapter.CommentHolder holder, final int position) {

        Book book=wishedBooks.get(position);


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


       holder.flipkart_icon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String pathUrl=wishedBooks.get(position).getFlipkart();
               Intent intent=new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(pathUrl));
               mContext.startActivity(intent);
           }
       });

        holder.amazone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl=wishedBooks.get(position).getAmazon();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pathUrl));
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return wishedBooks.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {


        TextView book_name,auther_name;
        RoundedImageView book_img;
        ImageView flipkart_icon,amazone_icon;

        public CommentHolder(final View convertView) {
            super(convertView);
            book_name=(TextView)convertView.findViewById(R.id.book_name);
            auther_name=(TextView)convertView.findViewById(R.id.auther_name);
            book_img=(RoundedImageView)convertView.findViewById(R.id.book_img);
            flipkart_icon=(ImageView)convertView.findViewById(R.id.flipkart_icon);
            amazone_icon=(ImageView)convertView.findViewById(R.id.amazone_icon);

        }

    }

    ProgressDialog progressDialog = null;








}




