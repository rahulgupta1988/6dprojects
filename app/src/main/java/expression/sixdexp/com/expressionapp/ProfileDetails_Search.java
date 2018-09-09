package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import db.UserLoginInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import java.net.URI;
import java.net.URISyntaxException;

import expression.sixdexp.com.expressionapp.manager.UserInfoManager;

/**
 * Created by Praveen on 23-Jan-17.
 */

public class ProfileDetails_Search extends Activity {
    Context mContext;
    TextView txt_username,recognition_given,recognition_recieved,company_txt,
            department_txt,roleprofile_txt,experience_txt,hobbies_txt,quote;
    CircleImageView user_profile_img;
    ImageView back_img,donor_icon;
    ImageView connecttosolve_icon,mvoice_icon,toread_icon,gp_tan_ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledetail_search);
        mContext=this;
        initViews();
    }

    public void initViews(){
        connecttosolve_icon=(ImageView)findViewById(R.id.connecttosolve_icon);
        mvoice_icon=(ImageView)findViewById(R.id.mvoice_icon);
        toread_icon=(ImageView)findViewById(R.id.toread_icon);
        gp_tan_ic=(ImageView)findViewById(R.id.gp_tan_ic);

        connecttosolve_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl="https://webapps2.tatapower.com/connecttosolve";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pathUrl));
                startActivity(intent);
            }
        });


        mvoice_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pathUrl="https://play.google.com/store/apps/details?id=com.tata.servpro_pokt_tata_feedback&hl=en";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pathUrl));
                startActivity(intent);
            }
        });




        txt_username=(TextView)findViewById(R.id.txt_username);
        recognition_given=(TextView)findViewById(R.id.recognition_given);
        recognition_recieved=(TextView)findViewById(R.id.recognition_recieved);
        company_txt=(TextView)findViewById(R.id.company_txt);
        department_txt=(TextView)findViewById(R.id.department_txt);
        roleprofile_txt=(TextView)findViewById(R.id.roleprofile_txt);
        experience_txt=(TextView)findViewById(R.id.experience_txt);
        hobbies_txt=(TextView)findViewById(R.id.hobbies_txt);
        quote=(TextView)findViewById(R.id.quote);
        user_profile_img=(CircleImageView) findViewById(R.id.user_profile_img);
        back_img=(ImageView)findViewById(R.id.back_img);
        donor_icon=(ImageView)findViewById(R.id.donor_icon);
        setUserDetails();
    }


    public void setUserDetails(){
        UserLoginInfo userLoginInfo = UserInfoManager.userInfos;

        final  String userid=userLoginInfo.getUserid();
        final String companyname = userLoginInfo.getCompany();
        final String departmentname = userLoginInfo.getDepartment();
        final String roleprofilename = userLoginInfo.getRole();
        final String totalexp = userLoginInfo.getExperience();
        final String user_img_url = userLoginInfo.getImageurl();
        final String user_designation = userLoginInfo.getDesignation();
        final String user_role = userLoginInfo.getRole();
        final String user_location = userLoginInfo.getLocation();
        final String user_name = userLoginInfo.getName();
        final String user_hobby = userLoginInfo.getHobbies();
        final String user_quote = userLoginInfo.getQuote();
        final String rrecognition = userLoginInfo.getRrecognition();
        final String grecognition = userLoginInfo.getGrecognition();

        final String isdonor = userLoginInfo.getIsdonor();

        if(isdonor.equals("1"))
            donor_icon.setVisibility(View.VISIBLE);
        else
            donor_icon.setVisibility(View.GONE);




        recognition_recieved.setText("Given\n"+rrecognition);
        recognition_given.setText("Received\n"+grecognition);
        if (user_name != null)
            txt_username.setText(user_name);

/*
        if (user_designation != null && !user_designation.equalsIgnoreCase("")) {
            designation.setText(user_designation);
        }*/
/*
        if (user_location != null)
            location.setText(user_location);*/

        if (user_quote != null)
            quote.setText(user_quote);


        URI nominator_uri = null;
        try {
            nominator_uri = new URI(user_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String nominator_imageurl1 = nominator_uri.toString();

        if (!nominator_imageurl1.equalsIgnoreCase("null") && !nominator_imageurl1.equalsIgnoreCase("")
                && !nominator_imageurl1.equalsIgnoreCase(" ")) {


            Picasso.with(mContext)
                    .load(nominator_imageurl1)
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(user_profile_img);

            Picasso.with(mContext)
                    .load(nominator_imageurl1)
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(back_img);



        }

        if (companyname != null && !companyname.equalsIgnoreCase(""))
            company_txt.setText(companyname);
        if (departmentname != null && !departmentname.equalsIgnoreCase(""))
            department_txt.setText(departmentname);
        if (roleprofilename != null && !roleprofilename.equalsIgnoreCase(""))
            roleprofile_txt.setText(roleprofilename);
        if (totalexp != null && !totalexp.equalsIgnoreCase(""))
            experience_txt.setText(totalexp);
        if (user_hobby != null && !user_hobby.equalsIgnoreCase("")) {
            hobbies_txt.setText(user_hobby);
        }


        toread_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,UserToReadActivity.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

        gp_tan_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent usergp_intent=new Intent(mContext, expression.sixdexp.com.expressionapp.xpassions.UserGPListActivity.class);
                usergp_intent.putExtra("user_name",user_name);
                usergp_intent.putExtra("userid",userid);
                startActivity(usergp_intent);
                //Toast.makeText(mContext,"Coming soon...",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
