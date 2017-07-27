package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Winner implements Initializable {
	@FXML Pane win,avatar;
	@FXML Label name;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		avatar.setBackground(Control.Winner);
		win.setBackground(Main.winnerBackground);
	}
	public void back(Event event){
		Main.window.setScene(Control.scene);
	}

}