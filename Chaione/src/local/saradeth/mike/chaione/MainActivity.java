package local.saradeth.mike.chaione;

import java.util.ArrayList;

import local.saradeth.mike.chaione.adapters.Adapter;
import local.saradeth.mike.chaione.core.Post;
import local.saradeth.mike.chaione.utils.LoadImageUrl;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/*
 Programmer:  Mike Saradeth
 Date: 12/17/2014
 Data Source: https://alpha-api.app.net/stream/0/posts/stream/global

https://c1codechallenge.herokuapp.com/android.html
Android App.net Client
Create an App.net client that just lists the most recent posts from the public timeline.
-Posts should be rendered in a ListView or RecyclerView with the most recent at the top.
-Each row should contain the user’s avatar (bonus if the image is circular or corners are rounded)
-Each row should contain the poster’s name in bold
-Each row should contain the post text, and be variable height, depending on the text size
-Pull to refresh should be implemented to refresh the timeline
-The list should scroll quickly, without dropping frames on a Nexus 5
-The latest publicly released Android SDK should be used (bonus if the support library is used to implement Material Design)
-Third party libraries are allowed but not required
The timeline can be fetched here: https://alpha-api.app.net/stream/0/posts/stream/global.
Feel free to use any library you want. The code should reflect how you write classes, methods, etc. in a normal application.
 
 */
/**

https://c1codechallenge.herokuapp.com/android.html
Android App.net Client
Create an App.net client that just lists the most recent posts from the public timeline.
Posts should be rendered in a ListView or RecyclerView with the most recent at the top.
Each row should contain the user’s avatar (bonus if the image is circular or corners are rounded)
Each row should contain the poster’s name in bold
Each row should contain the post text, and be variable height, depending on the text size
Pull to refresh should be implemented to refresh the timeline
The list should scroll quickly, without dropping frames on a Nexus 5
The latest publicly released Android SDK should be used (bonus if the support library is used to implement Material Design)
Third party libraries are allowed but not required
The timeline can be fetched here: https://alpha-api.app.net/stream/0/posts/stream/global.
Feel free to use any library you want. The code should reflect how you write classes, methods, etc. in a normal application
 * 
 * @author Android Developer
 * @since 15-17-2014
 * @version 1.1
 */
public class MainActivity extends Activity {
	
	
	/** ArrayList where all the image's URLs will be stored **/
	private ArrayList<Post> alImageUrl = new ArrayList<Post>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_list_view);		
		this.getActionBar().hide(); 
		
		//To prevent errors or download again the URL list in case of screen rotations
		if(savedInstanceState != null){
			alImageUrl = (ArrayList<Post>) savedInstanceState.getSerializable("posts");
		}	
		
		
		//If there're not data on the ArrayList
		if(alImageUrl.size() == 0){
			
			// Load ImageUrl from URL
			LoadImageUrl loadImageUrl = new LoadImageUrl(this, this.alImageUrl);
			try {
				loadImageUrl.execute();
			} catch (Exception e) {
				loadImageUrl.cancel(true);
				alert("Problem loading from Instagram");
			}
		} else { // get ImageUrl from memory
			this.setListView();
		}
		
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("posts", alImageUrl);
		
	}


	/**
	 * Toast a given message
	 * 
	 * @param msg Message to be displayed 
	 */
	public void alert(String msg) {
		Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Setup and create list view after data is loaded
	 * 
	 * @param myImageUrl ArrayList with the image URLs
	 */
	public void setListView() {		

		// Create the adapter
		Adapter adapter = new Adapter(this, R.layout.image_list_row, alImageUrl);
		ListView lvImage = (ListView) findViewById(R.id.image_list_view);
		lvImage.setAdapter(adapter);

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}

}
