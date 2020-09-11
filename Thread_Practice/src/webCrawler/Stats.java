package webCrawler;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Stats {
	/*
	 * stats
	 * 
	 * chartData - JSONArray, 달별 기록 monthlyTime monthlyDistance
	 */

	JSONObject obj;
	List<ChartData> chartDatas;

	public Stats(JSONObject obj) {
		this.obj = obj;
		chartDatas = new ArrayList<>();

		JSONArray arr = (JSONArray) obj.get("chartData");
		if (arr != null) {
			for (int i = 0; i < arr.size(); i++) {
				chartDatas.add(new ChartData((JSONObject) arr.get(i)));
			}
		}
	}

	public String getMonthlyTime() {
		if (obj.get("monthlyTime") == null)
			return "";
		String str = obj.get("monthlyTime").toString();
		if (str.contains("<abbr class='unit' title='second'>s</abbr>"))
			str = "0";
		return str;
	}

	public String getMonthlyDistance() {
		if (obj.get("monthlyDistance") == null)
			return "";
		return obj.get("monthlyDistance").toString();
	}

	public List<ChartData> getChartDatas() {
		return chartDatas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stats\n");

		sb.append("\t" + "monthlyTime : ");
		sb.append(getMonthlyTime() + "\n");
		sb.append("\t" + "monthlyDistance : ");
		sb.append(getMonthlyDistance() + "\n");

		for (ChartData chartData : chartDatas) {
			sb.append(chartData);
		}

		return sb.toString();
	}
}
