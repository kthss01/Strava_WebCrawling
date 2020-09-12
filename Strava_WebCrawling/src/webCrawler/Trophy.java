package webCrawler;

import org.json.simple.JSONObject;

public class Trophy {
	/*
	 * Trophies
	 * 
	 * 	date
	 * 	logo_url
	 * 	name
	 * 	url
	 * 	teaser
	 */
	
	JSONObject obj;
	
	Trophy(JSONObject obj) {
		this.obj = obj;
	}
	
	public String getDate() {
		if (obj.get("date") == null) return "";
		return obj.get("date").toString();
	}
	
	public String getLogo_url() {
		if (obj.get("logo_url") == null) return "";
		return obj.get("logo_url").toString();
	}
	
	public String getName() {
		if (obj.get("name") == null) return "";
		return obj.get("name").toString();
	}
	
	public String getUrl() {
		if (obj.get("url") == null) return "";
		return obj.get("url").toString();
	}
	
	public String getTeaser() {
		if (obj.get("teaser") == null) return "";
		return obj.get("teaser").toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trophy\n");
		
		sb.append("\t" + "date : "); sb.append(getDate() + "\n");
		sb.append("\t" + "logo_url : "); sb.append(getLogo_url() + "\n");
		sb.append("\t" + "name : "); sb.append(getName() + "\n");
		sb.append("\t" + "url : "); sb.append(getUrl() + "\n");
		sb.append("\t" + "teaser : "); sb.append(getTeaser() + "\n");
		
		return sb.toString();
	}
}
