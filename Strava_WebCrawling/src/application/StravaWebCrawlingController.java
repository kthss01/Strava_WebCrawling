package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import javafx.fxml.Initializable;
import webCrawler.WebCrawler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class StravaWebCrawlingController implements Initializable {

	final String urlBase = "https://www.strava.com/athletes/";

	WebCrawler webCrawler;

	@FXML
	TextField userQuery;

	@FXML
	public void searchUserBtn(ActionEvent event) {
		loadData(urlBase + userQuery.getText());
		showData();
	}

	@FXML
	ImageView profile;
	@FXML
	Label name;
	@FXML
	Label primarySport;
	@FXML
	Label location;
	@FXML
	Label followersCount;
	@FXML
	Label followingCount;
	@FXML
	Label monthlyDistance;
	@FXML
	Label monthlyTime;

	private void showBasicData() {
		profile.setImage(new Image(webCrawler.getAthlete().getAvatarUrl()));
		name.setText(webCrawler.getAthlete().getName());
		primarySport.setText(webCrawler.getAthlete().getPrimarySport());
		location.setText(webCrawler.getAthlete().getLocation());
		followersCount.setText(webCrawler.getAthlete().getFollowersCount());
		followingCount.setText(webCrawler.getAthlete().getFollowingCount());
		monthlyDistance.setText(webCrawler.getStats().getMonthlyDistance());
		monthlyTime.setText(webCrawler.getStats().getMonthlyTime());
	}

	@FXML
	Tab activityNameDate1;
	@FXML
	ImageView detailedTypeImg1;
	@FXML
	Label movingTime1;
	@FXML
	Label distance1;
	@FXML
	Label elevation1;
	@FXML
	Label startDateLocal1;
	@FXML
	Label activitiyName1;
	@FXML
	Tab imageType11;
	@FXML
	ImageView image11;
	@FXML
	Label detailedType1;
	@FXML
	TextArea description1;

	@FXML
	Tab activityNameDate2;
	@FXML
	ImageView detailedTypeImg2;
	@FXML
	Label movingTime2;
	@FXML
	Label distance2;
	@FXML
	Label elevation2;
	@FXML
	Label startDateLocal2;
	@FXML
	Label activitiyName2;
	@FXML
	Tab imageType21;
	@FXML
	ImageView image21;
	@FXML
	Label detailedType2;
	@FXML
	TextArea description2;

	@FXML
	Tab activityNameDate3;
	@FXML
	ImageView detailedTypeImg3;
	@FXML
	Label movingTime3;
	@FXML
	Label distance3;
	@FXML
	Label elevation3;
	@FXML
	Label startDateLocal3;
	@FXML
	Label activitiyName3;
	@FXML
	Tab imageType31;
	@FXML
	ImageView image31;
	@FXML
	Label detailedType3;
	@FXML
	TextArea description3;

	private void showRecentlyData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		loadData("https://www.strava.com/athletes/51315032");
		showData();
	}

	private void loadData(String url) {
		try {
			webCrawler = new WebCrawler(url);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private void showData() {
		showBasicData();
		showRecentlyData();
	}

}
