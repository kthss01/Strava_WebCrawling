package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class SampleController implements Initializable {
	@FXML
	Label label1;
	@FXML
	TextField textField1;

	@FXML
	protected void changeLabel(ActionEvent ev) {
		String str = textField1.getText();
		System.out.println(str);
		label1.setText(str);
	}

	@FXML
	FlowPane flowPane1;

	static int num = 0;
	
	@FXML
	protected void addButton(ActionEvent ev) {

		Button btn = new Button();
		btn.setText("버튼 삭제" + num++);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				flowPane1.getChildren()
					.remove(flowPane1.getChildren().indexOf(btn));
			}
		
		});
		
		flowPane1.getChildren().add(btn);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textField1.setText("Test Init");
	}
}
