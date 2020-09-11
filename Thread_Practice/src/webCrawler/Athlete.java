package webCrawler;

import java.io.File;

import org.json.simple.JSONObject;

public class Athlete {

	/*
	 * athlete
	 * 
	 * avatarUrl 
	 * name 
	 * primarySport 
	 * location 
	 * followersCount 
	 * followingCount
	 */
	
	JSONObject obj;
	
	public Athlete(JSONObject obj) {
		this.obj = obj;
	}
	
	public String getAvatarUrl() {
		if (obj.get("avatarUrl") == null) return "";
		else if(obj.get("avatarUrl").toString().contains(".svg")) 
		{
			return "file:resources/avatar.png";
		}
		return obj.get("avatarUrl").toString();
	}
	
	public String getName() {
		if (obj.get("name") == null) return "";
		return obj.get("name").toString();
	}
	
	public String getPrimarySport() {
		if (obj.get("primarySport") == null) return "";
		return obj.get("primarySport").toString();
	}
	
	public String getLocation() {
		if (obj.get("location") == null) return "";
		return obj.get("location").toString();
	}
	
	public String getFollowersCount() {
		if (obj.get("followersCount") == null) return "";
		return obj.get("followersCount").toString();
	}
	
	public String getFollowingCount() {
		if (obj.get("followingCount") == null) return "";
		return obj.get("followingCount").toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Athlete\n");
		
		sb.append("\t" + "avatarUrl : "); sb.append(getAvatarUrl() + "\n");
		sb.append("\t" + "name : "); sb.append(getName() + "\n");
		sb.append("\t" + "primarySport : "); sb.append(getPrimarySport() + "\n");
		sb.append("\t" + "location : "); sb.append(getLocation() + "\n");
		sb.append("\t" + "followersCount : "); sb.append(getFollowersCount() + "\n");
		sb.append("\t" + "followingCount : "); sb.append(getFollowingCount() + "\n");
		
		return sb.toString();
	}
}
