package webCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebCrawler {
	JSONObject obj;

	String userNumber;
	boolean isBookMarked;
	int bookMarkImageNum;

	Athlete athlete;
	Stats stats;
	List<RecentActivity> recentActivities;

	boolean isOpened;

	public WebCrawler(String url) throws IOException, ParseException {
		Document doc = Jsoup.connect(url).get();
		Elements contents = doc.select("div");
		String json = contents.attr("data-react-props");

		if (json.equals("")) {
			isOpened = false;
			return;
		}

		isOpened = true;

		JSONParser parser = new JSONParser();
		obj = (JSONObject) parser.parse(json);

		isBookMarked = false;
		userNumber = url.substring(url.indexOf("athletes/") + "athletes/".length());

		// 즐겨찾기시 화면 아래 flowPane에서의 list 인덱스
		bookMarkImageNum = -1;

		addData();
	}

	void addData() {

		/*
		 * 여기서 내가 필요한 데이터 -> 클래스화 해서 쉽게 꺼낼 수 있게
		 * 
		 * athlete - 스트라바 선수 정보 stats - 선수 스텟 recentActivities - 최근 활동 내역
		 */

		athlete = new Athlete((JSONObject) obj.get("athlete"));
		stats = new Stats((JSONObject) obj.get("stats"));
		recentActivities = new ArrayList<>();

		JSONArray arr = (JSONArray) obj.get("recentActivities");
		if (arr != null) {
			for (int i = 0; i < arr.size(); i++) {
				recentActivities.add(new RecentActivity((JSONObject) arr.get(i)));
			}
		}
	}

	public boolean isBookMarked() {
		return isBookMarked;
	}

	public void setBookMarked(boolean isBookMarked) {
		this.isBookMarked = isBookMarked;
	}

	public int getBookMarkImageNum() {
		return bookMarkImageNum;
	}

	public void setBookMarkImageNum(int bookMarkImageNum) {
		this.bookMarkImageNum = bookMarkImageNum;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public Stats getStats() {
		return stats;
	}

	public List<RecentActivity> getRecentActivities() {
		return recentActivities;
	}

	public RecentActivity getRecentActivity(int i) {
		return recentActivities.get(i);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (isOpened == false) {
			sb.append("Is Not Opened\n");
			return sb.toString();
		}

		sb.append(getAthlete());
		sb.append(getStats());
		for (RecentActivity recentActivity : recentActivities) {
			sb.append(recentActivity);
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException, ParseException {
//		System.out.println(new WebCrawler("https://www.strava.com/athletes/51315032"));
//		System.out.println(new WebCrawler("https://www.strava.com/athletes/7013156"));
//		System.out.println(new WebCrawler("https://www.strava.com/athletes/6013156"));
//		System.out.println(new WebCrawler("https://www.strava.com/athletes/4013156"));

		// 없는 거 테스트
		System.out.println(new WebCrawler("https://www.strava.com/athletes/10000"));

		// 검색을 위한 스레드 테스트
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < 100; i++) {
//			sb.append(new WebCrawler("https://www.strava.com/athletes/51315032"));
//			sb.append(i);
//		}
	}
}
