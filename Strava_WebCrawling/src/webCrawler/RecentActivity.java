package webCrawler;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RecentActivity {
	/*
	 * recentActivities - 여러 개 존재
	 * 
	 * elevation
	 * images - JSONArray 
	 * distance
	 * hasGps - X
	 * startDateLocal
	 * isManual - X
	 * name
	 * detailedType
	 * movingTime
	 * id - X
	 * type 
	 */
	
	JSONObject obj;
	List<Image> images;

	public RecentActivity(JSONObject obj) {
		this.obj = obj;
		images = new ArrayList<>();
		
		JSONArray arr = (JSONArray) obj.get("images");
		for (int i = 0; i < arr.size(); i++) {
			images.add(new Image((JSONObject) arr.get(i)));
		}
	}
	
	public String getElevation() {
		if (obj.get("elevation") == null) return "";
		return obj.get("elevation").toString();
	}
	
	public String getDistance() {
		if (obj.get("distance") == null) return "";
		return obj.get("distance").toString();
	}
	
	public String getStartDateLocal() {
		if (obj.get("startDateLocal") == null) return "";
		return obj.get("startDateLocal").toString();
	}
	
	public String getName() {
		if (obj.get("name") == null) return "";
		return obj.get("name").toString();
	}
	
	public String getDetailedType() {
		if (obj.get("detailedType") == null) return "";
		return obj.get("detailedType").toString();
	}
	
	public String getType() {
		if (obj.get("type") == null) return "";
		return obj.get("type").toString();
	}
	
	public String getDescription() {
		if (obj.get("description") == null) return "";
		return obj.get("description").toString();
	}
	
	public String getMovingTime() {
		if (obj.get("movingTime") == null) return "";
		return obj.get("movingTime").toString();
	}
	
	public List<Image> getImages() {
		return images;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RecentActivity\n");
		
		sb.append("\t" +"elevation : "); sb.append(getElevation() + "\n");
		sb.append("\t" +"distance : "); sb.append(getDistance() + "\n");
		sb.append("\t" +"startDateLocal : "); sb.append(getStartDateLocal() + "\n");
		sb.append("\t" +"name : "); sb.append(getName() + "\n");
		sb.append("\t" +"detailedType : "); sb.append(getDetailedType() + "\n");
		sb.append("\t" +"type : "); sb.append(getType() + "\n");
		sb.append("\t" +"description : "); sb.append(getDescription() + "\n");
		sb.append("\t" +"movingTime: "); sb.append(getMovingTime() + "\n");
		
		for (Image image : images) {
			sb.append(image);
		}
		
		return sb.toString();
	}
}