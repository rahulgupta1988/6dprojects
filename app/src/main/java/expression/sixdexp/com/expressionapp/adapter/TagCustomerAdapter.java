package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 07-Sep-17.
 */

public class TagCustomerAdapter extends ArrayAdapter<AllUsers>{

    Context context;
    List<AllUsers> customers, tempCustomer, suggestions;
    public TagCustomerAdapter(Context context, List<AllUsers> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.customers = objects;
        this.context=context;
        this.tempCustomer = new ArrayList<AllUsers>(objects);
        this.suggestions = new ArrayList<AllUsers>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllUsers customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tag_adapter_item, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.username);
        TextView user_desg = (TextView) convertView.findViewById(R.id.user_desg);

        ImageView ivCustomerImage = (ImageView) convertView.findViewById(R.id.user_img);
        if (txtCustomer != null)
            txtCustomer.setText(""+customer.getName());

        if (user_desg != null)
            user_desg.setText(""+customer.getDesignation());

       // ivCustomerImage.setImageResource(R.drawable.default_profile_picture);


        try {

            String image_url = customer.getImageurl();
            String user_image_url = image_url.replace(" ", "%20");
            Picasso.with(context)
                    .load(user_image_url)
                    .centerInside()
                    .resize(50,50)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_default)
                    .error(R.drawable.user_default)
                    .into(ivCustomerImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Now assign alternate color for rows

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            int temp_pos=0;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            synchronized (filterResults) {
                if (constraint != null) {
                    suggestions.clear();

                    for (AllUsers people : tempCustomer) {
                   /* String temp1=("Mr "+constraint.toString()).toLowerCase();
                    String temp2=("Ms "+constraint.toString()).toLowerCase();*/
                        if (people.getName().toString().toLowerCase().contains(constraint.toString().toLowerCase()))
                            /*|| people.getName().toString().toLowerCase().startsWith(temp2)*/
                        {
                            if (people.getName().toString().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                            {
                                suggestions.add(temp_pos,people);
                                temp_pos++;
                            }
                            else{
                                suggestions.add(people);
                             }



                        }
                    }


                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new Filter.FilterResults();
                }
            }
        }




        @Override
        synchronized protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<AllUsers> c = (ArrayList<AllUsers>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (AllUsers cust : c) {
                        add(cust);
                    }
                    notifyDataSetChanged();
                }

                else
                {
                    notifyDataSetInvalidated();
                }
            }




       /* @Override
        public CharSequence convertResultToString(Object resultValue) {
            AllUsers allUsers = (AllUsers) resultValue;
            return allUsers.getName()*//*+" "+allUsers.getDepartment()*//*;
        }*/
    };


    class MyComparator implements Comparator<AllUsers> {

        private final String keyWord;

        MyComparator(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public int compare(AllUsers o1, AllUsers o2) {

            if(o1.getName().startsWith(keyWord)) {
                return o2.getName().toLowerCase().startsWith(keyWord)? o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()): -1;
            } else {
                return o2.getName().toLowerCase().startsWith(keyWord)? 1: o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        }
    }

}
