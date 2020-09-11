package webCrawler;

import org.json.simple.JSONObject;

public class ChartData {
	/*
	 * chartData
	 * 
	 * hours
	 * month
	 * elev_gain
	 * miles
	 */
	
	JSONObject obj;
	
	public ChartData(JSONObject obj) {
		this.obj = obj;
	}
	
	public String getHours() { 
		if (obj.get("hours") == null) return "";
		return obj.get("hours").toString();
	}
	
	public String getMonth() {
		if (obj.get("month") == null) return "";
		return obj.get("month").toString();
	}
	
	public String getElevGain() {
		if (obj.get("elev_gain") == null) return "";
		return obj.get("elev_gain").toString();
	}
	
	public String getMiles() {
		if (obj.get("miles") == null) return "";
		return obj.get("miles").toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t chartData\n");
		
		sb.append("\t\t" +"hours : "); sb.append(getHours() + "\n");
		sb.append("\t\t" +"month : "); sb.append(getMonth() + "\n");
		sb.append("\t\t" +"elev_gain : "); sb.append(getElevGain() + "\n");
		sb.append("\t\t" +"miles : "); sb.append(getMiles() + "\n");
		
		return sb.toString();
	}
}