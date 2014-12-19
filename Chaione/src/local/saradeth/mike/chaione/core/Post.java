/**
 * 
 */
package local.saradeth.mike.chaione.core;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mike
 *
 */
public class Post implements Serializable{

	private String create_at=null;	//created date-time
	private String urlString=null;	//avatar's url
	public String getUrlString() {
		return urlString;
	}


	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}


	private String userName=null;	//poster's name
	private String text=null;		//poster's text
	
	
	/**
	 * 
	 */
	public Post() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getCreate_at() {
		return create_at;
	}


	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}




	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}



}
