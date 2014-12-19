package local.saradeth.mike.chaione.utils;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import local.saradeth.mike.chaione.MainActivity;
import local.saradeth.mike.chaione.core.Post;

/*
 Programmer:  Mike Saradeth
 Date: 12/10/2014
 */

/**
 * Download a list with the image URLs with the size pattern big, small, small
 *
 * @author Android Developer
 * @since 12/10/2014
 * @version 1.1
 */
public class LoadImageUrl extends AsyncTask<String, Integer, ArrayList<Post>> {

	/** Activity to access to UI **/
	private final Activity activity;
	/** Progress dialog to display the progress **/
	private ProgressDialog progDialog;
	/** ArrayList with the image URLs **/
	ArrayList<Post> alImageUrl = new ArrayList<Post>();

	/**
	 * Constructor
	 *
	 * @param activity Activity to access to UI
	 * @param alImageUrl ArrayList where image URLs will be stored
	 */
	public LoadImageUrl(Activity activity, ArrayList<Post> alImageUrl) {
		this.activity = activity;
		this.alImageUrl = alImageUrl;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Create and display a progress dialog to inform the user about the
		// download
		String title = "Load Image URL";
		String message = "Loading image URL from Instagram ...";
		progDialog = ProgressDialog.show(this.activity, title, message, true, false);

	}

	@Override
	protected ArrayList<Post> doInBackground(String... params) {
		String urlString = "https://alpha-api.app.net/stream/0/posts/stream/global";		
		String jsonString;

		try {
			// Create HttpClient and make GET request to the given URL
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(urlString));

			// If response not OK (Code 200)
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.d("responseCode", "responseCode != HttpStatus.SC_OK "
						+ httpResponse.getStatusLine().getStatusCode());
				return alImageUrl;
			} else {
				// Get the result on a String to parse as JSON object
				jsonString = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (IOException ioException) {
			Log.d("ioException", "ioException EntityUtils.toString(httpResponse.getEntity() "
					+ ioException.toString());
			return alImageUrl;
		}

		
		// Parse JSON String and load array list
		JSONArray jsonArray=null;
		try {
			JSONObject jsonObject;
			alImageUrl.clear();

			JSONObject jsonObjects = new JSONObject(jsonString);
			jsonArray = jsonObjects.getJSONArray("data");
			
			// Get the URL from JSON objects and store it on
			// the ArrayList
			for (int ii = 0; ii < jsonArray.length(); ii++) {
				jsonObject = jsonArray.getJSONObject(ii);
				Post post = new Post();
				
				post.setCreate_at(jsonObject.getString("created_at"));
				post.setUrlString(jsonObject.getJSONObject("user").getJSONObject("avatar_image").getString("url"));	
				post.setUserName(jsonObject.getJSONObject("user").getString("username"));	
				post.setText(jsonObject.getJSONObject("user").getJSONObject("description").getString("text"));
				
				Log.d("avatar_image", ii + post.getUrlString());
				Log.d("create_at", post.getCreate_at());
				Log.d("username", post.getUserName());
				Log.d("text", post.getText());						
				  
				
				alImageUrl.add(post);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return alImageUrl;
	}

	@Override
	protected void onPostExecute(ArrayList<Post> result) {

		// If the list is empty show a message
		if (result.isEmpty()) {
			((MainActivity) activity).alert("Impossible download picture from Instagram. Please try it again later.");
		}

		// Dismiss the dialog and update the list
		progDialog.dismiss();
		((MainActivity) activity).setListView();
	}

}
