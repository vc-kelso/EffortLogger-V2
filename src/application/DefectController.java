package application;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class DefectController {
	//By Easton 
	@FXML 
	private Button Submit_btn;
	@FXML 
	private Button EL_btn;
	@FXML 
	private TextArea defect_description;
	@FXML 
	private ComboBox<String> Projects;
	@FXML 
	private ComboBox<String> group_name;
	@FXML 
	private ComboBox<String> group_num;
	@FXML
	private Button start_btn;
	@FXML
	private Label Char_text;
	@FXML 
	private Label error_txt;
	
	
	public void setProjects() {
		Projects.getItems().addAll("Project 1", "Project 2");
	}
	
	public void setName() {
		group_name.getItems().addAll("Engineer", "Developer", "Supervisor");
	}
	
	public void setNum() {
		group_num.getItems().addAll("1","2","3","4","5");
	}
	
	public void button() {
		setProjects();
		setName();
		setNum();
		
	}
	
	public void submit() {
		Boolean test = chkDefect();
		if(test) {
			System.out.print("its fully working");
		}
	}
	
	public Boolean chkDefect() {
		String defect = defect_description.getText();
		if(defect.isEmpty() == false) {
			//Creating needed variables
			String txt = defect;
			if(txt.length() > 500) {
				error_txt.setTextFill(Color.RED);
				error_txt.setText("Exceeded Character Limit of 1000");
				Char_text.setTextFill(Color.RED);
				return false;
			}else {
				if(Projects.getValue() ==null && group_num.getValue()==null && group_name.getValue()==null) {
					error_txt.setTextFill(Color.RED);
					error_txt.setText("One of the categories is left empty");
					return false;
				}
				DefectReporting report = new DefectReporting(defect, group_name.getValue(), group_num.getValue(), Projects.getValue());
				defect_description.setText("");
				error_txt.setText("Submitted!");
				return true;
			}
		}
		error_txt.setText("The defect descirption is left empty");
		return false;
	}

}
