package com.example.ashanotepad.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;

import com.example.ashanotepad.util.*;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

	private String jsonData;
	private String[] tabList;
	//private ArrayList<String> tabList = new ArrayList<String>();

	private int current_main_tab = 0;
	private String current_mydeals_tab = "saved";
	private String current_mydeals_action_date = "";

	private HashMap<String, String> deals_redeemable = new HashMap<String, String>();


	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}






	public String getJsonData(){
		return jsonData;
	}

	public void setJsonData(String jsonData){
		this.jsonData = jsonData;
	}



	public void addTabCreated(String tabName, int index)
	{
		tabList[index] = tabName;
	}


	public boolean getTabCreated(String tabName)
	{
		return Arrays.asList(tabList).contains(tabName);
	}



	public int getCurrent_main_tab (){ return this.current_main_tab; }

	public void setCurrent_main_tab (int current_main_tab){ this.current_main_tab = current_main_tab; }


	public String getCurrent_mydeals_tab (){ return this.current_mydeals_tab; }

	public void setCurrent_mydeals_tab (String current_mydeals_tab){ this.current_mydeals_tab = current_mydeals_tab; }


	public String getCurrent_mydeals_action_date (){ return this.current_mydeals_action_date; }

	public void setCurrent_mydeals_action_date (String current_mydeals_action_date){ this.current_mydeals_action_date = current_mydeals_action_date; }





    //public ArrayList<String> get_deals_redeemable (){ return this.deals_redeemable; }
    public HashMap<String, String> get_deals_redeemable (){ return this.deals_redeemable; }

    public void set_deals_redeemable (String deal_id){ this.deals_redeemable.put(deal_id, deal_id); }

}