package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.DB;
import classes.Player;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Control implements Initializable{
	@FXML Label one,two,three,four,five,six,seven,eight,nine,player1wins,player2wins,winner;
	@FXML Pane oneP,twoP,threeP,fourP,fiveP,sixP,sevenP,eightP,nineP;
	@FXML GridPane board;
	
	private Player player1=Log.selectedPlayer1,player2=Log.selectedPlayer2;
	private short player1winCount,player2winCount;
	private boolean turn1,moved1,moved2,moved3,moved7,moved8,moved9;
	private Pane selectedP;
	private Label selectedL;
	private Image avatar1,avatar2;
	static Scene scene;
	static Background Winner;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1winCount=0;player2winCount=0;
		player1wins.setText(player1.getName()+" \twon "+player1winCount);
		player2wins.setText(player2.getName()+" \twon "+player2winCount);
		winner.setText(player1.getName()+"'s turn");
		avatar1=new Image("/avatars/"+player1.getAvatar(),333,177,false,true);
		avatar2=new Image("/avatars/"+player2.getAvatar(),333,177,false,true);
		setBack(oneP,true);
		setBack(twoP,true);
		setBack(threeP,true);
		setBack(sevenP,false);
		setBack(eightP,false);
		setBack(nineP,false);
		board.setHgap(10);
		board.setVgap(10);
		board.setStyle("-fx-background-color: azure; -fx-grid-lines-visible:true;");
		click(oneP,one);
		click(twoP,two);
		click(threeP,three);
		click(fourP,four);
		click(fiveP,five);
		click(sixP,six);
		click(sevenP,seven);
		click(eightP,eight);
		click(nineP,nine);
		one.setText("1");
		two.setText("1");
		three.setText("1");
		four.setText("0");
		five.setText("0");
		six.setText("0");
		seven.setText("2");
		eight.setText("2");
		nine.setText("2");
		selectedP=null;
		selectedL=null;
		turn1=true;moved1=false;moved2=false;moved3=false;moved7=false;moved8=false;moved9=false;
	}
	
	private void click(Pane p, Label l) {
		p.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				if(turn1&&l.getText().equals("1")){
					if(selectedL==null&&selectedP==null){
						p.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(10))));
						selectedL=l;
						selectedP=p;
					}else if(selectedP!=p){
						selectedL=l;
						p.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(10))));
						selectedP.setBorder(null);
						selectedP=p;
					}
				}else if(turn1&&selectedL!=null&&!l.getText().equals("2")){
					String id=selectedL.getId();
					if(id.equals("one"))
						moved1=true;
					else if(id.equals("two"))
						moved2=true;
					else if(id.equals("three"))
						moved3=true;
					setBack(p, true);
					l.setText("1");
					selectedL.setText("0");
					selectedP.setBackground(null);
					selectedP.setBorder(null);
					selectedL=null;
					selectedP=null;
					turn1=!turn1;
					checkWinner();
				}else if(!turn1&&l.getText().equals("2")){
					if(selectedL==null&&selectedP==null){
						p.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(10))));
						selectedL=l;
						selectedP=p;
					}else if(selectedP!=p){
						selectedL=l;
						p.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(10))));
						selectedP.setBorder(null);
						selectedP=p;
					}
				}else if(!turn1&&selectedL!=null&&!l.getText().equals("1")){
					String id=selectedL.getId();
					if(id.equals("seven"))
						moved7=true;
					else if(id.equals("eight"))
						moved8=true;
					else if(id.equals("nine"))
						moved9=true;
					setBack(p, false);
					l.setText("2");
					selectedL.setText("0");
					selectedP.setBackground(null);
					selectedP.setBorder(null);
					selectedL=null;
					selectedP=null;
					turn1=!turn1;
					checkWinner();
				}
			}
		});
	}
	
	private void checkWinner() {
		boolean gar=true;
		String[] board={
				one.getText(),
				two.getText(),
				three.getText(),
				four.getText(),
				five.getText(),
				six.getText(),
				seven.getText(),
				eight.getText(),
				nine.getText()
		};
		if(turn1)
			winner.setText(player1.getName()+"'s turn");
		else
			winner.setText(player2.getName()+"'s turn");
		if(gar){
			if(!board[0].equals("0")&&board[0].equals(board[1])&&board[0].equals(board[2])){
				if(board[0].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
				}else if(board[0].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
			}
		}
		
		
		if(gar){
			if(!board[8].equals("0")&&board[8].equals(board[7])&&board[8].equals(board[6])){
				if(board[8].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[8],eightP.getBackground());
						gar=false;
					}
				}else if(board[8].equals("2")){
					if(moved7&&moved8&&moved9){
						winner(board[8],eightP.getBackground());
						gar=false;
					}
				}
			}
		}
		
		if(gar){
			if(!board[0].equals("0")&&board[0].equals(board[4])&&board[0].equals(board[8])){
				if(board[0].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
				}else if(board[0].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
			}
		}
		
		if(gar){
			if(!board[2].equals("0")&&board[2].equals(board[4])&&board[2].equals(board[6])){
				if(board[2].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[2],threeP.getBackground());
						gar=false;
					}
				}else if(board[2].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[2],threeP.getBackground());
						gar=false;
					}
			}
		}
		
		if(gar){
			if(!board[3].equals("0")&&board[3].equals(board[4])&&board[3].equals(board[5])){
				winner(board[3],fourP.getBackground());
				gar=false;
			}
		}
		
		if(gar){
			if(!board[0].equals("0")&&board[0].equals(board[3])&&board[0].equals(board[6])){
				if(board[0].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
				}else if(board[0].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[0],oneP.getBackground());
						gar=false;
					}
			}
		}
		
		if(gar){
			if(!board[1].equals("0")&&board[1].equals(board[4])&&board[1].equals(board[7])){
				if(board[1].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[1],twoP.getBackground());
						gar=false;
					}
				}else if(board[1].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[1],two.getBackground());
						gar=false;
					}
			}
		}
		
		if(gar){
			if(!board[2].equals("0")&&board[2].equals(board[5])&&board[2].equals(board[8])){
				if(board[2].equals("1")){
					if(moved1&&moved2&&moved3){
						winner(board[2],threeP.getBackground());
						gar=false;
					}
				}else if(board[2].equals("2"))
					if(moved7&&moved8&&moved9){
						winner(board[2],threeP.getBackground());
						gar=false;
					}
			}
		}
	}

	private void winner(String string, Background background) {
		Winner=background;
		board.setVisible(false);
		if(string.equals("1")){
			winner.setText(player1.getName()+" won!!");
			player1winCount++;
			player1wins.setText(player1.getName()+" \twon\t"+player1winCount);
			turn1=true;
			DB.editPlayer(player1.getName());
		}else if(string.equals("2")){
			winner.setText(player2.getName()+" won!!");
			player2winCount++;
			player2wins.setText(player2.getName()+" \twon\t"+player2winCount);
			turn1=false;
			DB.editPlayer(player2.getName());
		}
		scene=Main.window.getScene();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/design/winner.fxml"));
			Scene scene = new Scene(root);
			Main.window.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setBack(Pane p, boolean b) {
		Image avatar;
		if(b)
			avatar=avatar1;
		else
			avatar=avatar2;
		 p.setBackground(new Background(new BackgroundImage(avatar,BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true,true, true))));
	}
	
	public void playAgain(Event event) {
		if(turn1)
			winner.setText(player1.getName()+"'s trun");
		else
			winner.setText(player2.getName()+"'s turn");
		setBack(oneP,true);
		setBack(twoP,true);
		setBack(threeP,true);
		setBack(sevenP,false);
		setBack(eightP,false);
		setBack(nineP,false);
		one.setText("1");
		two.setText("1");
		three.setText("1");
		four.setText("0");
		five.setText("0");
		six.setText("0");
		seven.setText("2");
		eight.setText("2");
		nine.setText("2");
		selectedP=null;
		selectedL=null;
		moved1=false;moved2=false;moved3=false;moved7=false;moved8=false;moved9=false;
		oneP.setBorder(null);
		twoP.setBorder(null);
		threeP.setBorder(null);
		fourP.setBorder(null);
		fiveP.setBorder(null);
		sixP.setBorder(null);
		fourP.setBackground(null);
		fiveP.setBackground(null);
		sixP.setBackground(null);
		sevenP.setBorder(null);
		eightP.setBorder(null);
		nineP.setBorder(null);
		board.setVisible(true);
	}
	
	public void back(Event event){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/design/log.fxml"));
			Scene scene = new Scene(root);
			Main.window.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}