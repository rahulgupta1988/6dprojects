package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import expression.sixdexp.com.expressionapp.ToReadActivity;
import model.Book;

/**
 * Created by Praveen on 20-Nov-17.
 */

public class WishListAdapter  extends RecyclerView.Adapter<WishListAdapter.CommentHolder> {

    Context mContext;
    RecyclerView recyclerView;
    public List<Book> wishedBooks;
    String bookid="";

    public WishListAdapter(Context mContext,List<Book> wishedBooks) {
        this.mContext = mContext;
        this.wishedBooks=wishedBooks;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }



    @Override
    public WishListAdapter.CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlistbook_item, parent, false);
        return new WishListAdapter.CommentHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final WishListAdapter.CommentHolder holder, final int position) {

        Book book=wishedBooks.get(position);
        holder.saythank_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToReadActivity.sayThanks=true;
                bookid=wishedBooks.get(position).getId();
                String bookimgurl=wishedBooks.get(position).getBookicon();
                Intent  intent=new Intent(mContext, SayThanksForBook.class);
                intent.putExtra("bookid",bookid);
                intent.putExtra("bookimgurl",bookimgurl);
                mContext.startActivity(intent);
            }
        });


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
        return wishedBooks.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        ImageView saythank_icon;
        TextView book_name,auther_name;
        RoundedImageView book_img;

        public CommentHolder(final View convertView) {
            super(convertView);

            saythank_icon=(ImageView)convertView.findViewById(R.id.saythank_icon);
            book_name=(TextView)convertView.findViewById(R.id.book_name);
            auther_name=(TextView)convertView.findViewById(R.id.auther_name);
            book_img=(RoundedImageView)convertView.findViewById(R.id.book_img);
        }

    }








}


