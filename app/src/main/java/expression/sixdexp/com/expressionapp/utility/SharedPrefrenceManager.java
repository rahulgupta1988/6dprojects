package expression.sixdexp.com.expressionapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Praveen on 7/4/2016.
 */
public class SharedPrefrenceManager {


    private static final String SECURITY_SP = "xpression-tata-app-sp";
    public static void putToken(final Context context,String token) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.SESSION_TOKEN, token);
        editor.commit();
    }


    public static String getToken(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.SESSION_TOKEN, null);
        return sessiontoken;
    }

    public static void deleteToken(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Keys.SESSION_TOKEN);
        editor.commit();
    }

    public static void putUsername(final Context context,String username) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.USER_NAME, username);
        editor.commit();
    }

    public static void putPassword(final Context context,String password) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.PASSWORD, password);
        editor.commit();
    }


    public static String getUsername(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.USER_NAME, null);
        return sessiontoken;
    }

    public static String getPassword(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.PASSWORD, null);
        return sessiontoken;
    }

    public static void putUserFNLN(final Context context,String userFNLN) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.USER_FN_LN, userFNLN);
        editor.commit();
    }
    public static String getUserFNLN(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.USER_FN_LN, "");
        return sessiontoken;
    }


    public static void putUserID(final Context context,String userID) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.USER_ID, userID);
        editor.commit();
    }
    public static String getUserID(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.USER_ID, "");
        return sessiontoken;
    }

    public static void putSelectedTab(final Context context,String selectedTab) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.SELECTED_TAB, selectedTab);
        editor.commit();
    }
    public static String getSelectedTab(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.SELECTED_TAB, "");
        return sessiontoken;
    }


    public static void putRecentSearch(final Context context,String searchName) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);

        Set<String> search_set=sp.getStringSet(Keys.RECENT_SEARCH_SET, new LinkedHashSet<String>());
        search_set.add(searchName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(Keys.RECENT_SEARCH_SET, search_set);
        editor.commit();
    }

    public static Set<String> getRecentSearch(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        Set<String> search_set=sp.getStringSet(Keys.RECENT_SEARCH_SET, null);
        return search_set;
    }

    public static void clearRecentSearch(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Keys.RECENT_SEARCH_SET);
        editor.commit();
    }



    public static void putLoginID(final Context context,String loginID) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.LOGIN_ID, loginID);
        editor.commit();
    }
    public static String getLoginID(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.LOGIN_ID, "");
        return sessiontoken;
    }


    public static void putLoggedColorNo(final Context context,String number) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.LOGGED_COLOR_NO, number);
        editor.commit();
    }
    public static String getLoggedColorNo(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.LOGGED_COLOR_NO,"1");
        return sessiontoken;
    }



    public static void putVisitorCount(final Context context,String number) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.VISITER_COUNTER, number);
        editor.commit();
    }
    public static String getVisitorCount(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.VISITER_COUNTER,"0");
        return sessiontoken;
    }

    public static void putWebVisitorCount(final Context context,String number) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.WEB_VISITER_COUNTER, number);
        editor.commit();
    }
    public static String getWebVisitorCount(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.WEB_VISITER_COUNTER,"0");
        return sessiontoken;
    }



    public static void putGPVisitorCount(final Context context,String number) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Keys.VISITER_COUNTER, number);
        editor.commit();
    }
    public static String getGPVisitorCount(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String sessiontoken=sp.getString(Keys.VISITER_COUNTER,"0");
        return sessiontoken;
    }

}
