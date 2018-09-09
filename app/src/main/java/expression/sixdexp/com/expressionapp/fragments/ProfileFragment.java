package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.HostActivty;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.UpdateProfileManager;
import expression.sixdexp.com.expressionapp.utility.SettingMenuUtility;

/**
 * Created by Praveen on 6/30/2016.
 */
public class ProfileFragment extends Fragment {


    SettingMenuUtility settingMenuUtility;
    Context mContext;
    View view;
    TextView companyname_view, departmentname_view, roleprofilename_view, totalexp_view,username;
    CircularImageView userimg;
    TextView designation, location, myhobby, quotetxt;
    ImageView editimg;
    ProgressDialog progressDialog;

    //edit params
    String user_name_edit, user_location_edit, user_role_edit, user_hobby_edit, user_quote_edit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profileview, container, false);

        companyname_view = (TextView) view.findViewById(R.id.companyname);
        departmentname_view = (TextView) view.findViewById(R.id.departmentname);
        roleprofilename_view = (TextView) view.findViewById(R.id.roleprofilename);
        totalexp_view = (TextView) view.findViewById(R.id.totalexp);
        username = (TextView) view.findViewById(R.id.username);
        designation = (TextView) view.findViewById(R.id.designation);
        location = (TextView) view.findViewById(R.id.location);
        myhobby = (TextView) view.findViewById(R.id.myhobby);
        quotetxt = (TextView) view.findViewById(R.id.quotetxt);
        userimg = (CircularImageView) view.findViewById(R.id.userimg);
        editimg = (ImageView) view.findViewById(R.id.editimg);

        UserLoginInfo userLoginInfo = new LoginManager(mContext).getUserInfo().get(0);

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


        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingMenuUtility = new SettingMenuUtility(mContext, null, 0, userimg);
            }
        });


        editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mContext,R.style.AppTheme);
                final Window window = dialog.getWindow();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.editprofiledialog);
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window.setWindowAnimations(R.style.DialogAnimation);
                final TextView nametxt = (TextView) window.findViewById(R.id.nametxt);
                final TextView designationtxt = (TextView) window.findViewById(R.id.designationtxt);
                final TextView locationtxt = (TextView) window.findViewById(R.id.locationtxt);
                final TextView hobbytxt = (TextView) window.findViewById(R.id.hobbytxt);
                final TextView roletxt = (TextView) window.findViewById(R.id.roletxt);
                final TextView quote = (TextView) window.findViewById(R.id.quote);
                final ImageView cancel = (ImageView) window.findViewById(R.id.cancel);
                final TextView submit = (TextView) window.findViewById(R.id.submit);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v != null) {
                            InputMethodManager imm = (InputMethodManager)((HostActivty)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                        }

                        dialog.dismiss();
                    }
                });

                UserLoginInfo userLoginInfo = new LoginManager(mContext).getUserInfo().get(0);
                String user_name=userLoginInfo.getName();
                String user_location=userLoginInfo.getLocation();
                String user_role=userLoginInfo.getRole();
                String user_hobby=userLoginInfo.getHobbies();
                String user_quote=userLoginInfo.getQuote();

                nametxt.setText(user_name);
                locationtxt.setText(user_location);
                roletxt.setText(user_role);
                hobbytxt.setText(user_hobby);
                quote.setText(user_quote);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_name_edit = nametxt.getText().toString();
                        user_location_edit = locationtxt.getText().toString();
                        user_role_edit = roletxt.getText().toString();
                        user_hobby_edit = hobbytxt.getText().toString();
                        user_quote_edit = quote.getText().toString();

                        if (user_name_edit != null && !user_name_edit.equalsIgnoreCase("")) {


                            if (user_role_edit != null && !user_role_edit.equalsIgnoreCase("")) {
                                if (user_hobby_edit != null && !user_hobby_edit.equalsIgnoreCase("")) {
                                    if (user_quote_edit != null && !user_quote_edit.equalsIgnoreCase("")) {
                                        dialog.dismiss();
                                        new UpdateProfile().execute();

                                    } else {
                                        Toast.makeText(mContext, "Plae enter Quote", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(mContext, "Plae enter Hobby", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(mContext, "Plae enter Role", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(mContext, "Plae enter Name", Toast.LENGTH_LONG).show();
                        }


                    }
                });


                dialog.show();

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        settingMenuUtility.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
       /* AnimatorSet set = new AnimatorSet();
        set.playTogether(
                //ObjectAnimator.ofFloat(userimg, "rotationX", 0, 360),
                //ObjectAnimator.ofFloat(userimg, "rotationX", 360, 0)
                //ObjectAnimator.ofFloat(userimg, "rotationY", 0, 180),
                //ObjectAnimator.ofFloat(userimg, "rotation", 0, 360),
                //ObjectAnimator.ofFloat(userimg, "translationX", 0, 90),
               // ObjectAnimator.ofFloat(userimg, "translationY", 0, 90),
                //ObjectAnimator.ofFloat(userimg, "scaleX", 1, 1.5f),
               // ObjectAnimator.ofFloat(userimg, "scaleY", 1, 0.5f),
                 //ObjectAnimator.ofFloat(userimg, "alpha", 1, 0.25f, 1)


        );
        set.setDuration(5 * 1000).start();*/


        //View animating its own background to go from red to blue and then back again forever

       /* ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", *//*Red*//*0xFFFF8080, *//*Blue*//*0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();*/


        //You can also use a compatibility version of ViewPropertyAnimator which allows animating views in a much more declarative manner
        //Note: in order to use the ViewPropertyAnimator like this add the following import:
//  import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
        //animate(view).setDuration(2000).rotationYBy(720).x(100).y(100);
    }

    public class UpdateProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
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
                responseString = new UpdateProfileManager(mContext).callUpdateProfile(user_name_edit, user_role_edit,
                        user_hobby_edit, user_quote_edit);
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
            if (responseString.equals("100")) {
                username.setText(user_name_edit);
                roleprofilename_view.setText(user_role_edit);
                myhobby.setText(user_hobby_edit);
                quotetxt.setText(user_quote_edit);

                ((HostActivty)getActivity()).initnavigationdrawer();
                Toast.makeText(mContext, "Profile Updated.", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }


}
