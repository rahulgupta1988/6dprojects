package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.Book;

/**
 * Created by Praveen on 02-Jan-18.
 */

public class ToReadBooksManager  extends  BaseManager {

    Context _mContext;
    String responseString = "";
    String userid_new="";

    public List<Book> wishedBooks=new ArrayList<>();
    public List<Book> receivedBooks=new ArrayList<>();

    public  ToReadBooksManager(Context _mContext){
        this._mContext = _mContext;
    }

    public  ToReadBooksManager(Context _mContext,String userid_new){
        this._mContext = _mContext;
        this.userid_new=userid_new;
    }
    public String getBooks(String isread) {

        try {

            Log.i("responstring", "get books serviceUrl-->" + Constant.BASEURL);
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            if(userid_new.length()==0)
            entitymap.put("userid", userid);
            else
                entitymap.put("userid", userid_new);
            entitymap.put("isread", isread);


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/toread/gettoreadbooksbyuserid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseData(responseString);
    }

    public String parseData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;

                            JSONArray booksArray=jsonObject.getJSONArray("responseData");
                            for(int i=0;i<booksArray.length();i++){
                                Book book=new Book();
                                JSONObject book_json = booksArray.getJSONObject(i);

                                String id=book_json.getString("id");
                                String title=book_json.getString("title");
                                String authorname=book_json.getString("authorname");
                                String description=book_json.getString("description");
                                String bookicon=book_json.getString("bookicon");
                                String isread=book_json.getString("isread");
                                String isactive=book_json.getString("isactive");
                                String addeddate=book_json.getString("addeddate");
                                String addedbyuserid=book_json.getString("addedbyuserid");
                                String flipkart=book_json.getString("flipkart");
                                String amazon=book_json.getString("amazon");

                                book.setId(id);
                                book.setTitle(title);
                                book.setAuthorname(authorname);
                                book.setDescription(description);
                                book.setBookicon(bookicon);
                                book.setIsread(isread);
                                book.setIsactive(isactive);
                                book.setAddeddate(addeddate);
                                book.setAddedbyuserid(addedbyuserid);
                                book.setFlipkart(flipkart);
                                book.setAmazon(amazon);

                                if(isread.equals("0")){
                                    wishedBooks.add(book);
                                }

                                else{
                                    receivedBooks.add(book);
                                }

                            }



                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }
}
