package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import webCrawler.RecentActivity;
import webCrawler.Trophy;
import webCrawler.WebCrawler;

public class StravaWebCrawlingController implements Initializable {

	final String urlBase = "https://www.strava.com/athletes/";

	WebCrawler webCrawler;
	Map<String, WebCrawler> bookMarks;

	@FXML
	Pane mainPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createTrophiesData();

		createNoDataImg();
		bookMarkFlowPane.getChildren().clear();

		bookMarks = new HashMap<>();
		readBookMarks();

		if (bookMarks.size() == 0)
			loadData("51315032");
		else
			loadData((String) bookMarks.keySet().toArray()[0]);

		showData();
	}

	@FXML
	TextField userQuery;

	@FXML
	public void searchUserBtn(ActionEvent event) {
		loadData(userQuery.getText());
		showData();
	}

	private void readBookMarks() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./resources/bookmark.txt"));
			while (true) {
				String userNumber = br.readLine();
				if (userNumber == null)
					break;
				WebCrawler bookMark = new WebCrawler(urlBase + userNumber);
				bookMark.setBookMarked(true);

				bookMarks.put(bookMark.getUserNumber(), bookMark);
				updateBookMarkImage(true, bookMark.getUserNumber());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void saveBookMarks() {
		try {
			PrintWriter pw = new PrintWriter("./resources/bookmark.txt");
			bookMarks.forEach((key, value) -> pw.println(value.getUserNumber()));
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@FXML
	ImageView bookMarkImage1;
	@FXML
	FlowPane bookMarkFlowPane;
	Map<String, ImageView> bookMarkImages;

	private void updateBookMarkImage(boolean isAdd, String userName) {
		ObservableList<Node> bookMarkList = bookMarkFlowPane.getChildren();

		if (isAdd == true) {
			ImageView imageView = new ImageView();
			imageView.setImage(new Image(bookMarks.get(userName).getAthlete().getAvatarUrl()));
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setFitWidth(100);
			imageView.setFitHeight(100);

			imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					loadData(userName);
					showData();

					event.consume();
				}

			});

			bookMarkList.add(imageView);
			bookMarkFlowPane.setMargin(imageView, new Insets(5, 0, 0, 5));
		} else {

			String avatarUrl = bookMarks.get(userName).getAthlete().getAvatarUrl();

			for (Node bookMark : bookMarkList) {
				ImageView bookMarkImageView = (ImageView) bookMark;
				
				if (bookMarkImageView.getImage().getUrl().equals(avatarUrl)) {
					if (webCrawler.getUserNumber() == userName) {
						webCrawler = null;
					}
					
					bookMarkList.remove(bookMark);
					break;
				}
			}
		}
	}

	Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setOnCloseRequest(event -> {
			event.consume();
			/*
			 * Alert closeConfiguration = new Alert(Alert.AlertType.CONFIRMATION);
			 * closeConfiguration.setTitle("Strava 선수 검색기");
			 * closeConfiguration.setHeaderText("프로그램 종료");
			 * closeConfiguration.setContentText("프로그램을 종료하시겠습니까?"); Optional<ButtonType>
			 * result = closeConfiguration.showAndWait(); if
			 * (result.get().equals(ButtonType.OK)) { stage.close(); System.exit(1); }
			 */

			Alert saveConfiguration = new Alert(Alert.AlertType.CONFIRMATION);
			saveConfiguration.setTitle("Strava 선수 검색기");
			saveConfiguration.setHeaderText("즐겨찾기 저장 & 종료");
			saveConfiguration.setContentText("즐겨찾기를 저장하고 종료하시겠습니까?");

			ButtonType buttonTypeSaveAndQuit = new ButtonType("저장하고 종료");
			ButtonType buttonTypeQuit = new ButtonType("종료");
			ButtonType buttonTypeCancel = new ButtonType("취소", ButtonData.CANCEL_CLOSE);

			saveConfiguration.getButtonTypes().setAll(buttonTypeSaveAndQuit, buttonTypeQuit, buttonTypeCancel);

			Optional<ButtonType> result = saveConfiguration.showAndWait();
			if (result.get() == buttonTypeSaveAndQuit) {
				saveBookMarks();
				stage.close();
				System.exit(1);
			} else if (result.get() == buttonTypeQuit) {
				stage.close();
				System.exit(1);
			} else {

			}
		});
	}

	@FXML
	CheckBox bookMarkCheckBox;

	@FXML
	public void setBookMark(ActionEvent event) {
		if (bookMarkCheckBox.isSelected()) {
//			System.out.println("add bookmark");
			webCrawler.setBookMarked(true);

			bookMarks.put(webCrawler.getUserNumber(), webCrawler);
			updateBookMarkImage(true, webCrawler.getUserNumber());
		} else {
//			System.out.println("delete bookmark");
			webCrawler.setBookMarked(false);

			// webCrawler가 날라갈 수 있음 (null이 될 수 있음 북마크 해제하다가)
			String userNumber = webCrawler.getUserNumber();
			
			updateBookMarkImage(false, userNumber);
			bookMarks.remove(userNumber);
			
			if(webCrawler == null) {
				// 다음 즐겨찾기가 있으면 그거 보여줌
				if(bookMarks.values().iterator().hasNext()) {
					webCrawler = bookMarks.values().iterator().next();
					showData();
				}
				else {
					openPane.setVisible(false);
					trophiesFlowPane.setVisible(false);
				}
			}
				
		}

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

	private void loadData(String userNumber) {
		if (bookMarks.containsKey(userNumber))
			webCrawler = bookMarks.get(userNumber);
		else {
			try {
				webCrawler = new WebCrawler(urlBase + userNumber);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	Pane openPane;

	@FXML
	Pane closedPane;

	private void showData() {
		bookMarkCheckBox.setSelected(webCrawler.isBookMarked());

		if (webCrawler.isOpened()) {
			openPane.setVisible(true);
			closedPane.setVisible(false);
			showBasicData();
			showRecentlyData();
			showTrophiesData();
		} else {
			openPane.setVisible(false);
			closedPane.setVisible(true);
		}
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

		image1TabPane.getTabs().clear();
		image1TabPane.getTabs().add(imageType11);

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

		image2TabPane.getTabs().clear();
		image2TabPane.getTabs().add(imageType21);

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
					tab.setClosable(false);
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

		image3TabPane.getTabs().clear();
		image3TabPane.getTabs().add(imageType31);

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
					tab.setClosable(false);
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
			if (closedPane.isVisible() == false)
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

	@FXML
	FlowPane trophiesFlowPane;

	@FXML
	Pane trophyPane1;

	@FXML
	ImageView trophyLogoImage1;

	@FXML
	Label trophyDate1;

	@FXML
	Label trophyName1;

	List<Pane> trophiesPane;

	private void createTrophiesData() {
		trophiesPane = new ArrayList<>();

		trophiesPane.add(trophyPane1);

		for (int i = 1; i < 4; i++) {
			Pane tropyPane = new Pane();
			ImageView trophyLogoImage = new ImageView();
			Label trophyDate = new Label();
			Label trophyName = new Label();

			trophyLogoImage.setImage(trophyLogoImage1.getImage());
			trophyLogoImage.setPreserveRatio(true);
			trophyLogoImage.setSmooth(true);
			trophyLogoImage.setFitHeight(trophyLogoImage1.getFitHeight());
			trophyLogoImage.setFitWidth(trophyLogoImage1.getFitWidth());
			trophyLogoImage.setLayoutX(trophyLogoImage1.getLayoutX());
			trophyLogoImage.setLayoutY(trophyLogoImage1.getLayoutY());

			trophyName.setText(trophyName1.getText());
			trophyName.setLayoutX(trophyName1.getLayoutX());
			trophyName.setLayoutY(trophyName1.getLayoutY());
			trophyName.setFont(trophyName1.getFont());
			trophyName.setPrefHeight(trophyName1.getPrefHeight());
			trophyName.setPrefWidth(trophyName1.getPrefWidth());
			trophyName.setAlignment(trophyName1.getAlignment());

			trophyDate.setText(trophyDate1.getText());
			trophyDate.setLayoutX(trophyDate1.getLayoutX());
			trophyDate.setLayoutY(trophyDate1.getLayoutY());
			trophyDate.setFont(trophyDate1.getFont());
			trophyDate.setPrefHeight(trophyDate1.getPrefHeight());
			trophyDate.setPrefWidth(trophyDate1.getPrefWidth());
			trophyDate.setAlignment(trophyDate1.getAlignment());

			tropyPane.getChildren().add(trophyLogoImage);
			tropyPane.getChildren().add(trophyDate);
			tropyPane.getChildren().add(trophyName);

			trophiesPane.add(tropyPane);
		}
	}

	private void showTrophiesData() {
		trophiesFlowPane.setVisible(true);
		trophiesFlowPane.getChildren().clear();

		int trophyCount = webCrawler.getTrophies().size();
		for (int i = 0; i < trophyCount; i++) {
			Pane trophyPane = trophiesPane.get(i);
			ImageView trophyLogoImage = (ImageView) trophyPane.getChildren().get(0);
			Label trophyDate = (Label) trophyPane.getChildren().get(1);
			Label trophyName = (Label) trophyPane.getChildren().get(2);

			Trophy trophy = webCrawler.getTrophy(i);

			trophyLogoImage.setImage(new Image(trophy.getLogo_url()));
			trophyDate.setText(trophy.getDate());
			trophyName.setText(trophy.getName());

			trophiesFlowPane.getChildren().add(trophyPane);
		}
	}
}
