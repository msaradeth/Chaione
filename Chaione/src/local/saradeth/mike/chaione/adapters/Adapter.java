package local.saradeth.mike.chaione.adapters;

import java.util.List;

import local.saradeth.mike.chaione.R;
import local.saradeth.mike.chaione.core.Post;
import local.saradeth.mike.chaione.utils.ImageCache;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Generic Adapter to display images on a list. The layout for each row is given
 * by parameter so can be reused in different list with different layout if the
 * view's ID is the same.
 * 
 * @author Android Developer
 * @since 15-12-2014
 * @version 1.1
 */
public class Adapter extends ArrayAdapter<Post> {

	/** Row layout ID **/
	private int resource;
	/**
	 * ImageCache to load image from memory cache if exist or load image and
	 * cache it if not
	 **/
	private ImageCache imageCache;
	/** List with all the images URLs **/
	private List<Post> alImageUrl;
	/** LayoutInflater **/
	private LayoutInflater mInflater;

	
	public static class ViewHolder {
		public ImageView imageView;
		public String urlString;
		public Bitmap bitMap;		
		public String create_at=null;	//created date-time
		public String imageUrl=null;	//avatar's url
		public TextView userName;	//poster's name
		public TextView text;		//poster's text				
		public int position;

		public ViewHolder() {
		}

	}

	/** 
	 * Constructor.
	 * 
	 * @param context Application context
	 * @param resource Row layout ID
	 * @param alImageUrl String List with the image's URLs
	 */
	public Adapter(Context context, int resource, List<Post> alImageUrl) {
		super(context, resource, alImageUrl);

		this.resource = resource;
		this.alImageUrl = alImageUrl;
		this.imageCache = ImageCache.getInstance();
		
		mInflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();

		//If the view has not been inflated before
		if (convertView == null) {	
			convertView = mInflater.inflate(resource, parent, false);
			// Bind View to data and save holder
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_view1);
			holder.userName = (TextView) convertView.findViewById(R.id.user_name);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			
			convertView.setTag(holder);
		} else { 
			//Get the ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}

		// Save data to holder to draw it via ImageCache
		Post post = alImageUrl.get(position);
		holder.position = position;		
		holder.create_at = post.getCreate_at();
		holder.urlString = post.getUrlString();
		
		//Display data to view
		holder.userName.setText(post.getUserName());
		holder.text.setText(post.getText());		
		imageCache.drawImage(holder);

		return convertView;
	}
}
