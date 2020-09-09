package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import webCrawler.RecentActivity;
import webCrawler.WebCrawler;

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
	Label activityName1;
	@FXML
	Tab imageType11;
	@FXML
	ImageView image11;
	@FXML
	Label detailedType1;
	@FXML
	TextArea description1;
	@FXML
	TabPane image1TabPane;
	@FXML
	AnchorPane recentActivityAnchorPane1;

	void setRecentActivity1() {
		RecentActivity activity = webCrawler.getRecentActivity(0);

		activityName1.setText(activity.getName());
		startDateLocal1.setText(activity.getStartDateLocal());
		activityNameDate1.setText(activity.getName() + " | " + activity.getStartDateLocal());

		detailedType1.setText(activity.getDetailedType());
		detailedTypeImg1.setImage(new Image(findDetailedTypeImage(activity.getDetailedType())));

		movingTime1.setText(activity.getMovingTime());
		distance1.setText(activity.getDistance());
		elevation1.setText(activity.getElevation());

		description1.setText(activity.getDescription());

		int imageSize = activity.getImages().size();
		if (imageSize == 0) {
			image11.setImage(new Image("file:resources/noImage.png"));
		} else {
			image11.setImage(new Image(activity.getImages().get(0).getDefaultSrc()));

			if (imageSize > 1) {
				for (int i = 1; i < imageSize; i++) {
					ImageView imageView = new ImageView();
					imageView.setImage(new Image(activity.getImages().get(i).getDefaultSrc()));
					imageView.setFitWidth(image11.getFitWidth());
					imageView.setFitHeight(image11.getFitHeight());
					imageView.setPreserveRatio(true);

					Tab tab = new Tab();
					tab.setContent(imageView);
					tab.setText(activity.getImages().get(i).getType());
					tab.setClosable(false);
					image1TabPane.getTabs().add(tab);
				}
			}
		}
	}

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
	Label activityName2;
	@FXML
	Tab imageType21;
	@FXML
	ImageView image21;
	@FXML
	Label detailedType2;
	@FXML
	TextArea description2;
	@FXML
	TabPane image2TabPane;
	@FXML
	AnchorPane recentActivityAnchorPane2;

	void setRecentActivity2() {
		RecentActivity activity = webCrawler.getRecentActivity(1);

		activityName2.setText(activity.getName());
		startDateLocal2.setText(activity.getStartDateLocal());
		activityNameDate2.setText(activity.getName() + " | " + activity.getStartDateLocal());

		detailedType2.setText(activity.getDetailedType());
		detailedTypeImg2.setImage(new Image(findDetailedTypeImage(activity.getDetailedType())));

		movingTime2.setText(activity.getMovingTime());
		distance2.setText(activity.getDistance());
		elevation2.setText(activity.getElevation());

		description2.setText(activity.getDescription());

		int imageSize = activity.getImages().size();
		if (imageSize == 0) {
			image21.setImage(new Image("file:resources/noImage.png"));
		} else {
			image21.setImage(new Image(activity.getImages().get(0).getDefaultSrc()));

			if (imageSize > 1) {
				for (int i = 1; i < imageSize; i++) {
					ImageView imageView = new ImageView();
					imageView.setImage(new Image(activity.getImages().get(i).getDefaultSrc()));
					imageView.setFitHeight(image21.getFitHeight());
					imageView.setFitWidth(image21.getFitWidth());

					Tab tab = new Tab();
					tab.setContent(imageView);
					tab.setText(activity.getImages().get(i).getType());
					image2TabPane.getTabs().add(tab);
				}
			}
		}
	}

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
	Label activityName3;
	@FXML
	Tab imageType31;
	@FXML
	ImageView image31;
	@FXML
	Label detailedType3;
	@FXML
	TextArea description3;
	@FXML
	TabPane image3TabPane;
	@FXML
	AnchorPane recentActivityAnchorPane3;

	void setRecentActivity3() {
		RecentActivity activity = webCrawler.getRecentActivity(2);

		activityName3.setText(activity.getName());
		startDateLocal3.setText(activity.getStartDateLocal());
		activityNameDate3.setText(activity.getName() + " | " + activity.getStartDateLocal());

		detailedType3.setText(activity.getDetailedType());
		detailedTypeImg3.setImage(new Image(findDetailedTypeImage(activity.getDetailedType())));

		movingTime3.setText(activity.getMovingTime());
		distance3.setText(activity.getDistance());
		elevation3.setText(activity.getElevation());

		description3.setText(activity.getDescription());

		int imageSize = activity.getImages().size();
		if (imageSize == 0) {
			image31.setImage(new Image("file:resources/noImage.png"));
		} else {
			image31.setImage(new Image(activity.getImages().get(0).getDefaultSrc()));

			if (imageSize > 1) {
				for (int i = 1; i < imageSize; i++) {
					ImageView imageView = new ImageView();
					imageView.setImage(new Image(activity.getImages().get(i).getDefaultSrc()));
					imageView.setFitHeight(image31.getFitHeight());
					imageView.setFitWidth(image31.getFitWidth());

					Tab tab = new Tab();
					tab.setContent(imageView);
					tab.setText(activity.getImages().get(i).getType());
					image3TabPane.getTabs().add(tab);
				}
			}
		}
	}

	@FXML
	TabPane recentActivityTabPane;
	@FXML
	Pane pane;

	private ImageView noDataImg;

	private String findDetailedTypeImage(String detailedType) {
		String path = "file:resources/";

		Map<String, String> map = new HashMap<String, String>();
		map.put("Ride", "bicycle.png");
		map.put("VirtualRide", "bicycle.png");
		map.put("Run", "run.png");
		map.put("VirtualRun", "run.png");
		map.put("Swim", "swim.png");
		map.put("Walk", "walk.png");
		map.put("WeightTraining", "weighttraining.png");
		map.put("Workout", "workout.png");
		map.put("Yoga", "yoga.png");

		if (map.containsKey(detailedType) == false)
			return path + "no.png";
		else
			return path + map.get(detailedType);
	}

	private void showRecentlyData() {
		int activityCount = webCrawler.getRecentActivities().size();
		if (activityCount == 0) {
			noDataImg.setVisible(true);
			recentActivityTabPane.setVisible(false);
		} else {
			noDataImg.setVisible(false);
			recentActivityTabPane.setVisible(true);
			
			if (activityCount >= 1) {
				// RecentActivity 1
				setRecentActivity1();
			}

			if (activityCount >= 2) {
				// RecentActivity 2
				setRecentActivity2();
			}

			if (activityCount == 3) {
				// RecentActivity 3
				setRecentActivity3();
			}

			if (activityCount <= 2) {
				recentActivityTabPane.getTabs().remove(activityNameDate3);
			}
			if (activityCount == 1) {
				recentActivityTabPane.getTabs().remove(activityNameDate2);
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData("https://www.strava.com/athletes/51315032");
		showData();

		createNoDataImg();
	}

	private void createNoDataImg() {
		noDataImg = new ImageView();
		noDataImg.setImage(new Image("file:resources/noData.png"));
		noDataImg.setLayoutX(recentActivityTabPane.getLayoutX() + 150);
		noDataImg.setLayoutY(recentActivityTabPane.getLayoutY());
		noDataImg.setFitWidth(recentActivityTabPane.getPrefWidth());
		noDataImg.setFitHeight(recentActivityTabPane.getPrefHeight());
		noDataImg.setPreserveRatio(true);
		pane.getChildren().add(noDataImg);
		noDataImg.setVisible(false);
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
