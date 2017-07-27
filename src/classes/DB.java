package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public abstract class DB {
	private static Connection con;
	private static void getConnection(){
		try{
			con= DriverManager.getConnection("jdbc:sqlite:C:\\SegaData\\sega.db");
		}
		catch(Exception ex){
			
		}
	}
	
	private static void closeCon(){
		try {
			con.close();
		} catch (Exception e) {
		}
	}
	
	/**==========================================================================================**/
	
	private static ResultSet select(String sql){
		try{
			getConnection();
			return con.createStatement().executeQuery(sql);
		}catch (Exception  e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private static boolean editDataBase(String sql){
		try {
			getConnection();
			con.createStatement().execute(sql);
			closeCon();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean savePlayer(String name, String password, short gender, String avatar) {
		if(avatar.isEmpty()){
			if(gender==0)
				avatar="M00.png";
			else
				avatar="F00.png";
		}
		return editDataBase("insert into Players(name,password,gender,avatar) values('"+name+"','"+password+"',"+gender+",'"+avatar+"')");
	}

	public static ArrayList<Player> getAllPlayersExcept(String name) {
		ResultSet resultSet=select("select * from Players where Players.name != '"+name+"'");
		ArrayList<Player>players=new ArrayList<>();
		try {
			while (resultSet.next()) {
				Player player;
				if((player=createPlayer(resultSet))!=null)
					players.add(player);
			}
		} catch (Exception e) {
		}finally {
			closeCon();
		}
		return players;
	}

	private static Player createPlayer(ResultSet resultSet) {
		try {
			return new Player(resultSet.getString(1),resultSet.getString(2),resultSet.getShort(3),resultSet.getInt(4),resultSet.getString(5));
		} catch (Exception e) {return null;}
	}

	public static void editPlayer(String name) {
		editDataBase("update Players set score =score + 1 where Players.name='"+name+"'");
	}

	public static ArrayList<TopPlayer> getTopPlayers() {
		ResultSet resultSet=select("select Players.name,Players.score,Players.avatar from Players where score>0 order by score desc");
		ArrayList<TopPlayer>topPlayers=new ArrayList<>();
		try {
			while(resultSet.next()){
				TopPlayer player;
				if((player=createTopPlayer(resultSet))!=null)
					topPlayers.add(player);
			}
		} catch (Exception e) {
		}finally {
			closeCon();
		}
		return topPlayers;
	}

	private static TopPlayer createTopPlayer(ResultSet resultSet) {
		try{
			Pane pane=new Pane();
			pane.setMinWidth(334);
			pane.setMinHeight(177);
			BackgroundImage bim=new BackgroundImage(new Image("/avatars/"+resultSet.getString(3),334,
					177,false,true),BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true,true, true));
			pane.setBackground(new Background(bim));
			return new TopPlayer(pane,resultSet.getString(1), resultSet.getInt(2));
		}catch(Exception e){
			return null;
		}
	}

	public static boolean reset() {
		return editDataBase("update Players set score = 0 ");
	}

}
