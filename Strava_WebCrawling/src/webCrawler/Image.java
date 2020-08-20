package webCrawler;

import org.json.simple.JSONObject;

public class Image {
	/*
	 * images
	 * 
	 * defaultSrc 
	 * squareSrc
	 * caption
	 * type
	 * primary
	 */
	
	JSONObject obj;
	
	public Image(JSONObject obj) {
		this.obj = obj;
	}
	
	public String getDefaultSrc() {
		if (obj.get("defaultSrc") == null) return "";
		return obj.get("defaultSrc").toString();
	}
	
	public String getSquareSrc() {
		if (obj.get("squareSrc") == null) return "";
		return obj.get("squareSrc").toString();
	}
	
	public String getCaption() {
		if (obj.get("caption") == null) return "";
		return obj.get("caption").toString();
	}
	
	public String getType() {
		if (obj.get("type") == null) return "";
		return obj.get("type").toString();
	}
	
	public String getPrimary() {
		if (obj.get("primary") == null) return "";
		return obj.get("primary").toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t image\n");
		
		sb.append("\t\t" + "defaultSrc : "); sb.append(getDefaultSrc() + "\n");
		sb.append("\t\t" + "squareSrc : "); sb.append(getSquareSrc() + "\n");
		sb.append("\t\t" + "caption : "); sb.append(getCaption() + "\n");
		sb.append("\t\t" + "type : "); sb.append(getType() + "\n");
		sb.append("\t\t" + "primary : "); sb.append(getPrimary() + "\n");
		
		return sb.toString();
	}
}
