package classes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class BaseCreator {
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

	public static boolean exists() {
		if((new File("C:\\SegaData\\sega.db")).exists())
			return true;
		new File("C:\\SegaData").mkdir();
		return false;
	}

	public static void createSystem() {
		editDataBase("CREATE TABLE `Players` ( "
				+ "`name` TEXT NOT NULL, "
				+ "`password` TEXT NOT NULL, "
				+ "`gender` INTEGER NOT NULL, "
				+ "`score` INTEGER DEFAULT 0, "
				+ "`avatar` TEXT NOT NULL, "
				+ "PRIMARY KEY(`name`) )");
	}
}
