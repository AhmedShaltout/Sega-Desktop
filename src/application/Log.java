package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import classes.DB;
import classes.Player;
import classes.TopPlayer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Log implements Initializable{
	
	@FXML PasswordField player2Pass,player1Pass,signUpPass;
	@FXML TextField signUpName;
	@FXML GridPane newAccount,logIn,grid;
	@FXML MenuButton signUpGender,signUpAvatar,player1Name,player2Name;
	@FXML Pane signUpAvatarView,logo;
	private ImageView[]Male,Female;
	private String[]MaleN,FeN;
	private int pic=33;
	public static ArrayList<TopPlayer> topPlayers;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1Pass.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation());
		player2Pass.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation());
		signUpPass.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation());
		signUpName.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation());
		getAvailablePlayers(player1Name);
		getAvailablePlayers(player2Name);
		BackgroundImage bim=new BackgroundImage(new Image("/design/logo4.png",1000,
				640,false,true),BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true,true, true));
		grid.setBackground(new Background(bim));
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Male=new ImageView[pic];
				Female=new ImageView[pic];
				MaleN=new String[pic];
				FeN= new String[pic];
				for(int x=0;x<pic;x++){
					String male,fe;
					if(x<10){
						male="M0"+x+".png";
						fe="F0"+x+".png";
					}else{
						male="M"+x+".png";
						fe="F"+x+".png";
					}
					MaleN[x]=male;
					FeN[x]=fe;
					Male[x]= new ImageView(new Image("/avatars/"+male,100,70,false,true));
					Female[x]= new ImageView(new Image("/avatars/"+fe,100,70,false,true));
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				topPlayers=DB.getTopPlayers();
			}
		}).start();
	}
	
	private EventHandler<KeyEvent>letter_Validation() {
		
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            if(e.getCharacter().matches("[A-Za-z ا-ي0-9+-/*ئ!@#$%^&()-_+]")){ 
	            }else{
	                e.consume();
	            }
	        }
	    };
	}
	
	private static void showInfo(String message) {
    	Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        Label label = new Label(message);
        Button ok=new Button("ok");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
	}
	
	public void toggal(Event event){
		if(logIn.isVisible()){
			logIn.setVisible(false);
			newAccount.setVisible(true);
			((Button)event.getSource()).setText("sign in");
		}else{
			logIn.setVisible(true);
			newAccount.setVisible(false);
			((Button)event.getSource()).setText("New Player?");
		}
	}
	
	public void createAccount(Event event){
		String name=signUpName.getText();
		short gender=-1;
		String genderN=signUpGender.getText();
		if(genderN.equals("Male"))
			gender=0;
		else if(genderN.equals("Female"))
			gender=1;
		String password=signUpPass.getText();
		String avatar="",sAva=signUpAvatar.getText();
		if(!sAva.equals("Select Avatar"))
			avatar=sAva;
		if(gender!=-1&&name.length()>0&&password.length()>0){
			if(!DB.savePlayer(name,password,gender,avatar))
				showInfo("Account exists");
			else{
				showInfo("Done");
				allPlayers=DB.getAllPlayersExcept(null);
				getAvailablePlayers(player1Name);
				getAvailablePlayers(player2Name);
			}
			signUpName.setText("");
			signUpGender.setText("Gender");
			signUpPass.setText("");
			signUpAvatar.setText("Select Avatar");
			signUpAvatarView.setBackground(null);
		}
		else
			showInfo("Please fill all required fields");
		
	}
	
	public void genderChange(Event event){
		String gender=((MenuItem)event.getSource()).getText();
		signUpGender.setText(gender);
		signUpAvatar.setText("Select Avatar");
		signUpAvatarView.setBackground(null);
		signUpAvatar.getItems().removeAll(signUpAvatar.getItems());
		ImageView[] sPic;
		String[] sN;
		if(gender.equals("Male")){
			sPic=Male;
			sN=MaleN;
		}
		else{
			sPic=Female;
			sN=FeN;
		}
		for (int x=0;x<pic;x++) {
			MenuItem MI=new MenuItem(sN[x],sPic[x]);
			MI.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					signUpAvatar.setText(MI.getText());
				}
			});
			signUpAvatar.getItems().add(MI);
		}
	}
	private static ArrayList<Player>allPlayers=DB.getAllPlayersExcept(null);
	public static Player selectedPlayer1,selectedPlayer2;
	private void getAvailablePlayers(MenuButton button){
		ArrayList<Player>players=new ArrayList<>();
		if(button.getId().equals(player1Name.getId())){
			players=DB.getAllPlayersExcept(player2Name.getText());
		}else{
			players=DB.getAllPlayersExcept(player1Name.getText());
		}
		button.getItems().removeAll(button.getItems());
		for (Player player: players) {
			MenuItem mi=new MenuItem(player.getName());
			mi.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					button.setText(mi.getText());
					if(button.getId().equals(player1Name.getId())){
						getAvailablePlayers(player2Name);
						for (Player player1 : allPlayers) {
							if(player1.getName().equals(button.getText()))
								selectedPlayer1=player1;
						}
					}else{
						for (Player player2 : allPlayers) {
							if(player2.getName().equals(button.getText()))
								selectedPlayer2=player2;
						}
						getAvailablePlayers(player1Name);
					}
				}
			});
			button.getItems().add(mi);
		}
	}
	
	public void signIn1(Event event){
		if(!((Button)event.getSource()).getText().equals("sign out")){
			if(player1Pass.getText().equals(selectedPlayer1.getPassword())){
				((Button)event.getSource()).setText("sign out");
				player1Pass.setText("");
				player1Name.setDisable(true);
				player1Pass.setVisible(false);
			}else{
				showInfo("Incorrect password");
				player1Pass.setText("");
			}
		}else{
			((Button)event.getSource()).setText("sign in");
			player1Name.setDisable(false);
			player1Pass.setVisible(true);
		}
			
	}
	
	public void signIn2(Event event){
		if(!((Button)event.getSource()).getText().equals("sign out")){
			if(player2Pass.getText().equals(selectedPlayer2.getPassword())){
				((Button)event.getSource()).setText("sign out");
				player1Pass.setText("");
				player2Name.setDisable(true);
				player2Pass.setVisible(false);
			}else{
				showInfo("incorrect password");
				player1Pass.setText("");
			}
		}else{
			((Button)event.getSource()).setText("sign in");
			player2Name.setDisable(false);
			player2Pass.setVisible(true);
		}
	}
	
	public void startGame(Event event){
		if(selectedPlayer1!=null && selectedPlayer2!=null){
			try {
				Parent root =FXMLLoader.load(getClass().getResource("/design/board.fxml"));
				Scene scene = new Scene(root);
				Main.window.setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			showInfo("Please login first");
		}
	}
	
	public void topPlayers(Event event){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/design/top.fxml"));
			Scene scene = new Scene(root);
			Main.window.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
