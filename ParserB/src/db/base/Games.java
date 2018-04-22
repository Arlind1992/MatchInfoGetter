package db.base;

import java.io.FileNotFoundException;
import java.sql.Connection;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

import db.query.executor.QueryExecutor;

public class Games {
	private String flashcoreID;
	private String homeT;
	private String awayT;
	private String date;
	public Games(String fId,String homeT,String awayT,String date) {
		this.flashcoreID=fId;
		this.homeT=homeT;
		this.awayT=awayT;
		this.date=date;
	}
	public String getFlashcoreID() {
		return flashcoreID;
	}
	public void setFlashcoreID(String flashcoreID) {
		this.flashcoreID = flashcoreID;
	}
	public String getHomeT() {
		return homeT;
	}
	public void setHomeT(String homeT) {
		this.homeT = homeT;
	}
	public String getAwayT() {
		return awayT;
	}
	public void setAwayT(String awayT) {
		this.awayT = awayT;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void insertIntoDB() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("home", this.homeT);
		params.put("away", this.awayT);
		params.put("flashscore_id", this.flashcoreID);
		params.put("date", date);
		QueryExecutor.excecuteUpdate("insertGames", params);
	}
}