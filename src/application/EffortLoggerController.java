package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class EffortLoggerController {
	@FXML
	private Button defect_btn;
	
	private Scene scene;
	private Stage stage;
	
	public void defect_button() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DefectConsole.fxml"));
		scene = new Scene(fxmlLoader.load());
		stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
