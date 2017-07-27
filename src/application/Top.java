package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.DB;
import classes.TopPlayer;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Top implements Initializable{
	@FXML TableView<TopPlayer> top;
	@FXML TableColumn<TopPlayer, Pane>avatar;
	@FXML TableColumn<TopPlayer, String>name;
	@FXML TableColumn<TopPlayer, Integer>score;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		avatar.setCellValueFactory(new PropertyValueFactory<TopPlayer,Pane>("avatar"));
		avatar.setStyle("-fx-alignment: CENTER;-fx-font-size:25px;-fx-font-family:serif;");
		name.setCellValueFactory(new PropertyValueFactory<TopPlayer,String>("name"));
		name.setStyle("-fx-alignment: CENTER;-fx-font-size:25px;-fx-font-family:serif;");
		score.setCellValueFactory(new PropertyValueFactory<TopPlayer,Integer>("score"));
		score.setStyle("-fx-alignment: CENTER;-fx-font-size:25px;-fx-font-family:serif;");
		top.setItems(FXCollections.observableList(Log.topPlayers));
	}
	
	public void back(Event event){
		goBack();
	}
	
	private void goBack() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/design/log.fxml"));
			Scene scene = new Scene(root);
			Main.window.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reset(Event event){
		if(DB.reset()){
			goBack();
		}
	}

}
