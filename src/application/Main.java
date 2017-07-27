package application;
	
import classes.BaseCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage window;
	public static Background winnerBackground;
	@Override
	public void start(Stage primaryStage) {
		winnerBackground=new Background(new BackgroundImage(new Image("/design/win2.gif",1000,463,false,true),BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true,true, true)));
		try {
			if((!BaseCreator.exists()))
				BaseCreator.createSystem();
			window=primaryStage;
			Parent root =FXMLLoader.load(getClass().getResource("/design/log.fxml"));
			Scene scene = new Scene(root);
			window.setTitle("Sega");
			window.getIcons().add(new Image("/design/logo.png"));
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
