package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.UserInfoManager;

/**
 * Created by Praveen on 9/14/2016.
 */
public class UserProfileActivity extends Activity {

    SettingMenuUtility settingMenuUtility;
    Context mContext;
    View view;
    TextView companyname_view, departmentname_view, roleprofilename_view, totalexp_view,username;
    CircularImageView userimg;
    TextView designation, location, myhobby, quotetxt;
    ImageView editimg;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileview);
        mContext=this;
        init();
    }

    public void init(){

        companyname_view = (TextView) findViewById(R.id.companyname);
        departmentname_view = (TextView) findViewById(R.id.departmentname);
        roleprofilename_view = (TextView) findViewById(R.id.roleprofilename);
        totalexp_view = (TextView) findViewById(R.id.totalexp);
        username = (TextView) findViewById(R.id.username);
        designation = (TextView) findViewById(R.id.designation);
        location = (TextView) findViewById(R.id.location);
        myhobby = (TextView) findViewById(R.id.myhobby);
        quotetxt = (TextView) findViewById(R.id.quotetxt);
        userimg = (CircularImageView) findViewById(R.id.userimg);
        editimg = (ImageView) findViewById(R.id.editimg);

        UserLoginInfo userLoginInfo = UserInfoManager.userInfos;

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


        if (user_name != null)
            username.setText(user_name);


        if (user_designation != null && !user_designation.equalsIgnoreCase("")) {
            designation.setText(user_designation);
        }

        if (user_location != null)
            location.setText(user_location);

        if (user_quote != null)
            quotetxt.setText(user_quote);


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
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(userimg);
        }

        if (companyname != null && !companyname.equalsIgnoreCase(""))
            companyname_view.setText(companyname);
        if (departmentname != null && !departmentname.equalsIgnoreCase(""))
            departmentname_view.setText(departmentname);
        if (roleprofilename != null && !roleprofilename.equalsIgnoreCase(""))
            roleprofilename_view.setText(roleprofilename);
        if (totalexp != null && !totalexp.equalsIgnoreCase(""))
            totalexp_view.setText(totalexp);
        if (user_hobby != null && !user_hobby.equalsIgnoreCase("")) {
            myhobby.setText(user_hobby);
        }

    }
}
