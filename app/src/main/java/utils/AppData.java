package utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import expression.sixdexp.com.expressionapp.SplashActivity;


public class AppData {
	//hello
	public static AppData smAppData;
	public static AppData getSharedAppData(){
		if (smAppData == null){
			smAppData = new AppData();
		}
		return smAppData;
	}
	private Map<String,Object> mSelectedItems;
	public AppData() {
		mSelectedItems = new HashMap<String, Object>();
	}
	public Map<String,Object> getSelectedContactItems() {
		return mSelectedItems;
	}

	public void setSelectedContactItems(Map<String,Object> aSelectedItems) {
		mSelectedItems.clear();
		mSelectedItems.putAll(aSelectedItems);
	}



	public void saveDataInPreference(String aKey, String aValue,Context context )
	{
		final SharedPreferences prefs = context.getSharedPreferences(SplashActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(aKey, aValue);
		editor.commit();
	}


	public String getSavedPreference(String aKey,Context context)
	{
		String value;
		final SharedPreferences prefs =  context.getSharedPreferences(
				SplashActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		value = (prefs.getString(aKey, ""));
		return value;
	}

}