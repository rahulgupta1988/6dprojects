package expression.sixdexp.com.expressionapp.utility;

import android.graphics.Bitmap;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import db.CelebrationMoments;
import db.DaoSession;
import db.GPWALLPOST;
import db.GetMorePost;
import db.GetXpresswayPost;
import db.RecentActivity;
import db.XPGPWALLPOST;

/**
 * Created by Praveen on 7/4/2016.
 */
public class Constant {

    // server key--> AIzaSyBUr2TRaXoitZKPESfBwf-9n4dE2fkS7uY


 public static Bitmap shareBitmap=null;
 public static String   web_app_version="0";

 // xpress last selected item postion
 public static int  lastposition=0;

 // home last selected item postion
 public static int  lastposition_home=0;
 public static int lastposition_home_wall=0;

       //public static String BASEURL="http://192.168.0.61/xpmob/";  //local machine
      //public static String BASEURL="http://newxpress.6degreesit.com/xpmob/";  //QA
      public static String BASEURL="https://webapps2.tatapower.com/mob_api/"; // Tata Server
     //public static String BASEURL="https://xpressions.tatapower.com/mob_api/"; // Tata Live


    public static String version="0.0";

    public static String notificationId = "";
    public static String GCMSenderId = "556141902369";//"360239548342";
    public static final boolean DEV_MODE = true;
    public static final boolean QA_MODE = false;



    public  static int today=0;
    public static int lastpostofPOST;
    // Google Console APIs developer key
    // Replace this key with your's
    public static final String DEVELOPER_KEY = "AIzaSyABYoczeHg4XABx_jMRfv-CqmA2YMsIY4A";

    public static List<CelebrationMoments> singleEvent=new ArrayList<CelebrationMoments>();
    public static List<GetMorePost> singlePostList=new ArrayList<GetMorePost>();
    public static List<RecentActivity> searchResultList=new ArrayList<>();

    public static List<GetXpresswayPost> singlePostList1=new ArrayList<GetXpresswayPost>();
    public static List<GPWALLPOST> singlePostList_gp=new ArrayList<GPWALLPOST>();
    public static List<XPGPWALLPOST> singlePostList_xp_gp=new ArrayList<XPGPWALLPOST>();



    public static String inactiveTime;
    public static DaoSession daoSession=null;
    public static int  sc_width=0;
    public static int  sc_height=0;
   // public  static List<AllUsers> allUserses=new ArrayList<AllUsers>();

    public static final String SHARED_PREFERENCE_Refresh_Home= "refreshhometop";
    public static final String SHARED_PREFERENCE_Refresh_ExpresssWay= "refreshexpresswaytop";
    public static final String SHARED_PREFERENCE_Visitors_Counter= "visitors";
    public static final String SHARED_PREFERENCE_USER_NAME= "username";
    public static final String SHARED_PREFERENCE_USER_PASSWORD= "password";
    public static final String SHARED_PREFERENCE_TOKEN_DATA= "token";

    // tag icon text

    public static TextView tag_txt=null;

}
